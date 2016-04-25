package alliance.networking.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
	
//	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);;
	
	private volatile static ArrayList<ChannelGroupStudentInvigilator> channelGroupStudentInvigilators 
		= new ArrayList<ChannelGroupStudentInvigilator>();;
	
	/**
	 * Add channel
	 * Display message for channel activation	
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.trace("New channel joined");
		
//		channelGroup.add(ctx.channel());

		//TODO channel is not added to channelGroup for unknown reasons
        ctx.write(
                "Welcome to " + InetAddress.getLocalHost().getHostName() + "!");
        ctx.write("It is " + new Date() + " now.");
        ctx.flush();
	}
	
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg)
//			throws Exception {
//		logger.trace("Read channel, forward message");
//		
//		logger.debug("number of channel: "+channelGroup.size());
//		for (Iterator iterator = channelGroup.iterator(); iterator.hasNext();) {
//			logger.debug("sending to a channel");
//			Channel channel = (Channel) iterator.next();
//			if(!channel.equals(ctx.channel())){
//				channel.writeAndFlush(msg);
//			}
//		}
////		channelGroup.writeAndFlush(msg);
//	}

	/**
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		

		if(msg instanceof VoiceSerializable){
			VoiceSerializable voice = (VoiceSerializable) msg;
			
			//find a group for the channel
			ChannelGroupStudentInvigilator channelGroup 
				= getChannelGroup(ctx.channel());
			if(channelGroup==null){
				throw new Exception("Group channel not found");
			}
			
			//send to all group member
//			ChannelGroup group = channelGroup.getChannelGroup();
//			for (Iterator iterator = group.iterator(); iterator.hasNext();) {
//				Channel channel = (Channel) iterator.next();
//				if(!channel.equals(ctx.channel())){
//					channel.writeAndFlush(msg);
//				}
//			}
			ArrayList<ChannelIdentity> channelIdentity = channelGroup.getChannelIdentity();
			for (int i = 0; i < channelIdentity.size(); i++) {
				if(!channelIdentity.get(i).getIdentity().getName().equals(
						voice.getIdentity().getName())){
					channelIdentity.get(i).getChannel().writeAndFlush(msg);
				}
			}
		}
		else if(msg instanceof VideoSerializable){
			VideoSerializable voice = (VideoSerializable) msg;
			
			//find a group for the channel
			ChannelGroupStudentInvigilator channelGroup 
				= getChannelGroup(ctx.channel());
			if(channelGroup==null){
				throw new Exception("Group channel not found");
			}
			
			ArrayList<ChannelIdentity> channelIdentity = channelGroup.getChannelIdentity();
			for (int i = 0; i < channelIdentity.size(); i++) {
				if(!channelIdentity.get(i).getIdentity().getName().equals(
						voice.getIdentity().getName())){
					channelIdentity.get(i).getChannel().writeAndFlush(msg);
				}
			}
		}
		else if(msg instanceof Identity){
			Identity identity = (Identity) msg;
			
			//check whether in the array list
			ChannelGroupStudentInvigilator channelGroup 
				= getChannelGroup(ctx.channel());
			
			if(channelGroup==null){
				//add to array list
				addChannel(ctx.channel(),identity);
			}
			
		}
	}

	/**
	 * Add a channel. Create new channel group if none exist, else join an existing one
	 * @param channel		New channel
	 * @param msg			Identity of the channel
	 * @throws Exception
	 */
	private void addChannel(Channel channel, Identity msg) throws Exception {
		if(channelGroupStudentInvigilators.size()==0 || isFull(msg)){
			//create a new one;
			ChannelGroupStudentInvigilator newGroup = new ChannelGroupStudentInvigilator();
			newGroup.addChannel(channel, msg);
			channelGroupStudentInvigilators.add(newGroup);
		}else{
			//join existing channel;
			joinChannelGroup(channel,msg);
		}
	}

	/**
	 * Join an existing channel group
	 * @param channel		New channel
	 * @param msg			Identity of channel
	 * @throws Exception
	 */
	private void joinChannelGroup(Channel channel, Identity msg) throws Exception {
		ChannelGroupStudentInvigilator channelGroup = null;
		for (int i = 0; i < channelGroupStudentInvigilators.size(); i++) {
			channelGroup = channelGroupStudentInvigilators.get(i);
			if(!channelGroup.isFull(msg)){
				channelGroup.addChannel(channel, msg);
			}
		}
	}

	/**
	 * Check if channel group is full
	 * @param msg
	 * @return
	 */
	private boolean isFull(Identity msg) {
		boolean full = true;
		
		ChannelGroupStudentInvigilator channelGroup = null;
		for (int i = 0; i < channelGroupStudentInvigilators.size(); i++) {
			channelGroup = channelGroupStudentInvigilators.get(i);
			if(!channelGroup.isFull(msg)){
				full = false;
				break;
			}
		}
		return full;
	}

	/**
	 * Return a channel group
	 * @param channel
	 * @return
	 */
	private ChannelGroupStudentInvigilator getChannelGroup(Channel channel) {
		ChannelGroupStudentInvigilator channelGroup = null;
		for (int i = 0; i < channelGroupStudentInvigilators.size(); i++) {
			channelGroup = channelGroupStudentInvigilators.get(i);
			for (int j = 0; j < channelGroup.getChannelIdentity().size(); j++) {
				if(channelGroup.getChannelIdentity().get(j).getChannel()
						.equals(channel)){
					return channelGroup;
				}
			}
		}
		return null;
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
