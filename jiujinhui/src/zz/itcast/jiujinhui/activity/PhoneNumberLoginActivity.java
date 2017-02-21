package zz.itcast.jiujinhui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class PhoneNumberLoginActivity extends BaseActivity {


	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.phone_number)
	private EditText phone_number;
	@ViewInject(R.id.phone_password)
	private EditText phone_password;
	@ViewInject(R.id.forget_password)
	private TextView forget_password;
	@ViewInject(R.id.btn_regiest)
	private Button btn_regiest;
	@ViewInject(R.id.btn_login)
	private Button btn_login;
	
	@ViewInject(R.id.checkRember)
	private CheckBox checkRember;
	@ViewInject(R.id.Autologin)
	private CheckBox Autologin;
	
	
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.phone_number_login;
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
      ViewUtils.inject(this);
      tv__title.setText("登录");
      tv__title.setTextSize(22);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
         tv_back.setOnClickListener(this);
         btn_regiest.setOnClickListener(this);
         btn_login.setOnClickListener(this);
         forget_password.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.btn_regiest://注册
			Intent intent1=new Intent(PhoneNumberLoginActivity.this,RegisterActivity.class);
			startActivity(intent1);
			
			break;
		case R.id.btn_login://登录
			
			break;
		case R.id.forget_password://忘记密码
			Intent intent3=new Intent(PhoneNumberLoginActivity.this,ForgetPwdActivity.class);
			startActivity(intent3);
			
			break;

		default:
			break;
		}
	}

	

}
