package com.example.awesome_toe;

import com.example.awesome_toe.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final String HOST_STRING = "192.168.1.112";
	final int    PORT_NUMBER = 8080;
	
	static GameState m_state = null;
	NetworkClient m_client = null;
	static TextView m_textView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_textView = (TextView)findViewById(R.id.my_text_view);
		
		m_client = new NetworkClient(HOST_STRING, PORT_NUMBER);
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

	public void updateView() {
		System.out.println("ABDEBUG: inside main updateView!");
		
//		Runnable mRunnable = new Runnable() {
//		    @Override
//		    public void run() {
//		    	m_textView.setText(m_state.toString());
//		    }
//		};
		
		//runOnUiThread(mRunnable);
	}
	
	
	
	public static void setState(int val) {
		System.out.println("ABDEBUG: inside main setState!");
		m_state.setValue(val);
	}

}
