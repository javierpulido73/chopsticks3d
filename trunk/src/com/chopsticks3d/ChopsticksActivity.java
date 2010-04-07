package com.chopsticks3d;

import com.chopsticks3d.scene.SceneNode;
import com.chopsticks3d.scene.camera.Camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;

public class ChopsticksActivity extends Activity implements Runnable {
	private ChopsticksView mGLView;
	private long mLastTimeUpdate;
	
	private PowerManager.WakeLock mWakeLock = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onInitialize();

		mGLView = new MyGLView(this);
		setContentView(mGLView);
	}

	public void onInitialize() {}
	public void onLoad() {}
	public void onUpdate(float dt) {}

	public void setScene(SceneNode scene) {
		mGLView.setScene(scene);
	}

	public void setCamera(Camera camera) {
		mGLView.setCamera(camera);
	}

	private void startEngine() {
		mGLView.setEvent(this);

		Runtime r = Runtime.getRuntime();
		r.gc();
	}

//	@Override
//	public void onPause() {
//		super.onPause();
//		mGLView.onPause();
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		mGLView.onResume();
//	}
	
	@Override
	public void onDestroy() {
		if (mWakeLock != null)
			mWakeLock.release();
		super.onDestroy();
	}

	@Override
	public void run() {
		// Calculation of delta time.
		final long time = SystemClock.uptimeMillis();
		final long timeDelta = time - mLastTimeUpdate;
		final float timeDeltaSeconds = mLastTimeUpdate > 0.0f ? timeDelta / 1000.0f : 0.0f;
		mLastTimeUpdate = time;

		onUpdate(timeDeltaSeconds);
	}

	public void setFullscreen(boolean fullscreen) {
		if (fullscreen) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		} else {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE); // Returns title?
		}
	}

	public void setHorizontal() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public void setVertical() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void setSleepOff() {
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag"); 
        mWakeLock.acquire(); 
	}

	private class MyGLView extends ChopsticksView {
		public MyGLView(Context context) {
			super(context);
		}
		
		@Override
		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			
			Camera.setDisplayDimensions(w, h);
			onLoad();
			startEngine();
		}
	}
}
