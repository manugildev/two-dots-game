package com.gikdew.gameworld;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.gikdew.gameobjects.Ball;
import com.gikdew.gameobjects.Center;
import com.gikdew.gameobjects.Menu;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.tweenaccessors.Value;
import com.gikdew.tweenaccessors.ValueAccessor;
import com.gikdew.twodots.ActionResolver;

public class GameWorld {

	private Menu menu;
	private Center center;
	private Ball ball;

	public float sW = Gdx.graphics.getWidth();
	public float sH = Gdx.graphics.getHeight();
	public float gameWidth = 320;
	public float gameHeight = sH / (sW / gameWidth);
	private int score = 0;

	private TweenCallback cb, cb1;
	private TweenManager manager;
	private Value distance = new Value();
	private Value distance1 = new Value();

	public enum GameState {
		READY, RUNNING, GAMEOVER, MENU
	}

	public GameState currentState;
	public ActionResolver actionResolver;

	private ArrayList<Sound> sounds;

	public void addScore(int increment) {
		score += increment;
		Gdx.app.log("Score", score + "");
		// ACHIEVEMENTS

		if (actionResolver.isSignedIn()) {
			if (score >= 5)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQAw");
			if (score >= 10)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQAg");
			if (score >= 15)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQBA");
			if (score >= 19)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQBQ");
			if (score >= 25)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQBg");
			if (score >= 30)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQCA");
			if (score >= 50)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQCg");
			if (score >= 75)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQCQ");
			if (score >= 100)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQCw");
			if (score >= 150)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQDA");
		}
	}

	public GameWorld(final ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
		menu = new Menu(this, 0, 0, gameWidth, gameHeight);
		center = new Center(this, gameWidth / 2, gameHeight / 2 - 20, 15);
		ball = new Ball(this, gameWidth / 2, -10, 10, new Vector2(0, 300), 0);

		currentState = GameState.MENU;
		distance.setValue(0);

		sounds = new ArrayList<Sound>();
		sounds.add(AssetLoader.sound);
		sounds.add(AssetLoader.sound1);
		sounds.add(AssetLoader.sound2);

		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				actionResolver.showOrLoadInterstital();
				distance.setValue(0);
			}

		};
		cb1 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				actionResolver.submitScore(getScore());

			}

		};

	}

	public void update(float delta) {
		manager.update(delta);
		menu.update(delta);
		switch (currentState) {
		case READY:
			center.update(delta);
			ball.update(delta);
			ball.setVelocityY(0);
			break;
		case RUNNING:
			center.update(delta);
			ball.update(delta);
			// Collisions
			// BLUE BALL
			if (Intersector.overlaps(ball.getCircle(), center.getCircleDown())) {
				if (ball.getType() == 1 && !ball.getCollided()) {
					sounds.get(1).play();
					ball.collideWithCenter();
					addScore(1);
					center.launchCircleUp();
				} else if (ball.getType() == 0 && !ball.getCollided()) {
					setScore0();
				}

			}
			// GREEN BALL
			if (Intersector.overlaps(ball.getCircle(), center.getCircleUp())) {
				if (ball.getType() == 0 && !ball.getCollided()) {
					sounds.get(2).play();
					ball.collideWithCenter();
					addScore(1);
					center.launchCircleDown();
				} else if (ball.getType() == 1 && !ball.getCollided()) {
					setScore0();
				}
			}
			break;
		case GAMEOVER:
			center.update(delta);
			break;
		default:
			center.updateMenu(delta);
			ball.update(delta);
			break;
		}
	}

	public Menu getMenu() {
		return menu;
	}

	public int getScore() {
		return score;
	}

	public Center getCenter() {
		return center;
	}

	public void setScore0() {
		AssetLoader.addGamesPlayed();
		int gamesPlayed = AssetLoader.getGamesPlayed();
		// GAMES PLAYED ACHIEVEMENTS!
		if (actionResolver.isSignedIn()) {
			if (gamesPlayed >= 10)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQDQ");
			if (gamesPlayed >= 25)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQDg");
			if (gamesPlayed >= 50)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQDw");
			if (gamesPlayed >= 75)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQEA");
			if (gamesPlayed >= 100)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQEQ");
			if (gamesPlayed >= 150)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQEg");
			if (gamesPlayed >= 200)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQEw");
			if (gamesPlayed >= 300)
				actionResolver.unlockAchievementGPGS("CgkIxYr7wJ0eEAIQFA");

		}
		// AssetLoader.dead.play();
		menu.fadeInAll();
		actionResolver.submitScore(score);

		if (score > AssetLoader.getHighScore()) {
			AssetLoader.setHighScore(score);
		} else {
			// ADS
			if (Math.random() < 0.8f) {
				Tween.to(distance, -1, .2f).target(1).repeatYoyo(0, 0)
						.setCallback(cb)
						.setCallbackTriggers(TweenCallback.COMPLETE)
						.ease(TweenEquations.easeInOutBounce).start(manager);
			}
		}

	}

	public void restart() {
		score = 0;
		ball.setPositionY(0 - ball.getCircle().radius * 5);
		ball.setVelocityY(300);
		center.setAngleValue(0);
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameover() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public Ball getBall() {
		return ball;
	}

}
