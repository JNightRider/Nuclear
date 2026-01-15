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
package org.nrr.nk.system;

/**
 * Class responsible for managing the amount of memory that Nuklear will have
 * available.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public final class BufferConfig {
    
    /**
     * Returns an instance.
     * @return BufferConfig
     */
    public static BufferConfig builder() {
        return new BufferConfig();
    }
    
    /** Maximum number of vertices. */
    private int maxVertexBuffer;
    /** Maximum number of elements. */
    private int maxElementBuffer;
    
    /**
     * A flag that indicates if there is a buffer update.
     */
    private boolean needsUpdate;
    /** Constructor of the class <code>BufferConfig</code>. */
    private BufferConfig() {}
    
    /**
     * Sets the {@code maxVertexBuffer|int} attribute.
     * 
     * @param maxVertexBuffer  int
     * @return BufferConfig
     */
    public BufferConfig maxVertexBuffer(int maxVertexBuffer) {
        if (this.maxVertexBuffer != maxVertexBuffer) {
            this.maxVertexBuffer = maxVertexBuffer;
            this.needsUpdate     = true;
        }
        return this;
    }

    /**
     * Sets the {@code maxElementBuffer|int} attribute.
     * 
     * @param maxElementBuffer  int
     * @return BufferConfig
     */
    public BufferConfig maxElementBuffer(int maxElementBuffer) {
        if (this.maxElementBuffer != maxElementBuffer) {
            this.maxElementBuffer = maxElementBuffer;
            this.needsUpdate     = true;
        }
        return this;
    }

    /**
     * Returns the value of the attribute: maxVertexBuffer
     * @return int
     */
    public int getMaxVertexBuffer() {
        return maxVertexBuffer;
    }

    /**
     * Returns the value of the attribute: maxElementBuffer
     * @return int
     */
    public int getMaxElementBuffer() {
        return maxElementBuffer;
    }

    /**
     * Indicates if there is a pending update.
     * @return boolean
     */
    public boolean needsUpdate() {
        return needsUpdate;
    }
    
    /**
     * Update ends
     */
    public void managedUpdate() {
        this.needsUpdate = false;
    }
}
