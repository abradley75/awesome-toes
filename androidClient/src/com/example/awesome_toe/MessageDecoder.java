package com.example.awesome_toe;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

//This is a handler for the client that takes care of the segmenting that can happen on a socket connection.
//It makes sure there are at least 4 bytes to read before reading from a socket.
public class MessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		if (in.readableBytes() < 54) {
            return;
        }
		
		char turn = in.readChar();
		char[][] board = new char[GameState.BOARDSIZE][GameState.BOARDSIZE];
		
		for(int i = 0; i < GameState.BOARDSIZE; i++) {
			for(int j = 0; j < GameState.BOARDSIZE; j++) {
				board[i][j] = in.readChar();
			}
		}
		
		boolean gameEnd = false;
		if(in.readInt() == 1)
			gameEnd = true;
		else if (in.readInt() == 0)
			gameEnd = false;
		else
			System.out.println("ABDEBUG: MessageDecoder -- should never hit this");
		
        out.add(new UpdatePacket(turn, board, gameEnd));
	}
}