package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.activity.LoginActivity;
import zz.itcast.jiujinhui.activity.TradeServiceActivity;
import zz.itcast.jiujinhui.activity.WoyaorengouActivity;
import zz.itcast.jiujinhui.activity.ZongZiChanActivity;
import zz.itcast.jiujinhui.bean.MinutesBean;
import zz.itcast.jiujinhui.res.NetUtils;
import zz.itcast.jiujinhui.res.OurApplication;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TradeFragment extends BaseFragment {

	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;

	@ViewInject(R.id.ll_content)
	private LinearLayout ll_content;

	private final int[] imageIds = { R.drawable.a, R.drawable.b, R.drawable.c, };
	private TextView tv_rate;
	private TextView tv_name;
	private TextView tv_dealcode;
	private TextView tv_stock;
	private TextView tv_dealterm;
	private TextView btn_name;
	private TextView tv_lirengou;
	private TextView tv_tian;
	private SharedPreferences sp;
	private Dialog loading_dialog = null;

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
		tv_back.setVisibility(view.GONE);
		tv__title.setText("天天涨钱");

		sp = OurApplication.getContext().getSharedPreferences("user", 0);
		loading_dialog = zz.itcast.jiujinhui.res.DialogUtil
				.createLoadingDialog(getActivity(), "加载中...");
	}

	boolean isaliv = true;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// 判断当前页面是否联网
		ConnectivityManager connectivityManager = (ConnectivityManager) OurApplication.getContext()
				.getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);

		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			isaliv = true;

		} else {
			isaliv = false;
			Toast.makeText(OurApplication.getContext(), "无网络连接", Toast.LENGTH_SHORT).show();

		}

	}

	private RelativeLayout btn_public;
	private TextView tv_jin;
	private TextView tv_deaTextView;
	private TextView tv_day;
	private RelativeLayout trading;

	private TextView tv_name2;
	private String dealgoodname;
	private LinearLayout litmit;

	private LinearLayout jinru;

	private JSONObject jsonObject3;
	private JSONArray dealgoodslist;
	private TextView dealcode;

	private String maingoodstate;
	private int length;

	private TextView lijin;
	// 首页轮播的界面
	private List<String> vp_ImgUrls;

	private zz.itcast.jiujinhui.adapter.HomeFragPagerAdapter adapterViewPager;
	private boolean isPlaying;
	private int currPosition;
	private int lastPosition;
	// 轮播图
	@ViewInject(R.id.ll_viewpager_home_frags)
	private LinearLayout ll_viewpager_home_frags;
	@ViewInject(R.id.vp_home_fragment)
	private ViewPager vp_home_fragment;

	// 跑马灯
	// @ViewInject(R.id.TextViewNotice)
	// private zz.itcast.jiujinhui.view.AutoScrollTextView autoScrollTextView;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				loading_dialog.dismiss();
				initViewPager();
				initViewPagerlistener();
				

				UpdateUI();
				
				break;
			case 1:
				updateViewPager();
				updateIndicatior();

				break;

			default:
				break;
			}

		}
	};
	
	
	private void initViewPager() {
		// TODO Auto-generated method stub

		initIndicator();
		// adapterViewPager.notifyDataSetChanged();
		// 设置初始显示条目
		vp_home_fragment.setCurrentItem(0);
		lastPosition = 0;
		isPlaying = true;
		handler.sendEmptyMessageDelayed(1, 2000);

	}

	private void initIndicator() {
		// TODO Auto-generated method stub
		for (int i = 0; i < vp_ImgUrls.size(); i++) {
			ImageView iv_indicator = new ImageView(OurApplication.getContext());
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, -2);
			layoutParams.leftMargin = zz.itcast.jiujinhui.res.DensityUtil
					.dip2px(OurApplication.getContext(), 20);
			if (i == 0) {
				iv_indicator.setImageResource(R.drawable.slide_adv_selected);
			} else {
				iv_indicator.setImageResource(R.drawable.slide_adv_normal);
			}
			ll_viewpager_home_frags.addView(iv_indicator, layoutParams);

		}

	}

	private void initViewPagerlistener() {
		// TODO Auto-generated method stub
		adapterViewPager = new zz.itcast.jiujinhui.adapter.HomeFragPagerAdapter(
				OurApplication.getContext(), vp_ImgUrls);
		vp_home_fragment.setAdapter(adapterViewPager);
		// 设置页面改变的监听
		vp_home_fragment
				.setOnPageChangeListener(new OnViewPagerPageChangeListener());

	}

	private class OnViewPagerPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			// 页面改变,更新当前条目,并更新指示器
			currPosition = vp_home_fragment.getCurrentItem();
			updateIndicatior();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	
	private LayoutInflater inflater;
	private double maingooddealprice;
	private RelativeLayout ll_ren;
	private TextView left;
	private String maindealterm;
	private String maindealcode;
	private String mainstock;
	private String mainrate;
	private String mainname;
	private TextView reprice;
	private RelativeLayout jiaoyizhong;
	private JSONObject jsonObject2;
	private String opentime;
	boolean stopThread = false;
	private String maindgid;
	private TextView tv_rate2;
	private DecimalFormat df;
	
	private String goodstate;

	@Override
	public void initData() {
		// 跑马灯
		/*
		 * autoScrollTextView.init(getActivity().getWindowManager());
		 * autoScrollTextView.startScroll();
		 */
		inflater = (LayoutInflater) OurApplication.getContext().getSystemService(
				OurApplication.getContext().LAYOUT_INFLATER_SERVICE);

		new Thread(new Runnable() {

			private InputStream is;

			@Override
			public void run() {

				try {
					String urlpath = "https://www.4001149114.com/NLJJ/ddapp/ttzqlist";
					HttpsURLConnection conn = NetUtils.httpsconnNoparm(urlpath,
							"GET");
					// 若连接服务器成功，返回数据
					int code = conn.getResponseCode();
					if (code == 200) {

						is = conn.getInputStream();
						String json = NetUtils.readString(is);
						// 解析json
						parsonJson(json);
						//is.close();
					}

				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}).start();
	}

	protected void updateViewPager() {
		// TODO Auto-generated method stub
		if (isPlaying) {
			currPosition = (vp_home_fragment.getCurrentItem() + 1)
					% vp_ImgUrls.size();
			vp_home_fragment.setCurrentItem(currPosition);
			updateIndicatior();
			handler.removeMessages(1);
			handler.sendEmptyMessageDelayed(1, 4000);
		}

	}

	private void updateIndicatior() {
		// TODO Auto-generated method stub
		ImageView lastIndicatior = (ImageView) ll_viewpager_home_frags
				.getChildAt(lastPosition);
		ImageView currIndicatior = (ImageView) ll_viewpager_home_frags
				.getChildAt(currPosition);
		lastIndicatior.setImageResource(R.drawable.slide_adv_normal);
		currIndicatior.setImageResource(R.drawable.slide_adv_selected);
		lastPosition = currPosition;
	}

	private void parsonJson(String json) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(json);
			// System.err.println(jsonObject.toString());
			// 判断json是否传输成功
			String success = jsonObject.getString("message");
			// Log.e("sss", "是否成功:" + success);
			// 置顶的酒金窖
			String maindealgood = jsonObject.getString("maindealgood");
			jsonObject2 = new JSONObject(maindealgood);

			mainname = jsonObject2.getString("name");
			opentime = jsonObject2.getString("subscribetime");
			// maindgid = jsonObject2.getString("dgid");
			// Log.e("vv", jsonObject2.getString("name"));
			maingooddealprice = jsonObject2.getDouble("realprice");
			maindealcode = jsonObject2.getString("dealcode");

			mainstock = jsonObject2.getString("stock");
			// 轮播图

			String wxapp = jsonObject.getString("wxapp");
			JSONObject jsonWxapp = new JSONObject(wxapp);

			String url1 = jsonWxapp.getString("jjimg1");
			String url2 = jsonWxapp.getString("jjimg2");
			String url3 = jsonWxapp.getString("jjimg3");

			String urlimg1 = "https://www.4001149114.com/NLJJ/resources/image/wxapps/"
					+ url1;
			String urlimg2 = "https://www.4001149114.com/NLJJ/resources/image/wxapps/"
					+ url2;
			String urlimg3 = "https://www.4001149114.com/NLJJ/resources/image/wxapps/"
					+ url3;

			vp_ImgUrls = new ArrayList<String>();

			vp_ImgUrls.add(urlimg1);
			vp_ImgUrls.add(urlimg2);
			vp_ImgUrls.add(urlimg3);

			double mainrateint = jsonObject2.getDouble("rate");
			DecimalFormat df = new DecimalFormat("#0.0");
			mainrate = df.format(mainrateint);

			maingoodstate = jsonObject2.getString("state");

			maindealterm = jsonObject2.getString("dealterm");

			dealgoodslist = jsonObject.getJSONArray("dealgoods");
			length = dealgoodslist.length();
			Message message = new Message();
			message.what = 0;
			handler.sendMessage(message);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	
	private void UpdateUI() {
		//节假日停盘
		if ("4".equals(maingoodstate)) {
			View view = inflater.inflate(R.layout.trade_item_jiujiao, null);
			ll_content.addView(view);
			btn_public = (RelativeLayout) view.findViewById(R.id.btn_public);
			
			LinearLayout ll_tradecode=(LinearLayout) view.findViewById(R.id.ll_tradecode);
			
			ll_tradecode.setVisibility(View.GONE);
			
			LinearLayout limit=(LinearLayout) view.findViewById(R.id.limit);
			
			limit.setVisibility(View.GONE);
			
			reprice = (TextView) view.findViewById(R.id.realprice_chengjiao);
			ll_ren = (RelativeLayout) view.findViewById(R.id.rengouqi);
			ll_ren.setVisibility(View.GONE);
			left = (TextView) view.findViewById(R.id.left_day);
			tv_rate = (TextView) view.findViewById(R.id.rate);
			tv_name = (TextView) view.findViewById(R.id.name);
			tv_dealcode = (TextView) view.findViewById(R.id.dealcode);
			tv_stock = (TextView) view.findViewById(R.id.stock);
			
			TextView tv_msg_stop=(TextView) view.findViewById(R.id.tv_msg_stop);
			
			tv_msg_stop.setVisibility(View.VISIBLE);
			left.setText(maindealterm);
			jiaoyizhong = (RelativeLayout) view.findViewById(R.id.jiaoyizhong);
			tv_tian = (TextView) view.findViewById(R.id.term_day);
			btn_name = (TextView) view.findViewById(R.id.btn_name);
			tv_rate.setText(mainrate);
			tv_name.setText(mainname);
			tv_dealcode.setText(maindealcode);
			tv_stock.setText(mainstock);
			btn_name.setText("节假日停盘");
			btn_name.setTextSize(18);

			// tv_lirengou.setTextColor(R.color.red);
			DecimalFormat df = new DecimalFormat("#0.00");
			// Log.e("maingooddealprice", "hhhjh");
			reprice.setText(df.format(maingooddealprice / 100));
			reprice.setTextSize(15);
			// tv_dealterm.setTextColor(R.color.red);
			jiaoyizhong.setVisibility(view.GONE);

			btn_public.setVisibility(View.VISIBLE);
			// tv_tian.setTextColor(R.color.red);

			btn_public.setOnClickListener(new OnClickListener() {

			

				@Override
				public void onClick(View v) {
					ConnectivityManager connectivityManager = (ConnectivityManager)OurApplication.getContext()
							.getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);

					NetworkInfo info = connectivityManager
							.getActiveNetworkInfo();
					if (info != null && info.isAvailable()) {
						isaliv = true;

					} else {
						isaliv = false;
						Toast.makeText(OurApplication.getContext(), "无网络连接",
								Toast.LENGTH_SHORT).show();

					}

					if (isaliv == true) {
						Toast.makeText(OurApplication.getContext(), "节假日停盘",
								Toast.LENGTH_SHORT).show();
						
					} else {

						Toast.makeText(OurApplication.getContext(), "无网络连接",
								Toast.LENGTH_SHORT).show();

					}

				}

			});

		}
		
		
		
		
		
		if ("3".equals(maingoodstate)) {
			View view = inflater.inflate(R.layout.trade_item_jiujiao, null);
			ll_content.addView(view);
			btn_public = (RelativeLayout) view.findViewById(R.id.btn_public);
			tv_rate = (TextView) view.findViewById(R.id.rate);
			tv_name = (TextView) view.findViewById(R.id.name);
			tv_dealcode = (TextView) view.findViewById(R.id.dealcode);
			tv_stock = (TextView) view.findViewById(R.id.stock);
			tv_lirengou = (TextView) view.findViewById(R.id.li);
			tv_dealterm = (TextView) view
					.findViewById(R.id.realprice_chengjiao);
			tv_tian = (TextView) view.findViewById(R.id.term_day);
			btn_name = (TextView) view.findViewById(R.id.btn_name);
			tv_rate.setText(mainrate);
			tv_name.setText(mainname);
			tv_dealcode.setText(maindealcode);
			tv_stock.setText(mainstock);
			btn_public.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					/*
					 * LayoutInflater inflater = LayoutInflater
					 * .from(getActivity());
					 */
					View openview = inflater.inflate(R.layout.open_trade, null);

					final AlertDialog builder = new AlertDialog.Builder(
							OurApplication.getContext()).create();
					builder.setView(openview, 0, 0, 0, 0);
					builder.setCancelable(false);
					builder.show();

					RelativeLayout okLayout = (RelativeLayout) openview
							.findViewById(R.id.dialog_ok);
					TextView opentimeview = (TextView) openview
							.findViewById(R.id.opentime);
					opentimeview.setText(opentime);
					okLayout.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							builder.dismiss();
						}
					});

				}
			});
			btn_name.setText("我要认购");
			btn_name.setTextSize(18);
			tv_lirengou.setText("离认购还剩:");
			tv_lirengou.setTextSize(15);
			// tv_lirengou.setTextColor(R.color.red);
			tv_dealterm.setText(maindealterm);
			tv_dealterm.setTextSize(15);
			// tv_dealterm.setTextColor(R.color.red);
			tv_tian.setText("天");
			tv_tian.setTextSize(15);
			btn_public.setVisibility(View.VISIBLE);
			// tv_tian.setTextColor(R.color.red);
		}
		if ("1".equals(maingoodstate)) {
			View view = inflater.inflate(R.layout.trade_item_jiujiao, null);
			ll_content.addView(view);
			btn_public = (RelativeLayout) view.findViewById(R.id.btn_public);
			reprice = (TextView) view.findViewById(R.id.realprice_chengjiao);
			ll_ren = (RelativeLayout) view.findViewById(R.id.rengouqi);
			ll_ren.setVisibility(View.VISIBLE);
			left = (TextView) view.findViewById(R.id.left_day);
			tv_rate = (TextView) view.findViewById(R.id.rate);
			tv_name = (TextView) view.findViewById(R.id.name);
			tv_dealcode = (TextView) view.findViewById(R.id.dealcode);
			tv_stock = (TextView) view.findViewById(R.id.stock);
			// 认购期还剩？天
			left.setText(maindealterm);

			tv_tian = (TextView) view.findViewById(R.id.term_day);
			btn_name = (TextView) view.findViewById(R.id.btn_name);
			tv_rate.setText(mainrate);
			tv_name.setText(mainname);
			tv_dealcode.setText(maindealcode);
			tv_stock.setText(mainstock);
			btn_name.setText("我要认购");
			btn_name.setTextSize(18);

			// tv_lirengou.setTextColor(R.color.red);
			DecimalFormat df = new DecimalFormat("#0.00");
			// Log.e("maingooddealprice", "hhhjh");
			reprice.setText(df.format(maingooddealprice / 100));
			reprice.setTextSize(15);
			// tv_dealterm.setTextColor(R.color.red);

			btn_public.setVisibility(View.VISIBLE);
			btn_public.setOnClickListener(new OnClickListener() {

				private String maingoodname;
				private String dgid;
				private SimpleDateFormat sdf;

				@Override
				public void onClick(View v) {

					try {
						dgid = jsonObject2.getString("dgid");
						maingoodname = jsonObject2.getString("name");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// TODO Auto-generated method stub
					Boolean isLogined = sp.getBoolean("isLogined", false);
					if (isLogined) {
						// 当前时间

						sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// 截止时间
						String endtime;
						try {
							long nowTime = new Date().getTime();
							endtime = jsonObject2.getString("subscribetime");
							long endTime = sdf.parse(endtime).getTime();
							long disTime = endTime - nowTime;
							if (disTime > 0) {

								long hour = disTime / 1000 / 3600;
								long minutes = ((disTime / 1000) - hour * 3600) / 60;
								long seconds = (disTime / 1000) - hour * 3600
										- minutes * 60;

								String showTime = hour + "小时" + minutes + "分钟"
										+ seconds + "秒";

								// TODO Auto-generated method stub

								LayoutInflater inflater = LayoutInflater
										.from(OurApplication.getContext());
								View openview = (View) inflater.inflate(
										R.layout.open_trade_left, null);

								final AlertDialog builder = new AlertDialog.Builder(
										OurApplication.getContext()).create();
								builder.setView(openview, 0, 0, 0, 0);
								builder.setCancelable(false);
								builder.show();

								RelativeLayout okLayout = (RelativeLayout) openview
										.findViewById(R.id.dialog_ok);
								TextView opentimeview = (TextView) openview
										.findViewById(R.id.opentime);
								opentimeview.setText(showTime);
								okLayout.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										builder.dismiss();
									}
								});

							} else {

								Intent intent1 = new Intent(OurApplication.getContext(),
										WoyaorengouActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("name", maingoodname);
								bundle.putString("dgid", dgid);
								bundle.putString("maindealterm", maindealterm);
								intent1.putExtras(bundle);
								startActivity(intent1);

							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						Intent intent = new Intent(OurApplication.getContext(),
								LoginActivity.class);
						startActivity(intent);
					}

				}
			});
			// tv_tian.setTextColor(R.color.red);
		}
		// 交易期
		if ("2".equals(maingoodstate)) {
			View view = inflater.inflate(R.layout.trade_item_jiujiao, null);
			ll_content.addView(view);
			btn_public = (RelativeLayout) view.findViewById(R.id.btn_public);
			reprice = (TextView) view.findViewById(R.id.realprice_chengjiao);
			ll_ren = (RelativeLayout) view.findViewById(R.id.rengouqi);
			ll_ren.setVisibility(View.GONE);
			left = (TextView) view.findViewById(R.id.left_day);
			tv_rate = (TextView) view.findViewById(R.id.rate);
			tv_name = (TextView) view.findViewById(R.id.name);
			tv_dealcode = (TextView) view.findViewById(R.id.dealcode);
			tv_stock = (TextView) view.findViewById(R.id.stock);
			// 认购期还剩？天
			left.setText(maindealterm);
			jiaoyizhong = (RelativeLayout) view.findViewById(R.id.jiaoyizhong);
			tv_tian = (TextView) view.findViewById(R.id.term_day);
			btn_name = (TextView) view.findViewById(R.id.btn_name);
			tv_rate.setText(mainrate);
			tv_name.setText(mainname);
			tv_dealcode.setText(maindealcode);
			tv_stock.setText(mainstock);
			btn_name.setText("进入交易大厅");
			btn_name.setTextSize(18);

			// tv_lirengou.setTextColor(R.color.red);
			DecimalFormat df = new DecimalFormat("#0.00");
			// Log.e("maingooddealprice", "hhhjh");
			reprice.setText(df.format(maingooddealprice / 100));
			reprice.setTextSize(15);
			// tv_dealterm.setTextColor(R.color.red);
			jiaoyizhong.setVisibility(view.VISIBLE);

			btn_public.setVisibility(View.VISIBLE);
			// tv_tian.setTextColor(R.color.red);

			btn_public.setOnClickListener(new OnClickListener() {

				private String maingoodname;
				private String dgid;

				@Override
				public void onClick(View v) {
					ConnectivityManager connectivityManager = (ConnectivityManager)OurApplication.getContext()
							.getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);

					NetworkInfo info = connectivityManager
							.getActiveNetworkInfo();
					if (info != null && info.isAvailable()) {
						isaliv = true;

					} else {
						isaliv = false;
						Toast.makeText(OurApplication.getContext(), "无网络连接",
								Toast.LENGTH_SHORT).show();

					}

					if (isaliv == true) {

						try {
							dgid = jsonObject2.getString("dgid");
							//Log.e("main_dgid", dgid);
							maingoodname = jsonObject2.getString("name");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// TODO Auto-generated method stub
						Boolean isLogined = sp.getBoolean("isLogined", false);
						if (isLogined) {
							Intent intent = new Intent(OurApplication.getContext(),
									TradeServiceActivity.class);
							intent.putExtra("name", maingoodname);
							intent.putExtra("dealdgid", dgid);

							startActivity(intent);
						} else {
							Intent intent = new Intent(OurApplication.getContext(),
									LoginActivity.class);
							startActivity(intent);
						}

					} else {

						Toast.makeText(OurApplication.getContext(), "无网络连接",
								Toast.LENGTH_SHORT).show();

					}

				}

			});

		}

		for (int i = 0; i < length; i++) {
			
			

			try {
				jsonObject3 = dealgoodslist.getJSONObject(i);

				goodstate = jsonObject3.getString("state");
				 
				
				

				View view1 = inflater.inflate(R.layout.trade_item_jiujiao, null);
				ll_content.addView(view1);
				// ll_content.addView(view1);
				tv_name2 = (TextView) view1.findViewById(R.id.name);

				tv_deaTextView = (TextView) view1
						.findViewById(R.id.realprice_chengjiao);
				TextView tv_newprive=(TextView) view1.findViewById(R.id.codetonewprice);
				
				tv_newprive.setText("最新价:¥");
				
	           TextView price_new_tv=(TextView) view1.findViewById(R.id.price_new);
	          
	           price_new_tv.setVisibility(View.VISIBLE);
	          
		
				tv_day = (TextView) view1.findViewById(R.id.term_day);
				tv_rate2 = (TextView) view1.findViewById(R.id.rate);
				
				litmit = (LinearLayout) view1.findViewById(R.id.limit);
				litmit.setVisibility(View.GONE);
				
				trading = (RelativeLayout) view1.findViewById(R.id.jiaoyizhong);
				trading.setVisibility(View.GONE);
	      
				 TextView yuan=(TextView) view1.findViewById(R.id.yuan);
				
				 yuan.setVisibility(View.VISIBLE);
				 
				 
				lijin = (TextView) view1.findViewById(R.id.li);
				lijin.setText("进入交易大厅>>");
				dealcode = (TextView) view1.findViewById(R.id.dealcode);
				
				dealcode.setVisibility(View.GONE);
				tv_deaTextView.setVisibility(View.GONE);
				tv_day.setVisibility(View.GONE);
				RelativeLayout btn_jinru = (RelativeLayout) view1
						.findViewById(R.id.btn_public);
				btn_jinru.setVisibility(View.GONE);
				
				double realprice=jsonObject3.getDouble("realprice");

				df = new DecimalFormat("#0.00");
				//tv_deaTextView.setText(df.format(realprice / 100));
				
				
				   price_new_tv.setText(df.format(realprice / 100));
				
				final String dealgoodname = jsonObject3.getString("name");
			//	Log.e("vr", dealgoodname);
				String goodsdealcode = jsonObject3.getString("dealcode");
				double rate = jsonObject3.getDouble("rate");
 
				
				
				//String nor_rate = df.format(rate);
				tv_rate2.setText(rate+"");

				final String dgid = jsonObject3.getString("dgid");
				sp.edit().putString("dgid", dgid).commit();
				//Log.e("GD", dgid);
				tv_name2.setText(dealgoodname);
				//dealcode.setText(goodsdealcode);
				if ("4".equals(goodstate)) {
					
					
					lijin.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							// TODO Auto-generated method stub

							ConnectivityManager connectivityManager = (ConnectivityManager) OurApplication.getContext()
									.getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);

							NetworkInfo info = connectivityManager
									.getActiveNetworkInfo();
							if (info != null && info.isAvailable()) {
								isaliv = true;

							} else {
								isaliv = false;
								Toast.makeText(OurApplication.getContext(), "无网络连接",
										Toast.LENGTH_SHORT).show();

							}
							if (isaliv == true) {

								Boolean isLogined = sp.getBoolean("isLogined",
										false);
								if (isLogined) {
								
										
										Toast.makeText(OurApplication.getContext(), "节假日停盘",
												Toast.LENGTH_SHORT).show();
										
										
									
									
								
								} else {
									Intent intent = new Intent(OurApplication.getContext(),
											LoginActivity.class);
									startActivity(intent);
								}

							} else {

								Toast.makeText(OurApplication.getContext(), "无网络连接",
										Toast.LENGTH_SHORT).show();

							}

						}

					});
					
					
					
					
				}
				
				
				if("2".equals(goodstate)){
					lijin.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							// TODO Auto-generated method stub

							ConnectivityManager connectivityManager = (ConnectivityManager) OurApplication.getContext()
									.getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);

							NetworkInfo info = connectivityManager
									.getActiveNetworkInfo();
							if (info != null && info.isAvailable()) {
								isaliv = true;

							} else {
								isaliv = false;
								Toast.makeText(OurApplication.getContext(), "无网络连接",
										Toast.LENGTH_SHORT).show();

							}
							if (isaliv == true) {

								Boolean isLogined = sp.getBoolean("isLogined",
										false);
								if (isLogined) {
								
										Intent intent = new Intent(OurApplication.getContext(),
												TradeServiceActivity.class);
										intent.putExtra("name", dealgoodname);
										intent.putExtra("dealdgid", dgid);
										startActivity(intent);
									
									
								
								} else {
									Intent intent = new Intent(OurApplication.getContext(),
											LoginActivity.class);
									startActivity(intent);
								}

							} else {

								Toast.makeText(OurApplication.getContext(), "无网络连接",
										Toast.LENGTH_SHORT).show();

							}

						}

					});
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// tv_jin.setId(i);
			

		}

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.frag_trade;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		handler.removeMessages(0);
		handler.removeMessages(1);
	}

}
