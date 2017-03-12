package com.gikdew.gameobjects;

import java.util.ArrayList;

import C.C;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gikdew.gameworld.GameWorld;
import com.gikdew.gameworld.GameWorld.GameState;
import com.gikdew.helpers.AssetLoader;
import com.gikdew.tweenaccessors.SpriteAccessor;
import com.gikdew.ui.SimpleButton;

public class Menu {
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private GameWorld world;
	private Rectangle rectangle, fontRectangle;

	private boolean goingUp = false;
	private boolean goingDown = false;
	private boolean isSet = true;
	private TweenManager manager;

	private Sprite sprite, spriteRectangle, spritelogo, fontSprite;
	private float duration = 0.5f;
	private float delay = 0f;

	private ArrayList<SimpleButton> menuButtons;
	private TweenCallback cbIn, cbOut;

	private SimpleButton playButton, rankButton, shareButton, achieveButton;

	public Menu(GameWorld world, float x, float y, float width, float height) {

		this.world = world;
		rectangle = new Rectangle(x, y, width, height);
		spriteRectangle = new Sprite(AssetLoader.menuBg);
		spriteRectangle.setBounds(x, y, width, height);
		// spritelogo = new Sprite(AssetLoader.logoGame);

		fontRectangle = new Rectangle(x, y, width, height);
		fontSprite = new Sprite(AssetLoader.menuBg);
		fontSprite.setBounds(x, y, width, height);

		// float desiredWidth = world.gameWidth * 1f;
		// float scale = desiredWidth / AssetLoader.logoGame.getRegionWidth();
		// Gdx.app.log("Scale", String.valueOf(scale));
		// spritelogo.setSize(spritelogo.getWidth() * scale,
		// spritelogo.getHeight() * scale);
		// spritelogo.setPosition((width / 2) - (spritelogo.getWidth() / 2),
		// (height / 2) - (spritelogo.getHeight() / 2));

		setupTween();
		position = new Vector2(x, y);
		velocity = new Vector2();
		acceleration = new Vector2();

		// MENU BUTTONS
		menuButtons = new ArrayList<SimpleButton>();
		playButton = new SimpleButton(world, rectangle.width / 2
				- AssetLoader.playButtonDown.getRegionWidth() / 4, rectangle.y
				+ rectangle.height / 2 + world.gameHeight / 7 - 20, 100 / 2,
				100 / 2, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
		rankButton = new SimpleButton(world, rectangle.width / 2 - 90,
				rectangle.y + rectangle.height / 2 + world.gameHeight / 7 + 5
						+ 5, 100 / 2, 100 / 2, AssetLoader.rankButtonUp,
				AssetLoader.rankButtonDown);

		shareButton = new SimpleButton(world, rectangle.width / 2 + 40,
				rectangle.y + rectangle.height / 2 + world.gameHeight / 7 + 5
						+ 5, 100 / 2, 100 / 2, AssetLoader.shareButtonUp,
				AssetLoader.shareButtonDown);

		achieveButton = new SimpleButton(world, rectangle.width / 2
				- AssetLoader.achieveButtonDown.getRegionWidth() / 4,
				rectangle.y + rectangle.height / 2 + world.gameHeight / 7 + 30
						+ 10, 100 / 2, 100 / 2, AssetLoader.achieveButtonUp,
				AssetLoader.achieveButtonDown);

		menuButtons.add(playButton);
		menuButtons.add(rankButton);
		menuButtons.add(shareButton);
		menuButtons.add(achieveButton);
	}

	public void update(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.update(delta);
		playButton.update(delta);
		rankButton.update(delta);
		shareButton.update(delta);
		achieveButton.update(delta);

		// Gdx.app.log("Sprite Alpha", fontSprite.getColor().a + "");
	}

	public void render(SpriteBatch batch, ShaderProgram fontShader) {
		if (isSet) {
			spriteRectangle.setColor(C.backColor);
			spriteRectangle.draw(batch);
			for (int i = 0; i < menuButtons.size(); i++) {
				menuButtons.get(i).draw(batch);
			}
			// spritelogo.draw(batch);

			batch.setShader(fontShader);
			AssetLoader.font.setColor(Color.BLACK);
			AssetLoader.font.draw(batch, C.gameName, (world.gameWidth / 2)
					- (12 * (C.gameName.length() - 0.9f)), world.gameHeight / 2
					- (world.gameWidth / 2) + 20);
			batch.setShader(null);
			if (world.currentState == GameState.GAMEOVER) {
				AssetLoader.font1.setColor(Color.BLACK);
				batch.setShader(fontShader);
				AssetLoader.font1.draw(
						batch,
						"Score: " + world.getScore(),
						(world.gameWidth / 2)
								- (7f * (("Score: " + world.getScore())
										.length() - 0.9f)),
						world.gameHeight / 2 - 60);
				AssetLoader.font1.draw(
						batch,
						"Highscore: " + AssetLoader.getHighScore(),
						(world.gameWidth / 2)
								- (7f * (("Highscore: " + AssetLoader
										.getHighScore()).length() - 0.9f)),
						world.gameHeight / 2 - 30);
				AssetLoader.font1.draw(
						batch,
						"Games Played: " + AssetLoader.getGamesPlayed(),
						(world.gameWidth / 2)
								- (7.2f * (("Games Played: " + AssetLoader
										.getGamesPlayed()).length() - 0.9f)),
						world.gameHeight / 2 - 0);

				batch.setShader(null);
			}
			fontSprite.setColor(C.backColor.r, C.backColor.g, C.backColor.b,
					fontSprite.getColor().a);
			fontSprite.draw(batch);
		}
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void stop() {
		goingUp = false;
		goingDown = false;
		velocity.y = 0;
	}

	public boolean isMoving() {
		return goingDown || goingUp;
	}

	public ArrayList<SimpleButton> getMenuButtons() {
		return menuButtons;
	}

	public void fadeOutAll() {
		world.getCenter().backToPos();
		world.getCenter().clickPlay();
		// isSet = false;
		Tween.to(spriteRectangle, SpriteAccessor.ALPHA, duration).target(0)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, .4f)
				.start(manager);
		Tween.to(fontSprite, SpriteAccessor.ALPHA, duration).target(1)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, 1f)
				.setCallback(cbOut).setCallbackTriggers(TweenCallback.COMPLETE)
				.start(manager);
		// playButton.fadeOut(duration, delay);
		// achieveButton.fadeOut(duration, delay);
		// rankButton.fadeOut(duration, delay);
		// shareButton.fadeOut(duration, delay);
	}

	public void fadeInAll() {
		isSet = true;

		world.currentState = GameState.GAMEOVER;
		Tween.to(spriteRectangle, SpriteAccessor.ALPHA, duration).target(1)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, .4f)
				.setCallback(cbIn).setCallbackTriggers(TweenCallback.COMPLETE)
				.start(manager);
		fontSprite.setAlpha(1);
		Tween.to(fontSprite, SpriteAccessor.ALPHA, duration).target(0)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, .4f)
				.start(manager);
		// playButton.fadeIn(duration);
		// achieveButton.fadeIn(duration);
		// rankButton.fadeIn(duration);
		// shareButton.fadeIn(duration);
	}

	public Sprite getSpriteRectangle() {
		return spriteRectangle;
	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();

		// CALLBACKS
		cbOut = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				isSet = false;
				world.currentState = GameState.READY;
				world.restart();
			}
		};
		cbIn = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				isSet = true;
			}
		};

		// FIRST TWEEN
		Tween.to(fontSprite, SpriteAccessor.ALPHA, duration).target(0)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(0, .4f)
				.start(manager);

	}
}
