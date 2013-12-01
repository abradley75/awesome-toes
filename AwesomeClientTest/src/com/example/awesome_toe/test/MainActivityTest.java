package com.example.awesome_toe.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.awesome_toe.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity activity;

	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    activity = getActivity();
	  }
	
	public void testInitilizedCorrectly() throws Exception {
		assertNotNull(activity.getState());
		assertNotNull(activity.getClient());
	}
	
	public void testLayoutExists() throws Exception {
		LinearLayout outerLayout = (LinearLayout) activity.findViewById(com.example.awesome_toe.R.id.outermostLayout);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), outerLayout);
	}
	
	public void testSetState() throws Exception {
		assertEquals("Make sure initial state is default -1", activity.getState().getValue(), -1); // State should not have been set yet
		MainActivity.setState(5);
		assertEquals("Make sure state is set after call", activity.getState().getValue(), 5);
	}
	
	public void testUpdateGameState() throws Exception {
		activity.updateGameState(10);
		TextView view = activity.getTextView();
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), view);
		getInstrumentation().waitForIdleSync();
		assertEquals("Text should be 10", "10", view.getText());
	}

}
