package io.nettythrift;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CommonServer {

	public void start(int port, ChannelHandler channelHandler) throws Exception {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
			   .option(ChannelOption.SO_BACKLOG, 100)
					.childHandler(channelHandler);
			ChannelFuture f = b.bind(port).sync();
//			System.out.printf("Server started and listen on port:%d\n", port);//
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully().sync();
			workerGroup.shutdownGracefully().sync();
		}
	}
}