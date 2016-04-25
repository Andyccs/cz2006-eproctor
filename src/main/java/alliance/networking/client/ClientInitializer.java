package alliance.networking.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import alliance.networking.server.Identity;


public class ClientInitializer extends ChannelInitializer<SocketChannel>{

	private Identity identity;
	
	public ClientInitializer(Identity identity) {
		this.identity = identity;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(
				new ObjectEncoder(),
				new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
				new ClientHandler(identity));
	}
	
}
