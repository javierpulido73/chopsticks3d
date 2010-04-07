package com.chopsticks3d.scene.camera;

import com.chopsticks3d.math.FastMath;

import android.opengl.Matrix;

public class Camera {
	private float[] project = {
			1,0,0,0,
			0,1,0,0,
			0,0,1,0,
			0,0,0,1
	};

	private float[] model = {
			1,0,0,0,
			0,1,0,0,
			0,0,1,0,
			0,0,0,1	
	};

	private static float aspect;

	private float[] x = new float[3]; 
	private float[] y = new float[3];
	private float[] z = new float[3];
	private float mag;

	//	private float[] position = new float[3];

	public Camera() {
		setPerspective(60.0f, 0.1f, 20.0f);
	}

	public static void setDisplayDimensions(int width, int height) {
		Camera.aspect = (float)width / height;
	}

	public void setPerspective(float fovy, float zNear, float zFar) {
		float tan_fovy_half = (float) Math.tan((fovy * FastMath.DEG_TO_RAD) / 2);
		project[5] = 1 / tan_fovy_half;  // = cot(fovy/2)

		// Remember, column major matrix
		project[0] = project[5] / aspect;
		project[1] = 0.0f;
		project[2] = 0.0f;
		project[3] = 0.0f;

		project[4] = 0.0f;
		//project[5] = 1 / near_height;  // already set
		project[6] = 0.0f;
		project[7] = 0.0f;

		project[8] = 0.0f;
		project[9] = 0.0f;
		project[10] = (zFar + zNear) / (zNear - zFar);
		project[11] = -1.0f;

		project[12] = 0.0f;
		project[13] = 0.0f;
		project[14] = (2 * zFar * zNear) / (zNear - zFar);
		project[15] = 0.0f;
	}

	/**
	 * @return the projection matrix 
	 */
	public float[] getProjectionM() {
		return project;
	}

	/**
	 * @param m the model-view matrix to set
	 */
	public void setModelM(float[] m) {
		synchronized(model) {
			for(int i = 0; i < 16; i++) {
				model[i] = m[i];
			}
		}
	}

	/**
	 * @return the model view matrix
	 */
	public float[] getModelM() {
		synchronized(model) {
			return model;
		}
	}

	/**
	 * Define a viewing transformation in terms of an eye point, a center of view, and an up vector.
	 * @param eyex eye x coordinate
	 * @param eyey eye y coordinate
	 * @param eyez eye z coordinate
	 * @param centerx view center x coordinate
	 * @param centery view center y coordinate
	 * @param centerz view center z coordinate
	 * @param upx up vector x coordinate
	 * @param upy up vector y coordinate
	 * @param upz up vector z coordinate
	 */
	public void lookAt(
			float eyex, float eyey, float eyez,
			float centerx, float centery, float centerz,
			float upx, float upy, float upz) {

		// Make rotation matrix

		// Z vector
		z[0] = eyex - centerx;
		z[1] = eyey - centery;
		z[2] = eyez - centerz;

		mag = Matrix.length(z[0], z[1], z[2]);
		if (mag > 0) {			// mpichler, 19950515
			mag = 1/mag;
			z[0] *= mag;
			z[1] *= mag;
			z[2] *= mag;
		}

		// Y vector
		y[0] = upx;
		y[1] = upy;
		y[2] = upz;

		// X vector = Y cross Z    	
		x[0] = y[1] * z[2] - y[2] * z[1];
		x[1] = -y[0] * z[2] + y[2] * z[0];
		x[2] = y[0] * z[1] - y[1] * z[0];

		// Recompute Y = Z cross X    	
		y[0] = z[1] * x[2] - z[2] * x[1];
		y[1] = -z[0] * x[2] + z[2] * x[0];
		y[2] = z[0] * x[1] - z[1] * x[0];

		mag = Matrix.length(x[0], x[1], x[2]);
		if (mag > 0) {
			mag = 1/mag;
			x[0] *= mag;
			x[1] *= mag;
			x[2] *= mag;
		}

		mag = Matrix.length(y[0], y[1], y[2]);
		if (mag > 0) {
			mag = 1/mag;
			y[0] *= mag;
			y[1] *= mag;
			y[2] *= mag;
		}

		synchronized(model) {
			model[0] = x[0];
			model[4] = x[1];
			model[8] = x[2];
			model[12] = 0.0f;
			model[1] = y[0];
			model[5] = y[1];
			model[9] = y[2];
			model[13] = 0.0f;
			model[2] = z[0];
			model[6] = z[1];
			model[10] = z[2];
			model[14] = 0.0f;
			model[3] = 0.0f;
			model[7] = 0.0f;
			model[11] = 0.0f;
			model[15] = 1.0f;

			//Matrix.multiplyMM(model, 0, m, 0, model, 0);
			// Translate Eye to Origin 
			Matrix.translateM(model, 0, -eyex, -eyey, eyez);
		}
	}
	
	public void setIdentity() {
		synchronized(model) {
			Matrix.setIdentityM(model, 0);
		}
	}
}