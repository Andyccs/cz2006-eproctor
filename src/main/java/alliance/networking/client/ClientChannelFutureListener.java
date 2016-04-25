package alliance.networking.client;

import alliance.networking.interfaces.ClientInterface;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class ClientChannelFutureListener implements ChannelFutureListener {
	private ClientInterface client;
	
	public ClientChannelFutureListener(ClientInterface client) {
		this.client = client;
	}
	
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		client.stop();
	}

}
