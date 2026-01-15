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

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Vector2f;

import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkMouse;
import org.lwjgl.nuklear.NkVec2;

import org.lwjgl.system.MemoryStack;
import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * Class that handles keyboard and mouse input for Nuklear backend event handling.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkInputListener implements RawInputListener {
    
    /**  Mouse and keyboard event input manager JME3. */
    private final InputManager inputManager;    
    /** Context of the prepared nuclear event.  */
    private NkContext ctx;
    
    /** Identifies an input pointer by touch. */
    private int inputPointerId = -1;
    /** height of the window used to invert the coordinates. */
    private int height = 1;
    /** x and y coordinates. */
    private int x, y;
    
    /**
     * Constructor of the class <code>NkInputListener</code>.
     * @param inputManager InputManager
     */
    public NkInputListener(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * Set the context
     * @param ctx NkContext
     */
    public void setContext(NkContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Set the height
     * @param height int
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Reset attributes.
     */
    public void reset() {
        x = 0;
        y = 0;
        inputPointerId = -1;
    }
    
    /*(non-Javadoc)
     */
    @Override
    public void beginInput() {
        nk_input_begin(ctx);
        NkMouse mouse = ctx.input().mouse();
        if (mouse.grab()) {
            inputManager.setCursorVisible(false);
        } else if (mouse.grabbed()) {
            Vector2f pos = inputManager.getCursorPosition();            
            mouse.pos().x(pos.x);
            mouse.pos().y(height - pos.y);
        } else if (mouse.ungrab()) {
            inputManager.setCursorVisible(true);
        }
    }

    /*(non-Javadoc)
     */
    @Override
    public void endInput() {
        nk_input_end(ctx);
    }

    /*(non-Javadoc)
     */
    @Override
    public void onJoyAxisEvent(JoyAxisEvent evt) {
        
    }

    /*(non-Javadoc)
     */
    @Override
    public void onJoyButtonEvent(JoyButtonEvent evt) {
        
    }

    /*(non-Javadoc)
     */
    @Override
    public void onMouseMotionEvent(MouseMotionEvent evt) {
        int wheel = evt.getDeltaWheel() / 120;
        x = evt.getX();
        y = height - evt.getY();
        
        nk_input_motion(ctx, x, y);
        try (MemoryStack stack = stackPush()) {
            NkVec2 scroll = NkVec2.malloc(stack)
                .x((float)0)
                .y((float) wheel);
            nk_input_scroll(ctx, scroll);
        }
    }

    /*(non-Javadoc)
     */
    @Override
    public void onMouseButtonEvent(MouseButtonEvent evt) {
        x = evt.getX();
        y = (int) (height - evt.getY());

        int nkButton = switch (evt.getButtonIndex()) {
            case MouseInput.BUTTON_RIGHT -> NK_BUTTON_RIGHT;
            case MouseInput.BUTTON_MIDDLE -> NK_BUTTON_MIDDLE;
            default -> NK_BUTTON_LEFT;
        };

        nk_input_button(ctx, nkButton, x, y, evt.isPressed());
    }

    /*(non-Javadoc)
     */
    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        boolean press = evt.isPressed();
        if (press) {
            nk_input_unicode(ctx, evt.getKeyChar());
        }
        
        switch (evt.getKeyCode()) {
            case KeyInput.KEY_DELETE -> nk_input_key(ctx, NK_KEY_DEL, press);
            case KeyInput.KEY_RETURN -> nk_input_key(ctx, NK_KEY_ENTER, press);
            case KeyInput.KEY_TAB -> nk_input_key(ctx, NK_KEY_TAB, press);
            case KeyInput.KEY_BACK -> nk_input_key(ctx, NK_KEY_BACKSPACE, press);
            case KeyInput.KEY_UP -> nk_input_key(ctx, NK_KEY_UP, press);
            case KeyInput.KEY_DOWN -> nk_input_key(ctx, NK_KEY_DOWN, press);
            case KeyInput.KEY_HOME -> {
                nk_input_key(ctx, NK_KEY_TEXT_START, press);
                nk_input_key(ctx, NK_KEY_SCROLL_START, press);
            }
            case KeyInput.KEY_END -> {
                nk_input_key(ctx, NK_KEY_TEXT_END, press);
                nk_input_key(ctx, NK_KEY_SCROLL_END, press);
            }
            case KeyInput.KEY_PGDN -> nk_input_key(ctx, NK_KEY_SCROLL_DOWN, press);
            case KeyInput.KEY_PGUP -> nk_input_key(ctx, NK_KEY_SCROLL_UP, press);
            case KeyInput.KEY_LSHIFT, KeyInput.KEY_RSHIFT -> nk_input_key(ctx, NK_KEY_SHIFT, press);
            case KeyInput.KEY_LCONTROL, KeyInput.KEY_RCONTROL -> {
                if (press) {
                    nk_input_key(ctx, NK_KEY_COPY, press);
                    nk_input_key(ctx, NK_KEY_PASTE, press);
                    nk_input_key(ctx, NK_KEY_CUT, press);
                    nk_input_key(ctx, NK_KEY_TEXT_UNDO, press);
                    nk_input_key(ctx, NK_KEY_TEXT_REDO, press);
                    nk_input_key(ctx, NK_KEY_TEXT_WORD_LEFT, press);
                    nk_input_key(ctx, NK_KEY_TEXT_WORD_RIGHT, press);
                    nk_input_key(ctx, NK_KEY_TEXT_LINE_START, press);
                    nk_input_key(ctx, NK_KEY_TEXT_LINE_END, press);
                } else {
                    nk_input_key(ctx, NK_KEY_LEFT, press);
                    nk_input_key(ctx, NK_KEY_RIGHT, press);
                    nk_input_key(ctx, NK_KEY_COPY, false);
                    nk_input_key(ctx, NK_KEY_PASTE, false);
                    nk_input_key(ctx, NK_KEY_CUT, false);
                    nk_input_key(ctx, NK_KEY_SHIFT, false);
                }
            }
        }
    }

    /*(non-Javadoc)
     */
    @Override
    public void onTouchEvent(TouchEvent evt) {
        if (inputManager.isSimulateMouse()) {
            return;
        }
        
        x = (int) evt.getX();
        y = (int) (height - evt.getY());

        // Input manager will not convert touch events to mouse events,
        // so we must do it ourselves.
        switch (evt.getType()) {
            case DOWN -> {
                if (inputPointerId != -1) {
                    // Another touch was done by the user
                    // while the other interacts with nifty, ignore.
                    break;
                }

                inputPointerId = evt.getPointerId();
                nk_input_button(ctx, NK_BUTTON_LEFT, x, y, true);
            }
            case UP -> {
                if (inputPointerId != evt.getPointerId()) {
                    // Another touch was done by the user
                    // while the other interacts with nifty, ignore.
                    break;
                }

                inputPointerId = -1;
                nk_input_button(ctx, NK_BUTTON_LEFT, x, y, false);
            }
        }
    }
}
