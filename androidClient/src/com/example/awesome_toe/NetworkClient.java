package com.example.awesome_toe;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;

//First parameter should be the host string, and 2nd should be the port as an integer.
public class NetworkClient {
	
	private String host;
	private int port;
	
	public NetworkClient( String in_host, int in_port) {
		this.host = in_host;
		this.port = in_port;		
	}
	
	public NetworkClient() {
		this("localhost", 8080);
	}
		
    public void run() throws Exception {
    	
        EventLoopGroup workerGroup = new OioEventLoopGroup();
        
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(OioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageDecoder(), new NetworkClientHandler());
                }
            });
            
            // Start the client.
            ChannelFuture f = b.connect(this.host, this.port).sync(); // (5)
            System.out.println("ABDEBUG: Client setup!");

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
