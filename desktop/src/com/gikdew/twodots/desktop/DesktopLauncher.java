package com.gikdew.twodots.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gikdew.twodots.TwoDots;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Two Dots";
		config.width = 320;
		config.height = 480;
		new LwjglApplication(new TwoDots(new ActionResolverDesktop()), config);
	}
}
