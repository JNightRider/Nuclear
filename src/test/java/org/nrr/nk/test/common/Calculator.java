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

import java.nio.*;
import java.text.*;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Line-by-line coupled example of LWJGL3 and Nuklear
 * <p>
 * Source code: https://github.com/LWJGL/lwjgl3/blob/master/modules/samples/src/test/java/org/lwjgl/demo/nuklear/Calculator.java
 * 
 * @author wil
 * @version 1.0.0
 * @since 
 */
public class Calculator implements NkRender {
    
    private static final String NUMS = "789456123";
    private static final String OPS  = "+-*/";

    private char
        prev,
        op;

    private boolean set;

    private final double[]
        a = new double[1],
        b = new double[1];

    private double[] current = a;

    private final DecimalFormat format = new DecimalFormat();

    final NkPluginFilter numberFilter;
    public Calculator() {
        format.setGroupingUsed(false);
        format.setDecimalSeparatorAlwaysShown(true);

        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        DecimalFormatSymbols dfs = format.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(dfs);

        numberFilter = NkPluginFilter.create(Nuklear::nnk_filter_float);
    }
    
    @Override
    public void handle(NkContext ctx, NkDisplay nuklear, float tpf) {
        try (MemoryStack stack = stackPush()) {
            NkRect rect = NkRect.malloc(stack);
            if (nk_begin(ctx, "Calculator", nk_rect(300, 50, 180, 250, rect), NK_WINDOW_BORDER | NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_MOVABLE)) {
                nk_layout_row_dynamic(ctx, 35, 1);
                {
                    ByteBuffer buffer = stack.calloc(256);

                    int length = memASCII(format.format(current[0]), false, buffer);

                    IntBuffer len = stack.ints(length);
                    nk_edit_string(ctx, NK_EDIT_SIMPLE, buffer, len, 255, numberFilter);
                    try {
                        current[0] = format.parse(memASCII(buffer, len.get(0))).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace(System.err);
                    }
                }

                nk_layout_row_dynamic(ctx, 35, 4);

                boolean solve = false;
                for (int i = 0; i < 16; ++i) {
                    if (i >= 12 && i < 15) {
                        if (i > 12) {
                            continue;
                        }
                        if (nk_button_label(ctx, "C")) {
                            a[0] = b[0] = op = 0;
                            current = a;
                            set = false;
                        }
                        if (nk_button_label(ctx, "0")) {
                            current[0] *= 10.0f;
                            set = false;
                        }
                        if (nk_button_label(ctx, "=")) {
                            solve = true;
                            prev = op;
                            op = 0;
                        }
                    } else if (((i + 1) % 4) != 0) {
                        if (nk_button_text(ctx, Character.toString(NUMS.charAt((i / 4) * 3 + i % 4)))) {
                            current[0] = current[0] * 10.0f + (NUMS.charAt((i / 4) * 3 + i % 4) - '0');
                            set = false;
                        }
                    } else if (nk_button_text(ctx, Character.toString(OPS.charAt(i / 4)))) {
                        if (!set) {
                            if (current != b) {
                                current = b;
                            } else {
                                prev = op;
                                solve = true;
                            }
                        }
                        op = OPS.charAt(i / 4);
                        set = true;
                    }
                }
                if (solve) {
                    if (prev == '+') {
                        a[0] += b[0];
                    }
                    if (prev == '-') {
                        a[0] -= b[0];
                    }
                    if (prev == '*') {
                        a[0] *= b[0];
                    }
                    if (prev == '/') {
                        a[0] /= b[0];
                    }
                    current = a;
                    if (set) {
                        current = b;
                    }
                    b[0] = 0;
                    set = false;
                }
            }
            nk_end(ctx);
        }
    }
}
