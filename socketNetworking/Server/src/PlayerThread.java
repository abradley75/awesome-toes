import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PlayerThread implements Runnable {
	private Socket m_playerSock;
	private GameServerState m_gameState;
	private ObjectInputStream m_inStream;
	private ObjectOutputStream m_outStream;
	private ExecutorService execute;
	private String msg = "";
	private char piece;
	private int playernum;//used for removing form array list
	
	PlayerThread(GameServerState gameState, Socket playerSock, int players) throws IOException{
		System.out.println("Client Connected");
		execute = Executors.newSingleThreadExecutor();
		m_playerSock = playerSock;
		m_gameState = gameState;
		m_outStream = new ObjectOutputStream(m_playerSock.getOutputStream());
		m_outStream.flush();
		m_inStream = new ObjectInputStream(m_playerSock.getInputStream());
		playernum = players;
		
	}
	
	public void run(){		
		try {		
			
			sendInitialState();
			piece = m_gameState.getTurn();
			m_gameState.incrementPlayer();			 
			if(m_gameState.getGameState()==-1 && piece == GameServerState.TPLAYER){
				System.out.println("HERE");
				waitBoardSize();
			}			
			waitPlayerMsg();
			playGame();
			//game is over close client connections and decrement player
			Server.players--;
			Server.pThreads.remove(playernum);
			m_outStream.close();
			m_inStream.close();
			m_playerSock.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playGame() throws IOException, InterruptedException {
		int playerMove;
		int prevMove = 0;
		if(piece == GameServerState.TPLAYER)
			playerMove = 0;
		else if(piece == GameServerState.OPLAYER)
			playerMove = 1;
		else
			playerMove = 2;
		
		while(m_gameState.getGameState() != 25){
			if(m_gameState.getGameState()%3== playerMove)
				waitForMove();
			if(m_gameState.getGameState()!=prevMove){
				prevMove = m_gameState.getGameState();
				sendGameState();
			Thread.sleep(1000);//sleep for awhile since players wont make moves too fast anyway
			}			
		}		
	}

	private void waitForMove() {
		System.out.println("waiting on msg");
		
		execute.submit(new Runnable(){
			@Override
			public void run() {
				try {
					msg = (String)m_inStream.readObject();
					System.out.println(msg);
					String moveMsg[] = msg.split(":");
					int row = Integer.parseInt(moveMsg[1].split(",")[0]);
					int col = Integer.parseInt(moveMsg[1].split(",")[1]);
					m_gameState.sendMove(piece, row, col);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		});
	}

	private void waitBoardSize() {
		System.out.println("waiting on msg");
		
		execute.submit(new Runnable(){
			@Override
			public void run() {
				try {
					msg = (String)m_inStream.readObject();
					//parse message and set board
					String[] boardMsg = msg.split(":", 2);
					int row = Integer.parseInt(boardMsg[1].split(",")[0]);
					int col = Integer.parseInt(boardMsg[1].split(",")[1]);
					m_gameState.setBoard(row, col);
					System.out.println(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		});
	}

	private void waitPlayerMsg() throws InterruptedException {
		while(m_gameState.getGameState()==-1){
			m_gameState.updateGameState();
			Thread.sleep(1000);//go to sleep until game state is not in -1;
		}
		
	}

	private void sendInitialState() throws IOException {
		m_outStream.reset();
		m_outStream.writeObject(m_gameState.getGameStateMsg());	
		m_outStream.flush();
	}
	
	private void sendGameState() throws IOException{
		m_outStream.reset();
		m_outStream.writeObject(m_gameState.getGameStateMsg());
		m_outStream.flush();
	}

}
