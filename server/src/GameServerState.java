import java.util.Random;

public class GameServerState {
	
	public static GameServerState m_instance;
	
	public int m_tScore, m_oScore, m_eScore; //made public so we dont need getter and setter for each
	
	public final static int BOARDSIZE = 5; // Size of board
	public final static char BLANKSLOT = 'a';
	public final static char TPLAYER = 't';
	public final static char OPLAYER = 'o';
	public final static char EPLAYER = 'e';
	
	private char m_playerTurn;
	private char[][] m_board = new char[BOARDSIZE][BOARDSIZE];

	private boolean gameEnd = false;
	
	private GameServerState() {
		initializeGameServer();
		loadMockData();
	}
	
	public static GameServerState getInstance() {
		if (m_instance == null) {
			m_instance = new GameServerState();
		}
		return m_instance;
	}
	
	private void initializeGameServer(){
		
		for(int i=0; i<BOARDSIZE; i++) {
			for(int j=0; j<BOARDSIZE; j++) {
				m_board[i][j] = BLANKSLOT;
			}
		}
		m_tScore = 0;
		m_oScore = 0;
		m_eScore = 0;
		
		//need to set playerPiece after server response
		//Should be randomly set by server. Choices: t, o , e.
		// Order is always the same so assignment is randomization of order.
		pickStartingPlayer();
	}
	
	public void pickStartingPlayer() {
		Random rand = new Random();
		
		int randomNum = rand.nextInt(3);
		
		switch (randomNum) {
			case 0:
				m_playerTurn = TPLAYER;
				break;
			case 1:
				m_playerTurn = OPLAYER;
				break;
			case 2:
				m_playerTurn = EPLAYER;
				break;
			default:
				System.out.println("ABDEBUG: pickStartingPlayer Invalid random number");
				return;
		}
	}
	
	public void handlePlayerMove(UpdatePacket move) {
		if(checkMovePacketValidity(move)) {
			m_board = move.getBoardState();
			advanceTurn();
		}
		else {
			System.out.println("ABDEBUG: handlePlayerMove, invalid packet");
			return;
		}
		
	}
	
	private void advanceTurn() {
		
		if(checkIfGameIsEnd())
			gameEnd = true;
		
		if(m_playerTurn == TPLAYER)
			m_playerTurn = OPLAYER;
		else if (m_playerTurn == OPLAYER)
			m_playerTurn = EPLAYER;
		else if (m_playerTurn == EPLAYER)
			m_playerTurn = TPLAYER;		
	}

	//Return true if this is valid move packet and from player whose turn it is. 
	private boolean checkMovePacketValidity(UpdatePacket move) {
		
		char[][] in_board = move.getBoardState();
		
		if (m_playerTurn != move.getPlayerSending())
			return false;
		if (move.getPlayerTurn() != UpdatePacket.NOTUSED)
			return false;
		if (move.isGameEnd())
			return false;
		
		//Check board has only changed by one character from last move and changed character is player's piece.
		int numChanged = 0;
		for(int i=0; i < BOARDSIZE; i++) {
			for(int j=0; j < BOARDSIZE; j++) {
				if(m_board[i][j] != in_board[i][j]) {
					numChanged++;
					//Return false if any slot was not changed from a blank slot to the players piece whose turn it is.
					if(m_board[i][j] != BLANKSLOT && in_board[i][j] != m_playerTurn)
						return false;
				}
			}
		}
		
		// Make sure exactly one change on the board occurred.
		if(numChanged != 1)
			return false;
		
		return true;
	}

	public UpdatePacket updateClients() {
		return createUpdatePacket();
	}
	
	public boolean checkIfGameIsEnd() {
		for(int i=0; i < BOARDSIZE; i++) {
			for(int j=0; j < BOARDSIZE; j++) {
				if(m_board[i][j] == BLANKSLOT)
					return false;
				else if (m_board[i][j] != TPLAYER && m_board[i][j] != OPLAYER && m_board[i][j] != EPLAYER) {
					System.out.println("ABDEBUG: Error in checkGameEnd - Slot" + i + j + " is not blank or players");
					return false;
				}
			}
		}
		return true;
	}
	
	public char[][] getBoard() {
		return m_board;
	}

	public void setBoard(char[][] in_board) {
		this.m_board = in_board;
	}

	public char getPlayerTurn() {
		return m_playerTurn;
	}

	public void setPlayerTurn(char playerTurn) {
		this.m_playerTurn = playerTurn;
	}
	
	public UpdatePacket createUpdatePacket() {
		
		UpdatePacket u = new UpdatePacket(m_playerTurn, m_board, gameEnd, UpdatePacket.FROMSERVER);
		
		return u;	
	}
	
	public void loadMockData() {
		m_playerTurn = 'e';		
		
		for(int i=0; i<BOARDSIZE; i++) {
			for(int j=0; j<BOARDSIZE; j++) {
				m_board[i][j] = 'o';
			}
		}
	}
}
