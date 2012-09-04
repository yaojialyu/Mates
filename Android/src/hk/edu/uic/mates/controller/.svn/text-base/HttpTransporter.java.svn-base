package hk.edu.uic.mates.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import hk.edu.uic.mates.model.vo.Room;
import hk.edu.uic.mates.model.vo.User;
import hk.edu.uic.mates.model.vo.User.Gender;
import hk.edu.uic.mates.model.vo.User.Grade;
import hk.edu.uic.mates.model.vo.User.Major;

public class HttpTransporter {
	
	private static final String SERVER_URL = "http://2090.me:2090/Mates2";
	/** USER URL */
	private static final String SIGNUP = "/user/signup";
	private static final String AVATARURL_UID = "/user/joinRoom";
	private static final String USER_PROFILE = "/user/";
	private static final String MODIFY_USER_PROFILE = "/user/modify/";
	/** ROOM URL */
	private static final String UPDATE = "/room/checkUpdate";
	private static final String GET_TOPIC = "/room/topic/";
	private static final String GET_TOPICS = "/room/getRoomTopics";
	private static final String MODIFY_TOPIC = "/room/topic/modify/";
	/** GEO URL */
	private static final String UPLOAD_GEO = "/geo/uploadGeoData";
	
	private JSONParser jp;
	
	public HttpTransporter() {
		super();
		jp = new JSONParser();
	}
	
	/**
	 * 注册用户（首次登陆）
	 * 将通过Oauth2验证的微博用户信息提交至Server进行注册
	 * 
	 * @param weiboId 微博ID
	 * @param weiboName 微博名
	 * @param avatarUrl 微博头像地址
	 * @return User 对象
	 */
	public User signup(String weiboId, String weiboName, String avatarUrl_small, String avatarUrl_big) {
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + SIGNUP);
		httpHelper.initConnection();
		httpHelper.addParams("weiboId", weiboId);
		httpHelper.addParams("weiboName", weiboName);
		httpHelper.addParams("avatarUrl_small", avatarUrl_small);
		httpHelper.addParams("avatarUrl_big", avatarUrl_big);
		
