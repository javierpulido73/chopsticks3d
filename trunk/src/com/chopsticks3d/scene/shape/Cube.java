package com.chopsticks3d.scene.shape;

import com.chopsticks3d.scene.Mesh;

public class Cube extends Mesh {
	private static final char[] INDICES = {
			 0, 1, 2,	 2, 3, 0,	//front
			 5, 4, 7,	 7, 6, 5,	//back
			 8, 9,10,	10,11, 8,	//right
			12,13,14,	14,15,12,	//left
			16,17,18,	18,19,16,	//top
			20,21,22,	22,23,20	//bottom
	};
	
	private static final float[] VERTICES = {
		   -0.5f,-0.5f, 0.5f,	0.5f,-0.5f, 0.5f,	//0,1 = front
			0.5f, 0.5f, 0.5f,  -0.5f, 0.5f, 0.5f,	//2,3
		   -0.5f,-0.5f,-0.5f,	0.5f,-0.5f,-0.5f,	//4,5 = back
			0.5f, 0.5f,-0.5f,  -0.5f, 0.5f,-0.5f, 	//6,7			(index below)
			0.5f,-0.5f, 0.5f,	0.5f,-0.5f,-0.5f,	//1,5 = right	8,9
			0.5f, 0.5f,-0.5f,	0.5f, 0.5f, 0.5f,	//6,2			10,11
		   -0.5f,-0.5f,-0.5f,  -0.5f,-0.5f, 0.5f,	//4,0 = left	12,13
		   -0.5f, 0.5f, 0.5f,  -0.5f, 0.5f,-0.5f,	//3,7			14,15
		   -0.5f, 0.5f, 0.5f,	0.5f, 0.5f, 0.5f,	//3,2 = top		16,17
			0.5f, 0.5f,-0.5f,  -0.5f, 0.5f,-0.5f,	//6,7			18,19
			0.5f,-0.5f, 0.5f,  -0.5f,-0.5f, 0.5f,	//1,0 = bottom	20,21
		   -0.5f,-0.5f,-0.5f,	0.5f,-0.5f,-0.5f	//4,5			22,23
	};
		
	private static final float[] NORMALS = {
			 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,	//front
			 0, 0,-1, 0, 0,-1, 0, 0,-1, 0, 0,-1,	//back
			 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,	//right
			-1, 0, 0,-1, 0, 0,-1, 0, 0,-1, 0, 0,	//left
			 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,	//top
			 0,-1, 0, 0,-1, 0, 0,-1, 0, 0,-1, 0		//bottom
	};
		
	private static final float[] TEXCOORDS = {
			0,0,1,0,1,1,0,1,	//front
			0,0,1,0,1,1,0,1,	//back
			0,0,1,0,1,1,0,1,	//right
			0,0,1,0,1,1,0,1,	//left
			0,0,1,0,1,1,0,1,	//top
			0,0,1,0,1,1,0,1		//bottom
	};
	
	public Cube() {
		super();
		setVertices(VERTICES);
		setIndices(INDICES);
		setNormals(NORMALS);
		setTexCoords(TEXCOORDS);
	}
}
