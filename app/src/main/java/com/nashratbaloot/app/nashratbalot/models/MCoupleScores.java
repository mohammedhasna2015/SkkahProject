package com.nashratbaloot.app.nashratbalot.models;

public class MCoupleScores {
	private int scoreLeft;

	private int scoreRight;
	
	public MCoupleScores() {
		
	}
	
	public MCoupleScores(int scoreLeft, int scoreRight) {
		this.scoreLeft = scoreLeft;
		this.scoreRight = scoreRight;
	}

	public int getScoreLeft() {
		return scoreLeft;
	}

	public void setScoreLeft(int scoreLeft) {
		this.scoreLeft = scoreLeft;
	}

	public int getScoreRight() {
		return scoreRight;
	}

	public void setScoreRight(int scoreRight) {
		this.scoreRight = scoreRight;
	}
}
