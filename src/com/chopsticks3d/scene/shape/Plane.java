package com.chopsticks3d.scene.shape;

import com.chopsticks3d.scene.Mesh;

public class Plane extends Mesh {
	private static final char[] INDICES = {
		0,1,2,3
	};
	
	private static final float[] VERTICES = {
		   -0.5f,-0.5f, 0,
		    0.5f,-0.5f, 0,
		    0.5f, 0.5f, 0,
		   -0.5f, 0.5f, 0
	};
	
	private static final float[] NORMALS = {
			0,0,1,0,0,1,0,0,1,0,0,1
	};
	
	private static final float[] TEXCOORDS = {
			0,0,1,0,1,1,0,1
	};
	
	public Plane() {
		super();
		setDrawMode(MODE_TRIANGLE_FAN);
		setVertices(VERTICES);
		setIndices(INDICES);
		setNormals(NORMALS);
		setTexCoords(TEXCOORDS);
	}
}
