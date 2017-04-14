package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.Arith;
import zz.itcast.jiujinhui.res.NetUtils;

public class WoyaorengouActivity extends BaseActivity {

	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	private SharedPreferences sp;
	private String unionidString;

	@ViewInject(R.id.jiujiaoname)
	private TextView jiujiaoname;
	@ViewInject(R.id.person_assets)
	private RelativeLayout person_assets;

	@ViewInject(R.id.hscrollview)
	private HorizontalScrollView hscrollview;
	@ViewInject(R.id.ll_scroll)
	private LinearLayout ll_scroll;

	@ViewInject(R.id.jiubi)
	private TextView jiubi;
	@ViewInject(R.id.zongzichan)
	private TextView zongzichan;

	@ViewInject(R.id.yitihuo)
	private TextView yitihuo;
	@ViewInject(R.id.xiaji)
	private TextView xiaji;

	@ViewInject(R.id.reward)
	private TextView reward;

	@ViewInject(R.id.ren_price)
	private TextView ren_price;
	@ViewInject(R.id.ren_owner)
	private TextView ren_owner;

	@ViewInject(R.id.ren_leftday)
	private TextView ren_leftday;

	@ViewInject(R.id.rb_buy_rengou)
	private LinearLayout rb_buy_rengou;

	@ViewInject(R.id.rb_tihuo_rengou)
	private LinearLayout rb_tihuo_rengou;

	boolean stopThread = false;
	private String dgid;
	private String name;
	private String maindealtermString;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				loading_dialog.dismiss();
				UpdateUI();
				break;
			case 2:
				UpdatehscrollviewUI();
				break;
			case 3:
				loading_dialog.dismiss();
				builder.dismiss();
				Intent intent = new Intent(WoyaorengouActivity.this,
						RengouSuccessActivity.class);
				startActivity(intent);

				// Toast.makeText(getApplicationContext(), "恭喜您，认购成功",
				// 0).show();

