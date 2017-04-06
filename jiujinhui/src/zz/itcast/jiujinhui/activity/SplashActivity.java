package zz.itcast.jiujinhui.activity;

import android.content.Intent;
import android.os.Handler;
import zz.itcast.jiujinhui.R;

public class SplashActivity extends BaseActivity {

	@Override
	public void initView() {
		// TODO Auto-generated method stub
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
		//ToDo检查更新版本

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.splash_activity;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
