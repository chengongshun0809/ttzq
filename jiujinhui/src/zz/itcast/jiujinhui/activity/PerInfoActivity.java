package zz.itcast.jiujinhui.activity;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.Constants;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

public class PerInfoActivity extends BaseActivity {

	@ViewInject(R.id.tuichu)
	private RelativeLayout tuichu;
	private SharedPreferences sp;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.et_phonenumber)
	private TextView et_phonenumber;
	@ViewInject(R.id.et_register_username_id)
	private TextView et_register_username_id;

	// 圆形图片
	@ViewInject(R.id.circleImabeView)
	private zz.itcast.jiujinhui.view.CircleImageView circleImabeView;

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.perinfo_activity;
	}

	private String isshun;

	private Button btnOK;
	private Button btnCancel;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("user", 0);
		isshun = getIntent().getStringExtra("shun");
		
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tuichu.setOnClickListener(this);
		tv_back.setOnClickListener(this);

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("个人信息");
		// 微信头像
		sp = getSharedPreferences("user", 0);
		String headimgurl = sp.getString("headimg", null);
		Picasso.with(this).load(headimgurl).into(circleImabeView);
		// 微信昵称
		String nickNameString = sp.getString("nickname", null);
		et_register_username_id.setText(nickNameString);
		
		//个人手机号
		String number=sp.getString("mobile", null);
		et_phonenumber.setText(number);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tuichu:

			LayoutInflater inflater = LayoutInflater.from(this);
			View view = (View) inflater.inflate(R.layout.person_info_tuichu,
					null);
			btnOK = (Button) view.findViewById(R.id.dialog_ok);
			btnCancel = (Button) view.findViewById(R.id.dialog_cancel);
			final AlertDialog builder = new AlertDialog.Builder(this).create();
			builder.setView(view,0,0,0, 0);
			builder.setCancelable(false);
			builder.show();
			btnOK.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if ("shun".equals(isshun)) {
						setResult(200);
					}
					sp.edit().putBoolean("isLogined", false).commit();
					builder.dismiss();
					finish();

				}
			});

			// 取消
			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					builder.dismiss();
				}
			});

			break;
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}

	}

}
