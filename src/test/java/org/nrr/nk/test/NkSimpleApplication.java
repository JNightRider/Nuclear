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
package org.nrr.nk.test;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;

import org.lwjgl.nuklear.NkColorf;
import org.nrr.nk.NkBitmapFontHandler;

import org.nrr.nk.NkDisplay;
import org.nrr.nk.NkThemeHandler;
import org.nrr.nk.test.common.Calculator;
import org.nrr.nk.test.common.Canvas;
import org.nrr.nk.test.common.Demo;

/**
 * Demonstration of how the JME3 backend works with Nuklear using the LWJGL3library.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkSimpleApplication extends SimpleApplication {

    /**
     * The main method; uses zero arguments in the args array.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NkSimpleApplication app = new NkSimpleApplication();
        AppSettings settings = new AppSettings(true);

        settings.setGammaCorrection(false);
        settings.setResizable(false);
        settings.setResolution(1200, 800);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }
    
    /** The backend of nuklear. */
    private NkDisplay display;
    
    /*(non-JavaDoc)
     */
    @Override
    public void simpleInitApp() {
        /*stateManager.attachAll(new DetailedProfilerState());*/
        display = new NkDisplay(assetManager, inputManager, renderManager);
        guiViewPort.addProcessor(display);
        
        display.setStyle(NkThemeHandler.Theme.THEME_DARK);
        display.setDefaultStyleFont(NkBitmapFontHandler.builder()
                .assetManager(assetManager)
                .display(display)
                .font("Interface/Nuklear/Fonts/Roboto-Regular.fnt")
                .size(13)
                .build());

        display.addNkRender(new Demo());
        display.addNkRender(new Calculator());
        display.addNkRender(new Canvas());

        flyCam.setEnabled(false);
        flyCam.unregisterInput();

        setDisplayFps(false);
        setDisplayStatView(false);
    }

    /*(non-JavaDoc)
     */
    @Override
    public void simpleUpdate(float tpf) {        
        NkColorf bg = display.getNkRender(Demo.class).getBackground();
        ColorRGBA color = new ColorRGBA(bg.r(), bg.g(), bg.b(), bg.a());
        viewPort.setBackgroundColor(color);
    }

    /*(non-JavaDoc)
     */
    @Override
    public void destroy() {
        display.destroy(true);
        super.destroy();
    }
    
}
