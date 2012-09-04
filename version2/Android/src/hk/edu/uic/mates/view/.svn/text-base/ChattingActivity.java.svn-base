package hk.edu.uic.mates.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;

import hk.edu.uic.mates.R;
import hk.edu.uic.mates.controller.AsmackUtil;
import hk.edu.uic.mates.controller.FileUtil;
import hk.edu.uic.mates.controller.HttpTransporter;
import hk.edu.uic.mates.controller.WeiboUtil;
import hk.edu.uic.mates.model.vo.OnlineUser;
import hk.edu.uic.mates.model.vo.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class ChattingActivity extends Activity implements OnClickListener {

	/** UI Components */
	private ActionBar actionBar = null;
	private Action editSubjectAction = null;
	private ListView listview_chatting_board = null;
	private Button btn_sendMsg = null;
	private EditText etxt_inputMessage = null;
	private ImageView image_othersAvatar = null;
	private TextView txt_roomName;
	private TextView txt_subject;
	private LinearLayout linearLayout_avatars;
	private ImageView iv_checkOnlineUsers;
	private ViewGroup msgPanel_weibo;
	private ViewGroup msgPanel;
	private EditText etxt_inputMessage_weibo;
	private ImageView btn_sendMsg_weibo ;
	
	
	/** Controllers */
	private AsmackUtil asmackUtil;
	private HttpTransporter httpTransporter;
	FileUtil fileUtil;
	WeiboUtil weiboUtil;

	/** models */
	private User currentUser; // 当前用户的对象
	private User senderUser;

	/** Adapters */
	private MyMessageAdapter messageAdapter = null;

	/** others */
	private ArrayList<MyMessage> msgArrayList = new ArrayList<ChattingActivity.MyMessage>(); // 存放当前聊天室的信息
	private TextView msgshow = null; // 存放消息主体
	private String roomJid; // 当前房间jid
	private int roomId; //数据库里的id
	private List<User> occupantList;
	private MyHandle handler;
	private boolean isTweetPanelVisible;
	
	/** animation action **/
	TranslateAnimation slideLeft ;
	TranslateAnimation slideRight ;
	TranslateAnimation slideLeft_weibo ;
	TranslateAnimation slideRight_weibo ;
	float startX;
	float startY;
	

	/** pre defination */
	private final static int HANDLE_MESSAGE = 1;
	private final static int HANDLE_COMEIN = 2;
	private final static int HANDLE_GOOUT = 3;
	private final static int HANDLE_SUBJECT = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chatroom);

		Intent intent = getIntent();
		roomJid = intent.getStringExtra("jid");
		roomId = intent.getIntExtra("rid", 0);
	//	Log.e("MATES", roomId+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		currentUser = (User) intent.getSerializableExtra("user");

		initControllers();
		initUIComponents();
		initAdapters();
		initUIListener();
		// initAsmack();
		new InitAsmackUtil().execute();

		handler = new MyHandle();
	}
	

	@Override
	protected void onStop() {
		super.onStop();
		//asmackUtil.exitRoom();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		asmackUtil.exitRoom();
	}
	/**
	 * 初始化UI控件
	 */
	private void initUIComponents() {
		/*
		 * 设置action bar
		 */
		actionBar = (ActionBar) findViewById(R.id.actionbar_ChattingActivity);
		editSubjectAction = new Action() {
			@Override
			public void performAction(View view) {
				openModifySubjectDialog();
			}

			@Override
			public int getDrawable() {
				return R.drawable.pen_edit;
			}
		};
		actionBar.addAction(editSubjectAction);
		actionBar.setHomeAction(new Action() {
			@Override
			public void performAction(View view) {
				ChattingActivity.this.finish();
			}

			@Override
			public int getDrawable() {
				return R.drawable.arrow_action_bar;
			}
		});

		listview_chatting_board = (ListView) findViewById(R.id.listview_ChattingActivity_chattingboard);
		listview_chatting_board
				.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		btn_sendMsg = (Button) findViewById(R.id.btn_ChattingActivity_send);
		etxt_inputMessage = (EditText) findViewById(R.id.txt_ChattingActivity_userinput);
		txt_roomName = (TextView) findViewById(R.id.txt_ChattingActivity_room);
		txt_subject = (TextView) findViewById(R.id.txt_ChattingActivity_activity);
		linearLayout_avatars = (LinearLayout) findViewById(R.id.linearLayout_ChattingActivity_avatars);
		
		iv_checkOnlineUsers = (ImageView) findViewById(R.id.btn_checkOnlineUsers);
		iv_checkOnlineUsers.setOnClickListener(this);
		
		msgPanel = (ViewGroup )findViewById(R.id.MessagePannel);
		msgPanel_weibo = (ViewGroup) findViewById(R.id.MessagePannel_weibo);
		isTweetPanelVisible = false;
		etxt_inputMessage_weibo = (EditText)findViewById(R.id.txt_ChattingActivity_userinput_weibo);
		btn_sendMsg_weibo = (ImageView)findViewById(R.id.btn_ChattingActivity_send_weibo);
		
		/*
		 * 设置手势触发的动作
		 */
		
		//weiboPanel向左滑动
		 slideLeft_weibo = new TranslateAnimation(
				 Animation.RELATIVE_TO_SELF, 1.0f, 
				 Animation.RELATIVE_TO_SELF, 0.0f, 
				 Animation.RELATIVE_TO_SELF, 0.0f,
				 Animation.RELATIVE_TO_SELF, 0.0f);
		 slideLeft_weibo.setDuration(1500);
		 slideLeft_weibo.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					msgPanel_weibo.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationStart(Animation animation) {
					msgPanel.setVisibility(View.GONE);
				}
			});
		 
		 //msgPanal 向左滑动
		 slideLeft = new TranslateAnimation(
				 Animation.RELATIVE_TO_SELF, 0.0f, 
				 Animation.RELATIVE_TO_SELF, -1.0f, 
				 Animation.RELATIVE_TO_SELF, 0.0f,
				 Animation.RELATIVE_TO_SELF, 0.0f);
		 slideLeft.setDuration(1500);
		 slideLeft.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationStart(Animation animation) {
				}
			});
		 
		 //msgPanal 向右滑动
		 slideRight= new TranslateAnimation(
				 Animation.RELATIVE_TO_SELF, -1.0f, 
				 Animation.RELATIVE_TO_SELF, 0.0f, 
				 Animation.RELATIVE_TO_SELF, 0.0f,
				 Animation.RELATIVE_TO_SELF, 0.0f);
		 slideRight.setDuration(1500);
		 slideRight.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationStart(Animation animation) {
					//msgPanel.setVisibility(View.VISIBLE);
				}
			});
		 
		 //WeoboPanel 向右滑动
		 slideRight_weibo = new TranslateAnimation(
				 Animation.RELATIVE_TO_SELF, 0.0f, 
				 Animation.RELATIVE_TO_SELF, 1.0f, 
				 Animation.RELATIVE_TO_SELF, 0.0f,
				 Animation.RELATIVE_TO_SELF, 0.0f);
		 slideRight_weibo.setDuration(1500);
		 slideRight_weibo.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					msgPanel.setVisibility(View.VISIBLE);
					msgPanel_weibo.setVisibility(View.GONE);
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationStart(Animation animation) {
					//msgPanel_weibo.setVisibility(View.VISIBLE);
				}
			});
	}

	/**
	 * 初始化Controllers
	 */
	private void initControllers() {
		asmackUtil = new AsmackUtil();
		asmackUtil.setCurUser(currentUser);
		httpTransporter = new HttpTransporter();
		fileUtil = new FileUtil();
		weiboUtil = new WeiboUtil(ChattingActivity.this);
	}

	/**
	 * 初始化adapter
	 */
	private void initAdapters() {
		messageAdapter = new MyMessageAdapter(this);
		listview_chatting_board.setAdapter(messageAdapter);
	}

	/**
	 * 初始化UI Listener
	 */
	private void initUIListener() {

		/*
		 * 发送信息按钮的监听器
		 */
		btn_sendMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = etxt_inputMessage.getText().toString();
				if (msg.length() > 0) {
					if (asmackUtil.sendMessage(msg)) {
						msgArrayList.add(new MyMessage(currentUser, msg));
						messageAdapter.notifyDataSetChanged();
						etxt_inputMessage.setText("");
					} else {
						Toast.makeText(ChattingActivity.this,getString(R.string.sendFailed), 
								Toast.LENGTH_SHORT).show();
					}

				}
			}
		});
		
		/**
		 * 发送微博监听
		 */
		btn_sendMsg_weibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String msg = etxt_inputMessage_weibo.getText().toString();
				if (msg.length() > 0) {
					if (asmackUtil.sendMessage(msg)) {
						msgArrayList.add(new MyMessage(currentUser, msg));
						messageAdapter.notifyDataSetChanged();
						etxt_inputMessage_weibo.setText("");
					} else {
						Toast.makeText(ChattingActivity.this, getString(R.string.sendFailed),
								Toast.LENGTH_SHORT).show();
					}
					String post = msg+"\t#"+txt_subject.getText()+"#\t我在#"+txt_roomName.getText()+"#";
					actionBar.setProgressBarVisibility(View.VISIBLE);
					new PostWeiboThread().execute(post);
					/*if(!weiboUtil.postMsg(msg+"\t#"+txt_subject.getText()+"#\t我在#"+txt_roomName.getText()+"#")){
						Toast.makeText(ChattingActivity.this, "微博消息发送不成功",
								Toast.LENGTH_SHORT).show();
					}*/
					
				}
			}
		});
	}

	/**
	 * 初始化asmack相关的东西
	 */
	private void initAsmack() {

		/*
		 * 消息监听器 负责拿到最新的消息
		 */
		PacketListener mListener = new PacketListener() {
			@Override
			public void processPacket(Packet packet) {
				try {
					Message message = (Message) packet;
					String sender = message.getFrom().split("/")[1];
					if (!sender.equals(currentUser.getWeiboName())) { // 如果不是自己发的消息，则显示在左边
						android.os.Message msg = new android.os.Message();
						msg.what = HANDLE_MESSAGE;
						Bundle bundle = new Bundle();
						bundle.putString("WeiboName", sender);
						bundle.putString("Msg", message.getBody());
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					// e.printStackTrace();
					// Log.e("MATES","处理了第一条系统发的房间信息");
				}
			}
		};

		/*
		 * 用户 加入 或 退出 房间的监听器
		 */
		PacketListener pListener = new PacketListener() {
			@Override
			public void processPacket(Packet packet) {
				Presence presence = (Presence) packet;

				// 05-12 07:59:57.564: E/MATES(29394): available*****
				// b203@uic.mates/智缺大师
				// 05-12 08:00:05.784: E/MATES(29394): unavailable*****
				// b203@uic.mates/智缺大师
				if(presence.getType().equals(Presence.Type.available)){
					android.os.Message msg = new android.os.Message();
					msg.what=HANDLE_COMEIN;
					Bundle bundle = new Bundle();
					bundle.putString("Name", presence.getFrom());
					msg.setData(bundle);
					handler.sendMessage(msg);
				//	Log.e("MATES",presence.getType()+"**进入房间*** "+presence.getFrom());
				}
				else if(presence.getType().equals(Presence.Type.unavailable)){
					android.os.Message msg = new android.os.Message();
					msg.what=HANDLE_GOOUT;
					Bundle bundle = new Bundle();
					bundle.putString("Name", presence.getFrom());
					msg.setData(bundle);
					handler.sendMessage(msg);
				//	Log.e("MATES",presence.getType()+"**退出房间*** "+presence.getFrom());
				}
				
			}
		};

		/*
		 * * 主题更改监听器
		 */
		SubjectUpdatedListener suListener = new SubjectUpdatedListener() {
			@Override
			public void subjectUpdated(String subject, String who) {
				String name = who.split("/")[1];
			//	Log.e("MATES", name + " change subject to be:" + subject);
				//txt_subject.setText(subject);
				android.os.Message msg = new android.os.Message();
				msg.what = HANDLE_SUBJECT;
				Bundle bundle = new Bundle();
				bundle.putString("subject", subject);
				bundle.putString("name", name);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		};

		if (asmackUtil.joinRoom(currentUser, roomJid)) {

			// 获取在线用户list
			occupantList = asmackUtil.getOccupantsList();

			asmackUtil.setMessageListener(mListener);
			asmackUtil.setParticipantListener(pListener);
			asmackUtil.setSubjectUpdatedListener(suListener);


			/*if(occupantList.size()!=0){
				for (Iterator<User> iterator = occupantList.iterator(); iterator.hasNext();) {
					User user =  iterator.next();
			//		Log.e("MATES", "这些人在房间里："+user.getWeiboName());
				}
			}
			else {
				//Toast.makeText(this, "occupantList 为0 ", Toast.LENGTH_SHORT).show();
			//	Log.e("MATES", "没有人在房间里： occupantList 为 0");
			}*/
		}

	}

	/**
	 * 打开修改Room Subject 的dialog
	 */
	public void openModifySubjectDialog() {
		final EditText etxt_subject = new EditText(this);
		new AlertDialog.Builder(this).setTitle(getString(R.string.changeSubject))
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(etxt_subject)
				.setPositiveButton(getString(R.string.ok), 
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new ChangeSubject().execute(etxt_subject.getText().toString());
					}
				})
				.setNegativeButton(getString(R.string.cancel), null).show();
	}

	public class MyHandle extends Handler {
		public MyHandle() {
		}

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			String weiboName;
			String name;
			String subject;
			User user;
			switch (msg.what) {
			case HANDLE_MESSAGE:
				weiboName = bundle.getString("WeiboName");

				
				for (Iterator<User> iterator = occupantList.iterator(); iterator.hasNext();) {
					user = iterator.next();
					if (user.getWeiboName().equals(weiboName)) {
						senderUser = user;
						// Log.e("MATES",
						// "！！！！！！！！！！！！！(senderUser)发送者："+senderUser.getWeiboName());
					}
				}
				msgArrayList.add(new MyMessage(senderUser, bundle
						.getString("Msg")));
				messageAdapter.notifyDataSetChanged();
				// Toast.makeText(ChattingActivity.this,
				// bundle.getString("Msg"), Toast.LENGTH_SHORT).show();
				break;

			case HANDLE_COMEIN:
				name = bundle.getString("Name");
				if (!name.split("/")[1].equals(currentUser.getWeiboName())) {
			//		Log.e("MATES", "***** 有人进来，准备下载头像");
					user = asmackUtil.getNewComeUser(name);
					new GetSingleAvatarsThread().execute(user);
				} 
				break;
				
			case HANDLE_GOOUT:
				name = bundle.getString("Name");
				String weiboName2  = name.split("/")[1];
				new DelSingleAvatar().execute(weiboName2);
				break;
			case HANDLE_SUBJECT:
				name = bundle.getString("name");
				subject = bundle.getString("subject");
				String[] param = new String[]{subject, name};
				new ChangeSubject().execute(param);
				break;
			default:
				break;
			}

		}
	}
