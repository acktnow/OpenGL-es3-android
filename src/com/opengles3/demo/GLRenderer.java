package com.opengles3.demo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.opengl_es_3_demo.R;
import com.opengles3.demo.objects.Mallet;
import com.opengles3.demo.objects.Puck;
import com.opengles3.demo.objects.Table;
import com.opengles3.demo.programs.ColorShaderProgram;
import com.opengles3.demo.programs.TextureShaderProgram;
import com.opengles3.demo.tools.Tools;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class GLRenderer implements GLTextureView.Renderer {
	private Context context;
	private final float[] viewMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16]; 
	private final float[] modelViewProjectionMatrix = new float[16];
	
	
	private float[] projectionMatrix = new float[16];
	private float[] modelMatrix = new float[16];
	private Table table;
	private Mallet mallet;
	private Puck puck;
	private TextureShaderProgram textureProgram;
	private ColorShaderProgram colorProgram;
	private int glTexture;
	public GLRenderer(Context context) {
		this.context = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES30.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
		
		table = new Table();
		mallet = new Mallet(0.08f, 0.15f, 32);
		puck = new Puck(0.06f, 0.02f, 32);
		
		textureProgram = new TextureShaderProgram(context);
		colorProgram = new ColorShaderProgram(context);
		
		glTexture = Tools.loadTexture(context, R.drawable.texture);
		
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
		
		Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		
		positionTableInScene();
		textureProgram.useProgram();
		textureProgram.setUniforms(modelViewProjectionMatrix, glTexture);
		table.bindData(textureProgram);
		table.draw();
		
		positionObjectInScene(0f, mallet.height / 2f, -0.4f);
		colorProgram.useProgram();
		colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
		mallet.bindData(colorProgram);
		mallet.draw();
		
		positionObjectInScene(0f, mallet.height / 2f, 0.4f);
		colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
		mallet.draw();
		
		positionObjectInScene(0f, puck.height / 2f, 0f);
		colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
		puck.bindData(colorProgram);
		puck.draw();
		
		
		
		
	}
	
	
	private void positionObjectInScene(float x, float y, float z) {
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, x, y, z);
		Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
				0, modelMatrix, 0);
	}
	private void positionTableInScene(){
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
		Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 
				0, modelMatrix, 0);
	}
	
	
	
	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		// TODO Auto-generated method stub
		GLES30.glViewport(0, 0, width, height);
		
		
		
		final float aspectRatio = width > height ? 
			(float) width / (float) height : 
			(float) height / (float) width;

		if (width > height) {
			// Landscape
			Matrix.orthoM(projectionMatrix, 0, 
				-aspectRatio, aspectRatio, 
				-1f, 1f, 
				-1f, 1f);
		} else {
			// Portrait or square
			Matrix.orthoM(projectionMatrix, 0, 
				-1f, 1f, 
				-aspectRatio, aspectRatio, 
				-1f, 1f);
		} 
		//field of fision 45deg starting in 1 and ending in 10
		Tools.perspectiveM(projectionMatrix, 45, (float)width/(float)height, 1f, 10f);
		
		Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
	}

	public void handleTouchPress(float normX, float normY){
		
	}
	public void handleTouchDrag(float normX, float normY){
		
	}

}