		String result = httpHelper.start();
	//	Log.d("MATES", result);
		User user = jp.parseUserFromMates(result);
		return user;
	}
	
	/**
	 * 根据uid 查看具体某用户的个人信息
	 * @param uid 用户ID
	 * @return User对象
	 */
    public User getUserProfile(long uid) {
    	HttpHelper httpHelper = new HttpHelper(SERVER_URL + USER_PROFILE + uid);
    	httpHelper.initConnection();
		String result = httpHelper.start();
		if(!result.equals("null")) {
			User user = jp.parseUserFromMates(result);
			return user;
		}
		//TODO 是否可以用同一个parser
		
		return null;
    }
    
    /**
	 * 根据weiboId 获得用户uid及头像URL
	 * @param 需要获取avatar及uid的用户list
	 * @return userList 更新的用户list
	 */
    public List<User> getUserAvatarAndUid(List<User> userList) {
    	
    	if(userList==null){
    		return null;
    	}
    	
    	HttpHelper httpHelper = new HttpHelper(SERVER_URL + AVATARURL_UID);
    	httpHelper.initConnection();
    	StringBuilder weiboIds = new StringBuilder();
    	for(int i = 0; i < userList.size(); i++) {
    		if(i != 0) {
    			weiboIds.append(",");
    		}
    		weiboIds.append(userList.get(i).getWeiboId());
    	}
		httpHelper.addParams("weiboIds", weiboIds.toString());
		
		String result = httpHelper.start();
		
		List<User> avatarAndUid = jp.parseAvatarUrlAndUid(result);
		
		for(int i = 0; i < avatarAndUid.size()&&i<userList.size(); i++) {
			userList.get(i).setMatesId(avatarAndUid.get(i).getMatesId());
			userList.get(i).setAvatarUrl_big(avatarAndUid.get(i).getAvatarUrl_big());
			userList.get(i).setAvatarUrl_small(avatarAndUid.get(i).getAvatarUrl_small());
		}
		
		return userList;
    }
    
    
    
    
    
    /**
	 * 根据avatarUrl 获得用户头像
	 * @param url 用户avatarUrl
	 * @return Bitmap
	 */
	public Bitmap getUserAvatar(String url) {
		//TODO 封装！！！
		URL imageUrl;
		Bitmap bm = null;
		
		try {
			imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			
			conn.setConnectTimeout(5 * 1000);     
	        conn.setRequestMethod("GET");  
	        
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){     
	            bm = BitmapFactory.decodeStream(conn.getInputStream());
	            return bm;
	        }
	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}     
		return null;
	}
	  
   
    /**
	 * 修改用户信息
	 * 要求先验证session
	 * @param uid
	 * @param phone
	 * @return
	 */
	public boolean modifyUser(long uid, String phone, Major major, 
			 String qq, Gender gender, Grade grade) {
		
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + MODIFY_USER_PROFILE + uid);
		httpHelper.initConnection();
		httpHelper.addParams("phone", phone);
		if(major != null) {
			httpHelper.addParams("major", major.toString());
		} else {
			httpHelper.addParams("major", null);
		}
		httpHelper.addParams("qq", qq);		
		if(gender != null) {
			httpHelper.addParams("gender", gender.toString());
		} else {
			httpHelper.addParams("gender", null);
		}
		if(grade != null) {
			httpHelper.addParams("grade", grade.toString());
		} else {
			httpHelper.addParams("grade", null);
		}
		
		String result = httpHelper.start();
		if(result.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据房间id, 获取房间"正在进行"的主题
	 * 
	 * @param room 房间对象
	 * @return room 更新后的房间对象
	 */
	public Room checkRoomTopic(Room room) {
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + GET_TOPIC + room.getId());
		httpHelper.initConnection();
		String result = httpHelper.start();
		if(!result.equals("null")) {
			String topic = jp.parseRoomTopic(result);
			room.setTopic(topic);
		}
		return room;
	}
	
	/**
	 * 批量获取房间主题
	 * 
	 * @param roomList 房间列表
	 * @return roomList 更新后的房间列表
	 */
	public List<Room> getkRoomTopics(List<Room> roomList) {
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + GET_TOPICS);
		StringBuilder roomIds = new StringBuilder();
		httpHelper.initConnection();
		for(int i = 0; i < roomList.size(); i++) {
			if(i != 0) {
				roomIds.append(",");
			}
			roomIds.append(roomList.get(i).getId());
		}
		
		httpHelper.addParams("roomIds", roomIds.toString());
		
		String result = httpHelper.start();
		if(!result.equals("null")) {
			ArrayList<String> rl = jp.parseRoomTpoics(result);
	//		Log.d(Config.TAG, "Topics:" + rl.toString());
			for(int i = 0; i < rl.size()&&i < roomList.size(); i++) {
				roomList.get(i).setTopic(rl.get(i));
			}
		}
		
		return roomList;
	}
	
	
	/**
	 * 修改房间主题
	 * 
	 * @param roomId
	 * @return boolean
	 */
	public boolean modifyRoomTopic(int roomId, String topic) {
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + MODIFY_TOPIC + roomId);
		httpHelper.initConnection();
		httpHelper.addParams("newSubject", topic);
		
		String result = httpHelper.start();
		if(result.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传所在位置地理数据
	 * 
	 * @param roomId 房间号
	 * @param lon 经度
	 * @param lat 维度
	 * @return
	 */
	public boolean updateGeoData(int roomId, double lon, double lat) {
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + UPLOAD_GEO);
		httpHelper.initConnection();
		httpHelper.addParams("roomId", String.valueOf(roomId));
		httpHelper.addParams("lon", String.valueOf(lon));
		httpHelper.addParams("lat", String.valueOf(lat));
		
		String result = httpHelper.start();
		//TODO 修改服务器返回值
		if(result.equals("true")) {
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * 检查房间数据版本更新信息
	 * 若有更新则返回新房间数据
	 * 
	 * @return 更新的Room list
	 */
	public List<Room> checkUpdate(String version) {
		HttpHelper httpHelper = new HttpHelper(SERVER_URL + UPDATE);
		httpHelper.initConnection();
		httpHelper.addParams("version", version);
		String result = httpHelper.start();
		
		if(!result.equals("null")) {
			List<Room> roomList = jp.parseRoomList(result);
			return roomList;
		}
		return null;
	}
}
