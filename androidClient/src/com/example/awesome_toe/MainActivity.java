package com.example.awesome_toe;

import com.example.awesome_toe.R.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnDataPass {
	
	final String HOST_STRING = "192.168.0.7";
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
				
		m_state = new GameState(dataPasser);		
		m_client = new NetworkClient(HOST_STRING, PORT_NUMBER, dataPasser);
		
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
			// TODO Auto-generated method stub
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
			m_state.sendMove(row, col);			
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
				//setting ui
				tScore.setText(Integer.toString(m_state.m_tScore));
				oScore.setText(Integer.toString(m_state.m_oScore));
				eScore.setText(Integer.toString(m_state.m_eScore));
				//need to set player piece, player turn and board
				//m_textView.setText(m_state.toString());;
				
				if(setBoardUI() == -1)
					gracefullyFailAndReset();//TODO handle invalid setBoard
			}

		});
	}
	
	protected void gracefullyFailAndReset() {
		// TODO Auto-generated method stub
		
	}

	//this function returns 0 is success. -1 means there is an invalid state and should gracefully fail.
	private int setBoardUI() {
		for (int i = 0 ; i < GameState.BOARDSIZE; i++) {
			for( int j = 0; j < GameState.BOARDSIZE; j++) {
				int resId = getResources().getIdentifier("board"+Integer.toString(i)+Integer.toString(j), "id", "com.example.awesome_toe");
				ImageButton selView = (ImageButton)findViewById(resId);
				
				char[][] board = m_state.getBoard();
				char piece = board[i][j];
				
				switch (piece) {
					case 'a':
						selView.setImageResource(drawable.back);
						break;
					case 't':
						selView.setImageResource(drawable.t);
						break;
					case 'o':
						selView.setImageResource(drawable.o);
						break;
					case 'e':
						selView.setImageResource(drawable.e);
						break;
					default:
						return -1; // Should never happen		
						
				}
			}
		}
		return 0;		
	}
	
	public static GameState getState() {
		return m_state;
	}
	
	public static NetworkClient getClient() {
		return m_client;
	}

	@Override
	public void updateGameState(UpdatePacket m) {
		// TODO Auto-generated method stub
		
	}
}
