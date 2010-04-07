package com.chopsticks3d.scene.camera;

import com.chopsticks3d.scene.SceneNode;

import android.util.FloatMath;

public class ThirdPersonCamera extends Camera {
	private static float POSITION_Y = 5.0f;
	
	private SceneNode mNode;
	private float angle;
	private float x;
	private float z;
	
	public ThirdPersonCamera(SceneNode node) {
		mNode = node;
		update();
	}
	
	public void update() {
		float[] matrix = mNode.getTransformation();
		
		angle = mNode.getRotationY();
		
		//TODO Calculate position from angle
//		x = FloatMath.sin((PI/2 - (angle * 180/PI))) * RADIUS;
//		z = FloatMath.cos((PI/2 - (angle * 180/PI))) * RADIUS;
		
//		lookAt(matrix[12] - x, POSITION_Y, matrix[14] - z, matrix[12], matrix[13], matrix[14], 0, 1, 0);
		lookAt(matrix[12] + 3, POSITION_Y, matrix[14] + 3, matrix[12], matrix[13], matrix[14], 0, 1, 0);
	}
}