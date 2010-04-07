package com.chopsticks3d.scene;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.Matrix;

import com.chopsticks3d.math.MatrixMath;

public class LightNode extends SceneNode {
	public final static int MAX_LIGHTS = 8;
	private static int mCurrentLights = 0;
	protected int mLightId = -1;
	
	private Light mLight;

	public LightNode(Light light) {
		mLight = light;
		mLightId = getId();
	}
	
	private static int getId() {
		if(mCurrentLights < MAX_LIGHTS) {
			mCurrentLights++;
			switch(mCurrentLights) {
			case 1:
				return GL10.GL_LIGHT0;
			case 2:
				return GL10.GL_LIGHT1;
			case 3:
				return GL10.GL_LIGHT2;
			case 4:
				return GL10.GL_LIGHT3;
			case 5:
				return GL10.GL_LIGHT4;
			case 6:
				return GL10.GL_LIGHT5;
			case 7:
				return GL10.GL_LIGHT6;
			case 8:
				return GL10.GL_LIGHT7;
			}
		}
		return -1;
	}

	public void onDraw(GL10 gl) {

		if (mParent != null) {
			System.arraycopy(mParent.mModelView, 0, mModelView, 0, 16);
		} else {
			Matrix.setIdentityM(mModelView, 0);
		}

		Matrix.translateM(mModelView, 0, mX, mY, mZ);
		MatrixMath.rotateM(mModelView, mRotationX, MatrixMath.AXIS_X);
		MatrixMath.rotateM(mModelView, mRotationY, MatrixMath.AXIS_Y);
		MatrixMath.rotateM(mModelView, mRotationZ, MatrixMath.AXIS_Z);
		Matrix.scaleM(mModelView, 0, mScaleX, mScaleY, mScaleZ);
		
		mLight.onDraw(gl, mModelView, mLightId);

		if (mChild != null)
			mChild.onDraw(gl);

		if (mSibling != null)
			mSibling.onDraw(gl);
	}
	
	
}
