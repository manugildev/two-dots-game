package com.gikdew.gameobjects;

import java.util.ArrayList;
import java.util.Random;

import C.C;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gikdew.gameworld.GameWorld;
import com.gikdew.gameworld.GameWorld.GameState;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.tweenaccessors.SpriteAccessor;
import com.gikdew.tweenaccessors.Value;
import com.gikdew.tweenaccessors.ValueAccessor;

public class Center {

	private Circle circleUp, circleDown, circleCenter, circleEffectUp,
			circleEffectDown;
	private Vector2 position;
	private GameWorld world;

	private int radius;
	private float angle, angle1;

	// Rotation Animations
	private TweenManager manager, manager1;
	private TweenCallback cb, cb1, cb2;
	private TweenEquation tweenEquation = TweenEquations.easeInOutSine;
	private Value angleValue = new Value();
	private Value radiusValue = new Value();

	private Value radiusEffectDown = new Value();
	private Value radiusEffectUp = new Value();

	private Value yPointTap = new Value();

	private float rotDuration = 0.20f;
	private Random randomGenarator;

	private Color color;
	private ArrayList<Integer> colors = new ArrayList<Integer>();
	private Tween rotationTween;
	private Tween backToPosTween;

	private boolean scoresAreSet = false;

	private Sprite spriteUp, spriteDown;

