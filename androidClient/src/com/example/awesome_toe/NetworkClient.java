package com.example.awesome_toe;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

//First parameter should be the host string, and 2nd should be the port as an integer.
public class NetworkClient {
	
	private String host;
	private int port;
	
	private Channel chan;
	
	public NetworkClient( String in_host, int in_port) {
		this.host = in_host;
		this.port = in_port;
	}
	
	public NetworkClient() {
		this("localhost", 8080);
	}
	
	public void writeToChannel(UpdatePacket m) {
		chan.writeAndFlush(m);
	}
		
    public void run() {
        
        Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				
				EventLoopGroup workerGroup = new OioEventLoopGroup();
				
				try {
		            Bootstrap b = new Bootstrap(); // (1)
		            b.group(workerGroup); // (2)
		            b.channel(OioSocketChannel.class); // (3)
		            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
		            b.handler(new ChannelInitializer<SocketChannel>() {
		                @Override
		                public void initChannel(SocketChannel ch) throws Exception {
		                    ch.pipeline().addLast(
		                    		new MessageEncoder(),
		                    		new MessageDecoder(),
		                    		new NetworkClientHandler(true));
		                }
		            });
		            
		            // Start the client.
		            chan = b.connect(new InetSocketAddress(host, port)).sync().channel();
		         
		            System.out.println("ABDEBUG: Client setup!");
		
		            // Wait until the connection is closed.
		            chan.closeFuture().awaitUninterruptibly();
		            System.out.println("ABDEBUG: sync() called!");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("ABDEBUG: In networkClient catch!");
					e.printStackTrace();
				} finally {
					System.out.println("ABDEBUG: in networkclient finally clause!");
					workerGroup.shutdownGracefully();
					System.out.println("ABDEBUG: in networkclient finally clause - after shutdown!");
				}
			}
        };
        
        Thread networkThread = new Thread(runnable);
        networkThread.start();      
        System.out.println("ABDEBUG: after threadStart called!");
    }
}
