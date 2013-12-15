package com.example.toeclient;

public class GameState {
        
        private static GameState m_instance = null;
        
        private final int MAXBOARDSIZE = 10;
        private int board_row = 5; // Size of board
        private int board_col = 5;
        private char turn;
        private char piece;
        private char[][] m_board = new char[MAXBOARDSIZE][MAXBOARDSIZE];

		private boolean gameEnd;
		
        public final static char BLANKSLOT = 'a';
        public final static char TPLAYER = 't';
        public final static char OPLAYER = 'o';
        public final static char EPLAYER = 'e';
        
        private GameState(){
        	initializeGameState();
        }
        
        public static GameState getInstance(){
        	if(m_instance == null)
        		m_instance = new GameState();
        	return m_instance;
        }
        
        public void initializeGameState() {
        	
        	for(int i=0; i< MAXBOARDSIZE; i++) {
    			for(int j=0; j< MAXBOARDSIZE; j++) {
    				m_board[i][j] = 'a';
    			}
    		}
        	
        	setGameEnd(false);
        	turn = 't';
        	
        	
        }
        
        public String toString(){
        	String gameStateMsg = "";
        	gameStateMsg+="piece:"+piece+"\n";
        	gameStateMsg+="turn:"+turn+"\n";
        	gameStateMsg+="row:"+Integer.toString(board_row)+"\n";
        	gameStateMsg+="col:"+Integer.toString(board_col)+"\n";
        	gameStateMsg+="board:";
        	
        	for(int i = 0; i< board_row; i++)
        		for(int j=0; j<board_row; j++)
        			if(i==0&&j==0)
        				gameStateMsg+=m_board[i][j];
        			else
        				gameStateMsg+=","+m_board[i][j];
        	
        	return gameStateMsg;
        }

		public void receivedUpdate(char piece, char turn, char[][] board) {
			//TODO
		}

		public char getPiece() {
			return piece;
		}

		public void setPiece(char piece) {
			this.piece = piece;
		}

		public boolean isGameEnd() {
			return gameEnd;
		}

		public void setGameEnd(boolean gameEnd) {
			this.gameEnd = gameEnd;
		}
}
