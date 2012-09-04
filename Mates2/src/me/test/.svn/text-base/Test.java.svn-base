package me.test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PrivacyListManager;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class Test {
	public static void main(String[] args) {XMPPConnection.DEBUG_ENABLED = true;
		//我的电脑IP:10.16.25.90
		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration("10.16.25.91", 5222, "");
		connectionConfig.setSASLAuthenticationEnabled(false);
				try {
					
					XMPPConnection connection = new XMPPConnection(connectionConfig);
					connection.connect();//连接
					connection.login("test", "test");//登陆
					System.out.println(connection.getUser());
					ChatManager chatmanager = connection.getChatManager();

					//新建一个会话
					Chat newChat = chatmanager.createChat("test3@pc2010102716", new MessageListener() {
					    public void processMessage(Chat chat, Message message) {
					    	System.out.println("Received from 【" + message.getFrom() + "】 message: " + message.getBody());
					    }
					});
					
					// 监听被动接收消息，或广播消息监听器
					chatmanager.addChatListener(new ChatManagerListener() {
						@Override
						public void chatCreated(Chat chat, boolean createdLocally) {
							chat.addMessageListener(new MessageListener() {
								@Override
								public void processMessage(Chat chat, Message message) {
									System.out.println("Received from 【" + message.getFrom() + "】 message: " + message.getBody());
								}
									
							});
						}
					});
					//发送消息
					newChat.sendMessage("我是菜鸟");
					
					//获取花名册
					Roster roster = connection.getRoster();
					Collection<RosterEntry> entries = roster.getEntries();
					for(RosterEntry entry : entries) {
						System.out.print(entry.getName() + " - " + entry.getUser() + " - " + entry.getType() + " - " + entry.getGroups().size());
						Presence presence = roster.getPresence(entry.getUser());
						System.out.println(" - " + presence.getStatus() +" - "+ presence.getFrom());
					}
					
					//添加花名册监听器，监听好友状态的改变。
					roster.addRosterListener(new RosterListener() {

						@Override
						public void entriesAdded(Collection<String> addresses) {
							System.out.println("entriesAdded");
						}

						@Override
						public void entriesUpdated(Collection<String> addresses) {
							System.out.println("entriesUpdated");
						}

						@Override
						public void entriesDeleted(Collection<String> addresses) {
							System.out.println("entriesDeleted");
						}

						@Override
						public void presenceChanged(Presence presence) {
							System.out.println("presenceChanged - >" + presence.getStatus());
						}
						
					});
					
					//创建组
//					/RosterGroup group = roster.createGroup("大学");
//					for(RosterEntry entry : entries) {
//						group.addEntry(entry);
//					}
					for(RosterGroup g : roster.getGroups()) {
						for(RosterEntry entry : g.getEntries()) {
							System.out.println("Group " +g.getName() +" >> " + entry.getName() + " - " + entry.getUser() + " - " + entry.getType() + " - " + entry.getGroups().size());
						}
					}
					
					//发送消息
					BufferedReader cmdIn = new BufferedReader(new InputStreamReader(System.in));
			        while(true) {
			          try {
			             String cmd = cmdIn.readLine();
			             if("!q".equalsIgnoreCase(cmd)) {
			                 break;
			             }
			             newChat.sendMessage(cmd);
			          }catch(Exception ex) {
			          }
			        }
			        connection.disconnect();
			        System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
}
