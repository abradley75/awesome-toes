import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


public class MessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		
		System.out.println("ABDEBUG: in messageDecoder");
		if(in.readableBytes() < 58) {
			return;
		}
		
		System.out.println("ABDEBUG: msgDecoder, enough bytes!");
		
		char playerSending = in.readChar();
		char playerTurn = in.readChar();
		char[][] board = new char[GameServerState.BOARDSIZE][GameServerState.BOARDSIZE];
		
		for(int i = 0; i < GameServerState.BOARDSIZE; i++) {
			for(int j = 0; j < GameServerState.BOARDSIZE; j++) {
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
		
		//If turn is unused and playerSending is valid then from client
		if(playerTurn == UpdatePacket.NOTUSED) {
			out.add(new UpdatePacket(playerSending, board, gameEnd, UpdatePacket.FROMCLIENT));
		}
		else {
			System.out.println("ABDEBUG: Invalid updatepacket from client, creating anyway");
			out.add(new UpdatePacket(playerSending, playerTurn, board, gameEnd));
		}
	}

}