package zz.itcast.jiujinhui.activity;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.BaseFragment;
import zz.itcast.jiujinhui.fragment.NoLoginPersonFragment;
import zz.itcast.jiujinhui.fragment.TradeFragment;
import zz.itcast.jiujinhui.fragment.helpFragment;
import zz.itcast.jiujinhui.fragment.personFragment;
import zz.itcast.jiujinhui.res.ToastUtil;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
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

	@Override
	public void initData() {
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new TradeFragment());
		fragments.add(new personFragment());
		fragments.add(new helpFragment());
		fragments.add(new NoLoginPersonFragment());
		// Ĭ��ѡ�е���Ŀ
		radiogroup.check(R.id.rb_trade);

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
						
						Intent intent = new Intent(MainActivity.this,
								LoginActivity.class);
						startActivity(intent);
						
						radiogroup.check(R.id.rb_trade);
						fm.beginTransaction()
								.replace(R.id.fl, fragments.get(0)).commit();
						return;
					}else {
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 判断请求码，结果码来确定要执行的代码
		if (resultCode==200) {
			// 在这里设置要显示的fragment
			radiogroup.check(R.id.rb_trade);
		
                   
	}
	}

	private boolean isSecondBackPressed;
	private long secondTime;
	private long firstTime;
	
	/**
	 * 点击两次返回键退出应用
	 */
	@Override
	public void onBackPressed() {
		if (currentItem != 0) {
			currentItem = 0;

			radiogroup.check(R.id.rb_trade);

		} else {

			if (isSecondBackPressed) {
				secondTime = System.currentTimeMillis();
				if (secondTime - firstTime < 2000) {
					// 两次点击时间间隔小于2s
					
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
