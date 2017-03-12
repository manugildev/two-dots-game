package com.gikdew.twodots;

public interface ActionResolver {
	public void showOrLoadInterstital();

	public void signIn();

	public void signOut();

	public void rateGame();

	public void submitScore(long score);

	public void showScores();

	public boolean isSignedIn();

	public boolean shareGame(String msg);

	public void unlockAchievementGPGS(String string);

	void showAchievement();

}
