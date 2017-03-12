package com.gikdew.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gikdew.gameworld.GameWorld;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.tweenaccessors.SpriteAccessor;

public class SimpleButton {

	private float x, y, width, height;

	private TextureRegion buttonUp;
	private TextureRegion buttonDown;

	public Rectangle bounds;
	public TweenManager manager;
	private Sprite sprite;

	private boolean isPressed = false;

	public SimpleButton(GameWorld world, float x, float y, float width,
			float height, TextureRegion buttonUp, TextureRegion buttonDown) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;
		sprite = new Sprite(buttonUp);
		sprite.setBounds(x, y, width, height);
		setupTween();
		bounds = new Rectangle(x, y, width, height);

	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();

		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {

			}
		};

	}

	public boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	public void update(float delta) {
		bounds.x = x;
		bounds.y = y;
		manager.update(delta);
	}

	public void draw(SpriteBatch batcher) {

		if (isPressed) {
			sprite.setRegion(buttonDown);
			// batcher.draw(buttonDown, x, y, width, height);
		} else {
			sprite.setRegion(buttonUp);
		}
		sprite.draw(batcher);
	}

	public boolean isTouchDown(int screenX, int screenY) {
		if (bounds.contains(screenX, screenY)) {
			isPressed = true;
			// AssetLoader.select.play();
			return true;
		}

		return false;
	}

	public void fadeOut(float duration, float delay) {
		Tween.to(sprite, SpriteAccessor.ALPHA, duration).target(0).delay(delay)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, .4f)
				.start(manager);
	}

	public void fadeIn(float duration) {
		Tween.to(sprite, SpriteAccessor.ALPHA, duration).target(1)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, .4f)
				.start(manager);
	}

	public boolean isTouchUp(int screenX, int screenY) {

		// It only counts as a touchUp if the button is in a pressed state.
		if (bounds.contains(screenX, screenY) && isPressed) {
			isPressed = false;
			AssetLoader.sound.play();
			return true;
		}

		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}