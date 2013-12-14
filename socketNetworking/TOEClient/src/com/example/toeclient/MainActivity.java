package com.example.toeclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText host_text;
	Button connect_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		host_text = (EditText)findViewById(R.id.editText1);
		connect_button = (Button)findViewById(R.id.button1);
		connect_button.setOnClickListener(connect);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	View.OnClickListener connect = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this, GameActivity.class);
            String message = host_text.getText().toString();
            intent.putExtra("serverIP", message);
            startActivity(intent);
		}
		
		
	};

}
