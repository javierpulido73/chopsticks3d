package com.chopsticks3d.scene;

import javax.microedition.khronos.opengles.GL10;

public class Light {
	private float[] pos4f = new float[4];
	private float[] ambient4f;
	private float[] diffuse4f;
	private float[] specular4f;

	public void onDraw(GL10 gl, float[] modelView, int lightId) {
		if(lightId != -1) {
			pos4f[0] = modelView[12];
			pos4f[1] = modelView[13];
			pos4f[2] = modelView[14];
			pos4f[3] = 1;
			gl.glLightfv(lightId, GL10.GL_POSITION, pos4f, 0);
			
			if(ambient4f != null) {
				gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient4f, 0);
			}
			if(specular4f != null) {
				gl.glLightfv(lightId, GL10.GL_SPECULAR, specular4f, 0);
			}
			if(diffuse4f != null) {
				gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse4f, 0);
			}
			
			gl.glLightf(lightId, GL10.GL_CONSTANT_ATTENUATION, 1.0f);
		    gl.glLightf(lightId, GL10.GL_LINEAR_ATTENUATION, 0.05f);
		    gl.glLightf(lightId, GL10.GL_QUADRATIC_ATTENUATION, 0.03f);
			
			
			gl.glEnable(lightId);
		}
	}

	public void setAmbient(float r, float g, float b, float a) {
		if(ambient4f == null) {
			ambient4f = new float[4];
		}
		ambient4f[0] = r;
		ambient4f[1] = g;
		ambient4f[2] = b;
		ambient4f[3] = a;
	}

	public void setAmbient(float[] rgba4f) {
		if(rgba4f == null) {
			ambient4f = null;
			return;
		}
		if(rgba4f.length == 4) {
			if(ambient4f == null) {
				ambient4f = new float[4];
			}
			for(int i = 0; i < 4; i++) {
				ambient4f[i] = rgba4f[i];
			}
		}
	}

	public float[] getAmbient() {
		return ambient4f;
	}

	/**
	 * Set the diffuse
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a alpha component
	 */
	public void setDiffuse(float r, float g, float b, float a) {
		if(diffuse4f == null) {
			diffuse4f = new float[4];
		}
		diffuse4f[0] = r;
		diffuse4f[1] = g;
		diffuse4f[2] = b;
		diffuse4f[3] = a;
	}

	/**
	 * Set the diffuse
	 * @param rgba4f color to set
	 */
	public void setDiffuse(float[] rgba4f) {
		if(rgba4f == null) {
			diffuse4f = null;
			return;
		}
		if(rgba4f.length == 4) {
			if(diffuse4f == null) {
				diffuse4f = new float[4];
			}
			for(int i = 0; i < 4; i++) {
				diffuse4f[i] = rgba4f[i];
			}
		}
	}

	/**
	 * @return the diffuse
	 */
	public float[] getDiffuse() {
		return diffuse4f;
	}

	/**
	 * Set the specular
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a alpha component
	 */
	public void setSpecular(float r, float g, float b, float a) {
		if(specular4f == null) {
			specular4f = new float[4];
		}
		specular4f[0] = r;
		specular4f[1] = g;
		specular4f[2] = b;
		specular4f[3] = a;
	}

	/**
	 * Set the specular
	 * @param rgba4f specular to set
	 */
	public void setSpecular(float[] rgba4f) {
		if(rgba4f == null) {
			specular4f = null;
			return;
		}
		if(rgba4f.length == 4) {
			if(specular4f == null) {
				specular4f = new float[4];
			}
			for(int i = 0; i < 4; i++) {
				specular4f[i] = rgba4f[i];
			}
		}
	}

	/**
	 * @return the specular
	 */
	public float[] getSpecular() {
		return specular4f;
	}
}
