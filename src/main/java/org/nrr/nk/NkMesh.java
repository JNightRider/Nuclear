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

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * A mesh that preloads the conditions for the Nuklear buffers.
 *
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkMesh extends Mesh {
    
    /**
     * Constructor of the class <code>NkMesh</code>.
     */
    public NkMesh() {
        VertexBuffer vertexPos = new VertexBuffer(Type.Position);
        VertexBuffer vertexTexCoord = new VertexBuffer(Type.TexCoord);
        VertexBuffer vertexColor = new VertexBuffer(Type.Color);
        VertexBuffer indexBuffer = new VertexBuffer(Type.Index);
        
        vertexPos.setupData(Usage.Stream, 2, VertexBuffer.Format.Float, BufferUtils.createFloatBuffer(1));
        vertexTexCoord.setupData(Usage.Stream, 2, VertexBuffer.Format.Float, BufferUtils.createFloatBuffer(1));
        vertexColor.setupData(Usage.Stream, 4, VertexBuffer.Format.Float, BufferUtils.createFloatBuffer(1));
        indexBuffer.setupData(Usage.Stream, 3, VertexBuffer.Format.UnsignedShort, BufferUtils.createShortBuffer(1));
        
        setBuffer(vertexPos);
        setBuffer(vertexTexCoord);
        setBuffer(vertexColor);
        setBuffer(indexBuffer);
    }
    
    /**
     * Update a specific buffer in this mesh.
     * 
     * @param type VertexBuffer.Type
     * @param buffer FloatBuffer
     * @param sizeof int
     * @return NkMesh
     */
    public NkMesh updateBuffer(VertexBuffer.Type type, FloatBuffer buffer, int sizeof) {
        setBuffer(type, sizeof, buffer);
        return this;
    }
    
    /**
     * Update a specific buffer in this mesh.
     * 
     * @param type VertexBuffer.Type
     * @param buffer ShortBuffer
     * @param sizeof int
     * @return NkMesh
     */
    public NkMesh updateBuffer(VertexBuffer.Type type, ShortBuffer buffer, int sizeof) {
        setBuffer(type, sizeof, buffer);
        return this;
    }
}
