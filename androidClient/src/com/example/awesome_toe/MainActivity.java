package com.example.awesome_toe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnDataPass {
	
	final String HOST_STRING = "192.168.1.134";
	final int    PORT_NUMBER = 8080;
	
	static GameState m_state = null;
	static NetworkClient m_client = null;
	
	public static TextView tScore, oScore, eScore; //text view for scores
	public static ImageView playerPiece, playerTurn;//image view for player piece and player turn
	
	public static ImageButton[][] boardButtons = new ImageButton[GameState.BOARDSIZE][GameState.BOARDSIZE]; //2d array for buttons	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		//Handle to activity 
		OnDataPass dataPasser = (OnDataPass)this;
				
		m_state = GameState.getInstance();
		m_state.setDataPassHandler(dataPasser);
		
		m_client = new NetworkClient(HOST_STRING, PORT_NUMBER);
		
		tScore = (TextView)findViewById(R.id.tScore);
		oScore = (TextView)findViewById(R.id.oScore);
		eScore = (TextView)findViewById(R.id.eScore);
		
		tScore.setText(Integer.toString(m_state.m_tScore));
		oScore.setText(Integer.toString(m_state.m_oScore));
		eScore.setText(Integer.toString(m_state.m_eScore));		
		
		playerPiece = (ImageView)findViewById(R.id.playerPiece);
		playerTurn = (ImageView)findViewById(R.id.playerTurn);
		
		for(int i=0; i<GameState.BOARDSIZE; i++) {
			for(int j=0; j<GameState.BOARDSIZE; j++){
				int resId = getResources().getIdentifier("board"+Integer.toString(i)+Integer.toString(j), "id", "com.example.awesome_toe");
				boardButtons[i][j] = (ImageButton)findViewById(resId);
				boardButtons[i][j].setOnClickListener(boardButtonHandler);		
			}
		}
	}
	
	View.OnClickListener boardButtonHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int row = -1, col = -1;
			
			for(int i=0; i<GameState.BOARDSIZE; i++)
				for(int j=0; j<GameState.BOARDSIZE; j++){
					if(v.getId() == boardButtons[i][j].getId()){
						row = i;
						col = j;
					}
				}
			
			//row and col identifies the button clicked
			//get m_state to send the move to server
			Log.i("AwesomeClient", "calling send move"+row+col);
			m_state.applyMoveToBoard(row, col);			
			m_client.writeToChannel(m_state.sendMove());
		}
	};	
	
	@Override
	protected void onResume() {
		super.onResume();
		try {
			m_client.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void updateUI() {
		System.out.println("ABDEBUG: inside main updateView!");
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				//setting UI
				tScore.setText(Integer.toString(m_state.m_tScore));
				oScore.setText(Integer.toString(m_state.m_oScore));
				eScore.setText(Integer.toString(m_state.m_eScore));
				setPlayerStatusUI();
				

				if(setBoardUI() == -1)
					gracefullyFailAndReset();
				
				if(m_state.checkGameEnd()){
					char winner = m_state.getWinner();
					Toast.makeText(MainActivity.this, "winner is "+winner, Toast.LENGTH_LONG).show();
				}
			}

		});
	}
	
	private void setPlayerStatusUI() {
		
		char piece = m_state.getPlayerPiece();
		char turn = m_state.getPlayerTurn();
		
		switch (piece) {
		case 'a':
			playerPiece.setImageResource(R.drawable.back);
			break;
		case 't':
			playerPiece.setImageResource(R.drawable.t);
			break;
		case 'o':
			playerPiece.setImageResource(R.drawable.o);
			break;
		case 'e':
			playerPiece.setImageResource(R.drawable.e);
			break;
		default:
			System.out.println("ABDEBUG: setplayerstatusui not a valid piece");
			return; // Should never happen			
	}
		
		switch (turn) {
		case 'a':
			playerTurn.setImageResource(R.drawable.back);
			break;
		case 't':
			playerTurn.setImageResource(R.drawable.t);
			break;
		case 'o':
			playerTurn.setImageResource(R.drawable.o);
			break;
		case 'e':
			playerTurn.setImageResource(R.drawable.e);
			break;
		default:
			System.out.println("ABDEBUG: setplayerstatusui not a valid turn");
			return; // Should never happen			
	}
		
		
	}

	//this function returns 0 is success. -1 means there is an invalid state and should gracefully fail.
		private int setBoardUI() {
			char[][] board = m_state.getBoard();
			for (int i = 0 ; i < GameState.BOARDSIZE; i++) {
				for( int j = 0; j < GameState.BOARDSIZE; j++) {
					int resId = getResources().getIdentifier("board"+Integer.toString(i)+Integer.toString(j), "id", "com.example.awesome_toe");
					ImageButton selView = (ImageButton)findViewById(resId);				
					
					char piece = board[i][j];
					
					switch (piece) {
						case 'a':
							selView.setImageResource(R.drawable.back);
							break;
						case 't':
							selView.setImageResource(R.drawable.t);
							break;
						case 'o':
							selView.setImageResource(R.drawable.o);
							break;
						case 'e':
							selView.setImageResource(R.drawable.e);
							break;
						default:
							return -1; // Should never happen		
							
					}
				}
			}
			return 0;		
		}
	
	protected void gracefullyFailAndReset() {
		// TODO Auto-generated method stub
	}
	
	public static GameState getState() {
		return m_state;
	}
	
	public static NetworkClient getClient() {
		return m_client;
	}
}
