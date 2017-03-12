package com.gikdew.twodots;

import com.badlogic.gdx.Game;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.screens.SplashScreen;

public class TwoDots extends Game {

	private ActionResolver actionResolver;

	public TwoDots(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;

	}

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this, actionResolver));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}