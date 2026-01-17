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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static com.jme3.util.BufferUtils.*;

/**
 * Class responsible for managing the vertices to be drawn on the OpenGL canvas
 * that belong to the Nuklear user interface components.
 * <pre><code>
 *   struct nk_vertex {
 *       float position[2];
 *       float uv[2];
 *       nk_byte col[4];
 *   };
 * </code></pre>
 *
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class Vertex {

    /**  A buffer with the positions (coordinates). */
    private final FloatBuffer position;    
    /** A buffer with the texture coordinates. */
    private final FloatBuffer texture;    
    /**  A buffer with the component colors. */
    private final FloatBuffer color;
    
    /**
     * The number of vertices to read (the number of segments to read).
     */
    private final int capacity;

    /**
     * The height of the graphics window that will be used to invert the
     * coordinates.
     */
    private float height;
    
    /**
     * The minimum index of the element buffer.
     */
    private int min;
    
    /**
     * Generate a new vertex handler {@code Vertex } with the respective capacities 
     * of each buffer.
     * 
     * @param capacity int
     */
    public Vertex(int capacity) {
        this.position = createFloatBuffer(capacity * 2);
        this.texture  = createFloatBuffer(capacity * 2);
        this.color    = createFloatBuffer(capacity * 4);
        this.capacity = capacity;
    }

    /**
     * Sets the {@code min|int} attribute.
     * 
     * @param min int
     * @return Vertex
     */
    public Vertex min(int min) {
        this.min = min;
        return this;
    }
    
    /**
     * Sets the {@code height|float} attribute.
     * 
     * @param height float
     * @return Vertex
     */
    public Vertex height(float height) {
        this.height = height;
        return this;
    }
    
    /**
     * Clean the buffers.
     *
     * @return Vertex
     */
    public Vertex clear() {
        position.clear();
        texture.clear();
        color.clear();
        return this;
    }
    
    /**
     * Method responsible for building the vertices in each buffer within a range
     * delimited by the number of elements of each drawing command, the reading
     * data is given by Nuclear.
     *
     * <p>
     * Each iteration of the vertex buffer has a range set in the Nuklear configurations
     * that corresponds to the displacements of positions, coordinates, and colors
     * within the pointer.
     * </p>
     *
     * @param vertices the vertex buffer
     * @param vertexsize number of vertices per segment
     */
    public void make(ByteBuffer vertices, int vertexsize) {
        int base = min * vertexsize;
        for (int i = 0; i < capacity; i++) {
            int offset = base + i * vertexsize;
            
            float x = vertices.getFloat(offset);
            float y = vertices.getFloat(offset + Float.BYTES);
            float u = vertices.getFloat(offset + (Float.BYTES * 2));
            float v = vertices.getFloat(offset + (Float.BYTES * 3));
            
            position.put(x).put(height - y);
            texture.put(u).put(1.0f - v);
            
            int rgba = vertices.getInt(offset + (Float.BYTES * 4));
            float a = ((rgba >> 24) & 0xFF) / 255f;
            float b = ((rgba >> 16) & 0xFF) / 255f;
            float g = ((rgba >> 8) & 0xFF) / 255f;
            float r = (rgba & 0xFF) / 255f;

            color.put(r).put(g).put(b).put(a);
        }
        
        position.flip();
        texture.flip();
        color.flip();
    }
    
    /**
     * Free memory
     */
    public void free() {
        destroyDirectBuffer(position);
        destroyDirectBuffer(texture);
        destroyDirectBuffer(color);
    }

    /**
     * Returns the buffer with the positions.
     * @return FloatBuffer
     */
    public FloatBuffer getPosition() {
        return position;
    }

    /**
     * Returns the buffer with the coordinates.
     * @return FloatBuffer
     */
    public FloatBuffer getTexture() {
        return texture;
    }

    /**
     * Returns the buffer with the colors
     * @return FloatBuffer
     */
    public FloatBuffer getColor() {
        return color;
    }
    
    /**
     * Returns the number of segments.
     * @return int
     */
    public int getCapacity() {
        return capacity;
    }
}
