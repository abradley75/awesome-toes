import java.util.Random;

public class GameServerState {
        
        private static GameServerState m_instance = null;
        
        private final int MAXBOARDSIZE = 10;
        private int board_row = 5; // Size of board
        private int board_col = 5;
        private char turn;
        private char[][] m_board = new char[MAXBOARDSIZE][MAXBOARDSIZE];
        public final static char BLANKSLOT = 'a';
        public final static char TPLAYER = 't';
        public final static char OPLAYER = 'o';
        public final static char EPLAYER = 'e';  
        public int playerCt = 0;
        public boolean boardSet = false;
        
        private boolean dirty = false;
        private boolean gameEnd = true;
        private boolean gameStart = false;
        private int numberOfUpdates = 0 ;
        
        private GameServerState(){
        	initializeGameState();
        }
        
        public static GameServerState getInstance(){
        	if(m_instance == null)
        		m_instance = new GameServerState();
        	return m_instance;
        }
        
        public void initializeGameState() {
        	
        	for(int i=0; i< MAXBOARDSIZE; i++) {
    			for(int j=0; j< MAXBOARDSIZE; j++) {
    				m_board[i][j] = 'a';
    			}
    		}
        	
        	gameEnd = false;
        	turn = 't';
        	
        	
        }
        
        public boolean gameReadyToStart() {
        	if (playerCt == 3 && boardSet) {
        		return true;
        	}
        	else
        		return false;
        }
        
        public void setGameStart(boolean in_start) {
        	this.gameStart = in_start;
        }
        
        public boolean getGameStart() {
        	return this.gameStart;
        }
        
        public String getGameStateMsg(){
        	String gameStateMsg = "";
        	gameStateMsg+="playerCt:"+Integer.toString(playerCt)+"\n";
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
        
        public void incrementPlayer(){
        	if(turn == TPLAYER)
        		turn = OPLAYER;
        	else if(turn == OPLAYER)
        		turn = EPLAYER;
        	else 
        		turn = TPLAYER;
        	if(playerCt!=3)
        		playerCt++;
        	setDirty(true);
        }

		public void setBoard(int row, int col) {
			// TODO Auto-generated method stub
			board_row = row;
			board_col = col;
			boardSet = true;
			setDirty(true);
		}

		public void receivedMove(char piece, int row, int col) {
			if(piece == turn) {
				m_board[row][col] = piece;
			}
			setDirty(true);
		}

		public boolean isDirty() {
			return dirty;
		}

		public void setDirty(boolean dirty) {
			this.dirty = dirty;
		}

		public boolean isGameEnd() {
			return gameEnd;
		}

		public void setGameEnd(boolean gameEnd) {
			this.gameEnd = gameEnd;
		}

		public char getTurn() {
			return turn;
		}

		public boolean detectAllUpdated() {
			if( numberOfUpdates == 3) {
				numberOfUpdates = 0;
				return true;
			}
			else 
				return false;
			
		}
}