package com.chopsticks3d.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class MeshHandler {
	private static List<Mesh> mMeshList = new ArrayList<Mesh>();
	private static Iterator<Mesh> mIterator;
	
	private static boolean mNew = false;

	public static void addMesh(Mesh mesh) {
		mMeshList.add(mesh);
		mNew = true;
	}
	
	public static boolean containsNew() {
		return mNew;
	}
	
	public static void generateHardwareBuffers(GL10 gl) {
		for(mIterator = mMeshList.iterator(); mIterator.hasNext();) {
	        mIterator.next().generateHardwareBuffers(gl);
	    }
		mNew = false;
	}
}
