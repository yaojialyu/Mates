package hk.edu.uic.mates.view;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

import hk.edu.uic.mates.R;
import hk.edu.uic.mates.controller.HttpTransporter;
import hk.edu.uic.mates.model.vo.User.Gender;
import hk.edu.uic.mates.model.vo.User.Grade;
import hk.edu.uic.mates.model.vo.User.Major;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener {

	private static final String[] gender = { "Male", "Female" };
	private static final String[] grade = { "Year_One", "Year_Two",
			"Year_Three", "Year_Four" };
	private static final String[] major = { "CST", "TESL", "AE", "FIN", "SAT" };
	private Spinner spinner;
	private ArrayAdapter<String> adapter;

	/** UI Components */
	private ActionBar actionBar;
	private View rl_name;
	private TextView txt_content_name;
	private View rl_major;
	private TextView txt_content_major;
	private View rl_gender;
	private TextView txt_content_gender;
	private View rl_grade;
	private TextView txt_content_grade;
	private View rl_phone;
	private TextView txt_content_phone;
	private View rl_qq;
	private TextView txt_content_qq;
	private View rl_logout;

	private EditText etxt_setting;
	/** Preferences */
	private SharedPreferences settings;
	private SharedPreferences.Editor settingsEditor;

	private boolean isLogout = false;
	private boolean isChanged = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);

		initPreferences();
		initUIComponents();
	}

	public void initUIComponents() {
		actionBar = (ActionBar) findViewById(R.id.ab_setting);
		actionBar.setTitle(getString(R.string.setting)); 
		actionBar.setHomeAction(new Action() {
			@Override
			public int getDrawable() {
				return R.drawable.arrow_action_bar;
			}

			@Override
			public void performAction(View view) {
				SettingsActivity.this.finish();
			}
		});

		rl_name = findViewById(R.id.rl_name);
		// rl_name = factory.inflate(R.id.rl_name, null);
		rl_name.setOnClickListener(this);
		txt_content_name = (TextView) findViewById(R.id.txt_content_name);
		txt_content_name.setText(settings.getString("weiboName", null));

		// rl_major = factory.inflate(R.id.rl_major, null);
		rl_major = findViewById(R.id.rl_major);
		rl_major.setOnClickListener(this);
		txt_content_major = (TextView) findViewById(R.id.txt_content_major);
		txt_content_major.setText(settings.getString("major", getResources()
				.getString(R.string.input)));

		// rl_gender = factory.inflate(R.id.rl_gender, null);
		rl_gender = findViewById(R.id.rl_gender);
		rl_gender.setOnClickListener(this);
		txt_content_gender = (TextView) findViewById(R.id.txt_content_gender);
		txt_content_gender.setText(settings.getString("gender", getResources()
				.getString(R.string.input)));

		// rl_grade = factory.inflate(R.id.rl_grade, null);
		rl_grade = findViewById(R.id.rl_grade);
		rl_grade.setOnClickListener(this);
		txt_content_grade = (TextView) findViewById(R.id.txt_content_grade);
		txt_content_grade.setText(settings.getString("grade", getResources()
				.getString(R.string.input)));

		// rl_phone = factory.inflate(R.id.rl_phone, null);
		rl_phone = findViewById(R.id.rl_phone);
		rl_phone.setOnClickListener(this);
		txt_content_phone = (TextView) findViewById(R.id.txt_content_phone);
		txt_content_phone.setText(settings.getString("phone", getResources()
				.getString(R.string.input)));

		// rl_qq = factory.inflate(R.id.rl_qq, null);
		rl_qq = findViewById(R.id.rl_qq);
		rl_qq.setOnClickListener(this);
		txt_content_qq = (TextView) findViewById(R.id.txt_content_qq);
		txt_content_qq.setText(settings.getString("qq", getResources()
				.getString(R.string.input)));

		// rl_logout = factory.inflate(R.id.rl_logout, null);
		rl_logout = findViewById(R.id.rl_logout);
		rl_logout.setOnClickListener(this);
	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, SettingsActivity.class);
		return i;
	}

	public void initPreferences() {
		settings = getSharedPreferences("settings", 0);
		settingsEditor = settings.edit();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rl_name:
			settingDialog("weiboName", R.string.name, txt_content_name
					.getText().toString(), R.id.rl_name);
			break;
		case R.id.rl_gender:
			settingDialog("gender", R.string.gender, txt_content_gender
					.getText().toString(), R.id.rl_gender);
			break;
		case R.id.rl_grade:
			settingDialog("grade", R.string.grade, txt_content_grade.getText()
					.toString(), R.id.rl_grade);
			break;
		case R.id.rl_major:
			settingDialog("major", R.string.major, txt_content_major.getText()
					.toString(), R.id.rl_major);
			break;
		case R.id.rl_phone:
			settingDialog("phone", R.string.phone, txt_content_phone.getText()
					.toString(), R.id.rl_phone);
			break;
		case R.id.rl_qq:
			settingDialog("qq", R.string.qq, txt_content_qq.getText()
					.toString(), R.id.rl_qq);
			break;
		case R.id.rl_logout:
			Builder b = new AlertDialog.Builder(this).setTitle(getString(R.string.confirm)).setMessage(getString(R.string.confirmMessage));
			b.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					settingsEditor.clear();
					settingsEditor.commit();
					isLogout = true;
					CookieSyncManager.createInstance(SettingsActivity.this); 
					CookieManager cookieManager = CookieManager.getInstance();
					cookieManager.removeAllCookie();
					setResult(1);
					SettingsActivity.this.finish();
				}	
			}).setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
				}
			}).create();
			b.show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (!isLogout && isChanged) {
			new Modify().execute();
		}
	}

	private class Modify extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			HttpTransporter httpTransporter = new HttpTransporter();
			Major major = null;
			Gender gender = null;
			Grade grade = null;
			if(settings.getString("major", null) != null) {
				major = Major.valueOf(settings.getString("major", null));
			} 
			if(settings.getString("gender", null) != null) {
				gender = Gender.valueOf(settings.getString("gender", null));
			}
			if(settings.getString("grade", null) != null) {
				grade = Grade.valueOf(settings.getString("grade", null));
			}
			httpTransporter.modifyUser(settings.getLong("matesId", 0), settings.getString("phone", null), major, settings.getString("qq", null), gender, grade);
			return null;
		}
	}

	public void settingDialog(final String option, int title, String text,
			final int id) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				SettingsActivity.this);
		LayoutInflater flater = LayoutInflater.from(SettingsActivity.this);
		final View view = flater.inflate(R.layout.dialog_settings, null);
		Button send = (Button) view.findViewById(R.id.btn_ok);
		Button cancle = (Button) view.findViewById(R.id.btn_cancle);
		builder.setView(view);
		final AlertDialog dialog = builder.create();

		switch (id) {
		case R.id.rl_gender:
			spinner = (Spinner) view.findViewById(R.id.spi_gender);
			spinner.setVisibility(View.VISIBLE);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, gender);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			break;
		case R.id.rl_grade:
			spinner = (Spinner) view.findViewById(R.id.spi_grade);
			spinner.setVisibility(View.VISIBLE);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, grade);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			break;
		case R.id.rl_major:
			spinner = (Spinner) view.findViewById(R.id.spi_major);
			spinner.setVisibility(View.VISIBLE);
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, major);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			break;
		default:
			etxt_setting = (EditText) view.findViewById(R.id.etxt_setting);
			etxt_setting.setVisibility(View.VISIBLE);
			// 设置默认text并全选
			etxt_setting.setText(text);
			etxt_setting.selectAll();
			break;
		}

		// 设置弹框title
		dialog.setTitle(getResources().getString(title));
		// 显示弹框
		dialog.show();

		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// etxt_setting =
				isChanged = true;
				switch (id) {
				case R.id.rl_name:
					
					settingsEditor.putString(option, etxt_setting.getText()
							.toString());
					settingsEditor.commit();
					txt_content_name.setText(etxt_setting.getText().toString());
					dialog.dismiss();
					etxt_setting.setVisibility(View.GONE);
					break;
				case R.id.rl_gender:
		//			Log.d("MATES", spinner.getSelectedItem().toString());
					settingsEditor.putString(option, spinner.getSelectedItem()
							.toString());
					settingsEditor.commit();
					txt_content_gender.setText(spinner.getSelectedItem()
							.toString());
					dialog.dismiss();
					spinner.setVisibility(View.GONE);
					break;
				case R.id.rl_grade:
		//			Log.d("MATES", spinner.getSelectedItem().toString());
					settingsEditor.putString(option, spinner.getSelectedItem()
							.toString());
					settingsEditor.commit();
					txt_content_grade.setText(spinner.getSelectedItem()
							.toString());
					dialog.dismiss();
					spinner.setVisibility(View.GONE);
					break;
				case R.id.rl_major:
		//			Log.d("MATES", spinner.getSelectedItem().toString());
					settingsEditor.putString(option, spinner.getSelectedItem()
							.toString());
					settingsEditor.commit();
					txt_content_major.setText(spinner.getSelectedItem()
							.toString());
					dialog.dismiss();
					spinner.setVisibility(View.GONE);
					break;
				case R.id.rl_phone:
					settingsEditor.putString(option, etxt_setting.getText()
							.toString());
					settingsEditor.commit();
					txt_content_phone
							.setText(etxt_setting.getText().toString());
					dialog.dismiss();
					etxt_setting.setVisibility(View.GONE);
					break;
				case R.id.rl_qq:
					settingsEditor.putString(option, etxt_setting.getText()
							.toString());
					settingsEditor.commit();
					txt_content_qq.setText(etxt_setting.getText().toString());
					dialog.dismiss();
					etxt_setting.setVisibility(View.GONE);
					break;
				default:
					dialog.dismiss();
					break;
				}
				// dataTransporter.addNotification(todoId, uid,
				// notification.getText().toString());

			}
		});

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}

}