				break;
			case 4:
				// builder.dismiss();
				Toast.makeText(getApplicationContext(), "认购失败，请重新认购", 0).show();
				break;
			default:
				break;
			}

		};

	};
	private double jiubiString;
	private double rengou_price;
	private DecimalFormat df;
	private String dealString;
	private String owner;
	private int zongString;
	private String yitihuoString;
	private String yitrans;
	private String xia;
	private double record;
	private String stock;

	protected void UpdateUI() {
		// TODO Auto-generated method stub
		jiujiaoname.setText(name);
		jiubi.setText(df.format(jiubiString / 100));
		zongzichan.setText(stock);
		yitihuo.setText(yitihuoString);
		xiaji.setText(xia);
		reward.setText(df.format(record / 100));
		ren_price.setText(df.format(rengou_price / 100));
		ren_owner.setText(owner);
		ren_leftday.setText(maindealtermString);
	}

	// 实现滚动线程
	int j = 0;
	private ImageView addImageView;
	private ImageView jianImageView;
	private EditText counTextView;
	private TextView price;
	private Button dialog_cancel;
	private Button dialog_ok;

	protected void UpdatehscrollviewUI() {
		// TODO Auto-generated method stub
		// 总宽度
		int totaloff = hscrollview.getMeasuredWidth();
		// 判断宽度
		int off = ll_scroll.getMeasuredWidth();
		int fax = totaloff / off;

		if (j < 7) {
			hscrollview.scrollBy(off, 0);
			j = j + 1;
		} else {
			j = 0;
			hscrollview.scrollBy(-7 * off, 0);
		}

		handler.removeMessages(2);
		handler.sendEmptyMessageDelayed(2, 2000);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		initData_onresume();
	}

	private String infojson_total;

	public void initData_onresume() {
		handler.sendEmptyMessageDelayed(2, 3000);
		unionidString = sp.getString("unionid", null);
		Bundle bundle = getIntent().getExtras();
		name = bundle.getString("name");
		dgid = bundle.getString("dgid");
		maindealtermString = bundle.getString("maindealterm");
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {

				String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/dealsubscribe?"
						+ "&dgid=" + dgid + "&unionid=" + unionidString;

				try {
					HttpsURLConnection connection = NetUtils.httpsconnNoparm(
							url_serviceinfo, "POST");

					int code = connection.getResponseCode();
					if (code == 200) {
						iStream = connection.getInputStream();
						infojson_total = NetUtils.readString(iStream);

						// Log.e("hahahhahh", infojson);
						parseJson(infojson_total);
						stopThread = true;

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
		}).start();

	}

	protected void parseJson(String json) {
		// TODO Auto-generated method stub
		try {
			df = new DecimalFormat("#0.00");
			JSONObject jObject = new JSONObject(json);
			jiubiString = jObject.getDouble("income");
			rengou_price = jObject.getDouble("realprice");
			// sp.edit().putString("huigoujia",
			// df.format(rengou_price/100)).commit();
			dealString = jObject.getString("dealgood");

			JSONObject jObject2 = new JSONObject(dealString);
			owner = jObject2.getString("owner");

			dealdataString = jObject.getString("dealdata");
			JSONObject jObject3 = new JSONObject(dealdataString);

			zongString = jObject3.getInt("subnum");// 认购数
			yitihuoString = jObject3.getString("consumenum");// 已提货
			yitrans = jObject3.getString("buybacknum");
			xia = jObject3.getString("downnum");
			record = jObject3.getDouble("downaward");
			stock = jObject3.getString("stock");// 剩余资产
			ddid = jObject3.getString("ddid");

			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		person_assets.setOnClickListener(this);
		rb_buy_rengou.setOnClickListener(this);
		rb_tihuo_rengou.setOnClickListener(this);
	}

	private Dialog loading_dialog = null;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("认购");
		sp = getSharedPreferences("user", 0);
		loading_dialog = zz.itcast.jiujinhui.res.DialogUtil
				.createLoadingDialog(WoyaorengouActivity.this, "加载中...");
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.woyaorengou_activity;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.person_assets:
			Intent intent = new Intent(WoyaorengouActivity.this,
					TradeRecordActivity.class);
			startActivity(intent);

			break;
		case R.id.rb_buy_rengou:// 认购
			showRengou_buy();
			break;
		case R.id.rb_tihuo_rengou:
			int zongzi = Integer.parseInt(stock);
			if (zongzi > 0) {
				showTihuo();
			} else {
				Toast.makeText(getApplicationContext(), "可提资产为0", 0).show();
			}

			break;
		default:
			break;
		}

	}

	private String countString;

	TextWatcher textWatcher = new TextWatcher() {
		private CharSequence charSequence;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// countString = counTextView.getText().toString().trim();
			/*
			 * String ss=s.toString(); int nu=Integer.valueOf(ss);
			 * price.setText("" + (rengou_price / 100)*nu);
			 */
			String coun = counTextView.getText().toString().trim();
			if (!TextUtils.isEmpty(coun)) {
				int num = Integer.valueOf(count);
				price.setText("" + (rengou_price / 100) * num);

			} else {
				price.setText("0.00");
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			charSequence = s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};
	private ImageView add_tihuo;
	private ImageView reduce_tihuo;
	private EditText num;
	int buy_count = 1;

	private void showRengou_buy() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = (View) inflater.inflate(R.layout.rengou_buy, null);
		addImageView = (ImageView) view.findViewById(R.id.add);
		jianImageView = (ImageView) view.findViewById(R.id.jian);
		counTextView = (EditText) view.findViewById(R.id.count);
		price = (TextView) view.findViewById(R.id.price);
		dialog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
		dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
		builder = new AlertDialog.Builder(this).create();
		builder.setView(view, 0, 0, 0, 0);
		builder.setCancelable(false);
		builder.show();

		// countString = counTextView.getText().toString().trim();
		// num2 = Integer.parseInt(countString);
		// counTextView.setText(1+"");
		price.setText(df.format(rengou_price / 100));

		counTextView.addTextChangedListener(textWatcher);
		// price.addTextChangedListener(textWatcher);

		addImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String num = counTextView.getText().toString().trim();
				if (num == null || num.equals("")) {

					price.setText(0.00 + "");

				} else {
					buy_count = Integer.parseInt(counTextView.getText()
							.toString().trim());
					;
					buy_count++;

					if (buy_count <= 100) {
						counTextView.addTextChangedListener(textWatcher);
						counTextView.setText(buy_count + "");

					} else {
						Toast.makeText(getApplicationContext(), "最大认购量不能超过100",
								0).show();
						buy_count = 100;
						counTextView.setText(buy_count + "");

					}
				}

			}
		});

		jianImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// int
				// n_1=Integer.parseInt(counTextView.getText().toString().trim());
				String num = counTextView.getText().toString().trim();
				if (num == null || num.equals("")) {
					counTextView.setText(1 + "");
					price.setText(df.format(rengou_price / 100));

				} else {
					buy_count = Integer.valueOf(num);

					if (buy_count > 1) {
						buy_count--;
						counTextView.addTextChangedListener(textWatcher);
						counTextView.setText(buy_count + "");

					} else {
						counTextView.setText(1 + "");

						Toast.makeText(getApplicationContext(), "最小认购量不能小于1", 0)
								.show();
					}

				}

			}
		});
		dialog_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.dismiss();
				buy_count = 1;

			}
		});
		dialog_ok.setOnClickListener(new OnClickListener() {

			private String pricString;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double priceDouble = Double.parseDouble(price.getText()
						.toString().trim());
				pricString = price.getText().toString().trim();

				Log.e("zhzh", price.getText().toString().trim());
				if ((jiubiString / 100) >= priceDouble) {

					if ((buy_count + zongString) <= 100) {
						// 继续购买
						builder.dismiss();
						loading_dialog.show();
						new Thread(new Runnable() {

							private InputStream iStream;

							@Override
							public void run() {

								String url = "https://www.4001149114.com/NLJJ/ddapp/dealsubscribepay?"
										+ "&ddid="
										+ ddid
										+ "&num="
										+ countString + "&price=" + pricString;
								try {
									HttpsURLConnection connection = NetUtils
											.httpsconnNoparm(url, "POST");
									int code = connection.getResponseCode();
									if (code == 200) {
										iStream = connection.getInputStream();
										String infojson = NetUtils
												.readString(iStream);
										// JSONObject jsonObject = new
										// JSONObject(infojson);
										Log.e("我靠快快快快快快快", infojson);
										// handler.sendEmptyMessage(3);
										// Log.e("hahahhahh", infojson);
										parseJson_rengoubuy(infojson);

										Log.e("sssssssssss", "hahah");
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

							private void parseJson_rengoubuy(String infojson) {
								// TODO Auto-generated method stub
								try {
									JSONObject jsonObject = new JSONObject(
											infojson);
									String success = jsonObject
											.getString("message");
									if ("success".equals(success)) {
										// 认购成功
										handler.sendEmptyMessage(3);

									}
									if ("error".equals(success)) {
										handler.sendEmptyMessage(4);
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}).start();

					} else {
						Toast.makeText(getApplicationContext(), "最多认购数量为100瓶！",
								0).show();

					}
				} else {
					Toast.makeText(getApplicationContext(), "账户酒币不够，请先充值", 0)
							.show();
				}

			}
		});

	}

	int tihuo_count = 1;
	private int num2;
	private String ddid;
	private AlertDialog builder;
	private String dealdataString;
	private AlertDialog builder1;

	// 提货
	private void showTihuo() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = (View) inflater.inflate(R.layout.rengou_tihuo, null);
		reduce_tihuo = (ImageView) view.findViewById(R.id.reduce_tihuo);
		add_tihuo = (ImageView) view.findViewById(R.id.add_tihuo);
		num = (EditText) view.findViewById(R.id.num);
		dialog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
		dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
		builder1 = new AlertDialog.Builder(this).create();
		builder1.setView(view, 0, 0, 0, 0);
		builder1.setCancelable(false);
		builder1.show();
		dialog_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder1.dismiss();
				tihuo_count = 1;

			}
		});
		dialog_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String num_t=num.getText().toString().trim();
				
				// 总资产
				int zong_num = Integer.parseInt(stock);
				if (!TextUtils.isEmpty(num_t)) {
					int number = Integer.parseInt(num_t);
					if (number <= zong_num) {

						builder1.dismiss();

						Log.e("tihuo_ok", num.getText().toString().trim());
						Intent intent = new Intent(WoyaorengouActivity.this,
								Rengou_detai_tihuolActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("num", number + "");
						bundle.putString("ddid", ddid);
						intent.putExtras(bundle);
						startActivity(intent);

					} else {
						Toast.makeText(getApplicationContext(), "提货的数量不能大于总资产", 0)
								.show();
					}
				}else {
					Toast.makeText(getApplicationContext(), "请输入提货的数量!", 0)
					.show();
				}
				
				
				

			}
		});
		add_tihuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nu = num.getText().toString().trim();
				if (nu == null || nu.equals("")) {
					num.setText("" + 1);
					num.setSelection(num.getText()
							.toString().trim().length());
				} else {
					int n = Integer.valueOf(nu);
					n++;
					num.setText("" + n);
					num.setSelection(num.getText()
							.toString().trim().length());
				}
			}
		});
		reduce_tihuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nu = num.getText().toString().trim();
				if (nu == null || nu.equals("")) {
					num.setText("" + 1);
					num.setSelection(num.getText()
							.toString().trim().length());
				} else {
					int n = Integer.valueOf(nu);

					if (n > 1) {
						n--;
						num.setText("" + n);
						num.setSelection(num.getText()
								.toString().trim().length());
					} else {
						n = 1;
					}
				}

			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		handler.removeMessages(1);
		handler.removeMessages(2);
		handler.removeMessages(3);
		handler.removeMessages(4);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
}
