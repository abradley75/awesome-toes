package com.example.toeclient;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
	//networking stuff
	private String hostString;
	private int port = 8080;
	private Client m_client;
	
	//UI COMPONENTS
	private TextView m_message;
	private TextView tScore;
	private TextView oScore;
	private TextView eScore;
	
	private TextView m_turn;
	private TextView m_piece;
	
	private TextView boardSize;
	private Button plus;
	private Button minus;
	private Button setBoard;
	
	private Button[][] m_buttons;
	
	private int size = 5;
	private GameState m_gameState;
	
	private String m_dispMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//display ui
		m_message = (TextView)findViewById(R.id.Message);
		tScore = (TextView)findViewById(R.id.tScore);
		oScore = (TextView)findViewById(R.id.oScore);
		eScore = (TextView)findViewById(R.id.eScore);
				
		m_turn = (TextView)findViewById(R.id.turn);
		m_piece = (TextView)findViewById(R.id.piece);
		
		boardSize = (TextView)findViewById(R.id.boardSize);
		//ui with events
		plus = (Button)findViewById(R.id.plusBut);
		minus = (Button)findViewById(R.id.minusBut);
		setBoard = (Button)findViewById(R.id.setBoardBut);		
		
		plus.setOnClickListener(addSize);
		minus.setOnClickListener(minusSize);
		setBoard.setOnClickListener(sendSize);
		m_gameState = GameState.getInstance();
		
		plus.setEnabled(false);
		minus.setEnabled(false);
		setBoard.setEnabled(false);
		
		Bundle extras = getIntent().getExtras();
		hostString = extras.getString("serverIP");
		
		try {
			m_client = new Client(GameActivity.this, hostString, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t = new Thread(m_client);
		t.start();			
		
	}
	public void setMessage(String dispMessage) throws IOException {
		m_dispMessage = dispMessage;
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				m_message.setText(m_dispMessage);			
			}			
		});
	}
	
	public void sendMessage(String message) throws IOException{
		m_client.sendMessage(message);
	}
	
	View.OnClickListener addSize = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			size+=1;
			boardSize.setText(Integer.toString(size));			
		}		
	};
	
	View.OnClickListener minusSize = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			size-=1;
			boardSize.setText(Integer.toString(size));			
		}		
	};
	
	View.OnClickListener sendSize = new View.OnClickListener(){
		@Override
		public void onClick(View v) {
			try {
				sendMessage("Board:"+Integer.toString(size)+","+Integer.toString(size));
				plus.setEnabled(false);
				minus.setEnabled(false);
				setBoard.setEnabled(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	};
	public void enableButtons() {
		plus.setEnabled(true);
		minus.setEnabled(true);
		setBoard.setEnabled(true);		
	}
	
	public void setBoardUI() {
		runOnUiThread(new Runnable(){
			@Override
			public void run() {
				int row = m_gameState.getRow();
				int col = m_gameState.getCol();
				m_buttons = new Button[row][col];
				LinearLayout container = (LinearLayout)findViewById(R.id.boardLayout);
				for(int i=0; i<row; i++){
					LinearLayout layoutRow = new LinearLayout(GameActivity.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					container.addView(layoutRow, lp);
					for(int j=0; j<col; j++){
						LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
						m_buttons[i][j] = new Button(GameActivity.this);
						m_buttons[i][j].setText("");
						m_buttons[i][j].setId(i*10+j);
						layoutRow.addView(m_buttons[i][j], bp);
						m_buttons[i][j].setOnClickListener(boardButs);
					}
				}			
			}			
		});
	}	
	
	View.OnClickListener boardButs = new View.OnClickListener(){
		@Override
		public void onClick(View v) {
			int butId = v.getId();
			int row = 0, col = 0;
			for(int i=0; i<m_gameState.getRow(); i++)
				for(int j=0; j<m_gameState.getCol(); j++)
					if(m_buttons[i][j].getId() == butId ){
						row = i;
						col = j;
					}
			String m = "Board:"+Integer.toString(row)+","+Integer.toString(col);
			
			try {
				m_client.sendMessage(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	};
	public void updateUI() {
		runOnUiThread(new Runnable(){
			System.out.println("SSSSSSSSSSasdawdawdadaSSSSSSSSSSSSSSSSSSSSSSSSSSSS");

			@Override
			public void run() {
				int row = m_gameState.getRow();
				int col = m_gameState.getCol();
				//calculate score
				char piece = m_gameState.getPiece();
				char turn = m_gameState.getTurn();
				char board[][] = m_gameState.getBoard();
				String message ="";
				if(piece == turn)
					message = "Your Turn!";
				else
					message = "Waiting For "+Character.toUpperCase(turn);
				m_message.setText(message);
				m_turn.setText(String.valueOf(turn));
				for(int i=0; i<row; i++)
					for(int j=0; j<col; j++)
						m_buttons[i][j].setText(String.valueOf(board[i][j]));				
			}			
		});
		
	}
	public void handleGameEnd() {
		// TODO Auto-generated method stub
		
	}
}


