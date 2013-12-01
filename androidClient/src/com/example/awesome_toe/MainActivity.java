package com.example.awesome_toe;

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
	NetworkClient m_client = null;
	static TextView m_textView = null;
	
	static TextView tScore, oScore, eScore; //text view for scores
	static ImageView playerPiece, playerTurn;//image view for player piece and player turn
	
	static ImageButton[][] boardButtons = new ImageButton[5][5];//2d array for buttons	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		//Handle to activity 
		OnDataPass dataPasser = (OnDataPass)this;
		
		m_textView = (TextView)findViewById(R.id.playerPieceMsg);		
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
		
		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++){
				int resId = getResources().getIdentifier("board"+Integer.toString(i)+Integer.toString(j), "id", "com.example.awesome_toe");
				boardButtons[i][j] = (ImageButton)findViewById(resId);
				boardButtons[i][j].setOnClickListener(boardButtonHandler);
				
			}
	}
	
	View.OnClickListener boardButtonHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int row = -1, col = -1;
			
			for(int i=0; i<5; i++)
				for(int j=0; j<5; j++){
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
			}
		});
	}
	
	public static void setState(int val) {
		System.out.println("ABDEBUG: inside main setState!");
		//m_state.setValue(val);
	}

	@Override
	public void updateGameState(int in_num) {
		//m_state.setValue(in_num);
		m_state.updateUI();
		
	}

}
