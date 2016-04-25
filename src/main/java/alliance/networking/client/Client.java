package alliance.networking.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import alliance.networking.interfaces.ClientInterface;
import alliance.networking.server.Identity;
import alliance.networking.server.VideoSerializable;
import alliance.networking.server.VoiceSerializable;


public class Client implements ClientInterface{
	private final String host;
	private final int port;
	
	private static Client instance;
	
	private boolean running;

	private Channel channel;
	private EventLoopGroup group ;
	
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	
	public static final String XML_PATH = "META-INF/spring/networkBean.xml";

	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);
	
	private Identity identity;
	
	private Client(String host, int port) {
		this.host = host;
		this.port = port;
		running = false;
	}
	
	/**
	 * Get an instance of client
	 * @return Client
	 */
	public static Client getInstance(){
		if(instance==null){
			instance = context.getBean(Client.class);
		}
		return instance;
	}
	/**
	 * Start running the client
	 * @param identity
	 */
	public void start(Identity identity) throws Exception{
		logger.trace("Start logger with, " +
				"identity: "+identity.getName()+
				", ip address: "+ identity.getIpAddress());
		
		//if the client is already running, do nothing
		if(running){
			return;
		}
		
		this.identity = identity;
		
		//set the running flag to true so that this method will
		//not be executed again while the client is running
		running = true;
		
		//a group of worker thread that will handle the network
		//session, such as reading the channel.
		group = new NioEventLoopGroup();
		Bootstrap bootStrap = new Bootstrap();
		bootStrap.group(group)
		.channel(NioSocketChannel.class)
		.handler(new ClientInitializer(identity));
		
		//start the channel
		channel = bootStrap.connect(host,port).sync().channel();
		
	}
	
	/**
	 * Check if client is already running
	 * @return boolean
	 */
	@Override
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Stops client
	 */
	public synchronized void stop() throws Exception{
//		//////added 2014/3/17 11.26am
//		
//		//channelFuture will be notified when this channel is closed. 
//		ChannelFuture channelFuture = channel.closeFuture().sync();
//		
//		//when a channel is complete for whatever reasons, stop it
//		
//		//for whatever reasons, stop it.
//		channelFuture.addListener(new ClientChannelFutureListener(this));
		////////
		group.shutdownGracefully();
		
		//the client is not running now
		running = false;
		
		//remove all observers
		ClientReceivedObject.getInstance().deleteObservers();
	}
	
	/**
	 * Sends video or voices
	 * @param object		Video or voice to be sent
	 */
	public synchronized void sendObject(Object object){
		
		//if it is a VideoSerializable, compress it
		if(object instanceof VideoSerializable){
			try {
				VideoSerializable video = (VideoSerializable) object;
				video.setVideoImage(CompressionUtils.compress(video.getVideoImage()));
				object = video;
			} catch (IOException e) {
				logger.error("Cannot compress Image, send as normal");
			}
		}
		
		//if it is a VoiceSerializable, compress it
		else if(object instanceof VoiceSerializable){
			try {
				VoiceSerializable voice = (VoiceSerializable) object;
				voice.setVoice(CompressionUtils.compress(voice.getVoice()));
				object = voice;
			} catch (IOException e) {
				logger.error("Cannot compress Voice, send as normal");
			}
		}	
		
		//whatever it is, send it
		channel.writeAndFlush(object);
	}
	
	/**
	 * Add new observers
	 * @param observer			Observer to be added
	 */
	public void registerObserver(Observer observer){
		ClientReceivedObject receivedObject = ClientReceivedObject.getInstance();
		receivedObject.addObserver(observer);
	}

	/**
	 * Remove observer
	 * @param observer			Observer to be deleted
	 */
	@Override
	public synchronized void deregisterObserver(Observer observer) {
		ClientReceivedObject receivedObject = ClientReceivedObject.getInstance();
		receivedObject.deleteObserver(observer);
	}
}
