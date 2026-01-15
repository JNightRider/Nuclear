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
package org.nrr.nk.awt;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.lwjgl.system.MemoryStack;
import org.nrr.nk.NkClipboardHandler;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Class that manages an {@code NkClipboardHandler} using AWT components.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkToolkitHandler implements NkClipboardHandler {
    
    /**
     * Returns an instance of an {@code NkClipboardHandler}.
     *
     * @return NkClipboardHandler
     */
    public static NkClipboardHandler getInstance() {
        return new NkToolkitHandler();
    }
    /** Constructor of the class <code>NkToolkitHandler</code>. */
    private NkToolkitHandler() {}

    /*(non-Javadoc)
     */
    @Override
    public void copy(long text, int len) {
        if (len == 0) {
            return;
        }

        String str = memUTF8(text, len);
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(str), null);
    }

    /*(non-Javadoc)
     */
    @Override
    public void paste(long edit) {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String str = (String) clipboard.getData(DataFlavor.stringFlavor);
            
            if (str == null || str.isEmpty()) {
                return;
            }

            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            
            try (MemoryStack stack = stackPush()) {
                ByteBuffer buf = stack.malloc(bytes.length + 1);
                buf.put(bytes).put((byte) 0).flip();
                
                nnk_textedit_paste(edit, memAddress(buf), bytes.length);
            }
        } catch (HeadlessException | UnsupportedFlavorException | IOException ignored) { }
    }

    /*(non-Javadoc)
     */
    @Override
    public void free() {
        
    }
    
}
