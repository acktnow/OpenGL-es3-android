package com.opengles3.demo.programs;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;

import com.opengles3.demo.tools.Tools;

public abstract class ShaderProgram {
	//Uniforms
	protected static final String U_MATRIX = "u_Matrix";
	protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
	protected static final String U_COLOR = "u_Color";
	//Attributes
	protected static final String A_POSITION = "a_Position";
	protected static final String A_COLOR = "a_Color";
	protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
	
	protected final int program;
	
	protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){
		String vertexShaderSource = Tools.textFromRawRes(context, vertexShaderResourceId);
		String fragmentShaderSource = Tools.textFromRawRes(context, fragmentShaderResourceId);
		program = Tools.buildProgram(vertexShaderSource, fragmentShaderSource);
		
	}
	public void useProgram(){
		GLES30.glUseProgram(program);
	}
}
