package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.BuyChartFragment;
import zz.itcast.jiujinhui.fragment.EveryDayTradeRecordFragment;
import zz.itcast.jiujinhui.fragment.NowTradeRecoedFragment;
import zz.itcast.jiujinhui.fragment.SaleChartFragment;
import zz.itcast.jiujinhui.res.Arith;
import zz.itcast.jiujinhui.res.DateTest;
import zz.itcast.jiujinhui.res.NetUtils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TradeServiceActivity extends BaseActivity {
	@ViewInject(R.id.scrollview)
	private zz.itcast.jiujinhui.view.MyScrollView scrollview;

	// 买入
	@ViewInject(R.id.rb_buy_service)
	private LinearLayout rb_buy_service;
	// 卖出
	@ViewInject(R.id.rb_sale_service)
	private LinearLayout rb_sale_service;

	// 提货
	@ViewInject(R.id.rb_tihuo_service)
	private LinearLayout rb_tihuo_service;
	// 转让
	@ViewInject(R.id.rb_zhuanrang_service)
	private LinearLayout rb_zhuanrang_service;
	// 个人资产
	@ViewInject(R.id.person_assets)
	private RelativeLayout person_assets;

	// 交易曲线
	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip tabs;
	@ViewInject(R.id.trade_pager)
	private ViewPager trade_pager;
	// 酒窖名字
	@ViewInject(R.id.jiujiaoname)
	private TextView jiujiaoname;

	//
	// 交易曲线
	private ArrayList<Fragment> fragmentsList1;
	// 买入卖出曲线
	@ViewInject(R.id.tabs_buysale)
	private PagerSlidingTabStrip tabs_buysale;
	@ViewInject(R.id.buy_sale_pager)
	private ViewPager buy_sale_pager;

	private ArrayList<Fragment> fragmentsList2;
	@ViewInject(R.id.hscrollview)
	private HorizontalScrollView hscrollview;
	@ViewInject(R.id.ll_scroll)
	private LinearLayout ll_scroll;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	private SharedPreferences sp;
	// 今日涨跌
	@ViewInject(R.id.total_zd)
	private TextView total_zd;
	// 交易指导价
	@ViewInject(R.id.realprice)
	private TextView realpri;
	// 酒币
	@ViewInject(R.id.jiubi)
	private TextView jiubi;
	// 总资产
	@ViewInject(R.id.subnum)
	private TextView total_assets;
	// 总收益
	@ViewInject(R.id.total_shouyi)
	private TextView total_shouyi;
	// 剩余资产
	@ViewInject(R.id.left_assets)
	private TextView left_assets;
	// 卖出中
	@ViewInject(R.id.saling)
	private TextView saling;
	@ViewInject(R.id.xiaji)
	private TextView xiaji;
	// 买入中
	@ViewInject(R.id.buying)
	private TextView buying;

	// 已成交
	@ViewInject(R.id.dealed)
	private TextView dealed;
	// 已提货
	@ViewInject(R.id.taked_goods)
	private TextView taked_goods;
	// 已转让
	@ViewInject(R.id.transed)
	private TextView transed;
	// 奖励
	@ViewInject(R.id.reward)
	private TextView reward;
	boolean stopThread = false;
	private static long lastTime = 0;
	private static final int DEFAULT_TIME = 2000;

	public static boolean isSingle() {
		boolean isSingle;
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTime <= DEFAULT_TIME) {
			isSingle = true;
		} else {
			isSingle = false;
		}
		lastTime = currentTime;

		return isSingle;

	}

	  boolean isaliv=true;
      @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
   
    	
		//判断当前页面是否联网
		ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo info=connectivityManager.getActiveNetworkInfo();
		if(info!=null&&info.isAvailable()){
			isaliv=true;
			
			
		}else{
			isaliv=false;
			Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show();
			
		}
			
      
      
      }
	
      
      
	// 定义一个Handler对象
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 1:
				UpdateUI();
				scrollview.invalidate();// 定时刷新
				break;
			case 2:
				UpdatehscrollviewUI();
				// hscrollview.invalidate();
				break;
			case 3:
				dialog_buy.dismiss();
				Toast.makeText(getApplicationContext(), "恭喜您买入成功", 0).show();
				break;
			case 4:
				dialog_buy.dismiss();
				Toast.makeText(getApplicationContext(), "买入失败，请重新买入", 0).show();
				break;
			case 5:
				dialog1.dismiss();
				Toast.makeText(getApplicationContext(), "您已转让成功", 0).show();
				break;
			case 6:
				dialog1.dismiss();
				Toast.makeText(getApplicationContext(), "转让失败", 0).show();
				break;
			case 7:
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "恭喜您卖出成功", 0).show();
				break;
			case 8:
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "卖出失败", 0).show();
				break;
			default:
				break;
			}

		};

	};

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);

		rb_buy_service.setOnClickListener(this);
		rb_sale_service.setOnClickListener(this);
		rb_tihuo_service.setOnClickListener(this);
		rb_zhuanrang_service.setOnClickListener(this);
		person_assets.setOnClickListener(this);
		/**/
	}

	// 实现滚动线程
	int j = 0;

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
		handler.sendEmptyMessageDelayed(2, 3000);
	}

	// 当前滚动距离
	int currentX = 0;

	@Override
	public void initView() {

		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("交易服务");
		// hscrollview定时滚动

		handler.sendEmptyMessageDelayed(2, 3000);

		dgid = getIntent().getStringExtra("dealdgid");
		String name = getIntent().getStringExtra("name");
		jiujiaoname.setText(name);
		jiujiaoname.setTextSize(16);
		// Log.e("mm", dgid);
		sp = getSharedPreferences("user", 0);
		unionid = sp.getString("unionid", null);
		// Log.e("ms我的unionID是：", unionid);
		/*
		 * for (int i = 0; i < nowlist.size(); i++) { String nowtime =
		 * nowlist.get(i);
		 * 
		 * if (nowtmString.equals(nowtime)) { huogoumoney =
		 * buybackpricelist.get(i); Log.e("huogoumoney", huogoumoney+""); }
		 * 
		 * }
		 */

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.frag_trade_service;
	}

	// 获取到数据后要更新ui
	/*
	 * Handler mHandler = new Handler() {
	 * 
	 * public void handleMessage(android.os.Message msg) { switch (msg.what) {
	 * case 1: UpdateUI(); break;
	 * 
	 * default: break; }
	 * 
	 * };
	 * 
	 * };
	 */
	private InputStream iStream;

	@Override
	public void initData() {
		// 获取系统当前时间

		/*
		 * Date date = new Date(); DateFormat format = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String nowtime =
		 * format.format(date); nowtmString = nowtime.substring(0, 10);
		 * Log.e("nowtime", nowtmString);
		 */
		// TODO Auto-generated method stub
		fragmentsList1 = new ArrayList<Fragment>();
		fragmentsList1.add(new NowTradeRecoedFragment());
		fragmentsList1.add(new EveryDayTradeRecordFragment());
		fragmentsList2 = new ArrayList<Fragment>();
		fragmentsList2.add(new SaleChartFragment());
		fragmentsList2.add(new BuyChartFragment());
		trade_pager.setAdapter(new MypagerAdapter(getSupportFragmentManager(),
				fragmentsList1));
		buy_sale_pager.setAdapter(new MyBuySalepagerAdapter(
				getSupportFragmentManager(), fragmentsList2));
		tabs.setViewPager(trade_pager);
		tabs_buysale.setViewPager(buy_sale_pager);
		tabs.setShouldExpand(true);
		tabs_buysale.setShouldExpand(true);

		// 获取数据

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stopThread) {

					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
							+ unionid + "&dgid=" + dgid;

					try {
						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");

						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("ssssssssss", jsonObject.toString());
							parseJson(jsonObject);
							// Thread.sleep(60000);
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
			}
		}).start();

	}

	protected void parseJson(JSONObject jsonObject) {
		try {
			// 用户手机号
			String phonenum = jsonObject.getString("mobile");
			// Log.e("mobile", phonenum);
			sp.edit().putString("mobile", phonenum).commit();

			income = jsonObject.getDouble("income");
			// 回购价

			buybackprice = jsonObject.getDouble("buybackprice");
			Log.e("buybackprice", buybackprice + "");
			// sp.edit().putFloat("jiubi", (float) (income/100)).commit();
			String incomeing = (income / 100) + "";
			sp.edit().putString("jiubi", incomeing).commit();
			tradeprice = jsonObject.getDouble("realprice");
			String dealdatajson = jsonObject.getString("dealdata");
			jsonObject2 = new JSONObject(dealdatajson);
			trans = jsonObject2.getInt("buybacknum");
			tihuo = jsonObject2.getInt("consumenum");
			buygooding = jsonObject2.getInt("getnum");
			salgooding = jsonObject2.getInt("putnum");
			leftgoodassets = jsonObject2.getInt("stock");
			xia = jsonObject2.getString("downnum");
			ddid = jsonObject2.getString("ddid");

			// 认购
			rengou = jsonObject2.getInt("subnum");
			dealnum = jsonObject2.getInt("dealnum");
			totalbuy = jsonObject2.getDouble("buyintotal");
			totaloutmoney = jsonObject2.getDouble("buyouttotal");
			downaward = jsonObject2.getDouble("downaward");
			// 今日涨跌

			jsonArraylist = jsonObject.getJSONArray("todaydeal");

			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
			// Log.e("shunshun", tradeprice + "");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void UpdateUI() {
		df = new DecimalFormat("#0.00");

		realpri.setText(df.format((tradeprice / 100)));
		jiubi.setText(df.format((income / 100)));
		totalassets = leftgoodassets + buygooding;
		xiaji.setText(xia);
		total_assets.setText((leftgoodassets + salgooding) + "");
		left_assets.setText(leftgoodassets + "");
		buying.setText(buygooding + "");
		saling.setText(salgooding + "");
		dealed.setText(dealnum + "");
		reward.setText(df.format((downaward / 100)));
		// tihuo,trans
		taked_goods.setText(tihuo + "");
		transed.setText(trans + "");

		/*
		 * double shouyi = (tradeprice * (leftgoodassets + salgooding) +
		 * totaloutmoney - totalbuy) / 100;
		 */
		total_shouyi.setText(df
				.format((tradeprice * (leftgoodassets + salgooding)
						+ totaloutmoney - totalbuy) / 100));
		// 今日涨跌
		try {
			JSONObject object = (JSONObject) jsonArraylist.get(0);
			double firstprice = object.getInt("price");
			double today_zd = tradeprice - firstprice;
			total_zd.setText(df.format(today_zd / 100));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 交易曲线图适配器
	public class MypagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragmentsList1;

		public MypagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentsList1 = fragments;
		}

		public MypagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		private final String[] titles1 = { "实时", "每天" };

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles1[position];
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentsList1.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles1.length;
		}

	}

	// 买入卖出图标适配器
	public class MyBuySalepagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragmentsList2;

		public MyBuySalepagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentsList2 = fragments;
		}

		public MyBuySalepagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		private final String[] titles2 = { "卖出", "买入" };

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles2[position];
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentsList2.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles2.length;
		}

	}

	private static TradeServiceActivity instance;
	private ImageView product_ordsubmit_count_sub;
	private ImageView product_ordsubmit_count_add;
	private TextView product_ordsubmit_count;
	private EditText product_ordsubmit_price;
	private TextView product_total_price;
	private Button dialog_ok;
	private Button diaog_cancel;
	private int count_buy = 1;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.tv_back:
			finish();
			break;

		case R.id.rb_buy_service:
			// 获取系统当前时间
              ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			
			NetworkInfo info=connectivityManager.getActiveNetworkInfo();
			if(info!=null&&info.isAvailable()){
				isaliv=true;
				
				
			}else{
				isaliv=false;
				
				
			}
			if(isaliv==true){
				
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				
				DateTest dateTest = new DateTest();
				boolean flag = dateTest.isNowDate(date,cal);
				if (flag == true) {
					// 符合交易时间
					showBuyDialog();

				} else {
					LayoutInflater inflater = LayoutInflater.from(this);
					View view = (View) inflater.inflate(R.layout.timeout_service,
							null);
					final AlertDialog.Builder builder = new AlertDialog.Builder(
							this);
					builder.setView(view);
					builder.setCancelable(false);
					dialog_NO = builder.show();

					RelativeLayout haode = (RelativeLayout) view
							.findViewById(R.id.haode);
					haode.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) { // TODO Auto-generated
							dialog_NO.dismiss();
						}
					});

				}
				
				
			}else{
				
				Toast.makeText(this, "无网络连接", 0).show();
				
				
			}
			
			
			

			break;
		case R.id.rb_sale_service:

			  ConnectivityManager connectivityManager_sale=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				
				NetworkInfo info_sale=connectivityManager_sale.getActiveNetworkInfo();
				if(info_sale!=null&&info_sale.isAvailable()){
					isaliv=true;
					
					
				}else{
					isaliv=false;
					
					
				}
			if(isaliv==true){
				DateTest dateT = new DateTest();
				Date date2 = new Date();
				Calendar cal1 = Calendar.getInstance();
				
			
				boolean flag1 = dateT.isNowDate(date2,cal1);
				if (flag1 == true) {
					// 符合交易时间
					if (leftgoodassets > 0) {
						showSaleDialog();
					} else {
						Toast.makeText(getApplicationContext(), "当前可卖出资产的数量为0", 0)
								.show();
					}

				} else {
					LayoutInflater inflater = LayoutInflater.from(this);
					View view = (View) inflater.inflate(R.layout.timeout_service,
							null);
					final AlertDialog builder = new AlertDialog.Builder(this)
							.create();
					builder.setView(view, 0, 0, 0, 0);
					builder.setCancelable(false);
					builder.show();

					RelativeLayout haode = (RelativeLayout) view
							.findViewById(R.id.haode);
					haode.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) { // TODO Auto-generated
							builder.dismiss();
						}
					});

				}
				
				
			}else{
				
				Toast.makeText(this, "无网络连接", 0).show();
				
				
			}
			
			

			break;

		case R.id.rb_tihuo_service:
			if (leftgoodassets > 0) {
				showTihuoDialog();
			} else {
				Toast.makeText(getApplicationContext(), "当前可提货资产的数量为0", 0)
						.show();
			}

			break;

		case R.id.rb_zhuanrang_service:
			  ConnectivityManager connectivityManager_trans=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				
				NetworkInfo info_trans=connectivityManager_trans.getActiveNetworkInfo();
				if(info_trans!=null&&info_trans.isAvailable()){
					isaliv=true;
					
					
				}else{
					isaliv=false;
					
					
				}
			if(isaliv==true){
				
				DateTest datet = new DateTest();
				Date date3 = new Date();
				Calendar cal2 = Calendar.getInstance();
				
				
				boolean flag2 = datet.isNowDate(date3,cal2);
				if (flag2 == true) {
					// 符合交易时间
					if (leftgoodassets > 0) {
						shouTransDialog();
					} else {
						Toast.makeText(getApplicationContext(), "当前可转让资产的数量为0", 0)
								.show();
					}

				} else {
					LayoutInflater inflater = LayoutInflater.from(this);
					View view = (View) inflater.inflate(R.layout.timeout_service,
							null);
					final AlertDialog builder = new AlertDialog.Builder(this)
							.create();
					builder.setView(view, 0, 0, 0, 0);
					builder.setCancelable(false);
					builder.show();

					RelativeLayout haode = (RelativeLayout) view
							.findViewById(R.id.haode);
					haode.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) { // TODO Auto-generated
							builder.dismiss();
						}
					});

				}
				
			}else{
				Toast.makeText(this, "无网络连接", 0).show();
				
				
			}
			
			

			break;
		// 个人资产
		case R.id.person_assets:
			Intent intent = new Intent(TradeServiceActivity.this,
					TradeRecordActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	int trans_count;

	// 转让
	private void shouTransDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View transView = (View) inflater.inflate(R.layout.trans_service, null);
		transTextView = (TextView) transView
				.findViewById(R.id.product_ordsubmit_count);
		transOk = (Button) transView.findViewById(R.id.dialog_ok);
		transCancel = (Button) transView.findViewById(R.id.dialog_cancel);
		trans_price = (TextView) transView.findViewById(R.id.trans_price);
		trans_price.setText((buybackprice / 100) + "");
		transAdd = (ImageView) transView
				.findViewById(R.id.product_ordsubmit_count_add);
		transReduce = (ImageView) transView
				.findViewById(R.id.product_ordsubmit_count_sub);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(transView);
		builder.setCancelable(false);
		dialog1 = builder.show();
		transAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				trans_count++;
				transTextView.setText("" + trans_count);
			}
		});
		transReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (trans_count > 1) {
					trans_count--;
					transTextView.setText("" + trans_count);
				} else {
					trans_count = 1;
				}

			}
		});
		transOk.setOnClickListener(new OnClickListener() {

			private int trans_num;
			private String totalprice;
			private Dialog dialog_wait_trans;

			@Override
			public void onClick(View v) {

				trans_num = Integer.parseInt(transTextView.getText().toString()
						.trim());
				double total_price = trans_num * buybackprice;
				totalprice = total_price + "";
				if (trans_num <= leftgoodassets) {
					rb_zhuanrang_service.setEnabled(false);
					if (TradeServiceActivity.isSingle()) {
						Toast.makeText(getApplicationContext(), "操作频繁", 0)
								.show();
					} else {
						
						new Thread(new Runnable() {

							private InputStream iStream;

							@Override
							public void run() {

								String url = "https://www.4001149114.com/NLJJ/ddapp/dealbuyback?"
										+ "&ddid="
										+ ddid
										+ "&num="
										+ trans_num
										+ "&price=" + buybackprice;
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
										parseJson_trans(infojson);
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

							private void parseJson_trans(String infojson) {
								// TODO Auto-generated method stub
								try {
									JSONObject jsonObject = new JSONObject(
											infojson);
									String success = jsonObject
											.getString("message");
									if ("success".equals(success)) {
										// 转让成功
										handler.sendEmptyMessage(5);

									}
									if ("error".equals(success)) {
										handler.sendEmptyMessage(6);
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}).start();
					}

				} else {
					Toast.makeText(getApplicationContext(), "可转让的资产不能大于剩余资产", 0)
							.show();
				}

			}
		});
		transCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog1.dismiss();
				trans_count = 1;
			}
		});

	}

	// 提货
	int tihuo_count;

	private void showTihuoDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View tihuoView = (View) inflater.inflate(R.layout.tihuo_service, null);
		tihuoTextView = (TextView) tihuoView
				.findViewById(R.id.product_ordsubmit_count);
		tihuoAdd = (ImageView) tihuoView
				.findViewById(R.id.product_ordsubmit_count_add);
		tihuoreduce = (ImageView) tihuoView
				.findViewById(R.id.product_ordsubmit_count_sub);
		tihuoOk = (Button) tihuoView.findViewById(R.id.dialog_ok);

		tihuocancel = (Button) tihuoView.findViewById(R.id.dialog_cancel);
		builder2 = new AlertDialog.Builder(this);
		builder2.setView(tihuoView);
		builder2.setCancelable(false);
		dialog0 = builder2.show();
		tihuo_count=1;
		tihuoAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tihuo_count++;
				tihuoTextView.setText("" + tihuo_count);
			}
		});
		tihuoreduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tihuo_count > 1) {
					tihuo_count--;
					tihuoTextView.setText("" + tihuo_count);
				} else {
					tihuo_count = 1;
				}
			}
		});

		tihuoOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num_ti = Integer.parseInt(tihuoTextView.getText()
						.toString().trim());
				rb_tihuo_service.setEnabled(false);
				// 总资产
				if (num_ti <= leftgoodassets) {

					dialog0.dismiss();
					Intent intent = new Intent(TradeServiceActivity.this,
							Rengou_detai_tihuolActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("num", num_ti + "");
					bundle.putString("ddid", ddid);
					intent.putExtras(bundle);
					startActivity(intent);

				} else {
					Toast.makeText(getApplicationContext(), "提货的数量不能大于剩余资产", 0)
							.show();
				}
				rb_tihuo_service.setEnabled(true);
			}
		});
		tihuocancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog0.dismiss();
				tihuo_count = 1;
			}
		});

	}

	int sale_count = 1;
	private boolean salefirstclick = false;

	// 卖出按钮
	private void showSaleDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View saleView = (View) inflater.inflate(R.layout.sale_service, null);
		product_ordsubmit_count2 = (TextView) saleView
				.findViewById(R.id.product_ordsubmit_count);
		zhi_price = (TextView) saleView.findViewById(R.id.zhi_price);
		zhi_price.setText(df.format(tradeprice / 100));

		// 卖出价
		salePrice = (EditText) saleView
				.findViewById(R.id.product_ordsubmit_price);
		// 加号
		add = (ImageView) saleView
				.findViewById(R.id.product_ordsubmit_count_add);
		// 减号
		reduce = (ImageView) saleView
				.findViewById(R.id.product_ordsubmit_count_sub);
		ok = (Button) saleView.findViewById(R.id.dialog_ok);
		cancel = (Button) saleView.findViewById(R.id.dialog_cancel);
		AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
		builder3.setView(saleView);
		builder3.setCancelable(false);
		dialog = builder3.show();

		ok.setOnClickListener(new OnClickListener() {

			private String count;
			private int count_sale;
			private String totalp;
			private long secondTime;
			private long firstTime;
			private String pricesale;
			private Dialog dialog_wait_sale;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				pricesale = salePrice.getText().toString().trim();
				count = product_ordsubmit_count2.getText().toString().trim();
				count_sale = Integer.parseInt(count);
				
				

				if (!TextUtils.isEmpty(pricesale)) {
					double sale_price = Double.parseDouble(pricesale);
					double total_price = sale_price * count_sale;
					totalp = total_price + "";
					if (sale_price < ((tradeprice / 100) * 0.9)) {
						Toast.makeText(getApplicationContext(),
								"卖出价不能小于:" + (tradeprice / 100) * 0.9, 0)
								.show();
					} else if (sale_price > ((tradeprice / 100) * 1.1)) {
						Toast.makeText(getApplicationContext(),
								"卖出价不能大于:" + (tradeprice / 100) * 1.1, 0)
								.show();
					} else {
						if (leftgoodassets >= count_sale) {
							if (TradeServiceActivity.isSingle()) {
								Toast.makeText(getApplicationContext(), "操作频繁",
										0).show();
							} else {
								
								new Thread(new Runnable() {

									private InputStream iStream;

									@Override
									public void run() {

										String url = "https://www.4001149114.com/NLJJ/ddapp/dealput?"
												+ "&ddid="
												+ ddid
												+ "&num="
												+ count_sale
												+ "&price="
												+ pricesale;
										try {
											HttpsURLConnection connection = NetUtils
													.httpsconnNoparm(url,
															"POST");
											int code = connection
													.getResponseCode();
											if (code == 200) {
												iStream = connection
														.getInputStream();
												String infojson = NetUtils
														.readString(iStream);
												parseJson_sale(infojson);
											
											}

										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} finally {
											if (iStream != null) {
												try {
													iStream.close();
												} catch (IOException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}

										}

									}

									private void parseJson_sale(String infojson) {
										// TODO Auto-generated method stub
										try {
											JSONObject jsonObject = new JSONObject(
													infojson);
											String s = jsonObject
													.getString("message");
											
											if ("success".equals(s)) {
												// 卖出成功
												handler.sendEmptyMessage(7);
											}
											if ("error".equals(s)) {
												handler.sendEmptyMessage(8);
											}

										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								}).start();

							}
						} else {
							Toast.makeText(getApplicationContext(),
									"卖出资产不能大于剩余资产", 0).show();
						}

					}

				} else {

					Toast.makeText(getApplicationContext(), "卖出价不能为空", 0)
							.show();
				}

			}
		});

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sale_count++;
				product_ordsubmit_count2.setText("" + sale_count);

			}
		});
		reduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sale_count > 1) {
					sale_count--;
					product_ordsubmit_count2.setText("" + sale_count);
				} else {
					sale_count = 1;
				}

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				sale_count = 1;
			}
		});

	}

	// 买入按钮
	private void showBuyDialog() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = LayoutInflater.from(this);
		 View view = (View) inflater.inflate(R.layout.buy_service, null);
		// 增加
		product_ordsubmit_count_sub = (ImageView) view
				.findViewById(R.id.product_ordsubmit_count_sub);
		// 减少
		product_ordsubmit_count_add = (ImageView) view
				.findViewById(R.id.product_ordsubmit_count_add);
		// 数量
		product_ordsubmit_count = (TextView) view
				.findViewById(R.id.product_ordsubmit_count);
		// 指导价
		TextView zhidaojia = (TextView) view.findViewById(R.id.zhidaojia);
		zhidaojia.setText(df.format(tradeprice / 100));

		// 买入价格
		product_ordsubmit_price = (EditText) view
				.findViewById(R.id.product_ordsubmit_price);
		product_ordsubmit_count.addTextChangedListener(textWatcher);
		product_ordsubmit_price.addTextChangedListener(textWatcher);

		// 总价钱
		product_total_price = (TextView) view
				.findViewById(R.id.product_total_price);

		dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
		diaog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setView(view);
		builder1.setCancelable(false);
		dialog_buy = builder1.show();

		// 取消按钮
		diaog_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_buy.dismiss();
				count_buy = 1;
			}
		});
		// 确定按钮
		dialog_ok.setOnClickListener(new OnClickListener() {

			private String num_buy;
			private String total_price;
			private String buy_priceString;
			private Dialog dialog_wait;

			@Override
			public void onClick(View v) {
                    
				
				
				num_buy = product_ordsubmit_count.getText().toString().trim();
				total_price = product_total_price.getText().toString().trim();
				buy_priceString = product_ordsubmit_price.getText().toString()
						.trim();
				double total_price_double = Double.parseDouble(total_price);

				if (!TextUtils.isEmpty(buy_priceString)) {
					double buyprice = Double.parseDouble(buy_priceString);
					if (buyprice >= (buybackprice / 100)) {

						if ((income / 100) >= total_price_double) {
							
							if (TradeServiceActivity.isSingle()) {
								Toast.makeText(getApplicationContext(), "操作频繁",
										0).show();
							} else {
								
								new Thread(new Runnable() {

									private InputStream iStream;

									@Override
									public void run() {

										String url = "https://www.4001149114.com/NLJJ/ddapp/dealbuy?"
												+ "&ddid="
												+ ddid
												+ "&num="
												+ num_buy
												+ "&price="
												+ buy_priceString;
										try {
											HttpsURLConnection connection = NetUtils
													.httpsconnNoparm(url,
															"POST");
											int code = connection
													.getResponseCode();
											if (code == 200) {
												iStream = connection
														.getInputStream();
												String infojson = NetUtils
														.readString(iStream);
												// JSONObject jsonObject = new
												// JSONObject(infojson);
												// Log.e("我靠快快快快快快快", infojson);
												handler.sendEmptyMessage(3);
												// Log.e("hahahhahh", infojson);
												// parseJson_buy(infojson);

												// Log.e("sssssssssss",
												// "hahah");
											}

										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} finally {
											if (iStream != null) {
												try {
													iStream.close();
												} catch (IOException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}

										}

									}

								}).start();

							}

						} else {
							// 酒币不够
							Toast.makeText(getApplicationContext(), "酒币不够，请充值",
									0).show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"买入价不能低于回购价:" + buybackprice / 100, 0).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "请输入买入价", 0).show();
				}
			}
		});

		product_ordsubmit_count_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				count_buy++;
				product_ordsubmit_count.addTextChangedListener(textWatcher);
				product_ordsubmit_count.setText(count_buy + "");

			}

		});
		product_ordsubmit_count_sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (count_buy > 1) {

					count_buy--;
					product_ordsubmit_count.addTextChangedListener(textWatcher);

					product_ordsubmit_count.setText(count_buy + "");

				} else {
					count_buy = 1;
					Toast.makeText(getApplicationContext(), "最小认购量不能小于1", 0)
							.show();
				}

			}
		});

	}

	// 买入价监听
	private TextWatcher textWatcher = new TextWatcher() {
		private CharSequence charSequence;
		private String price;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

			price = product_ordsubmit_price.getText().toString().trim();
			if (!TextUtils.isEmpty(price)) {

				product_total_price.setText(""
						+ Arith.mul(Double.parseDouble(price), count_buy));
			} else {

				product_total_price.setText("0.00");
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

	private Dialog dialog2;
	private EditText salePrice;
	private ImageView add;
	private ImageView reduce;
	private Button ok;
	private Button cancel;
	private Dialog dialog;
	private TextView product_ordsubmit_count2;
	private TextView tihuoTextView;
	private ImageView tihuoAdd;
	private ImageView tihuoreduce;
	private Button tihuoOk;
	private Button tihuocancel;
	private Dialog dialog0;
	private TextView transTextView;
	private Button transOk;
	private Button transCancel;
	private Dialog dialog1;
	private ImageView transAdd;
	private ImageView transReduce;
	private String unionid;
	private String dgid;
	private double income;
	private double tradeprice;
	private int trans;
	private int tihuo;
	private int buygooding;
	private int salgooding;
	private int leftgoodassets;
	private int rengou;
	private int dealnum;
	private double totalbuy;
	private double totaloutmoney;
	private double downaward;

	private int totalassets;

	private JSONObject jsonObject2;

	private JSONArray jsonArraylist;

	private DecimalFormat df;

	private String xia;

	private String ddid;

	private AlertDialog builder1;

	private String nowtmString;

	private ArrayList<Float> buybackpricelist;

	private ArrayList<String> nowlist;

	private double huogoumoney;

	private double buybackprice;

	private AlertDialog.Builder builder2;

	private TextView trans_price;

	private TextView zhi_price;

	private Dialog dialog_buy;

	private Dialog dialog_NO;

	private static long firstTime;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopThread = true;
		super.onDestroy();
		Log.e("onDestroy_stopThread", stopThread + "");
		handler.removeMessages(1);
		handler.removeMessages(2);
		handler.removeMessages(3);
		handler.removeMessages(4);
		handler.removeMessages(6);
		handler.removeMessages(7);
		handler.removeMessages(8);
		// handler.removeMessages(9);
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