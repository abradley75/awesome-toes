package com.example.awesome_toe;

public class GameState {
	
	private int m_value;
	private OnDataPass datapasser;
	
	public GameState() {
		this.m_value = -1;
	}
	
	public GameState(OnDataPass handler) {
		this.m_value = -1;
		datapasser = handler;
		
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

	public void updateUI() {
		datapasser.updateUI();
	}
	
	public String toString() {
		return Integer.toString(m_value);
	}
}
