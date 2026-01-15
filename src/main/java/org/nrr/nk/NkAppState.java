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

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.ViewPort;

import org.lwjgl.nuklear.NkContext;

/**
 * Class responsible for managing a {@code BaseAppState} for GUI with Nuklear.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class NkAppState extends BaseAppState implements NkRender {

    /**
     * {@code true} if the Nuklear components have been initialized, otherwise 
     * its value is {@code false}
     */
    private boolean nkuiInit;
    
    /**
     * If its value is {@code true}, this NkAppState will attempt to add itself
     * to the Nuklear context.
     */
    private boolean auto;

    /**
     * Constructor of the class <code>NkAppState</code>.
     * 
     * @param auto boolean
     */
    public NkAppState(boolean auto) {
        this(auto, null);
    }

    /**
     * Constructor of the class <code>NkAppState</code>.
     * 
     * @param auto boolean
     * @param id string
     */
    public NkAppState(boolean auto, String id) {
        super(id);
        this.auto = auto;
    }

    /*(non-Javadoc)
     */
    @Override
    protected void initialize(Application app) {
        preInit(app);
        if (auto) {
            if (!setupNkDisplay(app.getGuiViewPort())) {
                NkDisplay display = new NkDisplay(app.getAssetManager(), app.getInputManager(), app.getRenderManager());
                display.addNkRender(this);
                app.getGuiViewPort().addProcessor(display);
            }            
        }
        nkuiInit = false;
    }
    
    /**
     * Check if {@code NkDisplay} exists in the viewport; if so, add this renderer.
     * 
     * @param viewPort ViewPort
     * @return {@code true} if the context exists, otherwise {@code false}
     */
    private boolean setupNkDisplay(ViewPort viewPort) {
        for (SceneProcessor sp : viewPort.getProcessors()) {
            if (sp instanceof NkDisplay display) {
                display.addNkRender(this);
                return true;
            }
        }
        return false;
    }

    /*(non-Javadoc)
     */
    @Override
    public final void handle(NkContext ctx, NkDisplay display, float tpf) {
        if (!isInitialized()) {
            return;
        }
        
        if (!nkuiInit) {
            postInit(ctx, display);
            nkuiInit = true;
        } else {
            if (isEnabled()) {
                render(ctx, display);
            }
        }
    }
    
    /**
     * Pre-initializes all components, ideal for creating objects that require
     * only one instance.
     * 
     * @param app Application
     */
    protected abstract void preInit(Application app);
    
    /**
     * Initialize all components afterward, ideal for creating objects that require
     * a single instance and need a context {@code Nuklear}.
     * 
     * @param ctx NkContext
     * @param display NkDisplay
     */
    protected abstract void postInit(NkContext ctx, NkDisplay display);
    
    /**
     * This is where all the user interface components of {@code Nuklear} are represented.
     * 
     * @param ctx NkContext
     * @param display NkDisplay
     */
    protected abstract void render(NkContext ctx, NkDisplay display);

    /**
     * Returns whether this renderer was added automatically.
     * 
     * @return boolean
     */
    public boolean isAuto() {
        return auto;
    }

    /**
     * Returns {@code true} if the graphical interface components have been initialized.
     * 
     * @see #postInit(org.lwjgl.nuklear.NkContext, org.nrr.nk.NkDisplay) 
     
     * @return boolean
     */
    public boolean isNkUIInit() {
        return nkuiInit;
    }
}
