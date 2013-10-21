package com.example.awesome_toe;

import com.example.geoquiz.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final String HOST_STRING = "sweb.uky.edu/~CABR222";
	final int    PORT_NUMBER = 8080;
	
	static GameState m_state = null;
	NetworkClient m_client = null;
	static TextView m_textView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_textView = (TextView)findViewById(R.id.myTextView);
		
		m_client = new NetworkClient();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static void updateView() {
		m_textView.setText(m_state.toString());
		
	}

}
