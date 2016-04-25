package alliance.networking.server;

import java.io.Serializable;

public class VoiceSerializable implements Serializable {
	private byte[] voice;
	private Identity identity;

	public VoiceSerializable(byte[] voice,Identity identity) {
		this.voice = voice;
		this.identity = identity;
	}
	
	/**
	 * Get voice as byte array
	 * @return
	 */
	public byte[] getVoice() {
		return voice;
	}
	
	/**
	 * Set voice with byte array
	 * @param voice
	 */
	public void setVoice(byte[] voice) {
		this.voice = voice;
	}
	
	/**
	 * Get identity
	 * @return
	 */
	public Identity getIdentity() {
		return identity;
	}
	
	/**
	 * Set Identity
	 * @param identity
	 */
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
}
