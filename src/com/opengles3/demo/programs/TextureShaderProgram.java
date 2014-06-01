package com.opengles3.demo.programs;

import com.example.opengl_es_3_demo.R;

import android.content.Context;
import android.opengl.GLES30;

public class TextureShaderProgram extends ShaderProgram {
	//Uniform locations
	private final int uMatrixLocation;
	private final int uTextureUnitLocation;
	
	//Attribute locations
	private final int aPositionLocation;
	private final int aTextureCoordinatesLocation;
	
	public TextureShaderProgram(Context context){
		super(context, R.raw.texture_vertex_shader,R.raw.texture_fragment_shader);
		
		uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX);
		uTextureUnitLocation = GLES30.glGetUniformLocation(program, U_TEXTURE_UNIT);
		
		aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION);
		aTextureCoordinatesLocation = GLES30.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
		
	}
	public void setUniforms(float[] matrix, int textureId){
		//pass the matrix to shader program
		GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		//set active texture as unit 0
		GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
		//bind texture to unit 0
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
		//inform sampler to use texture in shader from unit 0
		GLES30.glUniform1i(uTextureUnitLocation, 0);
		
		
	}
	public int getPositionAttributeLocation(){
		return aPositionLocation;
	}
	public int getTextureCoordinateAttributeLocation(){
		return aTextureCoordinatesLocation;
	}
}
