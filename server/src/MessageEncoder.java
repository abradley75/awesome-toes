import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<UpdatePacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UpdatePacket msg, ByteBuf out) {
    	
    	//Total buffer size is: 56 bytes (2 for turn + 50 for board + 4 for endgame flag)
    	
        out.writeChar(msg.getPlayerTurn());
        char[][] board = msg.getBoardState();
        for(int i = 0; i < GameServerState.BOARDSIZE; i++) {
			for(int j = 0; j < GameServerState.BOARDSIZE; j++) {
				out.writeChar(board[i][j]);
			}
		}
        if (msg.isGameEnd())
        	out.writeInt(1);
        else
        	out.writeInt(0);
    }
}