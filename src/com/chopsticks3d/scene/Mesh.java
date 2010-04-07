package com.chopsticks3d.scene;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;

public class Mesh {
	public static final int MODE_TRIANGLES = GL10.GL_TRIANGLES;
	public static final int MODE_TRIANGLE_STRIP = GL10.GL_TRIANGLE_STRIP;
	public static final int MODE_TRIANGLE_FAN = GL10.GL_TRIANGLE_FAN;

	private static GL11 gl11;

	protected int mDrawMode = MODE_TRIANGLES;

	private CharBuffer mIndexBuffer;
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mNormalBuffer;
	private FloatBuffer mTextureBuffer;


	private int mIndexBufferIndex;
	private int mVertBufferIndex;
	private int mNormalBufferIndex;
	private int mTexCoordsBufferIndex;
	private int mIndexCount;

	public Mesh() {
		MeshHandler.addMesh(this);
	}

	public void onDraw(GL10 gl) {
		if (gl instanceof GL11) {
			gl11 = (GL11)gl;

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
			gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

			if(mNormalBufferIndex != 0) {
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mNormalBufferIndex);
				gl11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
			}
			if(mTexCoordsBufferIndex != 0) {
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mTexCoordsBufferIndex);
				gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
			}

			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferIndex);
			gl11.glDrawElements(mDrawMode, mIndexCount, GL11.GL_UNSIGNED_SHORT, 0);

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		} else {
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

			gl.glDrawElements(mDrawMode, mIndexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
		}
	}

	public void setDrawMode(int drawMode) {
		mDrawMode = drawMode;
	}

	public int getDrawMode() {
		return mDrawMode;
	}

	public void setIndices(char[] indices) {
		if(mIndexBuffer == null || mIndexBuffer.capacity() != indices.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(indices.length * 2);
			byteBuffer.order(ByteOrder.nativeOrder());
			mIndexBuffer = byteBuffer.asCharBuffer();
		}
		mIndexBuffer.clear();
		mIndexBuffer.put(indices);
		//		mIndexBuffer.position(0);
	}

	public void setVertices(float[] vertices) {
		if(mVertexBuffer == null || mVertexBuffer.capacity() != vertices.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			mVertexBuffer = byteBuffer.asFloatBuffer();
		}
		mVertexBuffer.clear();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
	}

	public void setNormals(float[] normals) {
		if(mNormalBuffer == null || mNormalBuffer.capacity() != normals.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(normals.length * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			mNormalBuffer = byteBuffer.asFloatBuffer();
		}
		mNormalBuffer.clear();
		mNormalBuffer.put(normals);
		mNormalBuffer.position(0);
	}

	public void setTexCoords(float[] texCoords) {
		if(mTextureBuffer == null || mTextureBuffer.capacity() != texCoords.length) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texCoords.length * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			mTextureBuffer = byteBuffer.asFloatBuffer();
		}
		mTextureBuffer.clear();
		mTextureBuffer.put(texCoords);
		mTextureBuffer.position(0);
	}

	public void generateHardwareBuffers(GL10 gl) {
		if (mVertBufferIndex == 0) {
			if (gl instanceof GL11) {
				gl11 = (GL11)gl;
				int[] buffer = new int[1];

				mVertexBuffer.rewind();
				mIndexBuffer.rewind();

				// Allocate and fill the vertex buffer.
				gl11.glGenBuffers(1, buffer, 0);
				mVertBufferIndex = buffer[0];
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
				final int vertexSize = mVertexBuffer.capacity() * 4;
				gl11.glBufferData(GL11.GL_ARRAY_BUFFER, vertexSize, 
						mVertexBuffer, GL11.GL_STATIC_DRAW);

				if(mNormalBuffer != null) {
					mNormalBuffer.rewind();
					gl11.glGenBuffers(1, buffer, 0);
					mNormalBufferIndex = buffer[0];
					gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mNormalBufferIndex);
					final int normalSize = mNormalBuffer.capacity() * 4;
					gl11.glBufferData(GL11.GL_ARRAY_BUFFER, normalSize, 
							mNormalBuffer, GL11.GL_STATIC_DRAW);
				}
				if(mTextureBuffer != null) {
					mTextureBuffer.rewind();
					gl11.glGenBuffers(1, buffer, 0);
					mTexCoordsBufferIndex = buffer[0];
					gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mTexCoordsBufferIndex);
					final int texcoordSize = mTextureBuffer.capacity() * 4;
					gl11.glBufferData(GL11.GL_ARRAY_BUFFER, texcoordSize, 
							mTextureBuffer, GL11.GL_STATIC_DRAW);
				}

				// Unbind the array buffer.
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

				// Allocate and fill the index buffer.
				gl11.glGenBuffers(1, buffer, 0);
				mIndexBufferIndex = buffer[0];
				gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 
						mIndexBufferIndex);

				final int indexSize = mIndexBuffer.capacity() * 2;
				gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, indexSize, mIndexBuffer, GL11.GL_STATIC_DRAW);

				// Unbind the element array buffer.
				gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

				mIndexCount = mIndexBuffer.limit();
			}
		}
	}

	public void importModel(int resourceId, Context context) {
		InputStream stream = context.getResources().openRawResource(resourceId);
		DataInputStream s = new DataInputStream(stream);

		try {
			int len;
//			mDrawMode = s.readInt();
//			s.readInt(); //TODO:remove
//
//			if(s.readInt() == 0) {
//			} else {
//				s.readFloat();
//				s.readFloat();
//				s.readFloat();
//			}
//
//			if(s.readInt() == 0) {
//			} else {
//				s.readFloat();
//				s.readFloat();
//				s.readFloat();
//				s.readFloat();
//				s.readFloat();
//				s.readFloat();
//			}

			if((len = s.readInt()) == 0) {
				mIndexBuffer = null;
			} else {
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(len * 2);
				byteBuffer.order(ByteOrder.nativeOrder());
				mIndexBuffer = byteBuffer.asCharBuffer();
				mIndexBuffer.clear();
				for (int x = 0; x < len; x++)
					mIndexBuffer.put(s.readChar());
			}

			if((len = s.readInt()) == 0) {
				mVertexBuffer = null;
			} else {
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(len * 4);
				byteBuffer.order(ByteOrder.nativeOrder());
				mVertexBuffer = byteBuffer.asFloatBuffer();
				mVertexBuffer.clear();
				for (int x = 0; x < len; x++)
					mVertexBuffer.put(s.readFloat());
			}

			if((len = s.readInt()) == 0) {
				mTextureBuffer = null;
			} else {
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(len * 4);
				byteBuffer.order(ByteOrder.nativeOrder());
				mTextureBuffer = byteBuffer.asFloatBuffer();
				mTextureBuffer.clear();
				for (int x = 0; x < len; x++)
					mTextureBuffer.put(s.readFloat());
			}

			if((len = s.readInt()) == 0) {
				mNormalBuffer = null;
			} else {
				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(len * 4);
				byteBuffer.order(ByteOrder.nativeOrder());
				mNormalBuffer = byteBuffer.asFloatBuffer();
				mNormalBuffer.clear();
				for (int x = 0; x < len; x++)
					mNormalBuffer.put(s.readFloat());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
