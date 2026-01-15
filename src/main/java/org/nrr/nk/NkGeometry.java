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

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.texture.Image;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture.MinFilter;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ColorSpace;
import com.jme3.util.BufferUtils;

import java.nio.ByteBuffer;

/**
 * A geometry that prepares the conditions or the form for Nuklear.
 * 
 * @author wil
 * @version 1.0.0
 * @since 1.0.0
 */
public class NkGeometry extends Geometry {

    /**
     * Constructor of the class <code>NkGeometry</code>.
     * 
     * @param assetManager AssetManager
     * @param texture boolean
     */
    public NkGeometry(AssetManager assetManager, boolean texture) {
        super("NkGeometry", new NkMesh());
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTransparent(true);        
        mat.setBoolean("VertexColor", true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        mat.getAdditionalRenderState().setDepthTest(false);
        mat.getAdditionalRenderState().setDepthWrite(false);
        
        if (texture) {
            Texture2D nullTexture = new Texture2D(1, 1, Image.Format.RGBA8);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4);
            buffer.putInt(0xFFFFFFFF).flip();

            Image image = new Image(Image.Format.BGRA8, 1, 1, buffer, ColorSpace.Linear);
            nullTexture.setImage(image);
            nullTexture.setMagFilter(MagFilter.Nearest);
            nullTexture.setMinFilter(MinFilter.NearestNoMipMaps);
            mat.setTexture("ColorMap", nullTexture);
        }
        
        setQueueBucket(RenderQueue.Bucket.Gui);
        setCullHint(CullHint.Never);
        setMaterial(mat);
    }
}
