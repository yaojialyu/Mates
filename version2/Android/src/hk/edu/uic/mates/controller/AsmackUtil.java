package hk.edu.uic.mates.controller;

import hk.edu.uic.mates.model.vo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;

import android.util.Log;




public class AsmackUtil {
	
	/*
	 * 服务器名字
	 * 服务器端口号
	 * */
	public static final String SERVER_NAME = "2090.me";
	public static int port = 2525;
	
	/*
	 * 服务器连接
	 * */
	public static XMPPConnection conn ;
	private MultiUserChat chatGroup ;
	/*
	 * 存储最新的消息
	 * */
	private Message receive_msg ; 
	private PacketListener mlListener;
	private PacketListener pListener;
	private SubjectUpdatedListener suListener;
	
	private User curUser = null;
	
	public AsmackUtil() {
		
	}
	/*
	 * 打开与 openfire的连接
	 * 
	 * */
	
	public static boolean openConnection(User user){
		ConnectionConfiguration connConfig = new ConnectionConfiguration(AsmackUtil.SERVER_NAME, AsmackUtil.port);
		SmackConfiguration.setKeepAliveInterval(30000);
		conn = new XMPPConnection(connConfig);
		try {
			conn.connect();
			conn.login(user.getWeiboId(),user.getWeiboId());
		} catch (XMPPException e) {
			e.printStackTrace();
			//Log.e("MATES", "false==============");
			return false;
			
		} 
		return  true;
	}
	
	/*
	 * 获得与 openfire的连接
	 * 
	 * */
	public static XMPPConnection getConnection(User user) {
		if(conn == null ){
			AsmackUtil.openConnection(user);
		}
		return AsmackUtil.conn;
	}
	
	/*
	 * 关闭与 openfire的连接
	 * 
	 * */
	public static void closeConnection(){
		try {
			AsmackUtil.conn.disconnect();
			AsmackUtil.conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 加入openfire的特定房间
	 * 
	 * */


	public boolean joinRoom(User user,String roomjid){
		
		try {
			chatGroup = new MultiUserChat(conn,roomjid);
			chatGroup.join(user.getWeiboName());
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public String getRoomName() {
		return chatGroup.getRoom().split("@")[0];
	}
	
	public String getRoomSubject() {
		return chatGroup.getSubject();
	}
	
	
	
	
	/*
	 * 退出openfire的特定房间
	 * 
	 * */
	public void exitRoom(){
		//chatGroup.removeMessageListener(arg0)
		try {
			//chatGroup.removeMessageListener(arg0);
			if(pListener!=null){
				chatGroup.removeParticipantListener(pListener);
			}
			if(mlListener!=null){
				chatGroup.removeMessageListener(mlListener);
			}
			if(suListener!=null){
				chatGroup.removeSubjectUpdatedListener(suListener);
			}
			if(chatGroup!=null){
				chatGroup.leave();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 发送信息给openfire的房间里德其他人
	 * 
	 * */
	
	public boolean sendMessage(String msg){
		if(chatGroup != null){
			try {
				chatGroup.sendMessage(msg);
			} catch (XMPPException e) {
				e.printStackTrace();
		//		Log.e("MATES","bError form AsmackUtil.sendMessage");
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取房间当前用户列表
	 * @return 
	 */
	public List<User> getOccupantsList(){
		//HashMap<String, String> id_name = new HashMap<String, String>();
		List<User> userList = new ArrayList<User>();
		Iterator<String> iterator = chatGroup.getOccupants();
		String id = null;
		String name = null;
		String weiboName = null;
		
		while (iterator.hasNext()) { 
			name = iterator.next();
			id = chatGroup.getOccupant(name).getJid();
			weiboName = name.split("/")[1];
			id = id.split("@")[0];
			User user = new User();
			user.setWeiboName(weiboName);
			user.setWeiboId(id);
			//System.out.println("name: "+weiboName +"; jid: "+id);
			userList.add(user);
		}
		userList.add(curUser);
		return userList;
	}
	
	
	public User getNewComeUser(String name) {
		User user = new User();
		String id = null;
		String weiboName = null;
		id = chatGroup.getOccupant(name).getJid();
		id = id.split("@")[0];
		weiboName = name.split("/")[1];
		user.setWeiboName(weiboName);
		user.setWeiboId(id);
		return user;
	}
	
	
	
	/*
	 * 获取最晚的信息
	 * */
	public Message receiveMsg(){
		return receive_msg;
	}
	/*
	 * 改变房间的主题
	 * */
	public boolean changeSubject(String subject){
		try {
			chatGroup.changeSubject(subject);
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 根据用户的名字来判断是否在当前房间
	 * True: 在当前房间
	 * False: 离开了
	 * */
	public boolean isInRoom(String who){
		Iterator<String> iterator = chatGroup.getOccupants();
		String Occupant;
		while (iterator.hasNext()) {
			
			Occupant = iterator.next();
			if(Occupant.equals(who)){
		//		Log.e("MATES",Occupant + "==" + who + " is in this room");
				return true;
			}
		}
		return false;
	}
	
	public String getOccupantJid(String who){
		return chatGroup.getOccupant(who).getJid();
	}
	
	public void setMessageListener(PacketListener mListener) {
		this.mlListener = mListener;
		chatGroup.addMessageListener(mlListener);
	}
	
	public void setParticipantListener(PacketListener pListener){
		this.pListener = pListener;
		chatGroup.addParticipantListener(pListener);
	}
	
	public void setSubjectUpdatedListener(SubjectUpdatedListener suListener) {
		
		this.suListener = suListener;
		chatGroup.addSubjectUpdatedListener(suListener);
	}
	 
	public void setCurUser(User user) {
		this.curUser = user;
	}
}
