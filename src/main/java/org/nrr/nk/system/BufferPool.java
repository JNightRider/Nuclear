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

import com.jme3.util.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Class that manages a group of buffers.
 * 
 * @param <E> buffer
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public final class BufferPool<E extends Buffer> extends ObjectPool<E>{

    /**
     * Buffer class.
     */
    private Class<E> buffer;

    /**
     * Constructor of the class <code>BufferPool</code>.
     *
     * @param buffer buffer
     */
    public BufferPool(Class<E> buffer) {
        this(buffer, DEAD_TIME);
    }

    /**
     * Constructor of the class <code>BufferPool</code>.
     *
     * @param buffer buffer
     * @param deadTime long
     */
    public BufferPool(Class<E> buffer, long deadTime) {
        super(deadTime);
        this.buffer = buffer;
    }

    /*(non-Javadoc)
     */
    @Override
    @SuppressWarnings("unchecked")
    protected E create(int capacity) {
        if (buffer.isAssignableFrom(ShortBuffer.class)) {
            return (E) BufferUtils.createShortBuffer(capacity);
        } else if (buffer.isAssignableFrom(FloatBuffer.class)) {
            return (E) BufferUtils.createFloatBuffer(capacity);
        } else if (buffer.isAssignableFrom(IntBuffer.class)) {
            return (E) BufferUtils.createIntBuffer(capacity);
        } else if (buffer.isAssignableFrom(ByteBuffer.class)) {
            return (E) BufferUtils.createByteBuffer(capacity);
        }
        throw new UnsupportedOperationException("Buffer <" + buffer + " >");
    }

    /*(non-Javadoc)
     */
    @Override
    protected boolean validate(E o, int capacity) {
        if (o == null) {
            return false;
        }
        return capacity <= o.capacity();
    }

    /*(non-Javadoc)
     */
    @Override
    protected void dead(E o) {
        BufferUtils.destroyDirectBuffer(o);
    }
}
