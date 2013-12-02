package com.example.awesome_toe.test;

import com.example.awesome_toe.GameState;
import com.example.awesome_toe.MainActivity;
import com.example.awesome_toe.OnDataPass;

import android.test.InstrumentationTestCase;

public class GameStateTest extends InstrumentationTestCase {
	
	GameState testState;

	public GameStateTest() {
		super();
	}
	
	@Override
	 protected void setUp() throws Exception {
	   super.setUp();
	   testState = new GameState();
	 }
	
	public void testInitializedProperly() throws Exception {
		assertNotNull(testState);
		assertEquals("Value should be 0", 0, testState.m_tScore);
		assertEquals("Value should be 0", 0, testState.m_oScore);
		assertEquals("Value should be 0", 0, testState.m_eScore);
	}
	
	public void testAddingHandler() throws Exception {
		MainActivity act = new MainActivity();
		testState = new GameState((OnDataPass)act);
		assertEquals("handler should be activity", (OnDataPass) act, testState._getDataPassHandler());
	}

//	public void testToString() throws Exception {
//		//TODO toString was removed...
//	}

	
	public void testUpdateUI() throws Exception {
		
		class MyOnDataPass implements OnDataPass {
			
			public boolean funcCalled = false;

			@Override
			public void updateUI() {
				funcCalled = true;
				
			}

			@Override
			public void updateGameState(int in_num) {
				// TODO Auto-generated method stub	
			}	
		};
		
		MyOnDataPass myHandler = new MyOnDataPass();
		
		testState = new GameState(myHandler);
		testState.updateUI();
		assertEquals("Handler should have been called", true, myHandler.funcCalled);
		
	}
}
