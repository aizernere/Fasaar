package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Screens.Fasaar;

import static com.mygdx.game.Screens.LoginApp.appExiting;


public class DesktopLauncher {


	public static void main (String[] arg) {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setTitle("Login");
			ApplicationListener login = new Fasaar();
			new Lwjgl3Application(login, config);
		Gdx.app.addLifecycleListener(new LifecycleListener() {
			@Override
			public void pause() {}

			@Override
			public void resume() {}

			@Override
			public void dispose() {
				if (appExiting) {
					try {
						TimeUtils.millis();
					} catch (GdxRuntimeException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
