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

/**
 * Interface responsible for managing a custom or default theme.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NkThemeHandler {
    
    /**
     * Lista de temas predeterminadas.
     */
    public static enum Theme {
        /** Theme: THEME_BLACK|default. */
        THEME_BLACK,
        
        /** Theme: THEME_WHITE. */
        THEME_WHITE,
        
        /** Theme: THEME_RED. */
        THEME_RED,
        
        /** Theme: THEME_BLUE. */
        THEME_BLUE,
        
        /** Theme: THEME_DARK. */
        THEME_DARK,
        
        /** Theme: THEME_DRACULA. */
        THEME_DRACULA,
        
        /** Theme: THEME_CATPPUCCIN_LATTE. */
        THEME_CATPPUCCIN_LATTE,
        
        /** Theme: THEME_CATPPUCCIN_FRAPPE. */
        THEME_CATPPUCCIN_FRAPPE,
        
        /** Theme: THEME_CATPPUCCIN_MACCHIATO. */
        THEME_CATPPUCCIN_MACCHIATO,
        
        /** Theme: THEME_CATPPUCCIN_MOCHA. */
        THEME_CATPPUCCIN_MOCHA
    }
    
    /**
     * Return to one of the default themes
     * @param theme theme
     * @return NkThemeHandler
     */
    public static NkThemeHandler getInstance(Theme theme) {
        return new NkDefaultThemeHandler(theme);
    }
    
    /**
     * Configure the buffer with your theme's colors
     * 
     * @param colors NkColor.Buffer
     * @param stack MemoryStack
     */
    void setupColors(NkColor.Buffer colors, MemoryStack stack);
}