class ChangeSubject extends AsyncTask<String, Void, String[]> {

	@Override
	protected String[] doInBackground(String... params) {
		if(params.length == 1) {
			asmackUtil.changeSubject(params[0]);
			httpTransporter.modifyRoomTopic(roomId, params[0]);
			return params;
		} else {
			return params;
		}
		
	}
	
	@Override
	protected void onPostExecute(String[] result) {
		if(result.length == 1) {
			txt_subject.setText(result[0]);
		} else {
			txt_subject.setText(result[0]);
			Toast.makeText(ChattingActivity.this, result[1] + " "+getString(R.string.changeSubjectTo) + result[0] , Toast.LENGTH_SHORT).show();
		}
	}
}
	class DelSingleAvatar extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... weiboname) {
			for(int i = 0; i < occupantList.size(); i++ ) {
				if(occupantList.get(i).getWeiboName().equals(weiboname[0])) {
					
					occupantList.remove(i);
					break;
				}
			}
			return weiboname[0];
		}
		
		@Override
		protected void onPostExecute(String result) {
			View view = linearLayout_avatars.findViewWithTag(result);
			linearLayout_avatars.removeView(view);
		}
	}
	
	class GetAvatarsThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			occupantList = httpTransporter.getUserAvatarAndUid(occupantList);
			//List<User> userList = new ArrayList<User>();
			/*for (Iterator iterator = occupantList.iterator(); iterator
					.hasNext();) {
				User user = (User) iterator.next();*/
			if(occupantList!=null){
				for(int i = 0; i < occupantList.size(); i++) {
					occupantList.get(i).setAvatar_small(httpTransporter.getUserAvatar(occupantList.get(i)
							.getAvatarUrl_small()));
					occupantList.get(i).setAvatarPath_small(fileUtil.writeAvatar(
							occupantList.get(i).getWeiboId(), occupantList.get(i).getAvatarsmall(),
							FileUtil.SMALL));
				}
			}
			
				/*user.setAvatar_small(httpTransporter.getUserAvatar(user
						.getAvatarUrl_small()));*/
				/*user.setAvatarPath_small(fileUtil.writeAvatar(
						user.getWeiboId(), user.getAvatarsmall(),
						FileUtil.SMALL));
				userList.add(user);*/
			//}
			//occupantList = userList;
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			/*if(occupantList==null||occupantList.size()==0){
				Toast.makeText(ChattingActivity.this.getApplicationContext(),
						"下载所有人头像失败", Toast.LENGTH_SHORT).show();
				return;
			}else {
				Toast.makeText(ChattingActivity.this.getApplicationContext(),
						"下载所有人头像成功", Toast.LENGTH_SHORT).show();
			}*/
			// 添加头像到scrollview
			for (int i = 0; i < occupantList.size(); i++) {

				final ImageView avatar = new ImageView(ChattingActivity.this);
				avatar.setImageBitmap(occupantList.get(i).getAvatarsmall());
				final long l = occupantList.get(i).getMatesId();
				avatar.setTag(occupantList.get(i).getWeiboName());
				avatar.setPadding(0, 0, 7, 0);
				avatar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					/*	Toast.makeText(getApplicationContext(),
								"该用户id " + avatar.getTag(),
								Toast.LENGTH_SHORT).show();*/
						Intent i = new Intent(ChattingActivity.this,
								PersonalInfoActivity.class);
						i.putExtra("matesId", l);
						startActivity(i);
					}
				});
				linearLayout_avatars.addView(avatar);
			}
		}
	}

	class PostWeiboThread extends AsyncTask<String, Void, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(!weiboUtil.postMsg(params[0])) {
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result == false) {
				Toast.makeText(ChattingActivity.this, getString(R.string.sendFailed),
						Toast.LENGTH_SHORT).show();
			}
			actionBar.setProgressBarVisibility(View.GONE);
		}
	}
	
	class GetSingleAvatarsThread extends AsyncTask<User, Void, User> {

		@Override
		protected User doInBackground(User... userArray) {

			List<User> users = new ArrayList<User>();
			users.add(userArray[0]);

		//	Log.e("MATES", "user list 人数： " + users.size() + " ××××××"+ users.get(0).getWeiboId());

			users = httpTransporter.getUserAvatarAndUid(users);

			users.get(0).setAvatar_small(
					httpTransporter.getUserAvatar(users.get(0)
							.getAvatarUrl_small()));
			users.get(0).setAvatarPath_small(
					fileUtil.writeAvatar(users.get(0).getWeiboId(), users
							.get(0).getAvatarsmall(), FileUtil.SMALL));

			return users.get(0);
		}

		@Override
		protected void onPostExecute(User resultUser) {
			super.onPostExecute(resultUser);
			occupantList.add(resultUser);
			/*Toast.makeText(ChattingActivity.this.getApplicationContext(),
					"下载所新进入User 头像成功", Toast.LENGTH_SHORT).show();
*/
			// 添加头像到scrollview
			final ImageView avatar = new ImageView(ChattingActivity.this);
			avatar.setImageBitmap(resultUser.getAvatarsmall());
			final long l = resultUser.getMatesId();
			avatar.setTag(resultUser.getWeiboName());
			avatar.setPadding(0, 0, 7, 0);
			avatar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/*Toast.makeText(getApplicationContext(),
							"该用户id " + avatar.getTag(),
							Toast.LENGTH_SHORT).show();*/
					Intent i = new Intent(ChattingActivity.this,
							PersonalInfoActivity.class);
					i.putExtra("matesId", l);
					startActivity(i);
				}
			});

			linearLayout_avatars.addView(avatar);

		}
	}

	class InitAsmackUtil extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			actionBar.setProgressBarVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(Void... params) {
			initAsmack();
			new GetAvatarsThread().execute();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			actionBar.setProgressBarVisibility(View.GONE);
			txt_roomName.setText(asmackUtil.getRoomName().toUpperCase());
			txt_subject.setText(asmackUtil.getRoomSubject());
			Toast.makeText(ChattingActivity.this.getApplicationContext(),
					getString(R.string.loadFinish), Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * message adapter
	 * 
	 * @author yujie
	 * 
	 */
	class MyMessageAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater inflater;

		public MyMessageAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			return msgArrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return msgArrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (msgArrayList.get(position).from.equals(currentUser)) {
				convertView = this.inflater.inflate(R.layout.outgoing_chat,
						null);
			} else {
				convertView = this.inflater.inflate(R.layout.incoming_chat,
						null);
				image_othersAvatar = (ImageView) convertView
						.findViewById(R.id.image_othersAvatar);
				image_othersAvatar
						.setImageBitmap(msgArrayList.get(position).from
								.getAvatarsmall());
		//		Log.e("MATES", "用户matesId "+msgArrayList.get(position).from.getMatesId());
				image_othersAvatar.setTag(msgArrayList.get(position).from.getMatesId());
				final long l = msgArrayList.get(position).from.getMatesId();
				image_othersAvatar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i = new Intent(ChattingActivity.this,
								PersonalInfoActivity.class);
						i.putExtra("matesId", l);
						startActivity(i);
					}
				});
				// Log.e("MATES","image_othersAvatar.setImageBitmap(senderUser.getAvatarsmall());");
			}
			msgshow = (TextView) convertView
					.findViewById(R.id.txt_ChattingActivity);
			msgshow.setText(msgArrayList.get(position).contentString);

			return convertView;
		}

	}

	/**
	 * 消息主体类
	 * 
	 * @author Yujie
	 */
	class MyMessage {

		/**
		 * 发信人
		 */
		private User from;

		/**
		 * 发送的内容
		 */
		private String contentString;

		public MyMessage(User from, String contentString) {
			super();
			this.from = from;
			this.contentString = contentString;
		}
	}

	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_checkOnlineUsers:
			Intent intent = new Intent(ChattingActivity.this, OnlineUsersActivity.class);
			//i.putExtra("users", occupantList);
			List<OnlineUser> oul = new ArrayList<OnlineUser>();
			
			for(int i = 0; i < occupantList.size(); i++) {
		//		Log.e("MATES", occupantList.get(i).getWeiboName());
				oul.add(new OnlineUser(occupantList.get(i)));
			}
			intent.putExtra("users", (Serializable)oul);
			
			intent.putExtra("roomName", txt_roomName.getText());
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
    @Override 
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			 if (event.getX() > startX+100) { // 向右滑动
				 if(isTweetPanelVisible){
					 msgPanel.startAnimation(slideRight);
					 msgPanel_weibo.startAnimation(slideRight_weibo);
					 isTweetPanelVisible = false;
				 }
			} 
			 else if (event.getX() < startX-100) { // 向左滑动
				 if(!isTweetPanelVisible){
					 msgPanel.startAnimation(slideLeft);
					 msgPanel_weibo.startAnimation(slideLeft_weibo);
					 isTweetPanelVisible = true;
				 }
			}
			
			break;
		}
		return true;
	}
    
	
}
