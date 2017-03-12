package com.gikdew.gameobjects;

import C.C;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gikdew.gameworld.GameWorld;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.tweenaccessors.SpriteAccessor;
import com.gikdew.tweenaccessors.Value;
import com.gikdew.tweenaccessors.ValueAccessor;

public class Ball {

	private Circle circle;
	private Vector2 position;
	private GameWorld world;

	private float radius;
	private Vector2 velocity;
	private boolean stop;
	private boolean collided = false;
	private int type;
	private Color color;

	// ANIMATIONS
	private TweenManager manager;
	private TweenCallback cb;
	private TweenEquation tweenEquation = TweenEquations.easeInOutSine;

	private Value radiusValue = new Value();

	public Ball(GameWorld world, float x, float y, float radius,
			Vector2 velocity, int type) {
		this.world = world;
		this.type = type;
		this.position = new Vector2(x, y);
		this.velocity = new Vector2(velocity.x, y < 0 ? velocity.y : velocity.y
				* -1);
		this.radius = radius;
		stop = false;
		radiusValue.setValue(radius);
		if (type == 0) {
			color = C.colorB1;
		} else {
			color = C.colorB2;
		}
		Tween.registerAccessor(Value.class, new ValueAccessor());
		setupTween();
		circle = new Circle(x, y, radius);

	}

	public void update(float delta) {
		manager.update(delta);
		circle.radius = radiusValue.getValue();
		if (!isStop()) {
			position.add(velocity.cpy().scl(delta));
		}
		circle.setPosition(position);
	}

	public void render(ShapeRenderer sR, SpriteBatch batch) {
		// sR.begin(ShapeType.Filled);
		// DRAWING THE CIRCLES
		// sR.setColor(color);
		// sR.circle(circle.x, circle.y, circle.radius - 0.5f, 1500);
		// sR.end();
		batch.end();
		batch.begin();
		batch.setColor(color);
		batch.draw(AssetLoader.dot, circle.x - circle.radius, circle.y
				- circle.radius, circle.radius, circle.radius,
				circle.radius * 2, circle.radius * 2, 1, 1, 0);
		batch.setColor(Color.WHITE);
	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();
		cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				reset();
			}

		};
	}

	private void reset() {
		position.y = Math.random() < 0.5f ? 0 - radius : world.gameHeight
				+ radius;
		velocity.y = position.y < 0 ? Math.abs(velocity.y * 100f) : -1
				* Math.abs(velocity.y * 100f);
		radiusValue.setValue(radius);
		type = Math.random() < 0.5f ? 1 : 0;
		if (type == 0) {
			color = C.colorB1;
		} else {
			color = C.colorB2;
		}
		collided = false;
	}

	public void collideWithCenter() {
		collided = true;
		velocity = new Vector2(0, velocity.y / 100f);
		Tween.to(radiusValue, -1, 0.1f).target(0).repeatYoyo(0, 0)
				.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
				.ease(tweenEquation).start(manager);
	}

	public boolean isStop() {
		return stop;
	}

	public Circle getCircle() {
		return circle;
	}

	public int getType() {
		return type;
	}

	public boolean getCollided() {
		return collided;
	}

	public void setPositionY(float f) {
		position.y = f;
	}

	public void setVelocityY(int i) {
		velocity.y = i;
	}

}
