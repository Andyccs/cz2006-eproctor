package alliance.networking.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {
	private final int port;

	public static final String XML_PATH = "META-INF/spring/networkBean.xml";
	
	public static final ApplicationContext context = new ClassPathXmlApplicationContext(XML_PATH);

	public static Server instance;
	private Server(int port){
		this.port = port;
	}
	/**
	 * Get an instance of server
	 * @return
	 */
	public static Server getInstance(){
		if(instance==null){
			instance = context.getBean(Server.class);
		}
		return instance;
	}
	/**
	 * Run the server
	 * @throws Exception
	 */
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(1);
		try{
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ServerInitializer());
			
			bootStrap.bind(port).sync().channel().closeFuture().sync();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
    public static void main(String[] args) throws Exception {
        Server.getInstance().run();
    }
}
