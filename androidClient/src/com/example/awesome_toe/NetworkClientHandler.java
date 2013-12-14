package com.example.awesome_toe;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NetworkClientHandler extends SimpleChannelInboundHandler<UpdatePacket> {

		public NetworkClientHandler(boolean autorelease) {
			super(autorelease);
	}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx,
				UpdatePacket msg) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("ABDEBUG: in clienthandler channelRead!");
			 
		 	GameState state = GameState.getInstance();
		 
	        UpdatePacket m = (UpdatePacket) msg; // (1)
	        
	        System.out.println("ABDEBUG: " + m.toString());
	     
	        state.updateGameState(m);
		}
}
