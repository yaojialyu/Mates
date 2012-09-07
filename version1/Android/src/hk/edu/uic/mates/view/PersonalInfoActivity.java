package hk.edu.uic.mates.view;

import hk.edu.uic.mates.controller.FileUtil;
import hk.edu.uic.mates.controller.HttpTransporter;
import hk.edu.uic.mates.controller.WeiboUtil;
import hk.edu.uic.mates.model.vo.User;
import hk.edu.uic.mates.R;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;
import com.weibo.net.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends Activity implements OnClickListener {
	/** UI Components */
	private ActionBar actionBar;
	private ImageView imgview_avatar;
	private TextView txt_weiboname;
	private Button btn_follow;
	private TextView txt_basic;
	private TextView txt_contact;
	
	/** Controllers */
	private WeiboUtil weiboUtil;
	private FileUtil fileUtil;
	private HttpTransporter httpTransporter;
	
	/** Models */
	private User thisUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile);
		
		this.initUIComponents();
		this.initControllers();
		
		Intent myLocalIntent = getIntent();
		Long matesId = myLocalIntent.getLongExtra("matesId", 0);
		actionBar.setProgressBarVisibility(View.VISIBLE);
		new getUserInfoTask().execute(matesId);
	}
	
	/**
	 * 初始化UI控件
	 */
	public void initUIComponents() {
		actionBar = (ActionBar) findViewById(R.id.ab_profile);
		actionBar.setTitle(getResources().getString(R.string.loading));
		actionBar.setHomeAction(new Action() {
			@Override
			public void performAction(View view) {
				PersonalInfoActivity.this.finish();
			}
			@Override
			public int getDrawable() {
				return R.drawable.arrow_action_bar;
			}
		});
		
		imgview_avatar = (ImageView) findViewById(R.id.ic_profile_avatar);
		txt_weiboname = (TextView) findViewById(R.id.txt_profile_weiboname);
		btn_follow = (Button) findViewById(R.id.btn_profile_follow);
		btn_follow.setOnClickListener(this);
		txt_basic = (TextView) findViewById(R.id.txt_basicinfo);
		txt_contact = (TextView) findViewById(R.id.txt_contact);
		
		

	}
	
	/**
	 * 初始化Controllers
	 */
	public void initControllers() {
		weiboUtil = new WeiboUtil(PersonalInfoActivity.this);
		httpTransporter = new HttpTransporter();
		fileUtil = new FileUtil();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_profile_follow:
			
				if(weiboUtil.follow(thisUser.getWeiboId())) {
					Toast.makeText(getApplicationContext(), getString(R.string.followSuccessfully), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), getString(R.string.alreadyFollow), Toast.LENGTH_SHORT).show();
				}

			break;
		}
	}
	
	private class getUserInfoTask extends AsyncTask<Long, Long, User> {

		@Override
		protected User doInBackground(Long... params) {
			User user = httpTransporter.getUserProfile(params[0]);
			Bitmap bm = httpTransporter.getUserAvatar(user.getAvatarUrl_big());
			user.setAvata_big(bm);
			try {
				user.setAvatarPath_big(fileUtil.writeAvatar(user.getWeiboId(), bm, FileUtil.BIG));
			} catch (Exception e) {
			}
			
			return user;
		}

		@Override
		protected void onPostExecute(User resultUser) {
			super.onPostExecute(resultUser);
			thisUser = resultUser;
			actionBar.setTitle(resultUser.getWeiboName());
			imgview_avatar.setImageBitmap(resultUser.getAvatar_big());
			txt_weiboname.setText(resultUser.getWeiboName());
			StringBuilder info = new StringBuilder();
			if(resultUser.getGender() != null) {
				info.append(resultUser.getGender().toString());
				info.append(" / ");
			}
			if(resultUser.getMajor() != null) {
				info.append(resultUser.getMajor().toString());
				info.append(" / ");
			}
			if(resultUser.getGrade() != null) {
				info.append(resultUser.getGrade().toString());
			}
			
			
			StringBuilder info2 = new StringBuilder();
			if(resultUser.getPhone() != null) {
				info2.append(resultUser.getPhone().toString());
				info2.append(" / ");
			}
			if(resultUser.getQq() != null) {
				info2.append(resultUser.getQq().toString());
			}
			if(info.toString().equals("") && info2.toString().equals("")) {
				txt_basic.setText(getString(R.string.inforNotFinish));
				txt_contact.setText("");
			} else {
				txt_basic.setText("");
				txt_contact.setText("");
				txt_basic.setText(info.toString());
				txt_contact.setText(info2.toString());
			}
			actionBar.setProgressBarVisibility(View.GONE);
		}
		
	}
}
