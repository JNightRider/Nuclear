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

import org.lwjgl.nuklear.NkColor;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.nuklear.Nuklear.*;

/**
 * Class that implements the default themes of {@code Nuklear}.
 * 
 * Visit {@code C}: https://github.com/Immediate-Mode-UI/Nuklear/blob/master/demo/common/style.c
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
class NkDefaultThemeHandler implements NkThemeHandler {
    
    /** The type of color theme. */
    private final Theme theme;
    
    /**
     * Constructor of the class <code>NkDefaultThemeHandler</code>.
     * @param theme theme
     */
    NkDefaultThemeHandler(Theme theme) {
        this.theme = theme;
    }

    /*(non-Javadoc)
     */
    @Override
    public void setupColors(NkColor.Buffer colors, MemoryStack stack) {
        switch (theme) {
            case THEME_WHITE -> {
                colors.put(NK_COLOR_TEXT, nk_rgba(stack, 70, 70, 70, 255));
                colors.put(NK_COLOR_WINDOW, nk_rgba(stack, 175, 175, 175, 255));
                colors.put(NK_COLOR_HEADER, nk_rgba(stack, 175, 175, 175, 255));
                colors.put(NK_COLOR_BORDER, nk_rgba(stack, 0, 0, 0, 255));
                colors.put(NK_COLOR_BUTTON, nk_rgba(stack, 185, 185, 185, 255));
                colors.put(NK_COLOR_BUTTON_HOVER, nk_rgba(stack, 170, 170, 170, 255));
                colors.put(NK_COLOR_BUTTON_ACTIVE, nk_rgba(stack, 160, 160, 160, 255));
                colors.put(NK_COLOR_TOGGLE, nk_rgba(stack, 150, 150, 150, 255));
                colors.put(NK_COLOR_TOGGLE_HOVER, nk_rgba(stack, 120, 120, 120, 255));
                colors.put(NK_COLOR_TOGGLE_CURSOR, nk_rgba(stack, 175, 175, 175, 255));
                colors.put(NK_COLOR_SELECT, nk_rgba(stack, 190, 190, 190, 255));
                colors.put(NK_COLOR_SELECT_ACTIVE, nk_rgba(stack, 175, 175, 175, 255));
                colors.put(NK_COLOR_SLIDER, nk_rgba(stack, 190, 190, 190, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR, nk_rgba(stack, 80, 80, 80, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, nk_rgba(stack, 70, 70, 70, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, nk_rgba(stack, 60, 60, 60, 255));
                colors.put(NK_COLOR_PROPERTY, nk_rgba(stack, 175, 175, 175, 255));
                colors.put(NK_COLOR_EDIT, nk_rgba(stack, 150, 150, 150, 255));
                colors.put(NK_COLOR_EDIT_CURSOR, nk_rgba(stack, 0, 0, 0, 255));
                colors.put(NK_COLOR_COMBO, nk_rgba(stack, 175, 175, 175, 255));
                colors.put(NK_COLOR_CHART, nk_rgba(stack, 160, 160, 160, 255));
                colors.put(NK_COLOR_CHART_COLOR, nk_rgba(stack, 45, 45, 45, 255));
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, nk_rgba(stack, 255, 0, 0, 255));
                colors.put(NK_COLOR_SCROLLBAR, nk_rgba(stack, 180, 180, 180, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, nk_rgba(stack, 140, 140, 140, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, nk_rgba(stack, 150, 150, 150, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, nk_rgba(stack, 160, 160, 160, 255));
                colors.put(NK_COLOR_TAB_HEADER, nk_rgba(stack, 180, 180, 180, 255));

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, colors.get(NK_COLOR_SLIDER_CURSOR));
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, colors.get(NK_COLOR_SLIDER_CURSOR_HOVER));
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, colors.get(NK_COLOR_SLIDER_CURSOR_ACTIVE));
            }
            case THEME_RED -> {
                colors.put(NK_COLOR_TEXT, nk_rgba(stack, 190, 190, 190, 255));
                colors.put(NK_COLOR_WINDOW, nk_rgba(stack, 30, 33, 40, 215));
                colors.put(NK_COLOR_HEADER, nk_rgba(stack, 181, 45, 69, 220));
                colors.put(NK_COLOR_BORDER, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_BUTTON, nk_rgba(stack, 181, 45, 69, 255));
                colors.put(NK_COLOR_BUTTON_HOVER, nk_rgba(stack, 190, 50, 70, 255));
                colors.put(NK_COLOR_BUTTON_ACTIVE, nk_rgba(stack, 195, 55, 75, 255));
                colors.put(NK_COLOR_TOGGLE, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_TOGGLE_HOVER, nk_rgba(stack, 45, 60, 60, 255));
                colors.put(NK_COLOR_TOGGLE_CURSOR, nk_rgba(stack, 181, 45, 69, 255));
                colors.put(NK_COLOR_SELECT, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_SELECT_ACTIVE, nk_rgba(stack, 181, 45, 69, 255));
                colors.put(NK_COLOR_SLIDER, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR, nk_rgba(stack, 181, 45, 69, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, nk_rgba(stack, 186, 50, 74, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, nk_rgba(stack, 191, 55, 79, 255));
                colors.put(NK_COLOR_PROPERTY, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_EDIT, nk_rgba(stack, 51, 55, 67, 225));
                colors.put(NK_COLOR_EDIT_CURSOR, nk_rgba(stack, 190, 190, 190, 255));
                colors.put(NK_COLOR_COMBO, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_CHART, nk_rgba(stack, 51, 55, 67, 255));
                colors.put(NK_COLOR_CHART_COLOR, nk_rgba(stack, 170, 40, 60, 255));
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, nk_rgba(stack, 255, 0, 0, 255));
                colors.put(NK_COLOR_SCROLLBAR, nk_rgba(stack, 30, 33, 40, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, nk_rgba(stack, 64, 84, 95, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, nk_rgba(stack, 70, 90, 100, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, nk_rgba(stack, 75, 95, 105, 255));
                colors.put(NK_COLOR_TAB_HEADER, nk_rgba(stack, 181, 45, 69, 220));

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, colors.get(NK_COLOR_SLIDER_CURSOR));
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, colors.get(NK_COLOR_SLIDER_CURSOR_HOVER));
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, colors.get(NK_COLOR_SLIDER_CURSOR_ACTIVE));
            }
            case THEME_BLUE -> {
                colors.put(NK_COLOR_TEXT, nk_rgba(stack, 20, 20, 20, 255));
                colors.put(NK_COLOR_WINDOW, nk_rgba(stack, 202, 212, 214, 215));
                colors.put(NK_COLOR_HEADER, nk_rgba(stack, 137, 182, 224, 220));
                colors.put(NK_COLOR_BORDER, nk_rgba(stack, 140, 159, 173, 255));
                colors.put(NK_COLOR_BUTTON, nk_rgba(stack, 137, 182, 224, 255));
                colors.put(NK_COLOR_BUTTON_HOVER, nk_rgba(stack, 142, 187, 229, 255));
                colors.put(NK_COLOR_BUTTON_ACTIVE, nk_rgba(stack, 147, 192, 234, 255));
                colors.put(NK_COLOR_TOGGLE, nk_rgba(stack, 177, 210, 210, 255));
                colors.put(NK_COLOR_TOGGLE_HOVER, nk_rgba(stack, 182, 215, 215, 255));
                colors.put(NK_COLOR_TOGGLE_CURSOR, nk_rgba(stack, 137, 182, 224, 255));
                colors.put(NK_COLOR_SELECT, nk_rgba(stack, 177, 210, 210, 255));
                colors.put(NK_COLOR_SELECT_ACTIVE, nk_rgba(stack, 137, 182, 224, 255));
                colors.put(NK_COLOR_SLIDER, nk_rgba(stack, 177, 210, 210, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR, nk_rgba(stack, 137, 182, 224, 245));
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, nk_rgba(stack, 142, 188, 229, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, nk_rgba(stack, 147, 193, 234, 255));
                colors.put(NK_COLOR_PROPERTY, nk_rgba(stack, 210, 210, 210, 255));
                colors.put(NK_COLOR_EDIT, nk_rgba(stack, 210, 210, 210, 225));
                colors.put(NK_COLOR_EDIT_CURSOR, nk_rgba(stack, 20, 20, 20, 255));
                colors.put(NK_COLOR_COMBO, nk_rgba(stack, 210, 210, 210, 255));
                colors.put(NK_COLOR_CHART, nk_rgba(stack, 210, 210, 210, 255));
                colors.put(NK_COLOR_CHART_COLOR, nk_rgba(stack, 137, 182, 224, 255));
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, nk_rgba(stack, 255, 0, 0, 255));
                colors.put(NK_COLOR_SCROLLBAR, nk_rgba(stack, 190, 200, 200, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, nk_rgba(stack, 64, 84, 95, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, nk_rgba(stack, 70, 90, 100, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, nk_rgba(stack, 75, 95, 105, 255));
                colors.put(NK_COLOR_TAB_HEADER, nk_rgba(stack, 156, 193, 220, 255));

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, colors.get(NK_COLOR_SLIDER_CURSOR));
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, colors.get(NK_COLOR_SLIDER_CURSOR_HOVER));
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, colors.get(NK_COLOR_SLIDER_CURSOR_ACTIVE));
            }
            case THEME_DARK -> {
                colors.put(NK_COLOR_TEXT, nk_rgba(stack, 210, 210, 210, 255));
                colors.put(NK_COLOR_WINDOW, nk_rgba(stack, 57, 67, 71, 215));
                colors.put(NK_COLOR_HEADER, nk_rgba(stack, 51, 51, 56, 220));
                colors.put(NK_COLOR_BORDER, nk_rgba(stack, 46, 46, 46, 255));
                colors.put(NK_COLOR_BUTTON, nk_rgba(stack, 48, 83, 111, 255));
                colors.put(NK_COLOR_BUTTON_HOVER, nk_rgba(stack, 58, 93, 121, 255));
                colors.put(NK_COLOR_BUTTON_ACTIVE, nk_rgba(stack, 63, 98, 126, 255));
                colors.put(NK_COLOR_TOGGLE, nk_rgba(stack, 50, 58, 61, 255));
                colors.put(NK_COLOR_TOGGLE_HOVER, nk_rgba(stack, 45, 53, 56, 255));
                colors.put(NK_COLOR_TOGGLE_CURSOR, nk_rgba(stack, 48, 83, 111, 255));
                colors.put(NK_COLOR_SELECT, nk_rgba(stack, 57, 67, 61, 255));
                colors.put(NK_COLOR_SELECT_ACTIVE, nk_rgba(stack, 48, 83, 111, 255));
                colors.put(NK_COLOR_SLIDER, nk_rgba(stack, 50, 58, 61, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR, nk_rgba(stack, 48, 83, 111, 245));
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, nk_rgba(stack, 53, 88, 116, 255));
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, nk_rgba(stack, 58, 93, 121, 255));
                colors.put(NK_COLOR_PROPERTY, nk_rgba(stack, 50, 58, 61, 255));
                colors.put(NK_COLOR_EDIT, nk_rgba(stack, 50, 58, 61, 225));
                colors.put(NK_COLOR_EDIT_CURSOR, nk_rgba(stack, 210, 210, 210, 255));
                colors.put(NK_COLOR_COMBO, nk_rgba(stack, 50, 58, 61, 255));
                colors.put(NK_COLOR_CHART, nk_rgba(stack, 50, 58, 61, 255));
                colors.put(NK_COLOR_CHART_COLOR, nk_rgba(stack, 48, 83, 111, 255));
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, nk_rgba(stack, 255, 0, 0, 255));
                colors.put(NK_COLOR_SCROLLBAR, nk_rgba(stack, 50, 58, 61, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, nk_rgba(stack, 48, 83, 111, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, nk_rgba(stack, 53, 88, 116, 255));
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, nk_rgba(stack, 58, 93, 121, 255));
                colors.put(NK_COLOR_TAB_HEADER, nk_rgba(stack, 48, 83, 111, 255));

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, colors.get(NK_COLOR_SLIDER_CURSOR));
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, colors.get(NK_COLOR_SLIDER_CURSOR_HOVER));
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, colors.get(NK_COLOR_SLIDER_CURSOR_ACTIVE));
            }
            case THEME_DRACULA -> {
                NkColor background   = nk_rgba(stack, 40, 42, 54, 255);
                NkColor currentline  = nk_rgba(stack, 68, 71, 90, 255);
                NkColor foreground   = nk_rgba(stack, 248, 248, 242, 255);
                NkColor comment      = nk_rgba(stack, 98, 114, 164, 255);
                /* NkColor cyan     = nk_rgba(stack, 139, 233, 253, 255); */
                /* NkColor green    = nk_rgba(stack, 80, 250, 123, 255); */
                /* NkColor orange   = nk_rgba(stack, 255, 184, 108, 255); */
                NkColor pink         = nk_rgba(stack, 255, 121, 198, 255);
                NkColor purple       = nk_rgba(stack, 189, 147, 249, 255);
                /* NkColor red      = nk_rgba(stack, 255, 85, 85, 255); */
                /* NkColor yellow   = nk_rgba(stack, 241, 250, 140, 255); */

                colors.put(NK_COLOR_TEXT, foreground);
                colors.put(NK_COLOR_WINDOW, background);
                colors.put(NK_COLOR_HEADER, currentline);
                colors.put(NK_COLOR_BORDER, currentline);
                colors.put(NK_COLOR_BUTTON, currentline);
                colors.put(NK_COLOR_BUTTON_HOVER, comment);
                colors.put(NK_COLOR_BUTTON_ACTIVE, purple);
                colors.put(NK_COLOR_TOGGLE, currentline);
                colors.put(NK_COLOR_TOGGLE_HOVER, comment);
                colors.put(NK_COLOR_TOGGLE_CURSOR, pink);
                colors.put(NK_COLOR_SELECT, currentline);
                colors.put(NK_COLOR_SELECT_ACTIVE, comment);
                colors.put(NK_COLOR_SLIDER, background);
                colors.put(NK_COLOR_SLIDER_CURSOR, currentline);
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, comment);
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, comment);
                colors.put(NK_COLOR_PROPERTY, currentline);
                colors.put(NK_COLOR_EDIT, currentline);
                colors.put(NK_COLOR_EDIT_CURSOR, foreground);
                colors.put(NK_COLOR_COMBO, currentline);
                colors.put(NK_COLOR_CHART, currentline);
                colors.put(NK_COLOR_CHART_COLOR, comment);
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, purple);
                colors.put(NK_COLOR_SCROLLBAR, background);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, currentline);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, comment);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, purple);
                colors.put(NK_COLOR_TAB_HEADER, currentline);

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, colors.get(NK_COLOR_SLIDER_CURSOR));
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, colors.get(NK_COLOR_SLIDER_CURSOR_HOVER));
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, colors.get(NK_COLOR_SLIDER_CURSOR_ACTIVE));
            }
            case THEME_CATPPUCCIN_LATTE -> {
                /* NkColor rosewater = nk_rgba(stack, 220, 138, 120, 255); */
                /* NkColor flamingo  = nk_rgba(stack, 221, 120, 120, 255); */
                NkColor pink        = nk_rgba(stack, 234, 118, 203, 255);
                NkColor mauve       = nk_rgba(stack, 136, 57, 239, 255);
                /* NkColor red      = nk_rgba(stack, 210, 15, 57, 255); */
                /* NkColor maroon   = nk_rgba(stack, 230, 69, 83, 255); */
                /* NkColor peach    = nk_rgba(stack, 254, 100, 11, 255); */
                NkColor yellow      = nk_rgba(stack, 223, 142, 29, 255);
                /* NkColor green    = nk_rgba(stack, 64, 160, 43, 255); */
                NkColor teal        = nk_rgba(stack, 23, 146, 153, 255);
                /* NkColor sky      = nk_rgba(stack, 4, 165, 229, 255); */
                /* NkColor sapphire = nk_rgba(stack, 32, 159, 181, 255); */
                /* NkColor blue     = nk_rgba(stack, 30, 102, 245, 255); */
                /* NkColor lavender = nk_rgba(stack, 114, 135, 253, 255); */
                NkColor text        = nk_rgba(stack, 76, 79, 105, 255);
                /* NkColor subtext1 = nk_rgba(stack, 92, 95, 119, 255); */
                /* NkColor subtext0 = nk_rgba(stack, 108, 111, 133, 255); */
                NkColor overlay2    = nk_rgba(stack, 124, 127, 147, 55);
                /* NkColor overlay1 = nk_rgba(stack, 140, 143, 161, 255); */
                NkColor overlay0    = nk_rgba(stack, 156, 160, 176, 255);
                NkColor surface2    = nk_rgba(stack, 172, 176, 190, 255);
                NkColor surface1    = nk_rgba(stack, 188, 192, 204, 255);
                NkColor surface0    = nk_rgba(stack, 204, 208, 218, 255);
                NkColor base        = nk_rgba(stack, 239, 241, 245, 255);
                NkColor mantle      = nk_rgba(stack, 230, 233, 239, 255);
                /* NkColor crust    = nk_rgba(stack, 220, 224, 232, 255); */

                colors.put(NK_COLOR_TEXT, text);
                colors.put(NK_COLOR_WINDOW, base);
                colors.put(NK_COLOR_HEADER, mantle);
                colors.put(NK_COLOR_BORDER, mantle);
                colors.put(NK_COLOR_BUTTON, surface0);
                colors.put(NK_COLOR_BUTTON_HOVER, overlay2);
                colors.put(NK_COLOR_BUTTON_ACTIVE, overlay0);
                colors.put(NK_COLOR_TOGGLE, surface2);
                colors.put(NK_COLOR_TOGGLE_HOVER, overlay2);
                colors.put(NK_COLOR_TOGGLE_CURSOR, yellow);
                colors.put(NK_COLOR_SELECT, surface0);
                colors.put(NK_COLOR_SELECT_ACTIVE, overlay0);
                colors.put(NK_COLOR_SLIDER, surface1);
                colors.put(NK_COLOR_SLIDER_CURSOR, teal);
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, teal);
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, teal);
                colors.put(NK_COLOR_PROPERTY, surface0);
                colors.put(NK_COLOR_EDIT, surface0);
                colors.put(NK_COLOR_EDIT_CURSOR, mauve);
                colors.put(NK_COLOR_COMBO, surface0);
                colors.put(NK_COLOR_CHART, surface0);
                colors.put(NK_COLOR_CHART_COLOR, teal);
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, mauve);
                colors.put(NK_COLOR_SCROLLBAR, surface0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, overlay0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, mauve);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, mauve);
                colors.put(NK_COLOR_TAB_HEADER, surface0);

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, pink);
            }
            case THEME_CATPPUCCIN_FRAPPE -> {
                /* NkColor rosewater = nk_rgba(stack, 242, 213, 207, 255); */
                /* NkColor flamingo  = nk_rgba(stack, 238, 190, 190, 255); */
                NkColor pink        = nk_rgba(stack, 244, 184, 228, 255);
                /* NkColor mauve    = nk_rgba(stack, 202, 158, 230, 255); */
                /* NkColor red      = nk_rgba(stack, 231, 130, 132, 255); */
                /* NkColor maroon   = nk_rgba(stack, 234, 153, 156, 255); */
                /* NkColor peach    = nk_rgba(stack, 239, 159, 118, 255); */
                /* NkColor yellow   = nk_rgba(stack, 229, 200, 144, 255); */
                NkColor green       = nk_rgba(stack, 166, 209, 137, 255);
                /* NkColor teal     = nk_rgba(stack, 129, 200, 190, 255); */
                /* NkColor sky      = nk_rgba(stack, 153, 209, 219, 255); */
                /* NkColor sapphire = nk_rgba(stack, 133, 193, 220, 255); */
                /* NkColor blue     = nk_rgba(stack, 140, 170, 238, 255); */
                NkColor lavender    = nk_rgba(stack, 186, 187, 241, 255);
                NkColor text        = nk_rgba(stack, 198, 208, 245, 255);
                /* NkColor subtext1 = nk_rgba(stack, 181, 191, 226, 255); */
                /* NkColor subtext0 = nk_rgba(stack, 165, 173, 206, 255); */
                NkColor overlay2    = nk_rgba(stack, 148, 156, 187, 255);
                NkColor overlay1    = nk_rgba(stack, 131, 139, 167, 255);
                NkColor overlay0    = nk_rgba(stack, 115, 121, 148, 255);
                NkColor surface2    = nk_rgba(stack, 98, 104, 128, 255);
                NkColor surface1    = nk_rgba(stack, 81, 87, 109, 255);
                NkColor surface0    = nk_rgba(stack, 65, 69, 89, 255);
                NkColor base        = nk_rgba(stack, 48, 52, 70, 255);
                NkColor mantle      = nk_rgba(stack, 41, 44, 60, 255);
                /* NkColor crust    = nk_rgba(stack, 35, 38, 52, 255); */

                colors.put(NK_COLOR_TEXT, text);
                colors.put(NK_COLOR_WINDOW, base);
                colors.put(NK_COLOR_HEADER, mantle);
                colors.put(NK_COLOR_BORDER, mantle);
                colors.put(NK_COLOR_BUTTON, surface0);
                colors.put(NK_COLOR_BUTTON_HOVER, overlay1);
                colors.put(NK_COLOR_BUTTON_ACTIVE, overlay0);
                colors.put(NK_COLOR_TOGGLE, surface2);
                colors.put(NK_COLOR_TOGGLE_HOVER, overlay2);
                colors.put(NK_COLOR_TOGGLE_CURSOR, pink);
                colors.put(NK_COLOR_SELECT, surface0);
                colors.put(NK_COLOR_SELECT_ACTIVE, overlay0);
                colors.put(NK_COLOR_SLIDER, surface1);
                colors.put(NK_COLOR_SLIDER_CURSOR, green);
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, green);
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, green);
                colors.put(NK_COLOR_PROPERTY, surface0);
                colors.put(NK_COLOR_EDIT, surface0);
                colors.put(NK_COLOR_EDIT_CURSOR, pink);
                colors.put(NK_COLOR_COMBO, surface0);
                colors.put(NK_COLOR_CHART, surface0);
                colors.put(NK_COLOR_CHART_COLOR, lavender);
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, pink);
                colors.put(NK_COLOR_SCROLLBAR, surface0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, overlay0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, lavender);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, lavender);
                colors.put(NK_COLOR_TAB_HEADER, surface0);

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, pink);
            }
            case THEME_CATPPUCCIN_MACCHIATO -> {
                /* NkColor rosewater = nk_rgba(stack, 244, 219, 214, 255); */
                /* NkColor flamingo  = nk_rgba(stack, 240, 198, 198, 255); */
                NkColor pink        = nk_rgba(stack, 245, 189, 230, 255);
                /* NkColor mauve    = nk_rgba(stack, 198, 160, 246, 255); */
                /* NkColor red      = nk_rgba(stack, 237, 135, 150, 255); */
                /* NkColor maroon   = nk_rgba(stack, 238, 153, 160, 255); */
                /* NkColor peach    = nk_rgba(stack, 245, 169, 127, 255); */
                NkColor yellow      = nk_rgba(stack, 238, 212, 159, 255);
                NkColor green       = nk_rgba(stack, 166, 218, 149, 255);
                /* NkColor teal     = nk_rgba(stack, 139, 213, 202, 255); */
                /* NkColor sky      = nk_rgba(stack, 145, 215, 227, 255); */
                /* NkColor sapphire = nk_rgba(stack, 125, 196, 228, 255); */
                /* NkColor blue     = nk_rgba(stack, 138, 173, 244, 255); */
                NkColor lavender    = nk_rgba(stack, 183, 189, 248, 255);
                NkColor text        = nk_rgba(stack, 202, 211, 245, 255);
                /* NkColor subtext1 = nk_rgba(stack, 184, 192, 224, 255); */
                /* NkColor subtext0 = nk_rgba(stack, 165, 173, 203, 255); */
                NkColor overlay2    = nk_rgba(stack, 147, 154, 183, 255);
                NkColor overlay1    = nk_rgba(stack, 128, 135, 162, 255);
                NkColor overlay0    = nk_rgba(stack, 110, 115, 141, 255);
                NkColor surface2    = nk_rgba(stack, 91, 96, 120, 255);
                NkColor surface1    = nk_rgba(stack, 73, 77, 100, 255);
                NkColor surface0    = nk_rgba(stack, 54, 58, 79, 255);
                NkColor base        = nk_rgba(stack, 36, 39, 58, 255);
                NkColor mantle      = nk_rgba(stack, 30, 32, 48, 255);
                /* NkColor crust    = nk_rgba(stack, 24, 25, 38, 255); */

                colors.put(NK_COLOR_TEXT, text);
                colors.put(NK_COLOR_WINDOW, base);
                colors.put(NK_COLOR_HEADER, mantle);
                colors.put(NK_COLOR_BORDER, mantle);
                colors.put(NK_COLOR_BUTTON, surface0);
                colors.put(NK_COLOR_BUTTON_HOVER, overlay1);
                colors.put(NK_COLOR_BUTTON_ACTIVE, overlay0);
                colors.put(NK_COLOR_TOGGLE, surface2);
                colors.put(NK_COLOR_TOGGLE_HOVER, overlay2);
                colors.put(NK_COLOR_TOGGLE_CURSOR, yellow);
                colors.put(NK_COLOR_SELECT, surface0);
                colors.put(NK_COLOR_SELECT_ACTIVE, overlay0);
                colors.put(NK_COLOR_SLIDER, surface1);
                colors.put(NK_COLOR_SLIDER_CURSOR, green);
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, green);
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, green);
                colors.put(NK_COLOR_PROPERTY, surface0);
                colors.put(NK_COLOR_EDIT, surface0);
                colors.put(NK_COLOR_EDIT_CURSOR, pink);
                colors.put(NK_COLOR_COMBO, surface0);
                colors.put(NK_COLOR_CHART, surface0);
                colors.put(NK_COLOR_CHART_COLOR, lavender);
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, yellow);
                colors.put(NK_COLOR_SCROLLBAR, surface0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, overlay0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, lavender);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, lavender);
                colors.put(NK_COLOR_TAB_HEADER, surface0);

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, pink);
            }
            case THEME_CATPPUCCIN_MOCHA -> {
                /* NkColor rosewater = nk_rgba(stack, 245, 224, 220, 255); */
                /* NkColor flamingo  = nk_rgba(stack, 242, 205, 205, 255); */
                NkColor pink        = nk_rgba(stack, 245, 194, 231, 255);
                /* NkColor mauve    = nk_rgba(stack, 203, 166, 247, 255); */
                /* NkColor red      = nk_rgba(stack, 243, 139, 168, 255); */
                /* NkColor maroon   = nk_rgba(stack, 235, 160, 172, 255); */
                /* NkColor peach    = nk_rgba(stack, 250, 179, 135, 255); */
                /* NkColor yellow   = nk_rgba(stack, 249, 226, 175, 255); */
                NkColor green       = nk_rgba(stack, 166, 227, 161, 255);
                /* NkColor teal     = nk_rgba(stack, 148, 226, 213, 255); */
                /* NkColor sky      = nk_rgba(stack, 137, 220, 235, 255); */
                /* NkColor sapphire = nk_rgba(stack, 116, 199, 236, 255); */
                /* NkColor blue     = nk_rgba(stack, 137, 180, 250, 255); */
                NkColor lavender    = nk_rgba(stack, 180, 190, 254, 255);
                NkColor text        = nk_rgba(stack, 205, 214, 244, 255);
                /* NkColor subtext1 = nk_rgba(stack, 186, 194, 222, 255); */
                /* NkColor subtext0 = nk_rgba(stack, 166, 173, 200, 255); */
                NkColor overlay2    = nk_rgba(stack, 147, 153, 178, 255);
                NkColor overlay1    = nk_rgba(stack, 127, 132, 156, 255);
                NkColor overlay0    = nk_rgba(stack, 108, 112, 134, 255);
                NkColor surface2    = nk_rgba(stack, 88, 91, 112, 255);
                NkColor surface1    = nk_rgba(stack, 69, 71, 90, 255);
                NkColor surface0    = nk_rgba(stack, 49, 50, 68, 255);
                NkColor base        = nk_rgba(stack, 30, 30, 46, 255);
                NkColor mantle      = nk_rgba(stack, 24, 24, 37, 255);
                /* NkColor crust    = nk_rgba(stack, 17, 17, 27, 255); */

                colors.put(NK_COLOR_TEXT, text);
                colors.put(NK_COLOR_WINDOW, base);
                colors.put(NK_COLOR_HEADER, mantle);
                colors.put(NK_COLOR_BORDER, mantle);
                colors.put(NK_COLOR_BUTTON, surface0);
                colors.put(NK_COLOR_BUTTON_HOVER, overlay1);
                colors.put(NK_COLOR_BUTTON_ACTIVE, overlay0);
                colors.put(NK_COLOR_TOGGLE, surface2);
                colors.put(NK_COLOR_TOGGLE_HOVER, overlay2);
                colors.put(NK_COLOR_TOGGLE_CURSOR, lavender);
                colors.put(NK_COLOR_SELECT, surface0);
                colors.put(NK_COLOR_SELECT_ACTIVE, overlay0);
                colors.put(NK_COLOR_SLIDER, surface1);
                colors.put(NK_COLOR_SLIDER_CURSOR, green);
                colors.put(NK_COLOR_SLIDER_CURSOR_HOVER, green);
                colors.put(NK_COLOR_SLIDER_CURSOR_ACTIVE, green);
                colors.put(NK_COLOR_PROPERTY, surface0);
                colors.put(NK_COLOR_EDIT, surface0);
                colors.put(NK_COLOR_EDIT_CURSOR, lavender);
                colors.put(NK_COLOR_COMBO, surface0);
                colors.put(NK_COLOR_CHART, surface0);
                colors.put(NK_COLOR_CHART_COLOR, lavender);
                colors.put(NK_COLOR_CHART_COLOR_HIGHLIGHT, pink);
                colors.put(NK_COLOR_SCROLLBAR, surface0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR, overlay0);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_HOVER, lavender);
                colors.put(NK_COLOR_SCROLLBAR_CURSOR_ACTIVE, pink);
                colors.put(NK_COLOR_TAB_HEADER, surface0);

                colors.put(NK_COLOR_KNOB, colors.get(NK_COLOR_SLIDER));
                colors.put(NK_COLOR_KNOB_CURSOR, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_HOVER, pink);
                colors.put(NK_COLOR_KNOB_CURSOR_ACTIVE, pink);
            }
            default -> {
            }
        }
    }
    
    /*(non-Javadoc)
     */
    private static NkColor nk_rgba(MemoryStack stack, int r, int g, int b, int a) {
        return NkColor.malloc(stack).set((byte) r, (byte) g, (byte) b, (byte) a);
    }
}
