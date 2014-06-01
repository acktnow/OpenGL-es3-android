package com.opengles3.demo.geometry;

import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES30;
import android.util.FloatMath;
import android.util.Log;

import com.opengles3.demo.geometry.Shapes.Circle;
import com.opengles3.demo.geometry.Shapes.Cylinder;
import com.opengles3.demo.geometry.Shapes.Point;

public class ObjectBuilder {
	private final static int FLOATS_PER_VERTEX = 3;
	private final float[] vertexData;
	private final List<DrawCommand> drawList = new ArrayList<DrawCommand>();
	private int offset=0;

	public ObjectBuilder(int sizeInVertices){
		vertexData = new float[sizeInVertices*FLOATS_PER_VERTEX];
	}

	public static interface DrawCommand {
		void draw();
	}
	
	private void appendCircleY(Circle circle, int numPoints){
		final int startVertex = offset / FLOATS_PER_VERTEX;
		final int numVertices = sizeOfCircleInVertices(numPoints);
		
		
		vertexData[offset++] = circle.center.x;
		vertexData[offset++] = circle.center.y;
		vertexData[offset++] = circle.center.z;
		
		
		for(int i=0; i<=numPoints; i++){
			float angleInRadians =
					((float)i/(float)numPoints)
					*((float)Math.PI *2f);
			vertexData[offset++] = circle.center.x
					+circle.radius * FloatMath.cos(angleInRadians);
			vertexData[offset++] = circle.center.y;
			vertexData[offset++] = circle.center.z
					+circle.radius * FloatMath.sin(angleInRadians);

			Log.v("circle","i="+i+";numPoints"+numPoints+";offset="+offset+";vertexData.length="+vertexData.length+";");
		}
		drawList.add(new DrawCommand() {
			@Override
			public void draw() {
				GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, startVertex, numVertices);
			}
		});
	}
	private void appendOpenCylinderY(Cylinder cylinder, int numPoints){
		final int startVertex = offset/FLOATS_PER_VERTEX;
		final int numVertices = sizeOfOpenCylinderInVertices(numPoints);
		final float yStart = cylinder.center.y - (cylinder.height/2f);
		final float yEnd = cylinder.center.y + (cylinder.height/2f);
		for(int i=0; i<=numPoints; i++){
			float angleInRadians =
					((float)i/(float)numPoints)
					*((float)Math.PI *2f);
			float xPosition = cylinder.center.x
					+cylinder.radius * FloatMath.cos(angleInRadians);
			float zPosition = cylinder.center.z
					+cylinder.radius * FloatMath.sin(angleInRadians);
			vertexData[offset++] = xPosition;
			vertexData[offset++] = yStart;
			vertexData[offset++] = zPosition;
			vertexData[offset++] = xPosition;
			vertexData[offset++] = yEnd;
			vertexData[offset++] = zPosition;
			Log.v("cylinder","i="+i+";numPoints"+numPoints+";offset="+offset+";vertexData.length="+vertexData.length+";");
		}
		drawList.add(new DrawCommand(){

			@Override
			public void draw() {
				GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, startVertex, numVertices);
				
			}
			
		});
	}
	private GeneratedData build(){
		return new GeneratedData(vertexData, drawList);
	}
	public static GeneratedData createPuck(Cylinder cylinder, int numPoints){
		int size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints);
		Log.v("puck",""+size);
		ObjectBuilder builder = new ObjectBuilder(size);
		Circle puckTop = new Circle(cylinder.center.translate(0, cylinder.height/2, 0), cylinder.radius);
		Log.v("puckCircle","numPoints="+numPoints);
		builder.appendCircleY(puckTop, numPoints);
		Log.v("puckCylinder","numPoints="+numPoints);
		builder.appendOpenCylinderY(cylinder, numPoints);
		

		
		return builder.build();
	}
	public static GeneratedData createMallet(Point center, float radius, float height, int numPoints){
		int size = sizeOfCircleInVertices(numPoints)*2 + sizeOfOpenCylinderInVertices(numPoints)*2;
		Log.v("mallet",""+size);
		ObjectBuilder builder = new ObjectBuilder(size);
		float baseHeight = height * 0.25f;
		Circle baseCircle = new Circle(center.translate(0, -baseHeight, 0), radius);
		Cylinder baseCylinder = new Cylinder(baseCircle.center.translate(0, -baseHeight/2f, 0), radius, baseHeight);
		Log.v("malletCircle1","numPoints="+numPoints);
		builder.appendCircleY(baseCircle, numPoints);
		Log.v("malletCylinder1","numPoints="+numPoints);
		builder.appendOpenCylinderY(baseCylinder, numPoints);
		float handleHeight = height *0.75f;
		float handleRadius = radius/3f;
		Circle handleCircle = new Circle(center.translate(0, height/2f, 0),handleRadius);
		Cylinder handleCylinder = new Cylinder(handleCircle.center.translate(0, -handleHeight/2f, 0), handleRadius, handleHeight);
		Log.v("malletCircle2","numPoints="+numPoints);
		builder.appendCircleY(handleCircle, numPoints);
		Log.v("malletCylinder2","numPoints="+numPoints);
		builder.appendOpenCylinderY(handleCylinder, numPoints);
		return builder.build();
	}
	private static int sizeOfCircleInVertices(int numPoints){
		return 1 + (numPoints +1);
	}
	private static int sizeOfOpenCylinderInVertices(int numPoints){
		return (numPoints + 1) *2;
	}
	public static class GeneratedData {
		public final float[] vertexData;
		public final List<DrawCommand> drawList;
		public GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
			this.vertexData = vertexData;
			this.drawList = drawList;
			
		}
	}
}
