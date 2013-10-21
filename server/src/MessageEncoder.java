import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<UpdatePacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UpdatePacket msg, ByteBuf out) {
        out.writeInt(msg.value());
    }
}