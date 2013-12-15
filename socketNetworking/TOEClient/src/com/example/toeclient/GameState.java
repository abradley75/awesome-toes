package com.example.toeclient;

public class GameState {
        
        private static GameState m_instance = null;
        
        private final int MAXBOARDSIZE = 10;
        private int board_row = 0; // Size of board
        private int board_col = 0;
        private char m_turn;
        private char piece;
        private char[][] m_board = new char[MAXBOARDSIZE][MAXBOARDSIZE];
        private int m_tScore, m_oScore, m_eScore;

		private boolean gameEnd;
		private boolean gameStart;
		
        public final static char BLANKSLOT = 'a';
        public final static char TPLAYER = 't';
        public final static char OPLAYER = 'o';
        public final static char EPLAYER = 'e';
        
        private GameState(){
        	initializeGameState();
        }
        public boolean isGameStart(){
        	return gameStart;
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
        	
        	m_tScore = 0;
        	m_oScore = 0;
        	m_eScore = 0;
        	gameStart = false;
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
			gameStart = true;
			return boardChanged;			
			
		}

		public char getTurn() {
			return m_turn;
		}

		public char[][] getBoard() {
			return m_board;
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
            for(int i=0; i<board_row; i++){
                    for(int j=0; j<board_col; j++){
                            //scores for rows
                            if(m_board[i][j] == piece)
                                    if(rowPrev == piece)
                                            rowCt *=2;
                                    else
                                            rowCt = 1;
                            else{
                                    if(rowPrev == piece){
                                    	if(rowCt!=1){
                                            row+=rowCt;                                            
                                    	}
                                    	rowCt=0;
                                    }                                                
                            }
                            rowPrev = m_board[i][j];
                            if(j==board_col-1){//add and reset score at end of row
                                    if(rowCt!=1){
                                    	row+=rowCt;
                                    }                                    
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
                                    	if(colCt!=1){
                                            col+=colCt;                                            
                                    	}
                                    	colCt=0;
                                    }                                                
                            }
                            colPrev = m_board[j][i];
                            if(i==board_row-1){//add and reset score at end of column
                                    if(colCt!=1){
                                    	col+=colCt;
                                    }
                                    colCt=0;
                                    colPrev = 'a';
                            }                                
                    }
            }
            //algorithm below found at: 
            //http://analgorithmaday.blogspot.com/2011/04/traverse-array-diagonally.html
            //loop in major diagonal direction
            for (int slice = 0; slice < board_row*2-1; ++slice) {
            int z = slice < board_row ? 0 : slice - board_row + 1;
            for (int j = z; j <= slice - z; ++j) {
                int c1=j;
                int c2=(board_col-1)-(slice-j);
                            if(m_board[c1][c2] == piece)
                                    if(majorPrev == piece)
                                            majorCt *=2;
                                    else
                                            majorCt = 1;
                            else{
                                    if(majorPrev == piece){
                                    	if(majorCt!=1){
                                    		majorDiag+=majorCt;
                                    	}
                                        majorCt=0;
                                    }                                                
                            }
                            majorPrev = m_board[c1][c2];
            }
            if(majorCt!=1){
            	majorDiag+=majorCt;
            }
            majorCt=0;
            majorPrev = 'a';
        }
            
            //traverse in minor diagonal direction
            for (int slice = 0; slice < board_row*2-1; ++slice) {
            int z = slice < board_row ? 0 : slice - board_row + 1;
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
                                    	if(minorCt!=1){
                                            minorDiag+=minorCt;
                                    	}
                                        minorCt=0;
                                    }                                                
                            }
                            minorPrev = m_board[c1][c2];
            }
            if(minorCt!=1){
            	minorDiag+=minorCt;
            }
            minorCt=0;
            minorPrev = 'a';
        }
            
            return row+col+majorDiag+minorDiag;
    }
    
    public int getTScore() {
    	return m_tScore;
    }
    
    public int getOScore() {
    	return m_oScore;
    }
    
    public int getEScore() {
    	return m_eScore;
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
}
