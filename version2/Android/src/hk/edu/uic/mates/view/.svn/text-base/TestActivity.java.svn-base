package hk.edu.uic.mates.view;

import java.util.LinkedList;
import java.util.List;

import hk.edu.uic.mates.controller.FileUtil;
import hk.edu.uic.mates.controller.HttpTransporter;
import hk.edu.uic.mates.controller.JSONParser;
import hk.edu.uic.mates.controller.WeiboUtil;
import hk.edu.uic.mates.model.db.DatabaseHelper;
import hk.edu.uic.mates.model.vo.Room;
import hk.edu.uic.mates.model.vo.User;
import hk.edu.uic.mates.model.vo.User.Gender;
import hk.edu.uic.mates.model.vo.User.Grade;
import hk.edu.uic.mates.model.vo.User.Major;
import hk.edu.uic.mates.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TestActivity extends Activity implements OnClickListener {

	/** UI Components */
	private Button btn_send;
	private Button btn_follow;
	private Button btn_createdb;
	private Button btn_getuser;
	private Button btn_signup;
	private Button btn_getprofile;
	private Button btn_getavatarandurl;
	private Button btn_getavatar;
	private Button btn_modifyuser;
	private Button btn_checkroomtopic;
	private Button btn_checkroomtopics;
	private Button btn_modifyroomtopic;
	private Button btn_uploadgeo;
	private Button btn_checkupdate;
	
	
	
	/** Controllers */
	private WeiboUtil weiboUtil;
	private JSONParser jsonParser;
	private HttpTransporter httpTransporter;
	private FileUtil fu;
	
	/** DAO */
	private DatabaseHelper dbHelper;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_test);
		
		this.initUIComponents();
		this.initControllers();
		initDaos();
	}
	
	
	/**
	 * 初始化UI控件
	 */
	public void initUIComponents() {
		/*btn_send = (Button) findViewById(R.id.btn_sendWeibo);
		btn_send.setOnClickListener(this);
		
		btn_follow = (Button) findViewById(R.id.btn_follow);
		btn_follow.setOnClickListener(this);
		
		btn_getuser = (Button) findViewById(R.id.btn_getuser);
		btn_getuser.setOnClickListener(this);
		
		btn_createdb = (Button) findViewById(R.id.btn_createdb);
		btn_createdb.setOnClickListener(this);*/
		
		btn_signup = (Button) findViewById(R.id.btn_signup);
		btn_signup.setOnClickListener(this);
		
		btn_getprofile = (Button) findViewById(R.id.btn_getprofile);
		btn_getprofile.setOnClickListener(this);
		
		btn_getavatarandurl = (Button) findViewById(R.id.btn_getavatarandurl);
		btn_getavatarandurl.setOnClickListener(this);
		
		btn_getavatar = (Button) findViewById(R.id.btn_getavatar);
		btn_getavatar.setOnClickListener(this);
		
		btn_modifyuser = (Button) findViewById(R.id.btn_modifyuser);
		btn_modifyuser.setOnClickListener(this);
		
		btn_checkroomtopic = (Button) findViewById(R.id.btn_checkroomtopic);
		btn_checkroomtopic.setOnClickListener(this);
		
		btn_checkroomtopics = (Button) findViewById(R.id.btn_checkroomtopics);
		btn_checkroomtopics.setOnClickListener(this);
		
		btn_modifyroomtopic = (Button) findViewById(R.id.btn_modifyroomtopic);
		btn_modifyroomtopic.setOnClickListener(this);
		
		btn_uploadgeo = (Button) findViewById(R.id.btn_uploadgeo);
		btn_uploadgeo.setOnClickListener(this);
		
		btn_checkupdate = (Button) findViewById(R.id.btn_checkupdate);
		btn_checkupdate.setOnClickListener(this);
	}
	
	/**
	 * 初始化Controllers
	 */
	public void initControllers() {
		weiboUtil = new WeiboUtil(TestActivity.this);
		jsonParser = new JSONParser();
		httpTransporter = new HttpTransporter();
		fu = new FileUtil();
	}

	public void initDaos() {
		dbHelper = new DatabaseHelper(this.getApplicationContext());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		/*case R.id.btn_sendWeibo:
			//weiboUtil.postMsg("这是测试。+2");
			new FirstAsyncTask().execute();
			break;
		case R.id.btn_follow:
			weiboUtil.follow("2520464264");
			break;
		case R.id.btn_getuser:
			new FirstAsyncTask().execute();
			break;
		case R.id.btn_createdb:
			if(dbHelper.getWritableDatabase() != null) {
				Toast.makeText(getApplicationContext(), "数据库创建成功", Toast.LENGTH_SHORT).show();
			}
			break;*/
		case R.id.btn_signup:
			User u = httpTransporter.signup("xiaoxi443", "我操", "xiaoxia1", "xiaoxia1");
			Log.d("MATES", u.toString());
			break;
		case R.id.btn_getprofile:
			User u2 = httpTransporter.getUserProfile(5);
			Log.d("MATES", u2.toString());
			break;
		case R.id.btn_getavatarandurl:
			User u3 = new User();
			u3.setWeiboId("xiaoxia1");
			User u4 = new User();
			u4.setWeiboId("xiaoxia1111");
			List<User> ul = new LinkedList<User>();
			ul.add(u3);
			ul.add(u4);
			ul = httpTransporter.getUserAvatarAndUid(ul);
			Log.d("MATES", ul.toString());
			break;
		case R.id.btn_getavatar:
			Bitmap b = httpTransporter.getUserAvatar("http://tp1.sinaimg.cn/2161932992/180/5625199087/1");
			if(b == null) {
				Log.d("MATES", "download pic failed!");
			}
			String path = fu.writeAvatar("cofthew7", b , FileUtil.BIG);
			Log.d("MATES", path);
			break;
		case R.id.btn_modifyuser:
			if(httpTransporter.modifyUser(5, "123", Major.AE, "167755", Gender.Female, Grade.Year_One)) {
				Log.d("MATES", "modify success");
			}
			break;
		case R.id.btn_checkroomtopic:
			Room r = new Room();
			r.setId(1);
			r = httpTransporter.checkRoomTopic(r);
			Log.d("MATES", r.toString());
			break;
		case R.id.btn_checkroomtopics:
			Room r1 = new Room();
			Room r2 = new Room();
			r1.setId(1);
			r2.setId(2);
			List<Room> rl = new LinkedList<Room>();
			rl.add(r1);
			rl.add(r2);
			rl = httpTransporter.getkRoomTopics(rl);
			Log.d("MATES", rl.toString());
			break;
		case R.id.btn_modifyroomtopic:
			//TODO 中文乱码 都是问号
			if(httpTransporter.modifyRoomTopic(1, "也是好苦逼")) {
				Log.d("MATES", "kubi");
			}
			break;
		case R.id.btn_uploadgeo:
			httpTransporter.updateGeoData(1, 11.111, 11.1112);
			break;
		case R.id.btn_checkupdate:
			List<Room> rl2 = httpTransporter.checkUpdate("1");
			Log.d("MATES", rl2.toString());
			break;
		}
	}
	
	/**
	 * 优化一下
	 * @author cofthew7
	 *
	 */
	class FirstAsyncTask extends AsyncTask <Void,Void,Void>{
		 
		   @Override
		   protected Void doInBackground(Void... arg0) {
		      // TODO Auto-generated method stub
			  //weiboUtil.postMsg("test 1111");
			  User user = jsonParser.parseUserFromSina(weiboUtil.getUserInfo("1985307284"));
			  Log.d("Mates", user.toString());
			  //Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
			  return null;
		   }
		 
		}
}
