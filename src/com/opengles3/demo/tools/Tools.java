package com.opengles3.demo.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

public class Tools {
	public static final int BYTES_PER_FLOAT = 4;
	public static String textFromRawRes(Context context,int resId){
		StringBuilder body = new StringBuilder();
		try{
			InputStream input = context.getResources().openRawResource(resId);
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader buffReader = new BufferedReader(reader);
			String line;
			while((line = buffReader.readLine()) !=null){
				body.append(line);
				body.append('\n');
			}
		}catch(IOException e){
			throw new RuntimeException("Could not open resource: "+resId,e);
		}catch (Resources.NotFoundException nfe){
			throw new RuntimeException("Resource not found: "+resId,nfe);
		}
		return body.toString();
	}
	public static int loadTexture(Context context,int resourceId){
		final BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId,opts);
		if(bitmap == null){
			Log.w("Tools.loadTexture","Resource "+resourceId+" could not be loaded.");
			return 0;
		}
		int[] texturesObjectIds = new int[1];
		GLES30.glGenTextures(1, texturesObjectIds, 0);
		if(texturesObjectIds[0] == 0){
			Log.w("Tools.loadTexture","Could not generate a new OpenGL texture Object");
			return 0;
		}
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texturesObjectIds[0]);
		GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D ,GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
		//GL_NEAREST -nearest neighbour-filtering
		//GL_NEAREST_MIPMAP_NEAREST -nearest neighbour-filtering with mipmaps
		//GL_NEAREST_MIPMAP_LINEAR -nearest neighbour-filtering with interpolation between mipmap levels
		//GL_LINEAR -bilinear filtering
		//GL_LINEAR_MIPMAP_NEAREST -bilinear filtering with mipmaps
		//GL_LINEAR_MIPMAP_LINEAR -trilinear filtering (bilinear filtering with interpolation between mipmap levels)
		GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D ,GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
		//GL_NEAREST -nearest neighbour-filtering
		//GL_LINEAR -bilinear filtering
		GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
		//loading texture
		GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
		//generating mipmaps
		bitmap.recycle();
		//cleaning up bitmap
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
		//locking texture (so we don't edit it by mistake)
		return texturesObjectIds[0];
	}
	public static int compileShader(int type, String shaderCode){
		final int shObjId = GLES30.glCreateShader(type);
		if(shObjId == 0){
			Log.w("Tools.compileShader","Could not create new shader");
			return 0;
		}
		GLES30.glShaderSource(shObjId, shaderCode);
		GLES30.glCompileShader(shObjId);
		final int[] compileStatus = new int[1];
		GLES30.glGetShaderiv(shObjId, GLES30.GL_COMPILE_STATUS, compileStatus, 0);
		if(compileStatus[0] == 0){
			Log.w("Tools.compileShader","CouldN not compile shader");
			GLES30.glDeleteShader(shObjId);
			return 0;
		}
		return shObjId;
	}
	public static int buildProgram(String vertexShaderSource,String fragmentShaderSource){
		
		int vertexShader = compileShader(GLES30.GL_VERTEX_SHADER, vertexShaderSource);
		int fragmentShader = compileShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderSource);
		int program = linkProgram(vertexShader, fragmentShader);
		//validateProgram(program);
		return program;
		
	}
	public static int linkProgram(int glVertexShaderId, int glFragmentShaderId){
		final int progrObjId = GLES30.glCreateProgram();
		if(progrObjId == 0){
			Log.w("Tools.linkProgram","Could not create new program");
			return 0;
		}
		GLES30.glAttachShader(progrObjId, glVertexShaderId);
		GLES30.glAttachShader(progrObjId, glFragmentShaderId);
		
		GLES30.glLinkProgram(progrObjId);
		final int[] linkStatus = new int[1];
		GLES30.glGetProgramiv(progrObjId, GLES30.GL_LINK_STATUS, linkStatus, 0);
		if(linkStatus[0] == 0){
			Log.w("Tools.linkProgram","Could not link program");
			GLES30.glDeleteProgram(progrObjId);
			return 0;
		}
		return progrObjId;
	}
	public static boolean validateProgram(int glProgramId){
		GLES30.glValidateProgram(glProgramId);
		final int[] validateStatus = new int[1];
		GLES30.glGetProgramiv(glProgramId, GLES30.GL_VALIDATE_STATUS, validateStatus, 0);
		return validateStatus[0] != 0;
	}
	public static void perspectiveM(float[] m,float yFovInDegrees, float aspect, float n, float f){
		final float angleInRadians= (float) Math.toRadians(yFovInDegrees);
		final float A = (float) (1.0 / Math.tan(angleInRadians/2.0));
		for(int x=0;x<16;x++){
			m[x]=0f;
		}
		m[0]=A/aspect;
		m[5]=A;
		m[10]=-((f+n)/(f-n));
		m[11] = -1f;
		m[14]=-((2f*f*n)/(f-n));
	}
	
}
