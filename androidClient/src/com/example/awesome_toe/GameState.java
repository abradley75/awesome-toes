package com.example.awesome_toe;

public class GameState {
	
	public int m_tScore, m_oScore, m_eScore; //made public so we dont need getter and setter for each
	
	private char playerPiece;
	private char[][] m_board = new char[5][5];
	private OnDataPass datapasser;
	private boolean gameEnd = false;
	
	public GameState() {
		initializeGame();
	}
	
	public GameState(OnDataPass handler) {
		initializeGame();
		datapasser = handler;
		
	}
	
	private void initializeGame(){
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
				m_board[i][j] = 'a';
		m_tScore = 0;
		m_oScore = 0;
		m_eScore = 0;
		
		//need to set playerPiece after server response		
	}
	
	public void sendMove(int row, int col){
		//send move to server
		//update the ui after response from server
	}
	
	public void checkGameStatus(){
		//check the status of game on server and set gameEnd flag
	}
	
	public void updateUI() {
		datapasser.updateUI();
	}
	/*public GameState(int in_num) {
		this.m_value = in_num;
	}*/

	/*public int getValue() {
		return m_value;
	}

	public void setValue(int value) {
		this.m_value = value;
	}*/	
	
	/*public String toString() {
		return Integer.toString(m_value);
	}*/
}
