package com.gikdew.twodots.desktop;

import com.gikdew.twodots.ActionResolver;

public class ActionResolverDesktop implements ActionResolver {

	@Override
	public void showOrLoadInterstital() {
		System.out.println("showOrLoadInterstital()");
	}

	@Override
	public void signIn() {
		System.out.println("DesktopGoogleServies: signIn()");
	}

	@Override
	public void signOut() {
		System.out.println("DesktopGoogleServies: signOut()");
	}

	@Override
	public void rateGame() {
		System.out.println("DesktopGoogleServices: rateGame()");
	}

	@Override
	public void submitScore(long score) {
		System.out.println("DesktopGoogleServies: submitScore(" + score + ")");
	}

	@Override
	public void showScores() {
		System.out.println("DesktopGoogleServies: showScores()");
	}

	@Override
	public boolean isSignedIn() {
		System.out.println("DesktopGoogleServies: isSignedIn()");
		return false;
	}

	@Override
	public boolean shareGame(String msg) {
		System.out.println("Share " + msg);
		return true;
	}

	@Override
	public void unlockAchievementGPGS(String string) {
		System.out.println("Unlock " + string);

	}

	@Override
	public void showAchievement() {
		System.out.println("DesktopGoogleServies: showAchievement()");
	}
}