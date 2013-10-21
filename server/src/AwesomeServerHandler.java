import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class AwesomeServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		UpdatePacket u = new UpdatePacket();
		System.out.println("Push: " + u.toString());
	    ChannelFuture f = ctx.writeAndFlush(u);
	    f.addListener(ChannelFutureListener.CLOSE);
	}
	
//	@Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ctx.write(msg);
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
