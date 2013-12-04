package com.example.awesome_toe;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NetworkClientHandler extends ChannelInboundHandlerAdapter {
	
	 	@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
	 		
	 		System.out.println("ABDEBUG: in clienthandler channelRead!");
		 
		 	GameState state = GameState.getInstance();
		 
	        UpdatePacket m = (UpdatePacket) msg; // (1)
	     
	        state.updateGameState(m);
	    }
}
