package alliance.networking.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alliance.networking.server.Identity;
import alliance.networking.server.VideoSerializable;
import alliance.networking.server.VoiceSerializable;


public class ClientHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	private static ClientReceivedObject object = ClientReceivedObject.getInstance();

	private Identity identity;
	public ClientHandler(Identity identity) {
		this.identity = identity;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(identity);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		logger.trace("read channel, message: " + msg);
		if(msg instanceof VideoSerializable){
			VideoSerializable video = (VideoSerializable) msg;
			video.setVideoImage(CompressionUtils.decompress(video.getVideoImage()));
			msg = video;
		}
		else if(msg instanceof VoiceSerializable){
			VoiceSerializable voice = (VoiceSerializable) msg;
			voice.setVoice(CompressionUtils.decompress(voice.getVoice()));
			msg = voice;
		}
		object.setReceived(msg);
		object.notifyObservers(msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error(cause.getMessage());
		cause.printStackTrace();
	}
}
