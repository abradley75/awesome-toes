package com.example.awesome_toe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ConnectActivity extends Activity {
	
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		
		editText = (EditText)findViewById(R.id.connectip);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connect, menu);
		return true;
	}
	
	public void connectToServer(View view){
		Intent intent = new Intent(this, MainActivity.class);
		String message = editText.getText().toString();
		
		if(!message.isEmpty()) {
			intent.putExtra("serverIP", message);
		}
		this.startActivity(intent);
	}

}
