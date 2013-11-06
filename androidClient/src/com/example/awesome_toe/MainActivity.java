package com.example.awesome_toe;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements OnDataPass {
	
	final String HOST_STRING = "192.168.1.129";
	final int    PORT_NUMBER = 8080;
	
	static GameState m_state = null;
	NetworkClient m_client = null;
	static TextView m_textView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Handle to activity 
		OnDataPass dataPasser = (OnDataPass)this;
		
		m_textView = (TextView)findViewById(R.id.playerPieceMsg);
		
		m_state = new GameState(dataPasser);
		
		m_client = new NetworkClient(HOST_STRING, PORT_NUMBER, dataPasser);
	}
	
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
				m_textView.setText(m_state.toString());;
			}
		});
	}
	
	public static void setState(int val) {
		System.out.println("ABDEBUG: inside main setState!");
		m_state.setValue(val);
	}

	@Override
	public void updateGameState(int in_num) {
		m_state.setValue(in_num);
		m_state.updateUI();
		
	}

}
