package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.minlog.Log;
import com.mygdx.game.MyGdxGame;
import com.mygdx.screens.TextScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Thermidor";
        cfg.useGL30 = false;
        cfg.width = 840;
        cfg.height = 480;

       // new LwjglApplication(new TextScreen() ,cfg);
        new LwjglApplication(new MyGdxGame(), cfg);
	}
}
