package com.nashratbaloot.app.nashratbalot.models;

public class MPoint {
	private float positionX;
	
	private float positionY;
	
	public MPoint(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
	
}
