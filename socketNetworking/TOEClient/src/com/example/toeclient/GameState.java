package com.example.toeclient;

public class GameState {
        
        private static GameState m_instance = null;
        
        private final int MAXBOARDSIZE = 10;
        private int board_row = 0; // Size of board
        private int board_col = 0;
        private char m_turn;
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
        	
        	gameEnd = false;
        	m_turn = 't';         	
        }
        
        public int getRow(){
        	return board_row;
        }
        public int getCol(){
        	return board_col;
        }
        
        public String toString(){
        	String gameStateMsg = "";
        	gameStateMsg+="piece:"+piece+"\n";
        	gameStateMsg+="turn:"+m_turn+"\n";
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

		public void receivedUpdate(char turn, char[][] board,boolean gameEndFlag) {
			m_turn = turn;
        	for(int i=0; i< board_row; i++) {
    			for(int j=0; j< board_col; j++) {
    				m_board[i][j] = board[i][j];
    			}
    		}
        	gameEnd = gameEndFlag;			
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
		
		public boolean setBoardSize(int row, int col){
			boolean boardChanged = false;
			if(row!=board_row || col!=board_col){
				board_row = row;
				board_col = col;
				boardChanged = true;
			}
			return boardChanged;			
			
		}

		public char getTurn() {
			return m_turn;
		}

		public char[][] getBoard() {
			return m_board;
		}
}
