package alliance.networking.server;

import java.io.Serializable;

public class VideoSerializable implements Serializable{
	private byte[] videoImage;
	private Identity identity;
	
	public VideoSerializable(byte[] videoImage,Identity identity){
		this.videoImage = videoImage;
		this.identity = identity;
	}

	/**
	 * Get the video image
	 * @return
	 */
	public byte[] getVideoImage() {
		return videoImage;
	}

	/**
	 * Set the video image
	 * @param videoImage
	 */
	public void setVideoImage(byte[] videoImage) {
		this.videoImage = videoImage;
	}

	/**
	 * Get the identity
	 * @return
	 */
	public Identity getIdentity() {
		return identity;
	}

	/**
	 * Set the identity
	 * @param identity
	 */
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
}
