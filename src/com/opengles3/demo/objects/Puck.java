package com.opengles3.demo.objects;



import java.util.List;

import com.opengles3.demo.geometry.ObjectBuilder;
import com.opengles3.demo.geometry.VertexArray;
import com.opengles3.demo.geometry.ObjectBuilder.DrawCommand;
import com.opengles3.demo.geometry.Shapes.Cylinder;
import com.opengles3.demo.geometry.Shapes.Point;
import com.opengles3.demo.programs.ColorShaderProgram;

public class Puck {
	private static final int POSITION_COMPONENT_COUNT = 3;
	public final float radius, height;
	private final VertexArray vertexArray;
	private final List<DrawCommand> drawList;
	public Puck(float radius, float height, int numPointsAroundPuck) {
		ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createPuck(
				new Cylinder(new Point(0f, 0f, 0f), radius, height),
				numPointsAroundPuck);
		this.radius = radius;
		this.height = height;
		vertexArray = new VertexArray(generatedData.vertexData);
		drawList = generatedData.drawList;
	}
	public void bindData(ColorShaderProgram colorProgram){
		vertexArray.setVertexAttribPointer(0,
				colorProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, 0);
		
	}
	public void draw(){
		for(DrawCommand drawCommand : drawList){
			drawCommand.draw();
		}
	}
}
