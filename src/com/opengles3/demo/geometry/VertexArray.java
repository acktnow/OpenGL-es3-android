package com.opengles3.demo.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;

public class VertexArray {
	private FloatBuffer floatBuffer;
	private final static int BYTES_PER_FLOAT=4;
	public VertexArray(float[] vertexData){
		floatBuffer = ByteBuffer
				.allocateDirect(vertexData.length*BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(vertexData);
	}
	public void setVertexAttribPointer(int dataOffset,int attributeLocation, int componentCount, int stride){

		floatBuffer.position(dataOffset);
        GLES30.glVertexAttribPointer(attributeLocation, componentCount, GLES30.GL_FLOAT, 
            false, stride, floatBuffer);
        GLES30.glEnableVertexAttribArray(attributeLocation);
		floatBuffer.position(0);
	}
}
