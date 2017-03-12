package com.gikdew.helpers;

import java.util.ArrayList;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.gikdew.tweenaccessors.Value;
import com.gikdew.tweenaccessors.ValueAccessor;

public class ColorManager {
	private TweenManager manager;
	private Value c1 = new Value();
	private Value c2 = new Value();
	private Value c3 = new Value();
	private TweenCallback cb, cb1, cb2;
	private Color color;
	private ArrayList<Integer> colors = new ArrayList<Integer>();
	private int target1, target2, target3;

	private Random randomGenarator;
	private Integer random, rtime;

	public ColorManager() {

		// COLORS
		// COLOR1
		colors.add(22);
		colors.add(166);
		colors.add(133);

		// COLOR2
		colors.add(52);
		colors.add(152);
		colors.add(219);

		// COLOR3
		colors.add(142);
		colors.add(68);
		colors.add(173);

		// COLOR4
		colors.add(108);
		colors.add(122);
		colors.add(137);

		// COLOR5
		colors.add(142);
		colors.add(68);
		colors.add(173);

		// COLOR6
		colors.add(233);
		colors.add(91);
		colors.add(76);

		// COLOR7
		colors.add(246);
		colors.add(36);
		colors.add(89);

		// COLOR8
		colors.add(247);
		colors.add(202);
		colors.add(24);

		c1.setValue(142);
		c2.setValue(68);
		c3.setValue(173);

		color = new Color(Color.rgb565(c1.getValue(), c2.getValue(),
				c3.getValue()));
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();

		randomGenarator = new Random();

		cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				random = randomGenarator.nextInt(colors.size() / 3);
				rtime = randomGenarator.nextInt(5) + 2;
				Gdx.app.log("Random Number", random.toString());
				target1 = colors.get(random);
				Tween.to(c1, -1, rtime).target(target1).repeatYoyo(0, 0)
						.setCallback(cb)
						.setCallbackTriggers(TweenCallback.COMPLETE)
						.ease(TweenEquations.easeOutBounce).start(manager);
			}
		};

		cb1 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				Gdx.app.log("Random Number", random.toString());
				target2 = colors.get(random + 1);
				Tween.to(c2, -1, rtime + 0.00001f).target(target2)
						.repeatYoyo(0, 0).setCallback(cb1)
						.setCallbackTriggers(TweenCallback.COMPLETE)
						.ease(TweenEquations.easeOutBounce).start(manager);
			}
		};

		cb2 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				Gdx.app.log("Random Number", random.toString());
				target3 = colors.get(random + 2);
				Tween.to(c3, -1, rtime + 0.00002f).target(target3)
						.repeatYoyo(0, 0).setCallback(cb2)
						.setCallbackTriggers(TweenCallback.COMPLETE)
						.ease(TweenEquations.easeOutBounce).start(manager);
			}
		};

		Tween.to(c1, -1, 5).target(230).repeatYoyo(0, 0).setCallback(cb)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.ease(TweenEquations.easeInOutBounce).start(manager);
		Tween.to(c2, -1, 5.00001f).target(126).repeatYoyo(0, 0)
				.setCallback(cb1).setCallbackTriggers(TweenCallback.COMPLETE)
				.ease(TweenEquations.easeInOutBounce).start(manager);
		Tween.to(c3, -1, 5.00002f).target(34).repeatYoyo(0, 0).setCallback(cb2)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.ease(TweenEquations.easeInOutBounce).start(manager);

	}

	public Color getColor() {
		return color;
	}

	public void update(float delta) {
		manager.update(delta);
		color = new Color(c1.getValue() / 255.0f, c2.getValue() / 255.0f,
				c3.getValue() / 255.0f, 1);
	}
}