	public Center(GameWorld world, float x, float y, int radius) {
		this.world = world;
		this.position = new Vector2(x, y);
		this.radius = radius;
		this.angle = 0;
		this.angle1 = this.angle + 180;

		angleValue.setValue(angle);
		radiusValue.setValue(radius);
		radiusEffectUp.setValue(0);
		radiusEffectDown.setValue(0);
		yPointTap.setValue(-100);

		Tween.registerAccessor(Value.class, new ValueAccessor());
		setupTween();
		circleUp = new Circle(calculatePositionUp().x, calculatePositionUp().y,
				radius);
		circleDown = new Circle(calculatePositionDown().x,
				calculatePositionDown().y, radius);

		circleCenter = new Circle(x, y, radius);
		circleEffectDown = new Circle(x, y, 0);
		circleEffectUp = new Circle(x, y, 0);
		rotationTween = Tween.to(angleValue, -1, 5).target(360)
				.repeatYoyo(10000000, 0).ease(tweenEquation).start(manager1);

	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();
		manager1 = new TweenManager();

		cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				angleValue.setValue(0);
				backToPosTween.kill();

			}
		};

		cb2 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				scoresAreSet = true;
				world.currentState = GameState.RUNNING;
				world.restart();

			}
		};

		cb1 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				yPointTap.setValue(world.gameHeight / 2
						- (world.gameWidth / 2 + 40));
			}
		};
	}

	public void update(float delta) {

		if (angleValue.getValue() == 360 || angleValue.getValue() == -360) {
			angleValue.setValue(0);
		}
		circleUp.radius = radiusValue.getValue();
		circleDown.radius = radiusValue.getValue();
		circleEffectDown.radius = radiusEffectDown.getValue();
		circleEffectUp.radius = radiusEffectUp.getValue();

		angle = angleValue.getValue();

		manager.update(delta);
		circleUp = new Circle(calculatePositionUp().x, calculatePositionUp().y,
				circleUp.radius);
		circleDown = new Circle(calculatePositionDown().x,
				calculatePositionDown().y, circleDown.radius);
		// circleEffectDown.setPosition(circleUp.x, circleUp.y);
		// circleEffectUp.setPosition(circleDown.x, circleDown.y);
	}

	public void updateMenu(float delta) {
		// Gdx.app.log("AngleValue", "" + angleValue.getValue());

		circleUp.radius = radiusValue.getValue();
		circleDown.radius = radiusValue.getValue();
		circleEffectDown.radius = radiusEffectDown.getValue();
		circleEffectUp.radius = radiusEffectUp.getValue();

		angle = angleValue.getValue();

		manager1.update(delta);
		circleUp = new Circle(calculatePositionUp().x, calculatePositionUp().y,
				circleUp.radius);
		circleDown = new Circle(calculatePositionDown().x,
				calculatePositionDown().y, circleDown.radius);
		// circleEffectDown.setPosition(circleUp.x, circleUp.y);
		// circleEffectUp.setPosition(circleDown.x, circleDown.y);
	}

	private Vector2 calculatePositionUp() {
		float cx = position.x;
		float cy = position.y;
		return new Vector2((float) (cx + radius
				* Math.sin(Math.toRadians(-angle))), (float) (cy + radius
				* Math.cos(Math.toRadians(-angle))));
	}

	private Vector2 calculatePositionDown() {
		angle1 = angle + 180;
		float cx = position.x;
		float cy = position.y;
		return new Vector2((float) (cx + radius
				* Math.sin(Math.toRadians(-angle1))), (float) (cy + radius
				* Math.cos(Math.toRadians(-angle1))));
	}

	public void render(ShapeRenderer sR, SpriteBatch batch) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		sR.begin(ShapeType.Filled);
		sR.setColor(new Color(C.colorB1.r, C.colorB1.g, C.colorB1.b, 0.1f));
		sR.circle(circleEffectDown.x, circleEffectDown.y,
				circleEffectDown.radius, 1500);

		sR.setColor(new Color(C.colorB2.r, C.colorB2.g, C.colorB2.b, 0.1f));
		sR.circle(circleEffectUp.x, circleEffectUp.y, circleEffectUp.radius,
				1500);
		sR.end();

		// DEBUGGING
		// sR.begin(ShapeType.Filled);
		// DRAWING THE CIRCLES
		// sR.setColor(new Color(0.18f, 0.80f, 0.443f, 0.0f));
		// sR.circle(circleUp.x, circleUp.y, circleUp.radius, 1500);
		// sR.setColor(new Color(C.colorB1.r, C.colorB1.g, C.colorB1.b, 0.8f));
		// sR.circle(circleUp.x, circleUp.y, circleUp.radius, 1500);
		// sR.setColor(C.colorB1);
		// sR.circle(circleUp.x, circleUp.y, circleUp.radius - 0.5f, 1500);
		// sR.setColor(new Color(0.16f, 0.50f, 0.72f, 0.0f));
		// sR.circle(circleDown.x, circleDown.y, circleDown.radius, 1500);
		// sR.setColor(new Color(C.colorB2.r, C.colorB2.g, C.colorB2.b, 0.8f));
		// sR.circle(circleDown.x, circleDown.y, circleDown.radius, 1500);
		// sR.setColor(C.colorB2);
		// sR.circle(circleDown.x, circleDown.y, circleDown.radius - 0.5f,
		// 1500);
		// sR.setColor(Color.WHITE);
		// sR.end();

		batch.end();
		batch.begin();
		batch.setColor(C.colorB1);
		batch.draw(AssetLoader.dot, circleUp.x - circleUp.radius, circleUp.y
				- circleUp.radius, circleUp.radius, circleUp.radius,
				circleUp.radius * 2, circleUp.radius * 2, 1, 1, 0);
		batch.setColor(C.colorB2);
		batch.draw(AssetLoader.dot, circleDown.x - circleDown.radius,
				circleDown.y - circleDown.radius, circleDown.radius,
				circleDown.radius, circleDown.radius * 2,
				circleDown.radius * 2, 1, 1, 0);
		batch.setColor(Color.WHITE);
		// batch.draw(AssetLoader.dot, circleUp.x, circleUp.y,circleUp.radius *
		// 2, circleUp.radius * 2);
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public void clickRight() {
		int target = 0;
		if (angleValue.getValue() >= 0 && angleValue.getValue() < 180) {
			target = 180;
		} else if (angleValue.getValue() >= 180 && angleValue.getValue() < 360) {
			target = 360;
		} else if (angleValue.getValue() < 0 && angleValue.getValue() >= -180) {
			target = 0;
		} else if (angleValue.getValue() < -180
				&& angleValue.getValue() >= -360) {
			target = -180;
		}
		Tween.to(angleValue, -1, rotDuration).target(target).repeatYoyo(0, 0)
				.ease(tweenEquation).start(manager);
	}

	public void clickLeft() {
		int target = 0;
		if (angleValue.getValue() > 0 && angleValue.getValue() <= 180) {
			target = 0;
		} else if (angleValue.getValue() > 180 && angleValue.getValue() <= 360) {
			target = 180;
		} else if (angleValue.getValue() <= 0 && angleValue.getValue() > -180) {
			target = -180;
		} else if (angleValue.getValue() <= -180
				&& angleValue.getValue() > -360) {
			target = -360;
		}
		Tween.to(angleValue, -1, rotDuration).target(target).repeatYoyo(0, 0)
				.ease(tweenEquation).start(manager);
	}

	public void clickPlay() {
		yPointTap.setValue(-100);
		scoresAreSet = false;
		Tween.to(yPointTap, -1, rotDuration)
				.target(world.gameHeight / 2 - (world.gameWidth / 2 + 40))
				.repeatYoyo(0, 0).ease(tweenEquation).start(manager).delay(.4f);
	}

	public void clickReady() {
		yPointTap.setValue(world.gameHeight / 2 - (world.gameWidth / 2 + 40));
		Tween.to(yPointTap, -1, rotDuration).target(-100).repeatYoyo(1, 0)
				.ease(tweenEquation).start(manager).delay(0)
				.setCallbackTriggers(TweenCallback.END).setCallback(cb2);
	}

	public void launchCircleDown() {
		radiusEffectDown.setValue(0);
		Tween.to(radiusEffectDown, -1, 0.4f).target(100).repeatYoyo(1, 0)
				.ease(tweenEquation).start(manager);
	}

	public void launchCircleUp() {
		radiusEffectUp.setValue(0);
		Tween.to(radiusEffectUp, -1, 0.4f).target(100).repeatYoyo(1, 0)
				.ease(tweenEquation).start(manager);
	}

	public Circle getCircleUp() {
		return circleUp;
	}

	public Circle getCircleDown() {
		return circleDown;
	}

	public void setAngleValue(int i) {
		this.angleValue.setValue(i);

	}

	public void backToPos() {
		rotationTween.kill();
		backToPosTween = Tween.to(angleValue, -1, .4f).target(0)
				.repeatYoyo(0, .4f).ease(tweenEquation).start(manager1);

	}

	public Value getyPointTap() {
		return yPointTap;
	}

	public void setYPointTap(int i) {
		yPointTap.setValue(i);
	}

	public boolean isScoresAreSet() {
		return scoresAreSet;
	}

}
