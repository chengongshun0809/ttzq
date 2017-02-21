package zz.itcast.jiujinhui.activity;

import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.Util;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ReChargeActivity extends BaseActivity {

	@ViewInject(R.id.chongzhi)
	private RelativeLayout chongzhi;

	@ViewInject(R.id.wubai)
	private RelativeLayout wubai;
	@ViewInject(R.id.yiqian)
	private RelativeLayout yiqian;
	@ViewInject(R.id.liangqian)
	private RelativeLayout liangqian;
	@ViewInject(R.id.wuqian)
	private RelativeLayout wuqian;
	@ViewInject(R.id.yiwan)
	private RelativeLayout yiwan;
	@ViewInject(R.id.sanwan)
	private RelativeLayout sanwan;

	// 小对勾
	@ViewInject(R.id.iv_drink_checked_wubai)
	private ImageView iv_drink_checked_wubai;
	@ViewInject(R.id.iv_drink_checked_yiqian)
	private ImageView iv_drink_checked_yiqian;
	@ViewInject(R.id.iv_drink_checked_liangqian)
	private ImageView iv_drink_checked_liangqian;
	@ViewInject(R.id.iv_drink_checked_wuqian)
	private ImageView iv_drink_checked_wuqian;
	@ViewInject(R.id.iv_drink_checked_yiwan)
	private ImageView iv_drink_checked_yiwan;
	@ViewInject(R.id.iv_drink_checked_sanwan)
	private ImageView iv_drink_checked_sanwan;
	@ViewInject(R.id.other_moneny)
	private EditText other_moneny;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.phonenumber)
	private TextView phonenumber;

	private SharedPreferences sp;

	// 圆形图片
	@ViewInject(R.id.circleImabeView)
	private zz.itcast.jiujinhui.view.CircleImageView circleImabeView;

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.recharge_activity;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("充值");
		tv__title.setTextSize(22);
		sp = getSharedPreferences("user", 0);
		// 此方法不完善，等绑定手机号后自动获取绑定的手机号
		String pnumber = sp.getString("mobile", null);
		phonenumber.setText(pnumber);

		sp = getSharedPreferences("user", 0);
		String headimgurl = sp.getString("headimg", null);
		Picasso.with(this).load(headimgurl).into(circleImabeView);
		openidString = sp.getString("openid", null);
		unionidString = sp.getString("unionid", null);

	}

	private IWXAPI api;

	@Override
	public void initData() {

		// TODO Auto-generated method stub
		api = WXAPIFactory.createWXAPI(this, null);
		api.registerApp("wxdb59e14854a747c8");
		iv_drink_checked_wubai.setVisibility(View.VISIBLE);
		total = "500";
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		chongzhi.setOnClickListener(this);
		wubai.setOnClickListener(this);
		yiqian.setOnClickListener(this);
		liangqian.setOnClickListener(this);
		wuqian.setOnClickListener(this);
		yiwan.setOnClickListener(this);
		sanwan.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		other_moneny.setOnTouchListener(new OnTouchListener() {
			// 按住和松开的标识

			int touch_flag = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				touch_flag++;
				if (touch_flag%2==0) {
					iv_drink_checked_wubai.setVisibility(v.GONE);
					iv_drink_checked_yiqian.setVisibility(v.GONE);
					iv_drink_checked_liangqian.setVisibility(v.GONE);
					iv_drink_checked_wuqian.setVisibility(v.GONE);
					iv_drink_checked_yiwan.setVisibility(v.GONE);
					iv_drink_checked_sanwan.setVisibility(v.GONE);
					total = other_moneny.getText().toString();

					other_moneny.setFocusable(true);
					other_moneny.setFocusableInTouchMode(true);
					other_moneny.requestFocus();
					//total = null;
					touch_flag = 0;
				}

				return false;
			}
		});
		other_moneny.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				total = other_moneny.getText().toString();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	String total = null;

	private String openidString;

	private String unionidString;

	private double totaldouble;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wubai:
			iv_drink_checked_wubai.setVisibility(v.VISIBLE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			total = "500";
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);

			break;
		case R.id.yiqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.VISIBLE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			total = "1000";
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.liangqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.VISIBLE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			total = "2000";

			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.wuqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.VISIBLE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			total = "5000";
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.yiwan:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.VISIBLE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			total = "10000";
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);

			break;
		case R.id.sanwan:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.VISIBLE);
			total = "30000";
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;

		case R.id.chongzhi:// 微信充值

			if (!TextUtils.isEmpty(total)) {
				totaldouble = Double.parseDouble(total);
				if (totaldouble < 2) {
					Toast.makeText(getApplicationContext(), "充值金额不能小于2", 0)
							.show();
					chongzhi.setEnabled(true);
				} else {
					other_moneny.setFocusable(false);
					other_moneny.setFocusableInTouchMode(false);
					Toast.makeText(ReChargeActivity.this, "请稍等，正在跳转微信支付...",
							Toast.LENGTH_SHORT).show();
					chongzhi.setEnabled(false);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							String url = "https://www.4001149114.com/NLJJ/ddapp/dealwinebuy?wxopenid="
									+ openidString
									+ "&unionid="
									+ unionidString
									+ "&total="
									+ total
									+ "&num=1";
							// String
							// url="http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
							/*
							 * Toast.makeText(ReChargeActivity.this, "获取订单中...",
							 * Toast.LENGTH_SHORT).show();
							 */
							try {
								byte[] buf = Util.httpGet(url);
								if (buf != null && buf.length > 0) {
									String content = new String(buf);
									//Log.e("get server pay params:", content);
									JSONObject json = new JSONObject(content);
									if (null != json && !json.has("retcode")) {
										PayReq req = new PayReq();
										// req.appId = "wxf8b4f85f3a794e77"; //
										// 测试用appId

										/*
										 * req.appId = json.getString("appid");
										 * req.partnerId =
										 * json.getString("partnerid");
										 * req.prepayId =
										 * json.getString("prepayid");
										 * req.nonceStr =
										 * json.getString("noncestr");
										 * req.timeStamp =
										 * json.getString("timestamp");
										 * req.packageValue =
										 * json.getString("package"); req.sign =
										 * json.getString("sign"); req.extData =
										 * "app data"; // optional
										 */
										req.appId = json.getString("appid");
										req.partnerId = json
												.getString("partnerid");
										req.prepayId = json
												.getString("prepayid");
										req.nonceStr = json
												.getString("noncestr");
										req.timeStamp = json
												.getString("timestamp");
										req.packageValue = json
												.getString("package");
										req.sign = json.getString("sign");
										req.extData = "app data"; // optional

										// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

										api.sendReq(req);
									} else {
										Log.d("PAY_GET",
												"返回错误"
														+ json.getString("retmsg"));
										/*
										 * Toast.makeText(ReChargeActivity.this,
										 * "返回错误" + json.getString("retmsg"),
										 * Toast.LENGTH_SHORT).show();
										 */
									}
								} else {
									Log.d("PAY_GET", "服务器请求错误");
									/*
									 * Toast.makeText(ReChargeActivity.this,
									 * "服务器请求错误", Toast.LENGTH_SHORT).show();
									 */
								}
							} catch (Exception e) {
								Log.e("PAY_GET", "异常：" + e.getMessage());
								/*
								 * Toast.makeText(ReChargeActivity.this, "异常：" +
								 * e.getMessage(), Toast.LENGTH_SHORT) .show();
								 */
							}

						}
					}).start();

				}
			} else {
				Toast.makeText(getApplicationContext(), "请输入充值金额", 0).show();
				chongzhi.setEnabled(true);
			}

			chongzhi.setEnabled(true);

			break;

		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}

	}
}
