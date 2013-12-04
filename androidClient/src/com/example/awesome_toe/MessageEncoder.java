package com.example.awesome_toe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<UpdatePacket> {
	
	@Override
    protected void encode(ChannelHandlerContext ctx, UpdatePacket msg, ByteBuf out) {
    	
    	//Total buffer size is: 62 bytes (2 for playerSending + 2 for current turn +  50 for board + 8 for endgame flag(represented as int)
    	
    	out.writeChar(msg.getPlayerSending());
        out.writeChar(msg.getPlayerTurn());
        char[][] board = msg.getBoardState();
        for(int i = 0; i < GameState.BOARDSIZE; i++) {
			for(int j = 0; j < GameState.BOARDSIZE; j++) {
				out.writeChar(board[i][j]);
			}
		}
        if (msg.isGameEnd())
        	out.writeInt(1);
        else
        	out.writeInt(0);
    }

}
