package com.example.awesome_toe;

public class GameState {
	
	private int m_value;
	
	public GameState() {
		this.m_value = -1;
	}
	
	public GameState(int in_num) {
		this.m_value = in_num;
	}

	public int getValue() {
		return m_value;
	}

	public void setValue(int value) {
		this.m_value = value;
	}

	public void update() {
		//MainActivity.updateView();
	}
	
	public String toString() {
		return Integer.toString(m_value);
	}
}
