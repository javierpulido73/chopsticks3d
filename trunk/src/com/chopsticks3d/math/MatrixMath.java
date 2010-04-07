package com.chopsticks3d.math;

import android.opengl.Matrix;
import android.util.FloatMath;

public class MatrixMath {
	private static final float DEG_TO_RAD = FastMath.DEG_TO_RAD;
	
	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	public static final int AXIS_Z = 2;
	
	private static float cosA;
	private static float sinA;
	
	private static float[] tempM = new float[16];
	private static float[] tempM2 = new float[16];
	
	public static void rotateM(float[] m, float angle, int axis) {
		Matrix.setIdentityM(tempM, 0);
		
		cosA = FloatMath.cos(angle * DEG_TO_RAD);
		sinA = FloatMath.sin(angle * DEG_TO_RAD);
		
		if (axis == AXIS_X) {
			tempM[5] = cosA;
			tempM[6] = sinA;
			tempM[9] = -sinA;
			tempM[10] = cosA;
		} else if (axis == AXIS_Y) {
			tempM[10] = cosA;
			tempM[8] = sinA;
			tempM[2] = -sinA;
			tempM[0] = cosA;
		} else if (axis == AXIS_Z) {
			tempM[0] = cosA;
			tempM[1] = sinA;
			tempM[4] = -sinA;
			tempM[5] = cosA;
		}
		
		System.arraycopy(m, 0, tempM2, 0, 16);
		
		Matrix.multiplyMM(m, 0, tempM2, 0, tempM, 0);
	}
}
