package com.opengles3.demo.programs;

import com.example.opengl_es_3_demo.R;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;

public class ColorShaderProgram extends ShaderProgram {
	//Uniform locations
	private final int uMatrixLocation;
	private final int uColorLocation;
	
	//Attribute locations
	private final int aPositionLocation;
	
	public ColorShaderProgram(Context context){
		super(context, R.raw.color_vertex_shader,R.raw.color_fragment_shader);
		
		uMatrixLocation = GLES30.glGetUniformLocation(program, U_MATRIX);
		uColorLocation = GLES30.glGetUniformLocation(program, U_COLOR);
		
		aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION);
	}
	public void setUniforms(float[] matrix, float r, float g, float b){
		//pass the matrix to shader program
		GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		GLES30.glUniform4f(uColorLocation, r, g, b, 1f);
		
	}
	public int getPositionAttributeLocation(){
		return aPositionLocation;
	}
}
