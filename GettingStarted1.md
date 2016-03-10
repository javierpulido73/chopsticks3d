#A tutorial that learn you to set up and start working with the Chopsticks3D framework.

# Introduction #
In this tutorial you will learn how to start a new Chopsticks3D project in Eclipse and how to create a simple scene displaying a rotating cube and a single light source.

Before you start, you should download the latest .jar file of Chopsticks3D. It can be found under the Download tab.

# Starting up a project in Eclipse #
Start by creating a new Android Project in Eclipse and name the activity HelloChopsticks.

After it is finished, **Right Click** on your project and select **Properties**.

In the Properties window, select **Java Build Path > Libraries > Add External JARs**.
Go to directory of the Chopsticks3D .jar and select it and press OK.

You now got a working Chopsticks3D project.

# Constructing a simple scene #
In the HelloChopsticks.java Activity file write the following code:

```
package com.example.hellochopsticks;

import com.chopsticks3d.ChopsticksActivity;
import com.chopsticks3d.scene.Light;
import com.chopsticks3d.scene.LightNode;
import com.chopsticks3d.scene.MeshNode;
import com.chopsticks3d.scene.SceneNode;
import com.chopsticks3d.scene.shape.Cube;

public class HelloChopsticks extends ChopsticksActivity {
	private MeshNode mCubeNode;

	@Override
	public void onInitialize() {
		setFullscreen(true);
		setHorizontal();
	}
	
	@Override
	public void onLoad() {
		SceneNode scene = new SceneNode();
		
		Cube cubeMesh = new Cube();
		mCubeNode = new MeshNode(cubeMesh);
		mCubeNode.setTranslationZ(-2);
		
		Light light = new Light();
		light.setAmbient(1.0f, 1.0f, 1.0f, 1.0f);
		LightNode lightNode = new LightNode(light);
		
		scene.addChild(mCubeNode);
		scene.addChild(lightNode);
		
		setScene(scene);
	}

	@Override
	public void onUpdate(float deltaTime) {
		mCubeNode.setRotationY(mCubeNode.getRotationY() + 20 * deltaTime);
	}
}
```