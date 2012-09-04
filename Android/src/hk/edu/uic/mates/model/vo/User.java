package hk.edu.uic.mates.model.vo;

import java.io.Serializable;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum Gender {
		Male, Female, NULL;
	}
	public enum Grade {
		Year_One, Year_Two, Year_Three, Year_Four, NULL;
	}
	public enum Major {
		CST, TESL, AE, FIN, SAT, NULL;
	}

	private int id;
	private long matesId;
	private String weiboId;
	private String weiboName;
	private String avatarUrl_big;
	private Bitmap avatar_big;
	private String avatarUrl_small;
	private Bitmap avatar_small;
	private String avatarPath_big;
	private String avatarPath_small;
	private String phone;
	private Major major;
	private String qq;
	private Gender gender;
	private Grade grade;

	public User() {
		
	}
	
	public User(SharedPreferences settings) {
		weiboId = settings.getString("weiboId", null);
		weiboName = settings.getString("weiboName", null);
		matesId =settings.getLong("matesId", 0);
		if (settings.getString("gender", null) != null) {
			gender = Gender.valueOf(settings
					.getString("gender", null));
		}
		if (settings.getString("major", null) != null) {
			major = Major.valueOf(settings.getString("major", null));
		}

		phone = settings.getString("phone", null);
		qq = settings.getString("qq", null);
		avatarPath_big = settings.getString("avatarPath_big", null);
		avatarPath_small = settings.getString("avatarPath_small",
				null);
		avatarUrl_big = settings.getString("avatarUrl_big", null);
		avatarUrl_small = settings.getString("avatarUrl_small", null);
	}
	
	public String getAvatarPath_big() {
		return avatarPath_big;
	}
	public String getAvatarPath_small() {
		return avatarPath_small;
	}
	
	public Bitmap getAvatar_big() {
		return avatar_big;
	}
	public Bitmap getAvatarsmall() {
		return avatar_small;
	}
	
	public String getAvatarUrl_big() {
		return avatarUrl_big;
	}
	public String getAvatarUrl_small() {
		return avatarUrl_small;
	}
	public Gender getGender() {
		return gender;
	}
	public Grade getGrade() {
		return grade;
	}
	public int getId() {
		return id;
	}
	public Major getMajor() {
		return major;
	}
	
	
	public long getMatesId() {
		return matesId;
	}

	public String getPhone() {
		return phone;
	}

	public String getQq() {
		return qq;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public String getWeiboName() {
		return weiboName;
	}

	public void setAvatarPath_big(String avatarPath_big) {
		this.avatarPath_big = avatarPath_big;
	}

	public void setAvatarPath_small(String avatarPath_small) {
		this.avatarPath_small = avatarPath_small;
	}

	public void setAvatarUrl_big(String avatarUrl_big) {
		this.avatarUrl_big = avatarUrl_big;
	}

	public void setAvatarUrl_small(String avatarUrl_small) {
		this.avatarUrl_small = avatarUrl_small;
	}

	public void setAvata_big(Bitmap avatar_big) {
		this.avatar_big = avatar_big;
	}

	public void setAvatar_small(Bitmap avatar_small) {
		this.avatar_small = avatar_small;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public void setMatesId(long matesId) {
		this.matesId = matesId;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public void setWeiboName(String weiboName) {
		this.weiboName = weiboName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", matesId="+ matesId + ", weiboId=" + weiboId + ", weiboName="
				+ weiboName + ", avatarUrl_big=" + avatarUrl_big 
				+ ", avatarUrl_small=" + avatarUrl_small 
				+ ", avatarPath_big=" + avatarPath_big
				+ ", avatarPath_small=" + avatarPath_small
				+ ", phone=" + phone
				+ ", major=" + major + ", qq=" + qq + ", gender=" + gender
				+ ", grade=" + grade + "]";
	}
}
