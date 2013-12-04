package com.example.awesome_toe;

public class GameState {
	
	public static GameState m_instance;
	
	public int m_tScore, m_oScore, m_eScore; //made public so we dont need getter and setter for each
	
	public final static int BOARDSIZE = 5; // Size of board
	public final static char BLANKSLOT = 'a';
	public final static char TPLAYER = 't';
	public final static char OPLAYER = 'o';
	public final static char EPLAYER = 'e';
	
	private char m_playerPiece;
	private char m_playerTurn;
	private char[][] m_board = new char[BOARDSIZE][BOARDSIZE];

	private OnDataPass datapasser;
	private boolean gameEnd = false;
	
	private GameState() {
		initializeGame();
	}
	
	public static GameState getInstance() {
		if (m_instance == null) {
			m_instance = new GameState();
		}
		return m_instance;
	}
	
	public void setDataPassHandler(OnDataPass handler) {
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
//		m_playerPiece = 't';
//		m_playerTurn = 't';
	}
	
	//Create an updatePacket to send to server with player's move
	public UpdatePacket sendMove() {
		UpdatePacket u = new UpdatePacket(m_playerPiece, m_board, gameEnd, UpdatePacket.FROMCLIENT);
		return u;
	}
	
	public boolean checkGameEnd(){
		//check the status of game on server and set gameEnd flag
		return gameEnd;
	}
	
	public void updateUI() {
		datapasser.updateUI();
	}
	
	public void calculateScores() {
		m_tScore = scoreForPiece('t');
		m_oScore = scoreForPiece('o');
		m_eScore = scoreForPiece('e');
	}
		
	private int scoreForPiece(char piece) {
		int row = 0, col = 0, majorDiag = 0, minorDiag = 0;
		int rowCt = 0, colCt = 0, majorCt = 0, minorCt = 0;
		char rowPrev = 'a', colPrev = 'a', majorPrev = 'a', minorPrev = 'a'; 
		
		//loop for row and col scores
		for(int i=0; i<BOARDSIZE; i++){
			for(int j=0; i<BOARDSIZE; j++){
				//scores for rows
				if(m_board[i][j] == piece)
					if(rowPrev == piece)
						rowCt *=2;
					else
						rowCt = 1;
				else{
					if(rowPrev == piece){
						row+=rowCt;
						rowCt=0;
					}						
				}
				rowPrev = m_board[i][j];
				if(j==BOARDSIZE-1){//add and reset score at end of row
					row+=rowCt;
					rowCt=0;
					rowPrev = 'a';
				}
				
				//scores for columns
				if(m_board[j][i] == piece)
					if(colPrev == piece)
						colCt *=2;
					else
						colCt = 1;
				else{
					if(rowPrev == piece){
						col+=colCt;
						colCt=0;
					}						
				}
				colPrev = m_board[j][i];
				if(i==BOARDSIZE-1){//add and reset score at end of column
					col+=colCt;
					colCt=0;
					colPrev = 'a';
				}				
			}
		}
		//algorithm below found at: 
		//http://analgorithmaday.blogspot.com/2011/04/traverse-array-diagonally.html
		//loop in major diagonal direction
		for (int slice = 0; slice < BOARDSIZE*2-1; ++slice) {
	        int z = slice < BOARDSIZE ? 0 : slice - BOARDSIZE + 1;
	        for (int j = z; j <= slice - z; ++j) {
	            int c1=j;
	            int c2=(BOARDSIZE-1)-(slice-j);
				if(m_board[c1][c2] == piece)
					if(majorPrev == piece)
						majorCt *=2;
					else
						majorCt = 1;
				else{
					if(majorPrev == piece){
						majorDiag+=majorCt;
						majorCt=0;
					}						
				}
				majorPrev = m_board[c1][c2];
	        }
	        majorDiag+=majorCt;
			majorCt=0;
	        majorPrev = 'a';
	    }
		
		//traverse in minor diagonal direction
		for (int slice = 0; slice < BOARDSIZE*2-1; ++slice) {
	        int z = slice < BOARDSIZE ? 0 : slice - BOARDSIZE + 1;
	        for (int j = z; j <= slice - z; ++j) {
	            int c1=j;
	            int c2=slice-j;
				if(m_board[c1][c2] == piece)
					if(minorPrev == piece)
						minorCt *=2;
					else
						minorCt = 1;
				else{
					if(minorPrev == piece){
						minorDiag+=majorCt;
						minorCt=0;
					}						
				}
				minorPrev = m_board[c1][c2];
	        }
	        minorDiag+=minorCt;
	        minorCt=0;
	        minorPrev = 'a';
	    }
		
		return row+col+majorDiag+minorDiag;
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
	
	public void setGameEnd(boolean gameStatus){
		gameEnd = gameStatus;
	}

	public OnDataPass _getDataPassHandler() {
		return datapasser;
	}

	public char getWinner() {
		char winner = 'a';
		if(m_tScore == m_oScore && m_tScore == m_eScore)
			winner = 'a';
		else if(m_tScore > m_oScore)
			if(m_tScore > m_eScore)
				winner = 't';
			else if(m_tScore < m_eScore)
				winner = 'e';
			else 
				winner = 'a';
		else if (m_tScore < m_oScore)
			if(m_oScore > m_eScore)
				winner = 'o';
			else if(m_oScore < m_eScore)
				winner = 'e';
			else 
				winner = 'a';
		else if(m_tScore == m_oScore)
			if(m_tScore < m_eScore)
				winner = 'e';
			else winner = 'a';
		return winner;
	}

	public void updateGameState(UpdatePacket update) {
		System.out.println("ABDEBUG: in updateGameState");
		setPlayerTurn(update.getPlayerTurn());
		setBoard(update.getBoardState());
		setGameEnd(update.isGameEnd());
		calculateScores();
		updateUI();
	}

	public void applyMoveToBoard(int row, int col) {
		m_board[row][col] = m_playerPiece;
	}
}
