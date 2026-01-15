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

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.util.NativeObject;

import java.util.function.Consumer;

import org.lwjgl.nuklear.NkHandle;

/**
 * An interface designed to manage and configure a handler that needs a texture.
 *
 * @param <T> type of texture
 *
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NkTextureHandler<T extends Texture> extends Consumer<NkHandle> {

    /**
     * Handler builder.
     * 
     * @param <E> type of texture
     *
     * @return Builder
     */
    public static <E extends Texture> Builder<E> builder() {
        return new Builder<>();
    }
    
    /*(non-Javadoc)
     */
    @Override
    public default void accept(NkHandle it) {
        T texture = handle();
        if (texture == null) {
            return;
        }
        
        Image image = texture.getImage();
        if (image == null) {
            return;
        }
        
        it.id(image.getId());
    }
    
    /**
     * Returns the handler value
     * @return value
     */
    public T handle();
    
    /**
     * Class responsible for managing the builder of the {@code NkTextureHandler}
     * interface.
     * 
     * @param <E> type of texture
     */
    public static class Builder<E extends Texture> {
        
        /** handler value */
        private E texture;
        /** The renderer of the graphical interface components */
        private NkDisplay display;
        
        /** Constructor of the class <code>Builder</code>. */
        private Builder() {}
        
        /**
         * Sets the {@code Texture|?} attribute.
         *
         * @param texture Texture
         * @return Builder
         */
        public Builder handle(E texture) {
            this.texture = texture;
            return this;
        }

        /**
         * Sets the {@code NkDisplay} attribute.
         *
         * @param display NkDisplay
         * @return Builder
         */
        public Builder display(NkDisplay display) {
            this.display = display;
            return this;
        }
        
        /**
         * Check if the texture is preloaded.
         * 
         * @return boolean
         */
        private boolean check() {
            if (texture == null) {
                return false;
            }
            Image image = texture.getImage();
            if (image == null) {
                return false;
            }
            return image.getId() == NativeObject.INVALID_ID;
        }
        
        /**
         * Preload the texture using a temporary geometry.
         */
        private void init() {
            Geometry drawable = new Geometry("nk_drawable", new Quad(1, 1));
            Material material = new Material(display.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            material.setTexture("ColorMap", texture);
            drawable.setMaterial(material);
            display.getRenderManager().preloadScene(drawable);
            display.addTexture(texture);
        }
        
        /**
         * Build the {@code Consumer<NkHandle>} controller that {@code Nuklear}
         * will use.
         * 
         * @return NkTextureHandler
         */
        public NkTextureHandler<E> build() {
            if (check()) {
                init();
            }            
            if (display == null) {
                throw new NullPointerException("The controller cannot be started without NkDisplay");
            }
            return () -> texture;
        }
    }
}
