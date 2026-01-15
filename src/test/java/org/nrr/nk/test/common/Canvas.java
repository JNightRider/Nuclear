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
package org.nrr.nk.test.common;

import org.lwjgl.nuklear.*;
import org.lwjgl.system.*;

import org.nrr.nk.NkDisplay;
import org.nrr.nk.NkRender;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * A line-by-line Java translation of the Nukleae 'canvas.c' example.
 * <p>
 * Source code: https://github.com/Immediate-Mode-UI/Nuklear/blob/master/demo/common/canvas.c
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class Canvas implements NkRender {
    
    class NkCanvas {
        private NkCommandBuffer painter;
        private NkVec2 item_spacing;
        private NkVec2 panel_padding;
        private NkStyleItem window_background;
    }

    private boolean 
    canvas_begin(MemoryStack stack, NkContext ctx, NkCanvas canvas, int flags,
        int x, int y, int width, int height, NkColor background_color)
    {
        /* save style properties which will be overwritten */
        canvas.panel_padding = NkVec2.malloc(stack);
        canvas.panel_padding.set(ctx.style().window().padding());
        canvas.item_spacing = NkVec2.malloc(stack);
        canvas.item_spacing.set(ctx.style().window().spacing());
        
        canvas.window_background = NkStyleItem.malloc(stack);
        canvas.window_background.set(ctx.style().window().fixed_background());
        
        
        /* use the complete window space and set background */
        ctx.style().window().spacing(NkVec2.calloc(stack).set(0, 0));
        ctx.style().window().padding(NkVec2.calloc(stack).set(0, 0));
        ctx.style().window().fixed_background(nk_style_item_color(background_color, NkStyleItem.calloc(stack)));

        /* create/update window and set position + size */
        if (!nk_begin(ctx, "Canvas", NkRect.malloc().set(x, y, width, height), NK_WINDOW_NO_SCROLLBAR|flags))
            return false;

        /* allocate the complete window space for drawing */
        {
            NkRect total_space;
            total_space = nk_window_get_content_region(ctx, NkRect.calloc(stack));
            nk_layout_row_dynamic(ctx, total_space.h(), 1);
            nk_widget(total_space, ctx);
            canvas.painter = nk_window_get_canvas(ctx);
        }

        return true;
    }

    private void
    canvas_end(NkContext ctx, NkCanvas canvas)
    {
        nk_end(ctx);
        ctx.style().window().spacing(canvas.panel_padding);
        ctx.style().window().padding(canvas.item_spacing);
        ctx.style().window().fixed_background(canvas.window_background);
    }
        

    private void
    canvas(NkContext ctx)
    {
        try (MemoryStack stack = stackPush()) {
            NkCanvas canvas = new NkCanvas();
            if (canvas_begin(stack, ctx, canvas, NK_WINDOW_BORDER|NK_WINDOW_MOVABLE|NK_WINDOW_SCALABLE|
                NK_WINDOW_CLOSABLE|NK_WINDOW_MINIMIZABLE|NK_WINDOW_TITLE, 10, 10, 500, 550, nk_rgb(250,250,25, NkColor.malloc(stack))))
            {
                float x = canvas.painter.clip().x(), y = canvas.painter.clip().y();

                nk_fill_rect(canvas.painter, nk_rect(x + 15, y + 15, 210, 210, NkRect.malloc(stack)), 5, nk_rgb(247, 230, 154, NkColor.malloc(stack)));
                nk_fill_rect(canvas.painter, nk_rect(x + 20, y + 20, 200, 200, NkRect.malloc(stack)), 5, nk_rgb(188, 174, 118, NkColor.malloc(stack)));
                /* nk_draw_text(canvas.painter, nk_rect(x + 30, y + 30, 150, 20), "Text to draw", 12, &font->handle, nk_rgb(188,174,118), nk_rgb(0,0,0)); */
                nk_fill_rect(canvas.painter, nk_rect(x + 250, y + 20, 100, 100, NkRect.malloc(stack)), 0, nk_rgb(0,0,255, NkColor.malloc(stack)));
                nk_fill_circle(canvas.painter, nk_rect(x + 20, y + 250, 100, 100, NkRect.malloc(stack)), nk_rgb(255,0,0,NkColor.malloc(stack)));
                nk_fill_triangle(canvas.painter, x + 250, y + 250, x + 350, y + 250, x + 300, y + 350, nk_rgb(0,255,0, NkColor.malloc(stack)));
                nk_fill_arc(canvas.painter, x + 300, y + 420, 50, 0, 3.141592654f * 3.0f / 4.0f, nk_rgb(255,255,0,NkColor.malloc(stack)));

                {
                    float points[] = new float[12];
                    points[0]  = x + 200; points[1]  = y + 250;
                    points[2]  = x + 250; points[3]  = y + 350;
                    points[4]  = x + 225; points[5]  = y + 350;
                    points[6]  = x + 200; points[7]  = y + 300;
                    points[8]  = x + 175; points[9]  = y + 350;
                    points[10] = x + 150; points[11] = y + 350;
                    nk_fill_polygon(canvas.painter, points /*6*/, nk_rgb(0,0,0, NkColor.malloc(stack)));
                }

                {
                    float points[] = new float[12];
                    points[0]  = x + 200; points[1]  = y + 370;
                    points[2]  = x + 250; points[3]  = y + 470;
                    points[4]  = x + 225; points[5]  = y + 470;
                    points[6]  = x + 200; points[7]  = y + 420;
                    points[8]  = x + 175; points[9]  = y + 470;
                    points[10] = x + 150; points[11] = y + 470;
                    nk_stroke_polygon(canvas.painter, points /*,6*/, 4, nk_rgb(0,0,0, NkColor.malloc(stack)));
                }

                {
                    float points[] = new float[8];
                    points[0]  = x + 250; points[1]  = y + 200;
                    points[2]  = x + 275; points[3]  = y + 220;
                    points[4]  = x + 325; points[5]  = y + 170;
                    points[6]  = x + 350; points[7]  = y + 200;
                    nk_stroke_polyline(canvas.painter, points, /*4,*/ 2, nk_rgb(255,128,0, NkColor.malloc(stack)));
                }

                nk_stroke_line(canvas.painter, x + 15, y + 10, x + 200, y + 10, 2.0f, nk_rgb(189,45,75, NkColor.malloc(stack)));
                nk_stroke_rect(canvas.painter, nk_rect(x + 370, y + 20, 100, 100, NkRect.malloc(stack)), 10, 3, nk_rgb(0,0,255, NkColor.malloc(stack)));
                nk_stroke_curve(canvas.painter, x + 380, y + 200, x + 405, y + 270, x + 455, y + 120, x + 480, y + 200, 2, nk_rgb(0,150,220, NkColor.malloc(stack)));
                nk_stroke_circle(canvas.painter, nk_rect(x + 20, y + 370, 100, 100, NkRect.malloc(stack)), 5, nk_rgb(0,255,120, NkColor.malloc(stack)));
                nk_stroke_triangle(canvas.painter, x + 370, y + 250, x + 470, y + 250, x + 420, y + 350, 6, nk_rgb(255,0,143, NkColor.malloc(stack)));
                nk_stroke_arc(canvas.painter, x + 420, y + 420, 50, 0, 3.141592654f * 3.0f / 4.0f, 5, nk_rgb(0,255,255, NkColor.malloc(stack)));
            }
            canvas_end(ctx, canvas);
        }
    }

    @Override
    public void handle(NkContext ctx, NkDisplay display, float tpf) {
        canvas(ctx);
    }
}
