package com.opengles3.demo.objects;

public class Table extends TexturedTriangleFan {
	private static float[] points = {
		 0.0f,  0.0f, 0.5f, 0.5f, 
		-0.5f, -0.8f, 0.0f, 0.9f,  
		 0.5f, -0.8f, 1.0f, 0.9f, 
		 0.5f,  0.8f, 1.0f, 0.1f, 
		-0.5f,  0.8f, 0.0f, 0.1f, 
		-0.5f, -0.8f, 0.0f, 0.9f
	};
	public Table(){
		super(points,2);
	}
}
