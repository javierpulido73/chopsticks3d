package com.chopsticks3d.math;

public class FastMath {
	public static final float DEG_TO_RAD = 0.01745329238474369f;
	public static final float RAD_TO_DEG = 57.2957763671875f;
	public static final float PI = 3.14159265358979323f;
	
	/** Prevents instantiation. */
	private FastMath() {}

	public static float sin(float angle) {
		return (float)Math.sin((double)angle);
	}

	public static float cos(float angle) {
		return (float)Math.cos((double)angle);
	}

	public static float sqrt(float value) {
		return (float)Math.sqrt((double)value);
	}
}
