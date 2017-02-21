package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.NetUtils;

public class Rengou_detai_tihuolActivity extends BaseActivity {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.et_address)
	private EditText et_address;
	@ViewInject(R.id.tv_num)
	private TextView tv_num;
	@ViewInject(R.id.tihuo)
	private RelativeLayout tihuo;
	@ViewInject(R.id.quxiao)
	private RelativeLayout quxiao;
	private String num;
	private String ddid;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "恭喜您提货成功", 0).show();
				finish();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "提货失败，请重新提货", 0).show();
				break;

			default:
				break;
			}

		};

	};
	private String addressString;
	private Dialog dialog_wait_tihuo;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		num = bundle.getString("num");
		ddid = bundle.getString("ddid");
		tv_num.setText(num);

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tihuo.setOnClickListener(this);
		quxiao.setOnClickListener(this);
		tv_back.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("提货");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			
			finish();
			break;
		case R.id.tihuo:
			addressString = et_address.getText().toString().trim();
			if (!TextUtils.isEmpty(addressString)) {
				
				new Thread(new Runnable() {

					private InputStream iStream;

					@Override
					public void run() {

						String url = "https://www.4001149114.com/NLJJ/ddapp/dealpick?"
								+ "&ddid="
								+ ddid
								+ "&num="
								+ num
								+ "&address="
								+ addressString;
						try {
							HttpsURLConnection connection = NetUtils
									.httpsconnNoparm(url, "POST");
							int code = connection.getResponseCode();
							if (code == 200) {
								iStream = connection.getInputStream();
								String infojson = NetUtils.readString(iStream);
								// JSONObject jsonObject = new
								// JSONObject(infojson);
								Log.e("我靠快快快快快快快", infojson);
								// handler.sendEmptyMessage(3);
								// Log.e("hahahhahh", infojson);
								parseJson_rengou_tihuo(infojson);

								Log.e("sssssssssss", "hahah");
							}else {
								
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if (iStream != null) {
								try {
									iStream.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}

					}

					private void parseJson_rengou_tihuo(String infojson) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(infojson);
							String success = jsonObject.getString("message");
							if ("success".equals(success)) {
								// 提货成功
								handler.sendEmptyMessage(0);

							}
							if ("error".equals(success)) {
								handler.sendEmptyMessage(1);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}).start();
			} else {
				Toast.makeText(getApplicationContext(), "请正确填写收货地址", 0).show();
			}
			break;
		case R.id.quxiao:
           finish();
			break;

		default:
			break;
		}
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.rengou_detail_activity;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		handler.removeMessages(0);
		handler.removeMessages(1);
	}
}
