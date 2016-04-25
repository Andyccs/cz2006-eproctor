package alliance.networking.server;

import io.netty.channel.Channel;

public class ChannelIdentity {
	private volatile Identity identity;
	private volatile Channel channel;
	
	public ChannelIdentity(Identity identity,Channel channel) {
		this.identity = identity;
		this.channel = channel;
	}

	/**
	 * Getter for Identity
	 * @return Identity
	 */
	public Identity getIdentity() {
		return identity;
	}

	/**
	 * Setter for Identity
	 * @param identity
	 */
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	/**
	 * Getter for channel
	 * @return
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Setter for channel
	 * @param channel
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
}
