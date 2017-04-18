package zz.itcast.jiujinhui.activity;

import java.io.File;

import zz.itcast.jiujinhui.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SplashActivity extends BaseActivity {

	private int versionCode;
	private String versionName;

	@Override
	public void initView() {
		// TODO Auto-generated method stub

		loadMainActivity();

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		
	}

	// 计算启动次数
	
	private SharedPreferences sp;

	private void loadMainActivity() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("start", MODE_PRIVATE);
		int num = sp.getInt("start", 1);
		num++;
		sp.edit().putInt("start", num).commit();

		
		
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent(SplashActivity.this,
						MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);// 跳转到MainActivity
				SplashActivity.this.finish();// 结束SplashActivity
			}
		}, 3000);
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.splash_activity;
	}

}
