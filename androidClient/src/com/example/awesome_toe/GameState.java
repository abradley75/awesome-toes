package com.example.awesome_toe;

public class GameState {
	
	public int m_tScore, m_oScore, m_eScore; //made public so we dont need getter and setter for each
	
	public final static int BOARDSIZE = 5; // Size of board
	
	private char m_playerPiece;
	private char m_playerTurn;
	private char[][] m_board = new char[BOARDSIZE][BOARDSIZE];

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
		for(int i=0; i<BOARDSIZE; i++) {
			for(int j=0; j<BOARDSIZE; j++) {
				m_board[i][j] = 'a';
			}
		}
		m_tScore = 0;
		m_oScore = 0;
		m_eScore = 0;
		
		//need to set playerPiece after server response
		//Should be randomly set by server. Choices: t, o , e.
		// Order is always the same so assignment is randomization of order.
		
		//TODO for now assign two states for unit tests.
		m_playerPiece = 't';
		m_playerTurn = 't';
	}
	
	public void sendMove(int row, int col){
		//send move to server
		//update the ui after response from server
	}
	
	public void checkGameStatus(){
		//check the status of game on server and set gameEnd flag
		if (gameEnd == true)
			doEndgame();
	}
	
	public void updateUI() {
		datapasser.updateUI();
	}
	
	public void doEndgame() {
		//TODO
	}
	
	public void calculateScores() {
		//TODO
	}
	
	public char[][] getBoard() {
		return m_board;
	}

	public void setBoard(char[][] in_board) {
		this.m_board = in_board;
	}
	
	public char getPlayerPiece() {
		return m_playerPiece;
	}

	public void setPlayerPiece(char playerPiece) {
		this.m_playerPiece = playerPiece;
	}

	public char getPlayerTurn() {
		return m_playerTurn;
	}

	public void setPlayerTurn(char playerTurn) {
		this.m_playerTurn = playerTurn;
	}

	public OnDataPass _getDataPassHandler() {
		return datapasser;
	}
}
