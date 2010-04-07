package com.chopsticks3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.chopsticks3d.scene.MeshHandler;
import com.chopsticks3d.scene.SceneNode;
import com.chopsticks3d.scene.TextureHandler;
import com.chopsticks3d.scene.camera.Camera;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ChopsticksView extends GLSurfaceView {
	private Context mContext;
	private Runnable mEvent = null;
	private SceneNode mScene = null;
	private Camera mCamera = null;
	private boolean newCamera = false;

	public ChopsticksView(Context context) {
		super(context);
		mContext = context;

		setRenderer(new Renderer());
	}

	public void setEvent(Runnable event) {
		mEvent = event;
	}
	
	public void setScene(SceneNode scene) {
		mScene = scene;
	}
	
	public void setCamera(Camera camera) {
		mCamera = camera;
		newCamera = true;
	}
	
	private class Renderer implements GLSurfaceView.Renderer {
		@Override
		public void onDrawFrame(GL10 gl) {
			if (TextureHandler.containsNew()) {
				TextureHandler.loadTextures(gl, mContext);
			}
			
			if (MeshHandler.containsNew()) {
				MeshHandler.generateHardwareBuffers(gl);
			}
			
			if (mEvent != null) {
				mEvent.run();
			}
			
			if (newCamera) {
				gl.glMatrixMode(GL10.GL_PROJECTION);
				gl.glLoadMatrixf(mCamera.getProjectionM(), 0);				
				gl.glMatrixMode(GL10.GL_MODELVIEW);
			}

			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glLoadMatrixf(mCamera.getModelM(), 0);

			if (mScene != null) {
				mScene.onDraw(gl);
			}
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {			
			gl.glViewport(0, 0, width, height);
			
			Camera.setDisplayDimensions(width, height);
			if (mCamera == null) {
				setCamera(new Camera());
			}
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);
			gl.glEnable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig egl) {
			gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			
//			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			gl.glDisable(GL10.GL_DITHER);
			    
	        gl.glEnable(GL10.GL_CULL_FACE);
	        gl.glCullFace(GL10.GL_BACK);
	        gl.glEnable(GL10.GL_DEPTH_TEST);
	        gl.glEnable(GL10.GL_LIGHTING);
	        gl.glDepthFunc(GL10.GL_LEQUAL);
	        gl.glShadeModel(GL10.GL_SMOOTH);
		}
	}
}
