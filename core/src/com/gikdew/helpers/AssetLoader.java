package com.gikdew.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Texture texture, logoTexture, logoGameTexture;
	public static TextureRegion logo, logoGame, dot, box, menuBg, playButtonUp,
			playButtonDown, rankButtonUp, rankButtonDown, shareButtonUp,
			shareButtonDown, achieveButtonUp, achieveButtonDown;
	public static BitmapFont font, font1;
	private static Preferences prefs;
	public static Sound sound, sound1, sound2;

	public static void load() {
		logoTexture = new Texture(Gdx.files.internal("logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);
		logo.flip(false, false);

		// MAIN TEXTURE (Located inside android > assets)
		texture = new Texture(Gdx.files.internal("texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		// JUMPING DOT TEXTURE
		dot = new TextureRegion(texture, 0, 0, 240, 240);
		dot.flip(false, true);

		// SQUARE BOX TEXTURE
		box = new TextureRegion(texture, 240, 0, 240, 240);
		box.flip(false, true);

		Texture tfont = new Texture(Gdx.files.internal("font.png"), true);
		tfont.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
		Texture tfont1 = new Texture(Gdx.files.internal("font1.png"), true);
		tfont1.setFilter(TextureFilter.MipMapLinearNearest,
				TextureFilter.Linear);
		// FONT
		font = new BitmapFont(Gdx.files.internal("font.fnt"),
				new TextureRegion(tfont), false);
		font.setScale(0.75f, -0.75f);
		font.setColor(Color.WHITE);

		font1 = new BitmapFont(Gdx.files.internal("font1.fnt"),
				new TextureRegion(tfont1), false);
		font1.setScale(1f, -1f);
		font1.setColor(Color.WHITE);

		// MENU BG TEXTURE
		menuBg = new TextureRegion(texture, 480, 0, 20, 20);

		// BUTTON TEXTURES
		playButtonUp = new TextureRegion(texture, 500, 100, 100, 100);
		playButtonDown = new TextureRegion(texture, 600, 100, 100, 100);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		rankButtonUp = new TextureRegion(texture, 500, 0, 100, 100);
		rankButtonDown = new TextureRegion(texture, 600, 0, 100, 100);
		rankButtonUp.flip(false, true);
		rankButtonDown.flip(false, true);

		shareButtonUp = new TextureRegion(texture, 700, 0, 100, 100);
		shareButtonDown = new TextureRegion(texture, 700, 100, 100, 100);
		shareButtonUp.flip(false, true);
		shareButtonDown.flip(false, true);

		achieveButtonUp = new TextureRegion(texture, 800, 0, 100, 100);
		achieveButtonDown = new TextureRegion(texture, 800, 100, 100, 100);
		achieveButtonUp.flip(false, true);
		achieveButtonDown.flip(false, true);

		prefs = Gdx.app.getPreferences("ZombieBird");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

		if (!prefs.contains("games")) {
			prefs.putInteger("games", 0);
		}

		sound = Gdx.audio.newSound(Gdx.files.internal("sound.wav"));
		sound1 = Gdx.audio.newSound(Gdx.files.internal("sound1.wav"));
		sound2 = Gdx.audio.newSound(Gdx.files.internal("sound2.wav"));
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void addGamesPlayed() {
		prefs.putInteger("games", prefs.getInteger("games") + 1);
		prefs.flush();
	}

	public static int getGamesPlayed() {
		return prefs.getInteger("games");
	}

	public static void dispose() {
		texture.dispose();
		font.dispose();
		sound.dispose();
		sound1.dispose();
		sound2.dispose();

	}
}
