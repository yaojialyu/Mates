package me.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("User")
public class User {
	
	@Column
	@Id
	private long id;
	
	@Column
	private String weiboId;
	
	@Column
	private String weiboName;
	
	
	@Column
	private String avatarUrl_big;
	
	@Column
	private String avatarUrl_small;
	
	@Column
	private String phone;
	
	@Column
	private Major major;
	
	@Column
	private String qq;
	
	@Column
	private Gender gender;
	
	@Column
	private Grade grade;
	
	public enum Gender{
		Male, Female;
	}

	public enum Major{
		CST,TESL,AE,FIN,SAT;
	}
	
	public enum Grade{
		Year_One, Year_Two, Year_Three, Year_Four;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}
	public String getWeiboName() {
		return weiboName;
	}
	public void setWeiboName(String weiboName) {
		this.weiboName = weiboName;
	}

	public void setAvatarUrl_big(String avatarUrl_big) {
		this.avatarUrl_big = avatarUrl_big;
	}

	public void setAvatarUrl_small(String avatarUrl_small) {
		this.avatarUrl_small = avatarUrl_small;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQQ() {
		return qq;
	}
	public void setQQ(String qq) {
		this.qq = qq;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	public String getAvatarUrl_big() {
		return avatarUrl_big;
	}
	public String getAvatarUrl_small() {
		return avatarUrl_small;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", weiboId=" + weiboId + ", weiboName="
				+ weiboName + ", avatarUrl_small=" + avatarUrl_small + ", avatarUrl_big=" + avatarUrl_big+ ", phone=" + phone
				+ ", major=" + major + ", qq=" + qq + ", gender=" + gender
				+ ", grade=" + grade + "]";
	}
	
	public void filter() {
		this.setGender(null);
		this.setGrade(null);
		this.setMajor(null);
		this.setPhone(null);
		this.setQQ(null);
		this.setWeiboName(null);
		this.setWeiboId(null);
	}
	
}
