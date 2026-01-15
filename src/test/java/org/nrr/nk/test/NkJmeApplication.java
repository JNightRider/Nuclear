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
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

import org.nrr.nk.NkDisplay;
import org.nrr.nk.test.screen.NkScreenAppState;

/**
 * Demonstration of how the JME3 backend works with Nuklear using the LWJGL3library.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkJmeApplication extends SimpleApplication {

    /**
     * The main method; uses zero arguments in the args array.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NkJmeApplication app = new NkJmeApplication();
        app.start();
    }
    
    /*(non-JavaDoc)
     */
    @Override
    public void simpleInitApp() {
        NkDisplay display = new NkDisplay(assetManager, inputManager, renderManager);
        guiViewPort.addProcessor(display);
        
        NkScreenAppState screenAppState = new NkScreenAppState();
        display.addNkRender(screenAppState);
        stateManager.attach(screenAppState);
        
        init();
    }
    
    private void init() {
        inputManager.addMapping("ALT", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener((ActionListener) (String name, boolean isPressed, float tpf) -> {
            boolean status = true;
            if ("ALT".equals(name) && isPressed) {
                if (flyCam.isEnabled()) {
                    status = false;
                } else {
                    status = false;
                }
            }
            
            if (flyCam.isEnabled() != status) {
                flyCam.setEnabled(status);
                inputManager.setCursorVisible(!status);
            }
        }, "ALT");
    }
}
