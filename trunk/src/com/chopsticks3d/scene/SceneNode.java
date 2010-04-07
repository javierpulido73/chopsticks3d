package com.chopsticks3d.scene;

import javax.microedition.khronos.opengles.GL10;

import com.chopsticks3d.math.MatrixMath;

import android.opengl.Matrix;

public class SceneNode {
	protected SceneNode mParent = null;
	protected SceneNode mChild = null;
	protected SceneNode mSibling = null;

	protected float mX = 0.0f;
	protected float mY = 0.0f;
	protected float mZ = 0.0f;

	protected float mScaleX = 1.0f;
	protected float mScaleY = 1.0f;
	protected float mScaleZ = 1.0f;

	protected float mRotationX = 0.0f;
	protected float mRotationY = 0.0f;
	protected float mRotationZ = 0.0f;

	protected float[] mModelView = new float[16];

	public void onDraw(GL10 gl) {
//		gl.glPushMatrix();
		
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
		
//		gl.glPopMatrix();
		
		if (mChild != null)
			mChild.onDraw(gl);

		if (mSibling != null)
			mSibling.onDraw(gl);
	}

	public void addChild(SceneNode child) {
		if (mChild == null) {
			mChild = child;
		} else {
			mChild.addSibling(child);
		}
		
		child.setParent(this);
	}

	protected void addSibling(SceneNode sibling) {
		if (mSibling == null) {
			mSibling = sibling;
		} else {
			mSibling.addSibling(sibling);
		}
	}

	protected void setParent(SceneNode parent) {
		mParent = parent;
	}
	
	public void setTransformation(float[] matrix) {
		mModelView = matrix;
	}
	
	public float[] getTransformation() {
		return mModelView;
	}

	public void setTranslation(float x, float y, float z) {
		mX = x;
		mY = y;
		mZ = z;
	}

	public void setTranslationX(float x) {
		mX = x;
	}

	public void setTranslationY(float y) {
		mY = y;
	}

	public void setTranslationZ(float z) {
		mZ = z;
	}

	public float getTranslationX() {
		return mX;
	}

	public float getTranslationY() {
		return mY;
	}

	public float getTranslationZ() {
		return mZ;
	}

	public void setScale(float scaleX, float scaleY, float scaleZ) {
		mScaleX = scaleX;
		mScaleY = scaleY;
		mScaleZ = scaleZ;
	}

	public void setScaleX(float scaleX) {
		mScaleX = scaleX;
	}

	public float getScaleX() {
		return mScaleX;
	}

	public void setScaleY(float scaleY) {
		mScaleY = scaleY;
	}

	public float getScaleY() {
		return mScaleY;
	}

	public void setScaleZ(float scaleZ) {
		mScaleZ = scaleZ;
	}

	public float getScaleZ() {
		return mScaleZ;
	}

	public void setRotation(float rotationX, float rotationY, float rotationZ) {
		mRotationX = rotationX;
		mRotationY = rotationY;
		mRotationZ = rotationZ;
	}

	public void setRotationX(float rotationX) {
		mRotationX = rotationX;
	}

	public float getRotationX() {
		return mRotationX;
	}

	public void setRotationY(float rotationY) {
		mRotationY = rotationY;
	}

	public float getRotationY() {
		return mRotationY;
	}

	public void setRotationZ(float rotationZ) {
		mRotationZ = rotationZ;
	}

	public float getRotationZ() {
		return mRotationZ;
	}
}
