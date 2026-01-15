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
package org.nrr.nk.test.screen;

import com.jme3.app.Application;
import com.jme3.math.ColorRGBA;
import com.jme3.util.BufferUtils;

import org.lwjgl.nuklear.*;
import org.lwjgl.system.*;

import org.nrr.nk.NkDisplay;
import org.nrr.nk.NkAppState;

import java.nio.*;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * An example of how to use the {@code NkAppState} class
 *
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkScreenAppState extends NkAppState {
    
    private static final int EASY = 0;
    private static final int HARD = 1;
    
    private IntBuffer compression;
    private NkColorf background;
    private ColorRGBA color;
    private int op = EASY;

    public NkScreenAppState() {
        super(false);
    }

    @Override
    protected void preInit(Application app) {
        compression = BufferUtils.createIntBuffer(1).put(0, 20);
        color      = new ColorRGBA();
        background = NkColorf.create();
        background.r(0.10f)
                  .g(0.18f)
                  .b(0.24f)
                  .a(1.0f);
    }

    @Override
    protected void postInit(NkContext ctx, NkDisplay display) {
        
    }

    @Override
    protected void render(NkContext ctx, NkDisplay display) {
        try (MemoryStack stack = stackPush()) {
            NkRect rect = NkRect.malloc(stack);

            if (nk_begin(
                    ctx,
                    "Demo",
                    nk_rect(50, 50, 230, 250, rect),
                    NK_WINDOW_BORDER | NK_WINDOW_MOVABLE | NK_WINDOW_SCALABLE | NK_WINDOW_MINIMIZABLE | NK_WINDOW_TITLE
            )) {
                nk_layout_row_static(ctx, 30, 80, 1);
                if (nk_button_label(ctx, "button")) {
                    System.out.println("button pressed");
                }

                nk_layout_row_dynamic(ctx, 30, 2);
                if (nk_option_label(ctx, "easy", op == EASY)) {
                    op = EASY;
                }
                if (nk_option_label(ctx, "hard", op == HARD)) {
                    op = HARD;
                }

                nk_layout_row_dynamic(ctx, 25, 1);
                nk_property_int(ctx, "Compression:", 0, compression, 100, 10, 1);

                nk_layout_row_dynamic(ctx, 20, 1);
                nk_label(ctx, "background:", NK_TEXT_LEFT);
                nk_layout_row_dynamic(ctx, 25, 1);
                if (nk_combo_begin_color(ctx, nk_rgb_cf(background, NkColor.malloc(stack)), NkVec2.malloc(stack).set(nk_widget_width(ctx), 400))) {
                    nk_layout_row_dynamic(ctx, 120, 1);
                    nk_color_picker(ctx, background, NK_RGBA);
                    nk_layout_row_dynamic(ctx, 25, 1);
                    background
                            .r(nk_propertyf(ctx, "#R:", 0, background.r(), 1.0f, 0.01f, 0.005f))
                            .g(nk_propertyf(ctx, "#G:", 0, background.g(), 1.0f, 0.01f, 0.005f))
                            .b(nk_propertyf(ctx, "#B:", 0, background.b(), 1.0f, 0.01f, 0.005f))
                            .a(nk_propertyf(ctx, "#A:", 0, background.a(), 1.0f, 0.01f, 0.005f));
                    nk_combo_end(ctx);
                }
            }
            nk_end(ctx);
        }
    }

    @Override
    public void update(float tpf) {
        color.set(background.r(), background.g(), background.b(), background.a());
        getApplication().getViewPort().setBackgroundColor(color);
    }

    @Override
    protected void cleanup(Application app) {
        
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
    
}
