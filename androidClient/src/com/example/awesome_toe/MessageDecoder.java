package com.example.awesome_toe;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


public class MessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		
		System.out.println("ABDEBUG: in messageDecoder");
		
		char playerSending = in.readChar();
		char playerTurn = in.readChar();
		char[][] board = new char[GameState.BOARDSIZE][GameState.BOARDSIZE];
		
		for(int i = 0; i < GameState.BOARDSIZE; i++) {
			for(int j = 0; j < GameState.BOARDSIZE; j++) {
				board[i][j] = in.readChar();
			}
		}
		
		boolean gameEnd;
		if(in.readInt() == 1)
			gameEnd = true;
		else if (in.readInt() == 0)
			gameEnd = false;
		else
			gameEnd = false;
			System.out.println("ABDEBUG: MessageDecoder -- bad GameEnd flag");
		
		//If turn is valid and playerSending is unused then from server
		if(playerSending == UpdatePacket.NOTUSED) {
			out.add(new UpdatePacket(playerTurn, board, gameEnd, UpdatePacket.FROMSERVER));
		}
		else {
			System.out.println("ABDEBUG: Invalid updatepacket from server, creating anyway");
			out.add(new UpdatePacket(playerSending, playerTurn, board, gameEnd));
		}
	}
}