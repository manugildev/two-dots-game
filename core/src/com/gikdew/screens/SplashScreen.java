package com.gikdew.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.tweenaccessors.SpriteAccessor;
import com.gikdew.twodots.ActionResolver;
import com.gikdew.twodots.TwoDots;

public class SplashScreen implements Screen {

	private TweenManager manager;
	private SpriteBatch batcher;
	private Sprite sprite;
	private TwoDots game;
	private ActionResolver actionResolver;

	public SplashScreen(TwoDots game, ActionResolver actionResolver) {
		this.game = game;
		this.actionResolver = actionResolver;
	}

	@Override
	public void show() {
		sprite = new Sprite(AssetLoader.logo);
		sprite.setColor(1, 1, 1, 0);

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		float desiredWidth = width * 1f;
		float scale = desiredWidth / sprite.getWidth();

		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
				- (sprite.getHeight() / 2));
		setupTween();
		batcher = new SpriteBatch();
	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();

		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				game.setScreen(new GameScreen(actionResolver));
			}
		};

		Tween.to(sprite, SpriteAccessor.ALPHA, 1.2f).target(1)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
				.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
				.start(manager);
	}

	@Override
	public void render(float delta) {
		manager.update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		sprite.draw(batcher);
		batcher.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}