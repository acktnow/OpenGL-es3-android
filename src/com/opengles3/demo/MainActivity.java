package com.opengles3.demo;

import com.example.opengl_es_3_demo.R;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static String TAG="MainActivity";
	private Boolean supportEs2=false;
	private GLTextureView glTextureView;
	private boolean rendererSet = false;
	
	private GLRenderer renderer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = manager.getDeviceConfigurationInfo();
		supportEs2 = info.reqGlEsVersion >= 0x30000;
		if(supportEs2){
			renderer = new GLRenderer(this);
			Log.d(TAG, "supports OpenGL ES 3.0");
			glTextureView = new GLTextureView(this);
			glTextureView.setEGLContextClientVersion(3);
			
			glTextureView.setRenderer(renderer);
			rendererSet = true;

			glTextureView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final float normalizedX = (event.getX()/(float)v.getWidth())*2-1;
					final float normalizedY = -((event.getY()/(float)v.getHeight())*2-1);
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						glTextureView.queueEvent(new Runnable() {
							@Override
							public void run() {
								renderer.handleTouchPress(normalizedX, normalizedY);
							}
						});
						return true;
					}else if(event.getAction() == MotionEvent.ACTION_MOVE){
						glTextureView.queueEvent(new Runnable() {
							@Override
							public void run() {
								renderer.handleTouchDrag(normalizedX, normalizedY);
							}
						});
						return true;
					}else{
						return false;
					}
				}
			});
			setContentView(glTextureView);
		}else{
			Toast.makeText(this, "This device does not support OpenGL ES 3.0", Toast.LENGTH_LONG).show();
			
		}
	}
	@Override
	protected void onPause() {
		if(rendererSet){
			glTextureView.onPause();
		}
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		if(rendererSet){
			glTextureView.onResume();
		}
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
