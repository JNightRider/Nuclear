/*
BSD 3-Clause License

Copyright (c) 2026, Night Rider

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.nrr.nk;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.VertexBuffer;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import com.jme3.util.IntMap;

import org.lwjgl.nuklear.*;
import org.lwjgl.system.*;

import org.nrr.nk.system.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Class responsible for implementing the Nuklear backend with JM3.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public final class NkDisplay implements SceneProcessor {
    
    /** initial buffer amount. */
    private static final int BUFFER_INITIAL_SIZE = 4 * 1024;

    /** Default vertex buffer size. */
    private static final int MAX_VERTEX_BUFFER  = 512 * 1024;
    /** Default elements buffer size. */
    private static final int MAX_ELEMENT_BUFFER = 128 * 1024;
    /** Number of vertices per segment. */
    private static final int MAX_VERTEX_SIZE    = 20;

    //===--------------------------------------------------------------------===
    //                      Nuklear dynamic memory
    //===--------------------------------------------------------------------===
    /** memory manager. */
    private static final NkAllocator ALLOCATOR;
    /** Vertex buffer configuration. */
    private static final NkDrawVertexLayoutElement.Buffer VERTEX_LAYOUT;

    static {
        ALLOCATOR = NkAllocator.create()
            .alloc((handle, old, size) -> nmemAllocChecked(size))
            .mfree((handle, ptr) -> nmemFree(ptr));

        VERTEX_LAYOUT = NkDrawVertexLayoutElement.create(4)
            .position(0).attribute(NK_VERTEX_POSITION).format(NK_FORMAT_FLOAT).offset(0)
            .position(1).attribute(NK_VERTEX_TEXCOORD).format(NK_FORMAT_FLOAT).offset(8)
            .position(2).attribute(NK_VERTEX_COLOR).format(NK_FORMAT_R8G8B8A8).offset(16)
            .position(3).attribute(NK_VERTEX_ATTRIBUTE_COUNT).format(NK_FORMAT_COUNT).offset(0)
            .flip();
    }
    
    //===--------------------------------------------------------------------===
    //                          Backend - Nuklear
    //===--------------------------------------------------------------------===
    /** Context of nuklear. */
    private final NkContext ctx  = NkContext.create();
    /** Command buffer. */
    private final NkBuffer  cmds = NkBuffer.create();
    /** Null texture used by nuklea by default. */
    private final NkDrawNullTexture nullTexture = NkDrawNullTexture.create();
    
    /**
     * List of designs where the user interface components are located, ready to
     * nukleate the processes in each cycle.
     */
    private final List<NkRender> renders    = new ArrayList<>();
    /** Texture map that Nuklear uses for graphing within the backend. */
    private final IntMap<Texture> textures  = new IntMap<>();
    
    /** Input manager for nuklear. */
    private NkInputListener inputSys;
    /** Nuclear dynamic clipboard. */
    private NkClipboardHandler clipboard;
    /** Default font. */
    private NkBitmapFontHandler fontHandler;
    /** Loaded theme. */
    private NkThemeHandler themeHandler;
    
    //===--------------------------------------------------------------------===
    //                          Backend - Render
    //===--------------------------------------------------------------------===
    /** Vertex buffer. */
    private ByteBuffer vertices;
    /** Elements buffer. */
    private ByteBuffer elements;
    
    /**
     * Dedicated buffer grouping for drawing elements.
     */
    private final BufferPool<ShortBuffer> indexPool = new BufferPool<>(ShortBuffer.class);
    /**
     * Grouping of vertices by drawing segment
     */
    private final ObjectPool<Vertex> vertexPool = new VertexPool();
    
    /** Drawing geometry. */
    private NkGeometry drawable;
    /** Drawing view. */
    private ViewPort viewPort;
    
    /** Buffer configuration. */
    private BufferConfig bufferConfig;
    /** Rendering Manager. */
    private RenderManager renderManager;
    /** Asset Manager. */
    private final AssetManager assetManager;
    /** Input Manager */
    private final InputManager inputManager;
    
    /** Window width */
    private float width  = 1;
    /** Height width*/
    private float height = 1;
    /** Scale in the x coordinate*/
    private float xScale = 1;
    /** Scale in the y coordinate*/
    private float yScale = 1;

    /**
     * Flag indicating whether the {@code Nucklear} context has been initialized.
     */
    private boolean initialized;
    
    /**
     * Generate a new backend for Nuklear using the <code>NkDisplay</code> class.
     * 
     * @param assetManager AssetManager
     * @param inputManager InputManager
     * @param renderManager RenderManager
     */
    public NkDisplay(AssetManager assetManager, InputManager inputManager, RenderManager renderManager) {
        this(assetManager, inputManager, renderManager, BufferConfig.builder()
                                                                           .maxElementBuffer(MAX_ELEMENT_BUFFER)
                                                                           .maxVertexBuffer(MAX_VERTEX_BUFFER));
    }
    
    /**
     * Generate a new backend for Nuklear using the <code>NkDisplay</code> class.
     * 
     * @param assetManager AssetManager
     * @param inputManager InputManager
     * @param renderManager RenderManager
     * @param bufferConfig BufferConfig
     */
    public NkDisplay(AssetManager assetManager, InputManager inputManager, RenderManager renderManager, BufferConfig bufferConfig) {
        this.assetManager  = assetManager;
        this.inputManager  = inputManager;
        this.renderManager = renderManager;
        this.bufferConfig  = bufferConfig;
        this.clipboard     = NkDefaultClipboardHandler.getInstance();
    }
    
    /*(non-Javadoc)
     */
    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        inputSys = new NkInputListener(inputManager);
        drawable = new NkGeometry(assetManager, false);
        if (inputManager != null) {
            inputManager.addRawInputListener(inputSys);
        }
        
        width         = vp.getCamera().getWidth();
        height        = vp.getCamera().getHeight();
        viewPort      = vp;
        renderManager = rm;
        
        inputSys.setContext(ctx);     
        inputSys.setHeight((int) height);
        inputSys.reset();

        nk_init(ctx, ALLOCATOR, null);
        ctx.clip()
            .copy((handle, text, len) -> {
                clipboard.copy(text, len);
            })
            .paste((handle, edit) -> {
                clipboard.paste(edit);
            });        
        nk_buffer_init(cmds, ALLOCATOR, BUFFER_INITIAL_SIZE);

        /* Configure the null texture, preloading a geometry into the renderer 
         * to obtain the texture ID.
         */
        NkGeometry nktmp = new NkGeometry(assetManager, true);
        rm.preloadScene(nktmp);
        
        Texture nullTex = nktmp.getMaterial()
                            .getTextureParam("ColorMap")
                            .getTextureValue();
        
        int nullTexID = nullTex.getImage().getId();        
        textures.put(nullTexID, nullTex);
        
        nullTexture.texture().id(nullTexID);
        nullTexture.uv().set(0.5f, 0.5f);
        
        /* Set the text font as well as the theme of the user interface
         * components.
         */
        if (fontHandler == null) {
            fontHandler = NkBitmapFontHandler.builder()
                                .display(this)
                                .assetManager(assetManager)
                                .font("Interface/Fonts/Default.fnt")
                                .size(14)
                                .build();
        }
        applyDefaultStyleFont(null, fontHandler);
        if (themeHandler != null) {
            applyStyleTheme(themeHandler);
        }
        
        initialized   = true;
    }
    
    /**
     * Sets a new default font for the components.
     *
     * @param font NkBitmapFontHandler
     */
    public void setDefaultStyleFont(NkBitmapFontHandler font) {
        if (fontHandler == font) {
            return;
        }
        if (isInitialized()) {
            applyDefaultStyleFont(fontHandler, font);
        }
        fontHandler = font;
    }
    
    /**
     * Apply a new default font to the components
     * 
     * @param currentFont NkBitmapFontHandler
     * @param newFont NkBitmapFontHandler
     */
    private void applyDefaultStyleFont(NkBitmapFontHandler currentFont, NkBitmapFontHandler newFont) {
        if (currentFont != null) {
            NkUserFont userFont = currentFont.userFont();
            NkQueryFontGlyphCallback queryFontGlyphCallback  = userFont.query();
            if (queryFontGlyphCallback != null) {
                queryFontGlyphCallback.free();
            }

            NkTextWidthCallback widthCallback = userFont.width();
            if (widthCallback != null) {
                widthCallback.free();
            }
            removeTexture(userFont.texture().id());
        }
        nk_style_set_font(ctx, newFont.userFont());
    }

    /**
     * Set a new color theme.
     *
     * @param theme Theme
     */
    public void setStyle(NkThemeHandler.Theme theme) {
        if (theme == null || theme == NkThemeHandler.Theme.THEME_BLACK) {
            nk_style_default(ctx);
        } else {
            setStyle(NkThemeHandler.getInstance(theme));
        }
    }
    
    /**
     * Set a new color theme.
     *
     * @param themeHandler NkThemeHandler
     */
    public void setStyle(NkThemeHandler themeHandler) {
        if (isInitialized()) {
            applyStyleTheme(themeHandler);
        }
        this.themeHandler = themeHandler;
    }
    
    /**
     * Apply a new color theme.
     * 
     * @param themeHandler NkThemeHandler
     */
    private void applyStyleTheme(NkThemeHandler themeHandler) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            int size = NkColor.SIZEOF * NK_COLOR_COUNT;
            ByteBuffer buffer = stack.calloc(size);
            NkColor.Buffer colors = new NkColor.Buffer(buffer);
            
            themeHandler.setupColors(colors, stack);
            nk_style_from_table(ctx, colors);
        }
    }
    
    /**
     * Add a new texture to the map so that the backend has it referenced.
     * 
     * @param texture Texture
     * @return boolean
     */
    public boolean addTexture(Texture texture) {
        return addTexture(texture.getImage().getId(), texture);
    }
    
    /**
     * Add a new texture to the map so that the backend has it referenced.
     * 
     * @param id int
     * @param texture Texture
     * @return boolean
     */
    public boolean addTexture(int id, Texture texture) {
        if (!this.textures.containsKey(id)) {
            this.textures.put(id, texture);
            return true;
        }
        return false;
    }
    
    /**
     * Remove a reference in the texture map.
     *
     * @param id id
     */
    public void removeTexture(int id) {
        this.textures.remove(id);
    }
    
    /**
     * Remove a reference in the texture map.
     * 
     * @param texture Texture
     */
    public void removeTexture(Texture texture) {
        if (texture == null) {
            return;
        }
        Image image = texture.getImage();
        if (image == null) {
            return;
        }
        int textureId = image.getId();
        if (textureId == -1) {
            for (IntMap.Entry<Texture> next : this.textures) {
                if (texture.equals(next.getValue())) {
                    this.textures.remove(next.getKey());
                    break;
                }
            }
        } else {
            this.textures.remove(textureId);
        }
    }
    
    /**
     * Add a new renderer.
     * 
     * @param render NkRender
     */
    public void addNkRender(NkRender render) {
        if (this.renders.contains(render)) {
            throw new IllegalStateException("The rendering driver already exists");
        }
        this.renders.add(render);
    }

    /**
     * Remove a renderer.
     * 
     * @param render NkRender
     * @return boolean
     */
    public boolean removeNkRender(NkRender render) {
        return this.renders.remove(render);
    }
    
    /**
     * Remove a renderer.
     * 
     * @param <T> NkRender
     * @param clazz class
     * @return NkRender
     */
    @SuppressWarnings("unchecked")
    public <T extends NkRender> T getNkRender(Class<T> clazz) {
        for (NkRender render : this.renders) {
            if (clazz.isAssignableFrom(render.getClass())) {
                return (T) render;
            }
        }
        return null;
    }
    
    /**
     * Set a new clipboard manager
     * 
     * @see org.nrr.nk.NkDefaultClipboardHandler
     * @see org.nrr.nk.awt.NkToolkitHandler
     * 
     * @param clipboard NkClipboardHandler
     */
    public void setClipboard(NkClipboardHandler clipboard) {
        if (this.clipboard != null) {
            this.clipboard.free();
        }
        this.clipboard = clipboard;
    }

    /*(non-Javadoc)
     */
    @Override
    public void reshape(ViewPort vp, int w, int h) {
        inputSys.setHeight(h);
        viewPort = vp;
        width    = w;
        height   = h;
    }

    /*(non-Javadoc)
     */
    @Override
    public void rescale(ViewPort vp, float x, float y) {
        xScale = x;
        yScale = y;
    }

    /*(non-Javadoc)
     */
    @Override
    public boolean isInitialized() {
        return initialized;
    }

    /*(non-Javadoc)
     */
    @Override
    public void preFrame(float tpf) {
        for (final NkRender nrk : renders) {
            nrk.handle(ctx, this, tpf);
        }
    }

    /*(non-Javadoc)
     */
    @Override
    public void postQueue(RenderQueue rq) {        
        Camera camera = viewPort.getCamera(); 
        renderManager.setCamera(camera, true);
        render(NK_ANTI_ALIASING_ON);        
        renderManager.setCamera(camera, false);
    }
    
    /**
     * Method responsible for reading the drawing buffers provided by Nuklear,
     * where they are broken down into the buffers required by JME3 to draw a
     * geometry.
     * 
     * @param AA int
     */
    private void render(int AA) {
        checkNkBuffers();        
        try (MemoryStack stack = stackPush()) {
            // fill convert configuration
            NkConvertConfig config = NkConvertConfig.calloc(stack)
                .vertex_layout(VERTEX_LAYOUT)
                .vertex_size(MAX_VERTEX_SIZE)
                .vertex_alignment(4)
                .tex_null(nullTexture)
                .circle_segment_count(22)
                .curve_segment_count(22)
                .arc_segment_count(22)
                .global_alpha(1.0f)
                .shape_AA(AA)
                .line_AA(AA);

            // setup buffers to load vertices and elements
            NkBuffer vbuf = NkBuffer.malloc(stack);
            NkBuffer ebuf = NkBuffer.malloc(stack);

            nk_buffer_init_fixed(vbuf, vertices/*, max_vertex_buffer*/);
            nk_buffer_init_fixed(ebuf, elements/*, max_element_buffer*/);
            nk_convert(ctx, cmds, vbuf, ebuf, config);
        }
        
        ShortBuffer elebuff = elements.asShortBuffer();
        Renderer renderer   = renderManager.getRenderer();
        
        NkMesh mesh = (NkMesh) drawable.getMesh();
        
        int offset = 0;
        for (NkDrawCommand cmd = nk__draw_begin(ctx, cmds); cmd != null; cmd = nk__draw_next(cmd, cmds, ctx)) {
            if (cmd.elem_count() == 0) {
                continue;
            }
            
            ShortBuffer index = indexPool.takeOut(cmd.elem_count());
            index.clear();
            
            // obtain and manage the elements
            int min = Integer.MAX_VALUE;
            int max = 0;
            for (int i = 0; i < cmd.elem_count(); i++) {
                short idx = elebuff.get(offset + i);
                index.put(idx);
                
                int v = idx & 0xFFFF;
                min = Math.min(min, v);
                max = Math.max(max, v);
            }
            index.flip();
            
            for (int i = 0; i < index.limit(); i++) {
                index.put(i, (short)(index.get(i) - min));
            }
            
            // Update the vertices
            int vertexCount = max - min + 1;
            Vertex vertex = vertexPool.takeOut(vertexCount)
                                    .clear()
                                    .min(min)
                                    .height(height);
            
            vertex.make(vertices, MAX_VERTEX_SIZE);        
            mesh.updateBuffer(VertexBuffer.Type.Position, vertex.getPosition(), 2)
                .updateBuffer(VertexBuffer.Type.TexCoord, vertex.getTexture(), 2)
                .updateBuffer(VertexBuffer.Type.Color, vertex.getColor(), 4)
                .updateBuffer(VertexBuffer.Type.Index, index, 3);
            
            renderer.setClipRect(
                (int)(cmd.clip_rect().x() * xScale),
                (int)((height - (int)(cmd.clip_rect().y() + cmd.clip_rect().h())) * yScale),
                (int)(cmd.clip_rect().w() * xScale),
                (int)(cmd.clip_rect().h() * yScale)
            );
            
            // draw geometry
            drawable.getMaterial()
                    .setTexture("ColorMap", textures.get(cmd.texture().id()));
            renderManager.renderGeometry(drawable);

            indexPool.takeIn(index);
            vertexPool.takeIn(vertex);
            offset += cmd.elem_count();
        }

        renderer.clearClipRect();
        
        nk_clear(ctx);
        nk_buffer_clear(cmds);
    }

    /**
     * Method responsible for cleaning and verifying the size of the read buffers.
     */
    private void checkNkBuffers() {
        if (bufferConfig.needsUpdate()) {
            if (vertices != null) {
                BufferUtils.destroyDirectBuffer(vertices);
            }
            if (elements != null) {
                BufferUtils.destroyDirectBuffer(elements);
            }
            vertices = BufferUtils.createByteBuffer(bufferConfig.getMaxVertexBuffer());
            elements = BufferUtils.createByteBuffer(bufferConfig.getMaxElementBuffer());
            bufferConfig.managedUpdate();
        } else {
            vertices.clear();
            elements.clear();
        }
    }

    /*(non-Javadoc)
     */
    @Override
    public void postFrame(FrameBuffer out) {
        
    }

    /*(non-Javadoc)
     */
    @Override
    public void cleanup() {
        inputSys.reset();
        if (inputManager != null) {
            inputManager.removeRawInputListener(inputSys);
        }
        vertices.clear();
        elements.clear();
        initialized = false;
    }

    /*(non-Javadoc)
     */
    @Override
    public void setProfiler(AppProfiler profiler) {
        
    }

    /**
     * Destruye toda la interfaz Nuklear.
     *
     * @param allocator {@code true} if the memory managers are destroyed (they 
     * are only created once).
     */
    public void destroy(boolean allocator) {
        NkPluginCopy pluginCopy = ctx.clip().copy();
        if (pluginCopy != null) {
            pluginCopy.free();
        }
        
        NkPluginPaste pluginPaste = ctx.clip().paste();
        if (pluginPaste != null) {
            pluginPaste.free();
        }
        
        nk_free(ctx);
        
        NkUserFont userFont = fontHandler.userFont();
        if (userFont != null) {
            NkQueryFontGlyphCallback queryFontGlyphCallback  = userFont.query();
            if (queryFontGlyphCallback != null) {
                queryFontGlyphCallback.free();
            }

            NkTextWidthCallback widthCallback = userFont.width();
            if (widthCallback != null) {
                widthCallback.free();
            }
        }
        
        if (allocator) {
            NkPluginAlloc pluginAlloc = ALLOCATOR.alloc();
            if (pluginAlloc != null) {
                pluginAlloc.free();
            }

            NkPluginFree pluginFree = ALLOCATOR.mfree();
            if (pluginFree != null) {
                pluginFree.free();
            }
        }
    }
    
    /**
     * Returns the value of the attribute: width
     * @return float
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the value of the attribute: height
     * @return float
     */
    public float getHeight() {
        return height;
    }

    /**
     * Returns the value of the attribute: xScale
     * @return float
     */
    public float getXScale() {
        return xScale;
    }

    /**
     * Returns the value of the attribute: yScale
     * @return float
     */
    public float getYScale() {
        return yScale;
    }
    
    /**
     * Returns the Nuklear context; if used outside of a dedicated renderer, checks 
     * if it has been initialized using the {@code isInitialized()} method.
     * <pre><code>
     *   public void simpleUpdate(float tpf) {
     *       if (display.isInitialized()) {
     *           NkContext context = display.getContext();
     *           ...
     *       }
     *   }
     * </code></pre>
     * 
     * @return NkContext
     */
    public NkContext getContext() {        
        return ctx;
    }  

    /**
     * Returns the value of the attribute: renderManager
     * @return RenderManager
     */
    public RenderManager getRenderManager() {
        return renderManager;
    }

    /**
     * Returns the value of the attribute: assetManager
     * @return AssetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    } 
}
