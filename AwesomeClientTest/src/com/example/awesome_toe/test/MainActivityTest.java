package com.example.awesome_toe.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.LinearLayout;

import com.example.awesome_toe.GameState;
import com.example.awesome_toe.MainActivity;
import com.example.awesome_toe.R.drawable;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private static MainActivity activity;

	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    activity = getActivity();
	  }
	
	public void testInitilizedCorrectly() throws Exception {
		assertNotNull("gameState should not be null", MainActivity.getState());
		assertNotNull("client should not be null", MainActivity.getClient());
		assertEquals("Scores should be set to 0", "0", MainActivity.tScore.getText());
		assertEquals("Scores should be set to 0", "0", MainActivity.oScore.getText());
		assertEquals("Scores should be set to 0", "0", MainActivity.eScore.getText());
		
		for(int i = 0 ; i < GameState.BOARDSIZE ; i++) {
			for(int j = 0 ; j < GameState.BOARDSIZE ; j++) {
				assertNotNull("boardButtons should all be there", MainActivity.boardButtons[i][j]);
			}
		}
	}
	
	public void testLayoutExists() throws Exception {
		LinearLayout outerLayout = (LinearLayout) activity.findViewById(com.example.awesome_toe.R.id.outermostLayout);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), outerLayout);
	}
	
	public void testUpdateUIWithScores() throws Exception {
		GameState testState = MainActivity.getState();
		testState.m_tScore = 50;
		testState.m_oScore = 50;
		testState.m_eScore = 50;
		
		activity.updateUI();
		getInstrumentation().waitForIdleSync();
		
		assertEquals("Scores should be set to 50", "50", MainActivity.tScore.getText());
		assertEquals("Scores should be set to 50", "50", MainActivity.oScore.getText());
		assertEquals("Scores should be set to 50", "50", MainActivity.eScore.getText());
		
	}
}
