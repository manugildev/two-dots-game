package com.gikdew.gameworld;

import C.C;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gikdew.gameobjects.Ball;
import com.gikdew.gameobjects.Center;
import com.gikdew.gameobjects.Menu;
import com.gikdew.gameworld.GameWorld.GameState;
import com.gikdew.helpers.AssetLoader;

public class GameRenderer {

	private GameWorld world;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	private Menu menu;
	private Center center;
	private Ball ball;
	private ShaderProgram fontShader;

	public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
		this.world = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initAssets();
		initObjects();

		fontShader = new ShaderProgram(Gdx.files.internal("font.vert"),
				Gdx.files.internal("font.frag"));
		if (!fontShader.isCompiled()) {
			Gdx.app.error("fontShader",
					"compilation failed:\n" + fontShader.getLog());
		}
	}

	private void initObjects() {
		menu = world.getMenu();
		center = world.getCenter();
		ball = world.getBall();

	}

	private void initAssets() {
	}

	public void render(float delta, float runTime) {
		Gdx.gl.glClearColor(C.backColor.r, C.backColor.g, C.backColor.b,
				C.backColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		if (world.isRunning()) {
			ball.render(shapeRenderer, batch);
			drawScore();
			center.render(shapeRenderer, batch);
		} else if (world.isReady()) {
			center.render(shapeRenderer, batch);
			drawScore();
		} else if (world.isGameover()) {
			drawMenu();
		} else if (world.isMenu()) {
			drawMenu();
			center.render(shapeRenderer, batch);
		}

		batch.end();

	}

	public void drawHScore() {
		batch.setShader(fontShader);
		AssetLoader.font1
				.draw(batch,
						"Score: " + world.getScore(),
						(world.gameWidth / 2)
								- (7.4f * (("Score: " + world.getScore())
										.length() - 0.9f)),
						world.gameHeight / 2 - 20);
		AssetLoader.font1.draw(
				batch,
				"Highscore: " + AssetLoader.getHighScore(),
				(world.gameWidth / 2)
						- (7.4f * (("Highscore: " + AssetLoader.getHighScore())
								.length() - 0.9f)), world.gameHeight / 2 - 20);

		batch.setShader(null);
	}

	private void drawMenu() {
		drawButtons();
		batch.setShader(fontShader);
		batch.setShader(null);
	}

	private void drawButtons() {
		menu.render(batch, fontShader);
	}

	private void drawScore() {
		batch.setShader(fontShader);
		AssetLoader.font.setColor(Color.BLACK);
		if (world.currentState == GameState.RUNNING
				&& world.getCenter().isScoresAreSet()) {
			if (world.getScore() > 9 && world.getScore() < 20) {
				AssetLoader.font.draw(
						batch,
						"" + world.getScore(),
						(world.gameWidth / 2)
								- (10 * (world.getScore() + "").length() - 1),
						world.getCenter().getyPointTap().getValue());

			} else {
				AssetLoader.font.draw(
						batch,
						"" + world.getScore(),
						(world.gameWidth / 2)
								- (13 * (world.getScore() + "").length() - 1),
						world.getCenter().getyPointTap().getValue());
			}
		} else {
			AssetLoader.font.draw(batch, "Tap to start", (world.gameWidth / 2)
					- (9.5f * ("Tap to Start").length() - 5), world.getCenter()
					.getyPointTap().getValue());
		}

		batch.setShader(null);
	}

}
