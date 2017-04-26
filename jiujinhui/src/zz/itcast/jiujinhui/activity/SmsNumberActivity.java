package zz.itcast.jiujinhui.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.OurApplication;
import zz.itcast.jiujinhui.service.CountDownTimerUtils;

public class SmsNumberActivity extends BaseActivity {

	@ViewInject(R.id.SmsSubmit)
	private RelativeLayout SmsSubmit;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;

	@ViewInject(R.id.smscod)
	// 验证码
	private TextView smscod;

	@ViewInject(R.id.phone_num)
	private EditText phone_num;

	@ViewInject(R.id.sms_code)
	private EditText sms_code;
	private SharedPreferences sp;
	private String unionString;

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.smsnumberverification_activity;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		sp = OurApplication.getContext().getSharedPreferences("user", 0);

		unionString = sp.getString("unionid", null);

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		SmsSubmit.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		smscod.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("绑定手机号");
		tv__title.setTextSize(22);

	}

	private String message;
	private int term;
	private String datainfo;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 0:

				// 发送成功进入倒计时
				CountDownTimerUtils countDownTimer = new CountDownTimerUtils(
						smscod, 60000, 1000);
				countDownTimer.start();

				break;

			case 1:
				Toast.makeText(getApplicationContext(), datainfo, 0).show();

				break;
			case 2:
				//验证失败
				Toast.makeText(getApplicationContext(), "验证失败", 0).show();

				break;
			case 3:
				//验证成功
				Toast.makeText(getApplicationContext(), "验证绑定成功", 0).show();
				
			
				
				String extra = getIntent().getStringExtra("sms");
				if (extra.equals("tixian")) {
					Intent intent = new Intent(SmsNumberActivity.this,
							MyTiXianActivity.class);
					startActivity(intent);

				} else if (extra.equals("recharge")) {
					Intent intent1 = new Intent(SmsNumberActivity.this,
							ReChargeActivity.class);
					startActivity(intent1);
				}else if (extra.equals("rebangding")) {
					Intent intent2 = new Intent(SmsNumberActivity.this,
							ReChargeActivity.class);
					startActivity(intent2);
				}
			
               finish();
				break;
				

			default:
				break;
			}

		};
	};
	private String num_phone;
	private String code;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {

		case R.id.smscod:

			num_phone = phone_num.getText().toString().trim();
			if (!TextUtils.isEmpty(num_phone)) {
				HttpUtils httpUtils = new HttpUtils();
				httpUtils.send(HttpRequest.HttpMethod.GET,
						"https://www.4001149114.com/NLJJ/ddapp/sendsms?unionid="
								+ unionString + "&mobile=" + num_phone,
						new RequestCallBack<String>() {

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								// TODO Auto-generated method stub
								try {
									JSONObject jsonObject = new JSONObject(
											responseInfo.result.toString());
									message = jsonObject.getString("message");

									term = jsonObject.getInt("term");

									datainfo = jsonObject.getString("data");
									if ("error".equals(message)) {
										handler.sendEmptyMessage(1);

									} else {
										// 手机号填写错误
										handler.sendEmptyMessage(0);

									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

						});
			} else {
				Toast.makeText(getApplicationContext(), "请输入您的手机号", 0).show();
			}

			break;

		case R.id.SmsSubmit:
			code = sms_code.getText().toString().trim();
			num_phone = phone_num.getText().toString().trim();
			if (!TextUtils.isEmpty(num_phone)) {
				if (!TextUtils.isEmpty(code)) {
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.send(HttpRequest.HttpMethod.GET,
							"https://www.4001149114.com/NLJJ/ddapp/sendsmsverify?unionid="
									+ unionString + "&mobile=" + num_phone
									+ "&smscode=" + code,
							new RequestCallBack<String>() {

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									// TODO Auto-generated method stub
									try {
										JSONObject jsonObject = new JSONObject(
												responseInfo.result.toString());
										message = jsonObject
												.getString("message");

										
										if ("error".equals(message)) {
											//验证失败
											handler.sendEmptyMessage(2);
                                          
										} else {
											// 手机号
											//验证成功
											sp.edit().putString("mobile", num_phone).commit();
											handler.sendEmptyMessage(3);

										}

									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(HttpException arg0,
										String arg1) {
									// TODO Auto-generated method stub

								}

							});
				} else {
					Toast.makeText(getApplicationContext(), "请输入验证码", 0).show();
				}

			} else {
				Toast.makeText(getApplicationContext(), "请输入您的手机号", 0).show();
			}

		
			
			break;
		case R.id.tv_back:
			finish();
			break;
		default:
			break;
		}

	}
}
