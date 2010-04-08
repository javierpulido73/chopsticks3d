package com.chopsticks3d.scene;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.Matrix;

import com.chopsticks3d.math.MatrixMath;

public class MeshNode extends SceneNode {
	private Mesh mMesh;
	private Texture mTexture;
	private Material mMaterial;
	
	public MeshNode(Mesh mesh) {
		mMesh = mesh;
	}
	
	public MeshNode(Mesh mesh, Texture texture) {
		mMesh = mesh;
		mTexture = texture;
	}
	
	public MeshNode(Mesh mesh, Material material) {
		mMesh = mesh;
		mMaterial = material;
	}
	
	public MeshNode(Mesh mesh, Texture texture, Material material) {
		mMesh = mesh;
		mTexture = texture;
		mMaterial = material;
	}
	
	@Override
	public void onDraw(GL10 gl) {
		gl.glPushMatrix();
		
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
		
		gl.glMultMatrixf(mModelView, 0);
		
		if (mTexture != null) {
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.textureId);
		} else {
			gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		}
		
		mMesh.onDraw(gl);
		
		gl.glPopMatrix();

		if (mChild != null)
			mChild.onDraw(gl);
		
		if (mSibling != null)
			mSibling.onDraw(gl);
	}
	
	public void setMesh(Mesh mesh) {
		mMesh = mesh;
	}
	
	public Mesh getMesh() {
		return mMesh;
	}
	
	public void setTexture(Texture texture) {
		mTexture = texture;
	}
	
	public Texture getTexture() {
		return mTexture;
	}
	
	public void setMaterial(Material material) {
		mMaterial = material;
	}
	
	public Material getMaterial() {
		return mMaterial;
	}
}
