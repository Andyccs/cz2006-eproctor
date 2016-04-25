package alliance.networking.server;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;

public class ChannelGroupStudentInvigilator {
	public static final int MAX_INVIGILATOR_NUMBER = 1;
	public static final int MAX_STUDENT_NUMBER = 2;
	
	private volatile int invigilatorNumber;
	private volatile int studentNumber;
	
	private volatile ArrayList<ChannelIdentity> channelIdentity;
	
	private volatile ChannelGroup channelGroup;
	
	public ChannelGroupStudentInvigilator() {
		invigilatorNumber = 0;
		studentNumber = 0;
		channelIdentity = new ArrayList<ChannelIdentity>();
		channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	}

	/**
	 * Get identity of channel in an array list
	 * @return ArrayList<ChannelIdentity>
	 */
	public ArrayList<ChannelIdentity> getChannelIdentity() {
		return channelIdentity;
	}

	/**
	 * Get the channel group object
	 * @return ChannelGroup
	 */
	public ChannelGroup getChannelGroup() {
		return channelGroup;
	}
	
	/**
	 * Check if anymore student or invigilator can be added into the channel
	 * @param identity		Can be invigilator or student
	 * @return	boolean		Checked result.
	 */
	public boolean isFull(Identity identity){
		if(identity.getName().equals(Identity.STUDENT) && studentNumber==MAX_STUDENT_NUMBER){
			return true;
		}
		else if(identity.getName().equals(Identity.INVIGILATOR) && invigilatorNumber==MAX_INVIGILATOR_NUMBER){
			return true;
		}
		return false;
	}

	/**
	 * Add a person into channel
	 * @param channel		Target channel
	 * @param identity		Target person
	 * @throws Exception
	 */
	public void addChannel(Channel channel, Identity identity) throws Exception{
		if(identity.getName().equals(Identity.STUDENT)){
			if(studentNumber>=MAX_STUDENT_NUMBER){
				throw new Exception("Channel full");
			}
			studentNumber++;
		}
		else if(identity.getName().equals(Identity.INVIGILATOR)){
			if(invigilatorNumber>=MAX_INVIGILATOR_NUMBER){
				throw new Exception("Channel full");
			}
			invigilatorNumber++;
		}
		channelIdentity.add(new ChannelIdentity(identity, channel));
		channelGroup.add(channel);
	}
	
}
