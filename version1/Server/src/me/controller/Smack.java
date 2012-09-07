package me.controller;

import java.util.Map;

import me.config.Config;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Smack 类负责处理Mates 与 Openfire 之间的通讯.
 * 包括: 创建用户, 更改房间主题
 * 
 * @author kevin
 *
 */
public class Smack {
	
	XMPPConnection connection;
	
	/**
	 * 建立XMPP连接,返回XMPPConnection
	 * @param username
	 * @param password
	 * @return XMPPConnection
	 */
	public XMPPConnection connect() {
		ConnectionConfiguration connectionConfig = new ConnectionConfiguration(Config.HOST, Config.PORT, "");
		connectionConfig.setSASLAuthenticationEnabled(false); 
		connection = new XMPPConnection(connectionConfig);
		try {
			connection.connect();		//连接
			//connection.login(username, password);	//登陆
		} catch (XMPPException e) {
			print("Error! Cannot connect to Server:"+Config.HOST);
			e.printStackTrace();
		}
		print("\n\n*****  Connect to Server: "+Config.HOST+" Successfully!  *****");
		return this.connection;
	}
	
	
	/**
	 * 在openfire中创建用户. 
	 * @param connection 
	 * @param weiboId 用户微博ID,作为jid(标识)
	 * @param accountAttributes  Map类型,包含了用户昵称,头像URL等.
	 * @return boolean 判断是否创建成功
	 */
	public boolean createUser(XMPPConnection connection, String weiboId, Map<String, String> accountAttributes) {
		try {
			connection.getAccountManager().createAccount(weiboId, weiboId, accountAttributes); //用户密码与jid(微博ID)一样
			print("\n*****  Create User: "+weiboId+" successfully!  *****\n\n");
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			print("Create User: "+weiboId+" failed~");
		}
		return false;
	}
	
	
	
/*	//注: 该方法已经不需要, 更改主题由客户端执行
	*//**
	 * 更改房间主题.
	 * @param connection
	 * @param roomJid 房间jid(标识)
	 * @param subject 新主题
	 * @return boolean 是否修改成功
	 *//*
	public boolean changeRoomSubject(XMPPConnection connection, String roomJid, String subject) {
		
		MultiUserChat muc = new MultiUserChat(connection, roomJid);
		try {
			muc.join("admin");
			muc.changeSubject(subject);
			print("Change room: "+roomJid+" subject to "+subject+" successfully.");
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			print("Change room subject failed.");
		}
		
		return false;
	}*/
	
	
	/**
	 * 断开连接
	 */
	public void disconnect() {
		this.connection.disconnect();
	}
	
	/**
	 * 方便print,偷懒!
	 * @param object
	 */
	public static void print(Object object) {
		System.out.println(object);
	}

}
