package com.chopsticks3d.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLUtils;

public class TextureHandler {
	private static List<Texture> mTextureList = new ArrayList<Texture>();
	private static Iterator<Texture> mIterator;

	private static boolean mNew = false;

	private static int textureNameWorkspace[] = new int[1];
	private static Texture mTempTexture;

	public static void addTexture(Texture texture) {
		if (!mTextureList.contains(texture)) {
			mTextureList.add(texture);
			mNew = true;
		}
	}

	public static boolean containsNew() {
		return mNew;
	}

	public static void loadTextures(GL10 gl, Context context) {
		for(mIterator = mTextureList.iterator(); mIterator.hasNext();) {
			mTempTexture = mIterator.next();
			if (mTempTexture.resourceId != -1 && mTempTexture.textureId == -1) {
				mTempTexture.textureId = loadBitmap(context, gl, mTempTexture.resourceId);
			}
		}
		mNew = false;
	}

	private static int loadBitmap(Context context, GL10 gl, int resourceId) {	
		int textureName = -1;
		if (context != null && gl != null) {
			gl.glGenTextures(1, textureNameWorkspace, 0);

			textureName = textureNameWorkspace[0];
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName);

			Options opt=new Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), resourceId, opt);
			
			//TODO: Check memory allocations:
			Matrix matrix = new Matrix();
			matrix.postRotate(180);
			Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap2, 0);

			bitmap.recycle();

//			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
//			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
//
//			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
//			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
//
//			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//
//			bitmap.recycle();
		}

		return textureName;
	}
}
