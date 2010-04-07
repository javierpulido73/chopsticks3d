package com.chopsticks3d.scene;

public class Texture {
	private static final int NULL = -1;
	
	public int resourceId = NULL;
	public int textureId = NULL;
	
	public Texture(int resource) {
		this.resourceId = resource;
		
		TextureHandler.addTexture(this);
	}
}
