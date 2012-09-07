package hk.edu.uic.mates.controller;

import android.content.SharedPreferences;

public class VersionManager {

	private static final String VERSION = "version";
	private SharedPreferences pre;
	private SharedPreferences.Editor editor;
	
	public VersionManager() {
		
	}
	
	public VersionManager(SharedPreferences pre) {
		this.pre = pre;
		this.editor = pre.edit();
	}
	
	public int getAppVersion() {
		return 0;
	}
	
	public int getDataVersion() {
		return 0;
	}
	
	public boolean isNewest(int version, int type) {
		return false;
	}
}
