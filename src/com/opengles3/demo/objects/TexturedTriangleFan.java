package com.opengles3.demo.objects;

import com.opengles3.demo.geometry.VertexArray;
import com.opengles3.demo.programs.TextureShaderProgram;

import android.graphics.Color;
import android.opengl.GLES30;
import android.util.Log;

public abstract class TexturedTriangleFan {
	protected int POSITION_COMPONENT_COUNT;
	protected int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
	protected static final int BYTES_PER_FLOAT=4;
	protected int STRIDE;
	private float[] vertices;
	
	protected int pointsCount;
	
	private final VertexArray vertexArray;
	
	protected TexturedTriangleFan(float[] vertices,int positionComponentCount){
		vertexArray = new VertexArray(vertices);
		this.vertices = vertices;
		POSITION_COMPONENT_COUNT=positionComponentCount;
		pointsCount = (vertices.length/(TEXTURE_COORDINATES_COMPONENT_COUNT+POSITION_COMPONENT_COUNT));
		STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)*BYTES_PER_FLOAT;
		
	}
	public void bindData(TextureShaderProgram textureProgram){
		vertexArray.setVertexAttribPointer(
				0,
				textureProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT,
				STRIDE);
		vertexArray.setVertexAttribPointer(
				POSITION_COMPONENT_COUNT,
				textureProgram.getTextureCoordinateAttributeLocation(),
				TEXTURE_COORDINATES_COMPONENT_COUNT,
				STRIDE);
	}
	public void draw(){
		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, pointsCount);
	}
	
	
	protected int getVerticesLength(){
		return this.vertices.length;
	}
}
