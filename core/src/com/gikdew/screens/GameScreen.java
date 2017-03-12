package com.gikdew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.gikdew.gameworld.GameRenderer;
import com.gikdew.gameworld.GameWorld;
import com.gikdew.helpers.InputHandler;
import com.gikdew.twodots.ActionResolver;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;
	public float sW = Gdx.graphics.getWidth();
	public float sH = Gdx.graphics.getHeight();
	public float gameWidth = 320;
	public float gameHeight = sH / (sW / gameWidth);

	public ActionResolver actionResolver;

	public GameScreen(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;

		Gdx.app.log("GameScreen", "Attached");
		world = new GameWorld(actionResolver);
		Gdx.input.setInputProcessor(new InputHandler(world, sW / gameWidth, sH
				/ gameHeight, actionResolver));
		renderer = new GameRenderer(world, (int) gameWidth, (int) gameHeight);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("GameScreen", "resize");
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show called");
	}

	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "pause called");
	}

	@Override
	public void resume() {
		Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	public void dispose() {
		// Leave blank
	}
}
