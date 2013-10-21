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
		if (in.readableBytes() < 4) {
            return;
        }
        out.add(new UpdatePacket(in.readInt()));
	}
}