package com.example.awesome_toe;

public class UpdatePacket {
    
	public final static char NOTUSED = 'z';
	public final static int FROMCLIENT = 0;
	public final static int FROMSERVER = 1;
	
	private final char playerSending;
	private final char playerTurn;
	private final char[][] boardState; 
	private final boolean gameEnd;
    
    public UpdatePacket(char in_sending, char in_playerTurn, char[][] in_boardState, boolean in_gameEnd) {
        this.playerSending = in_sending;
    	this.playerTurn = in_playerTurn;
        this.boardState = in_boardState;
        this.gameEnd = in_gameEnd;
    }
    
    public UpdatePacket(char player, char[][] in_boardState, boolean in_gameEnd, int Mode) {
    	
    	if(Mode == FROMCLIENT) {
    		this.playerSending = player;
    		this.playerTurn = NOTUSED;
    	}
    	else if (Mode == FROMSERVER) {
    		this.playerSending = NOTUSED;
    		this.playerTurn = player;
    	}
    	else {
    		this.playerSending = NOTUSED;
			this.playerTurn = NOTUSED;
			System.out.println("ABDEBUG: Invalid mode on UpdatePacket");
    	}
    	
        this.boardState = in_boardState;
        this.gameEnd = in_gameEnd;
    }
        
    public char getPlayerTurn() {
        return playerTurn;
    }
    
    public char getPlayerSending() {
    	return playerSending;
    }
    
    public char[][] getBoardState() {
    	return boardState;
    }
        
    public boolean isGameEnd() {
		return gameEnd;
	}

	// Prints board contents row order first.
    @Override
    public String toString() {
        String str1 = "**Update Packet** CurrentTurn: " + playerTurn;
        String str2 = ", Board Contents: [";
        String str4 = "]";
        
        String str3 = "";
        for (int j = 0 ; j < 5 ; j++) {
        	for (int i = 0 ; i < 5 ; i++) {
        		str3 = str3 + boardState[i][j] ;
        		
        		if(i == 5 && j != 5)
        			str3 = str3 + ", ";
        		else
        			str3 = str3 + " ";
        	}
        }
        
        return str1 + str2 + str3 + str4 ;        
    }
}