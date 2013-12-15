package com.example.toeclient;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {
	//networking stuff
	private String hostString;
	private int port = 8080;
	private Client m_client;
	
	//UI COMPONENTS
	private TextView message;
	private TextView tScore;
	private TextView oScore;
	private TextView eScore;
	
	private TextView turn;
	private TextView piece;
	
	private TextView boardSize;
	private Button plus;
	private Button minus;
	private Button setBoard;
	
	private int size = 5;
	
	private String m_dispMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//display ui
		message = (TextView)findViewById(R.id.Message);
		tScore = (TextView)findViewById(R.id.tScore);
		oScore = (TextView)findViewById(R.id.oScore);
		eScore = (TextView)findViewById(R.id.eScore);
				
		turn = (TextView)findViewById(R.id.turn);
		piece = (TextView)findViewById(R.id.piece);
		
		boardSize = (TextView)findViewById(R.id.boardSize);
		//ui with events
		plus = (Button)findViewById(R.id.plusBut);
		minus = (Button)findViewById(R.id.minusBut);
		setBoard = (Button)findViewById(R.id.setBoardBut);		
		
		plus.setOnClickListener(addSize);
		minus.setOnClickListener(minusSize);
		setBoard.setOnClickListener(sendSize);
		
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
				message.setText(m_dispMessage);			
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
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
		plus.setEnabled(true);
		minus.setEnabled(true);
		setBoard.setEnabled(true);		
	}			

}
