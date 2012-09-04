package hk.edu.uic.mates.model.vo;

import java.io.Serializable;

public class OnlineUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private long matesId;
	private String url;
	private String weiboName;
	private String weiboId;
	
	public OnlineUser() {
		
	}

	public OnlineUser(User u) {
		matesId = u.getMatesId();
		url = u.getAvatarUrl_big();
		weiboName = u.getWeiboName();
		weiboId = u.getWeiboId();
	}

	public long getMatesId() {
		return matesId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getWeiboId() {
		return weiboId;
	}

	public String getWeiboName() {
		return weiboName;
	}

	public void setMatesId(long matesId) {
		this.matesId = matesId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public void setWeiboName(String weiboName) {
		this.weiboName = weiboName;
	}

}