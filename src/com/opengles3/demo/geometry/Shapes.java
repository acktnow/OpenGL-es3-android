package com.opengles3.demo.geometry;

public class Shapes {
	public static class Point{
		public final float x, y, z;
		public Point(float x, float y, float z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		public Point translate(float x, float y, float z){
			return new Point(this.x+x, this.y+y, this.z+z);
		}
	}
	public static class Circle{
		public final Point center;
		public final float radius;
		public Circle(Point center, float radius) {
			this.center = center;
			this.radius = radius;
		}
		public Circle scale(float scale){
			return new Circle(center, radius*scale);
		}
		
	}
	public static class Cylinder{
		public final Point center;
		public final float radius;
		public final float height;
		public Cylinder(Point center, float radius, float height){
			this.center = center;
			this.radius = radius;
			this.height = height;
		}
		public Cylinder scale(float hScale, float rScale){
			return new Cylinder(center, hScale*height, rScale*radius);
		}
	}
}
