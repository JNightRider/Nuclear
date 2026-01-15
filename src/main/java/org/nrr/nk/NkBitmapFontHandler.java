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
import com.jme3.font.BitmapCharacter;
import com.jme3.font.BitmapCharacterSet;
import com.jme3.font.BitmapFont;

import org.lwjgl.nuklear.NkUserFont;
import org.lwjgl.nuklear.NkUserFontGlyph;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * An interface that handles a source for Nuklear.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NkBitmapFontHandler {
    
    /**
     * Handler builder.
     * 
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }
   
    /**
     * Returns a {@code NkUserFont} configured with a {@code BitmapFont}.
     *
     * @return NkUserFont
     */
    NkUserFont userFont();
    
    /**
     * Class responsible for managing the builder of the {@code NkBitmapFontHandler}
     * interface.
     */
    public static class Builder {
        
        /**
         * The font that will be used to create the text font controller for
         * {@code Nuklear}.
         */
        private BitmapFont font;
        /**asset manager.*/
        private AssetManager assetManager;
        
        /** The renderer of the graphical interface components */
        private NkDisplay display;
        /**  The new font */
        private NkUserFont userFont;
        
        /**
         * The page number that will be used to create the new font.
         */
        private int page;
        
        /**
         * If no font size is specified, the default size of the loaded font wil
         * be used.
         */
        private float size = -1;
        /** The scale is based on the font size. */
        private float scale = 1.0f;
        
        /** Constructor of the class <code>Builder</code>. */
        private Builder() {}
        
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
         * Sets the {@code BitmapFont} attribute.
         *
         * @param font BitmapFont
         * @return Builder
         */
        public Builder font(BitmapFont font) {
            this.font = font;
            return this;
        }
        
        /**
         * Sets the {@code BitmapFont|String} attribute.
         *
         * @param pathname string
         * @return Builder
         */
        public Builder font(String pathname) {
            return font(assetManager.loadFont(pathname));
        }

        /**
         * Sets the {@code AssetManagerg} attribute.
         *
         * @param assetManager AssetManager
         * @return Builder
         */
        public Builder assetManager(AssetManager assetManager) {
            this.assetManager = assetManager;
            return this;
        }

        /**
         * Sets the {@code page|int} attribute.
         *
         * @param page int
         * @return Builder
         */
        public Builder page(int page) {
            this.page = page;
            return this;
        }

        /**
         * Sets the {@code size|float} attribute.
         *
         * @param size float
         * @return Builder
         */
        public Builder size(float size) {
            this.size = size;
            if (userFont != null) {
                userFont.height(font.getCharSet().getLineHeight() * scale);
            }
            return this;
        }
        
        /**
         * Build the {@code NkBitmapFontHandler} controller that {@code Nuklear}
         * will use.
         * 
         * @return NkBitmapFontHandler
         */
        @SuppressWarnings("unchecked")
        public NkBitmapFontHandler build() {
            if (size == -1) {
                size = font.getCharSet().getLineHeight();
            }
            if (userFont == null) {
                userFont = NkUserFont.create();
                scale = size / font.getCharSet().getRenderedSize();
                
                userFont.width((handle, h, text, len) -> {
                    String str = memUTF8(text, len);
                    return font.getLineWidth(str) * scale;
                })
                .height(font.getCharSet().getLineHeight() * scale)
                .query((handle, font_height, glyph, codepoint, next_codepoint) -> {
                    BitmapCharacterSet characterSet = font.getCharSet();
                    BitmapCharacter character = characterSet.getCharacter(codepoint);

                    if (character != null) {
                        NkUserFontGlyph ufg = NkUserFontGlyph.create(glyph);

                        ufg.width(character.getWidth() * scale);
                        ufg.height(character.getHeight() * scale);
                        ufg.offset().set(character.getXOffset() * scale, character.getYOffset() * scale);
                        ufg.xadvance(font.getCharacterAdvance((char) codepoint, (char) next_codepoint, scale));

                        float u0 = (float) character.getX() / characterSet.getWidth();
                        float v0 = (float) character.getY() / characterSet.getHeight();
                        float u1 = u0 + (float) character.getWidth() / characterSet.getWidth();
                        float v1 = v0 + (float) character.getHeight() / characterSet.getHeight();

                        ufg.uv(0).set(u0, v0);
                        ufg.uv(1).set(u1, v1);
                    }
                })
                .texture(
                    NkTextureHandler.builder()
                                            .display(display)
                                            .handle(font.getPage(page).getTextureParam("ColorMap").getTextureValue())
                                            .build()
                );
            }
            return () -> userFont;
        }
    }
}
