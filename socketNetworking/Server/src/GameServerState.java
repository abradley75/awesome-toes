public class GameServerState {
        
        private static GameServerState m_instance = null;
        
        private final int MAXBOARDSIZE = 10;
        private int board_row = 5; // Size of board
        private int board_col = 5;
        private char turn;
        private int gameState;
        private char[][] m_board = new char[MAXBOARDSIZE][MAXBOARDSIZE];// allocate to as max board size
        public final static char BLANKSLOT = 'a';
        public final static char TPLAYER = 't';
        public final static char OPLAYER = 'o';
        public final static char EPLAYER = 'e';  
        public int playerCt = 0;
        public boolean boardSet = false;
        
        private GameServerState(){
        	turn = TPLAYER;
        	gameState = -1;
        	
        	for(int i=0; i< MAXBOARDSIZE; i++)
        		for(int j=0; j<MAXBOARDSIZE; j++)
        			m_board[i][j] = 'a';
        }
        
        public static GameServerState getInstance(){
        	if(m_instance == null)
        		m_instance = new GameServerState();
        	return m_instance;
        }
        
        public String getGameStateMsg(){
        	String gameStateMsg = "";
        	gameStateMsg+="gameState:"+Integer.toString(gameState)+"\n";
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
        
        /*increment player connected, if turn is E, set gamestate to 0*/
        public void incrementPlayer(){
        	if(turn == TPLAYER)
        		turn = OPLAYER;
        	else if(turn == OPLAYER)
        		turn = EPLAYER;
        	else 
        		turn = TPLAYER;
        	playerCt++;
        }
        
        public int getGameState(){
        	return gameState;
        }
        
        public char getTurn(){
        	return turn;
        }

		public void updateGameState() {
			if(playerCt == 3 && boardSet)
				gameState = 0;
			
		}

		public void setBoard(int row, int col) {
			// TODO Auto-generated method stub
			board_row = row;
			board_col = col;
			boardSet = true;
		}

		public void sendMove(char piece, int row, int col) {
			m_board[row][col] = piece;
			gameState++;
			if(turn == TPLAYER)
        		turn = OPLAYER;
        	else if(turn == OPLAYER)
        		turn = EPLAYER;
        	else 
        		turn = TPLAYER;			
		}
}