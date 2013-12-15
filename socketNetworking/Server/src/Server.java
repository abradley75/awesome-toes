import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private static int port = 8080;
	private static ServerSocket server;	
	private static final int MAXPLAYERS = 3;	
	
	public static ArrayList<PlayerThread> pThreads = new ArrayList<PlayerThread>();	
	public static int players = 0;

	public static void main(String[] args) throws IOException {
		
		server = new ServerSocket(port);		
		while(true){
			Socket player_sock = server.accept();
			player_sock.setTcpNoDelay(true);
			if(players<MAXPLAYERS){
				System.out.println("HERE");
				pThreads.add(new PlayerThread(GameServerState.getInstance(), player_sock, players));
				Thread t = new Thread(pThreads.get(players));
				t.start();
				players++;
			}
			else
				player_sock.close();
		}		

	}
	
	public static void resetGame(){
		if(players == 0){
			GameServerState.getInstance().resetGame();
		}
	}

}
