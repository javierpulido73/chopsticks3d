package com.chopsticks3d.scene;

import javax.microedition.khronos.opengles.GL10;

public class Material {
	public static float[] DEFAULT_AMBIENT = {0.2f, 0.2f, 0.2f, 1.0f};
	public static float[] DEFAULT_DIFFUSE = {0.8f, 0.8f, 0.8f, 1.0f};
	public static float[] DEFAULT_SPECULAR = {0.0f, 0.0f, 0.0f, 1.0f};
	public static float[] DEFAULT_EMISSION = {0.0f, 0.0f, 0.0f, 1.0f};
	public static float DEFAULT_SHININESS = 0.0f;
	
	private static float[] mCurrentAmbient;
	private static float[] mCurrentDiffuse;
	private static float[] mCurrentSpecular;
	private static float[] mCurrentEmission;
	private static float mCurrentShininess;
	private static boolean mCurrentUseColorMaterial;
	
	private float[] mAmbient;
	private float[] mDiffuse;
	private float[] mSpecular;
	private float[] mEmission;
	private float mShininess; // 0 - 128
	private boolean mUseColorMaterial;
	
	/**
	 * Sets the material to the one specified
	 * @param gl
	 */
	public void applyMaterial(GL10 gl) {
		if(mCurrentUseColorMaterial != mUseColorMaterial) {
			mCurrentUseColorMaterial = mUseColorMaterial;
			if(mCurrentUseColorMaterial) {
				gl.glEnable(GL10.GL_COLOR_MATERIAL);
			} else {
				gl.glDisable(GL10.GL_COLOR_MATERIAL);
			}
		}
		
		if(mAmbient != null) {
			if(mCurrentAmbient == null || !sameColor(mCurrentAmbient, mAmbient)) {
				mCurrentAmbient = mAmbient;
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mAmbient, 0);
			}
		} else if(mCurrentAmbient != null && !sameColor(mCurrentAmbient, DEFAULT_AMBIENT)) {
			mCurrentAmbient = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, DEFAULT_AMBIENT, 0);
		}
		
		if(mDiffuse != null) {
			if(mCurrentDiffuse == null || !sameColor(mCurrentDiffuse, mDiffuse)) {
				mCurrentDiffuse = mDiffuse;
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mDiffuse, 0);
			}
		} else if(mCurrentDiffuse != null && !sameColor(mCurrentDiffuse, DEFAULT_DIFFUSE)) {
			mCurrentDiffuse = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, DEFAULT_DIFFUSE, 0);
		}
		
		if(mEmission != null) {
			if(mCurrentEmission == null || !sameColor(mCurrentEmission, mEmission)) {
				mCurrentEmission = mEmission;
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, mEmission, 0);
			}
		} else if(mCurrentEmission != null && !sameColor(mCurrentEmission, DEFAULT_EMISSION)) {
			mCurrentEmission = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, DEFAULT_EMISSION, 0);
		}
		
		if(mSpecular != null) {
			if(mCurrentSpecular == null || !sameColor(mCurrentSpecular, mSpecular)) {
				mCurrentSpecular = mSpecular;
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mSpecular, 0);
			}
		} else if(mCurrentSpecular != null && !sameColor(mCurrentSpecular, DEFAULT_SPECULAR)) {
			mCurrentSpecular = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, DEFAULT_SPECULAR, 0);
		}
		
		if(mCurrentShininess != mShininess) {
			mCurrentShininess = mShininess;
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mShininess);
		}
	}
	
	/**
	 * Sets the material to the OpenGL standards
	 * @param gl
	 */
	public static void removeMaterial(GL10 gl) {
		
		if(mCurrentUseColorMaterial) {
			mCurrentUseColorMaterial = false;
			gl.glDisable(GL10.GL_COLOR_MATERIAL);
		}
		
		if(mCurrentAmbient != null && !sameColor(mCurrentAmbient, DEFAULT_AMBIENT)) {
			mCurrentAmbient = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, DEFAULT_AMBIENT, 0);
		}
		
		if(mCurrentDiffuse != null && !sameColor(mCurrentDiffuse, DEFAULT_DIFFUSE)) {
			mCurrentDiffuse = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, DEFAULT_DIFFUSE, 0);
		}
		
		if(mCurrentEmission != null && !sameColor(mCurrentEmission, DEFAULT_EMISSION)) {
			mCurrentEmission = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, DEFAULT_EMISSION, 0);
		}
		
		if(mCurrentSpecular != null && !sameColor(mCurrentSpecular, DEFAULT_SPECULAR)) {
			mCurrentSpecular = null;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, DEFAULT_SPECULAR, 0);
		}
		
		if(mCurrentShininess != DEFAULT_SHININESS) {
			mCurrentShininess = DEFAULT_SHININESS;
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, DEFAULT_SHININESS);
		}
	}

	/**
	 * @param color4f the ambient to set
	 */
	public void setAmbient(float[] color4f) {
		if(color4f == null || color4f.length != 4) {
			return;
		}
		if(mAmbient == null) {
			mAmbient = new float[4];
		}
		this.mAmbient[0] = color4f[0];
		this.mAmbient[1] = color4f[1]; 
		this.mAmbient[2] = color4f[2]; 
		this.mAmbient[3] = color4f[3]; 
	}
	
	/**
	 * Set the ambient
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a alpha component
	 */
	public void setAmbient(float r, float g, float b, float a) {
		if(mAmbient == null) {
			mAmbient = new float[4];
		}
		this.mAmbient[0] = r;
		this.mAmbient[1] = g; 
		this.mAmbient[2] = b; 
		this.mAmbient[3] = a; 
	}

	/**
	 * @return the ambient
	 */
	public float[] getAmbient() {
		return mAmbient;
	}
	
	/**
	 * @param color4f the diffuse to set
	 */
	public void setDiffuse(float[] color4f) {
		if(color4f == null || color4f.length != 4) {
			return;
		}
		if(mDiffuse == null) {
			mDiffuse = new float[4];
		}
		this.mDiffuse[0] = color4f[0];
		this.mDiffuse[1] = color4f[1]; 
		this.mDiffuse[2] = color4f[2]; 
		this.mDiffuse[3] = color4f[3]; 
	}
	
	/**
	 * Set the diffuse
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a alpha component
	 */
	public void setDiffuse(float r, float g, float b, float a) {
		if(mDiffuse == null) {
			mDiffuse = new float[4];
		}
		this.mDiffuse[0] = r;
		this.mDiffuse[1] = g; 
		this.mDiffuse[2] = b; 
		this.mDiffuse[3] = a; 
	}

	/**
	 * @return the diffuse
	 */
	public float[] getDiffuse() {
		return mDiffuse;
	}
	
	/**
	 * @param color4f the emission to set
	 */
	public void setEmission(float[] color4f) {
		if(color4f == null || color4f.length != 4) {
			return;
		}
		if(mEmission == null) {
			mEmission = new float[4];
		}
		this.mEmission[0] = color4f[0];
		this.mEmission[1] = color4f[1]; 
		this.mEmission[2] = color4f[2]; 
		this.mEmission[3] = color4f[3]; 
	}
	
	/**
	 * Set the emission
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a alpha component
	 */
	public void setEmission(float r, float g, float b, float a) {
		if(mEmission == null) {
			mEmission = new float[4];
		}
		this.mEmission[0] = r;
		this.mEmission[1] = g; 
		this.mEmission[2] = b; 
		this.mEmission[3] = a; 
	}

	/**
	 * @return the emission
	 */
	public float[] getEmission() {
		return mEmission;
	}
	
	/**
	 * @param color4f the specular to set
	 */
	public void setSpecular(float[] color4f) {
		if(color4f == null || color4f.length != 4) {
			return;
		}
		if(mSpecular == null) {
			mSpecular = new float[4];
		}
		this.mSpecular[0] = color4f[0];
		this.mSpecular[1] = color4f[1]; 
		this.mSpecular[2] = color4f[2]; 
		this.mSpecular[3] = color4f[3]; 
	}
	
	/**
	 * Set the specular
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a alpha component
	 */
	public void setSpecular(float r, float g, float b, float a) {
		if(mSpecular == null) {
			mSpecular = new float[4];
		}
		this.mSpecular[0] = r;
		this.mSpecular[1] = g; 
		this.mSpecular[2] = b; 
		this.mSpecular[3] = a; 
	}

	/**
	 * @return the specular
	 */
	public float[] getSpecular() {
		return mSpecular;
	}
	
	/**
	 * @param shininess the shininess to set
	 */
	public void setShininess(float shininess) {
		this.mShininess = shininess;
	}

	/**
	 * @return the shininess
	 */
	public float getShininess() {
		return mShininess;
	}
	
	/**
	 * @param b true if this material should use vertex colors
	 */
	public void setUseColorMaterial(boolean b) {
		mUseColorMaterial = b;
	}
	
	/**
	 * @return true if this material uses vertex colors
	 */
	public boolean usesColorMaterial() {
		return mUseColorMaterial;
	}
	
	// maybe not so clever
	private static boolean sameColor(float[] c1, float[] c2) {
		if(c1 == c2) {
			return true;
		}
		return (
				c1[0] == c2[0] &&
				c1[1] == c2[1] &&
				c1[2] == c2[2] &&
				c1[3] == c2[3]
		);
	}
}
