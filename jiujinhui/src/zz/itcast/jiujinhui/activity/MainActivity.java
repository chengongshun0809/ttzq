package zz.itcast.jiujinhui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.BaseFragment;
import zz.itcast.jiujinhui.fragment.TradeFragment;
import zz.itcast.jiujinhui.fragment.helpFragment;
import zz.itcast.jiujinhui.fragment.personFragment;
import zz.itcast.jiujinhui.res.ActivityCollector;
import zz.itcast.jiujinhui.res.OurApplication;
import zz.itcast.jiujinhui.res.ToastUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends BaseActivity {

	@ViewInject(R.id.radiogroup)
	private RadioGroup radiogroup;
	@ViewInject(R.id.rb_trade)
	private RadioButton rb_trade;

	private List<BaseFragment> fragments;
	private FragmentManager fm;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}
	private SharedPreferences sp_start;
	@Override
	public void initData() {
		preferences = getSharedPreferences(
				"first_pref", MODE_PRIVATE);
		
		sp_start=getSharedPreferences("start", MODE_PRIVATE);
		
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new TradeFragment());
		fragments.add(new personFragment());
		fragments.add(new helpFragment());
		// Ĭ��ѡ�е���Ŀ
		radiogroup.check(R.id.rb_trade);
		// 获取自己的版本信息

		PackageManager pm = getApplicationContext().getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			versionCode = packageInfo.versionCode;
			versionName = packageInfo.versionName;

			// 设置textview
			// String tv_versionName.setText(versionName);
		} catch (NameNotFoundException e) {
			// can not reach 异常不会发生
		}

		// 如果正常的从服务器获取应调用下面的timeInitialization()
		// ToDo检查更新版本
		checkVerion();
		int num_start=sp_start.getInt("start", 1);
		if (num_start>3) {
			preferences.edit().putBoolean("first_up_cancel", true).commit();
			sp_start.edit().putInt("start", 1).commit();
			
		}else {
			
		}
		
		
		
		
		
	}

	private void checkVerion() {
		// TODO Auto-generated method stub
		// 检测新版本
		// 获取服务器版本号
		int servercode = 3;
		if (servercode == versionCode) {

			// 不做提示

		} else {
			// 弹出提示更新的提示框
			
			boolean isFIrst_cancel=preferences.getBoolean("first_up_cancel", true);
			if (isFIrst_cancel) {
				showUpdateDialog();
				
			}else {
				preferences.edit().putBoolean("first_up_cancel", false).commit();
			}
			
		}

	}
	
	private void showUpdateDialog() {
		dialog = new AlertDialog.Builder(this).create();
		dialog.setCancelable(false);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.prompt_alertdialog);
		LinearLayout ll_title = (LinearLayout) window
				.findViewById(R.id.ll_title);
		ll_title.setVisibility(View.VISIBLE);
		TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
		pb_download = (ProgressBar) window
				.findViewById(R.id.pb_splash_download);
		pb_download.setVisibility(View.GONE);// 隐藏进度条
		TextView tv_content = (TextView) window.findViewById(R.id.tv_content);

		tv_content.setText("1.修复了部分bug 2.优化了用户体验");
		final TextView tv_sure = (TextView) window.findViewById(R.id.tv_sure);
		final TextView tv_cancle = (TextView) window
				.findViewById(R.id.tv_cancle);
		tv_cancle.setText("取消");
		tv_cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				preferences.edit().putBoolean("first_up_cancel", false).commit();
				

			}
		});
		tv_sure.setText("更新");
		tv_sure.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				downLoadNewApk();// 下载新版本
				tv_cancle.setEnabled(false);
				tv_sure.setEnabled(false);
				// dialog.cancel();
				// loadMainActivity();
				pb_download.setVisibility(View.VISIBLE);
			}
		});
	}

	/**
	 * 新版本的下载安装
	 */
	protected void downLoadNewApk() {
		HttpUtils utils = new HttpUtils();
		// parseJson.getUrl() 下载的url
		// target 本地路径
		// System.out.println(parseJson.getUrl());
		File file = new File("/mnt/sdcard/测试.apk");
		// 如果此文件已存在先删除
		file.delete();// 删除文件

		utils.download(
				"http://www.gamept.cn/d/file/game/qipai/20140627/HappyLordZZ_1.0.19_20140325_300002877528_2200139763.apk",
				"/mnt/sdcard/测试.apk", new RequestCallBack<File>() {

					@Override
					public void onLoading(final long total, final long current,
							boolean isUploading) {
						pb_download.setVisibility(View.VISIBLE);// 设置进度的显示
						int max = (int) total;
						int progress = (int) current;
						pb_download.setMax(max);// 设置进度条的最大值
						pb_download.setProgress(progress);// 设置当前进度

						// showNotification(max,progree);

						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						// 下载成功
						// 在主线程中执行
						Toast.makeText(getApplicationContext(), "下载新版本成功", 1)
								.show();
						// 安装apk
						installApk();// 安装apk
						pb_download.setVisibility(View.GONE);// 隐藏进度条
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// 下载失败
						Toast.makeText(getApplicationContext(),
								"下载新版本失败,请检查网络设置", 1).show();
						pb_download.setVisibility(View.GONE);// 隐藏进度条
						dialog.dismiss();
					}
				});
	}

	/**
	 * 安装下载的新版本
	 */
	protected void installApk() {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		String type = "application/vnd.android.package-archive";
		Uri data = Uri.fromFile(new File("/mnt/sdcard/测试.apk"));
		intent.setDataAndType(data, type);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 如果用户取消更新apk，那么直接进入主界面
		dialog.dismiss();
		super.onActivityResult(requestCode, resultCode, data);
	}

	int currentItem;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		fm = getSupportFragmentManager();

	}

	private SharedPreferences sp;

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_trade:
					currentItem = 0;// ������Ǯ
					break;
				case R.id.rb_person:
					currentItem = 1;// ��������

					break;
				case R.id.rb_help:
					currentItem = 2;// ��������
					break;

				default:
					break;
				}

				if (currentItem == 1) {

					sp = getSharedPreferences("user", MODE_PRIVATE);
					boolean isLogined = sp.getBoolean("isLogined", false);
					if (isLogined == false) {

						Intent intent = new Intent(OurApplication.getContext(),
								LoginActivity.class);
						startActivity(intent);

						radiogroup.check(R.id.rb_trade);
						fm.beginTransaction()
								.replace(R.id.fl, fragments.get(0)).commit();

						return;
					} else {
						radiogroup.check(R.id.rb_person);
						fm.beginTransaction()
								.replace(R.id.fl, fragments.get(1)).commit();
					}

				} else {

					fm.beginTransaction()
							.replace(R.id.fl, fragments.get(currentItem))
							.commit();
				}
			}
		});

	}

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { // TODO Auto-generated method stub
	 * super.onActivityResult(requestCode, resultCode, data); //
	 * 判断请求码，结果码来确定要执行的代码 if (resultCode==200) { // 在这里设置要显示的fragment
	 * radiogroup.check(R.id.rb_trade);
	 * 
	 * 
	 * } }
	 */

	private boolean isSecondBackPressed;
	private long secondTime;
	private long firstTime;
	private int versionCode;
	private String versionName;
	private Dialog dialog;
	private ProgressBar pb_download;
	private SharedPreferences preferences;

	/**
	 * 点击两次返回键退出应用
	 */
	@Override
	public void onBackPressed() {
		if (currentItem != 0) {
			currentItem = 0;

			radiogroup.check(R.id.rb_trade);
			fm.beginTransaction().replace(R.id.fl, fragments.get(0)).commit();

		} else {

			if (isSecondBackPressed) {
				secondTime = System.currentTimeMillis();
				if (secondTime - firstTime < 2000) {
					// 两次点击时间间隔小于2s
					ActivityCollector.finishAll();
					finish();
				} else {
					// 两次点击时间间隔大于2s
					firstTime = System.currentTimeMillis();
					ToastUtil.showTextToast(this, "再点一次退出");
				}
			} else {
				// 第一次点击返回键
				isSecondBackPressed = true;
				firstTime = System.currentTimeMillis();
				ToastUtil.showTextToast(this, "再点一次退出");
			}

		}

	}
}
