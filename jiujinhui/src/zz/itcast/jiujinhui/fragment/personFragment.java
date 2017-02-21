package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.activity.DrinkRecordActivity;
import zz.itcast.jiujinhui.activity.MyTiXianActivity;
import zz.itcast.jiujinhui.activity.PerInfoActivity;
import zz.itcast.jiujinhui.activity.ReChargeActivity;
import zz.itcast.jiujinhui.activity.SmsNumberActivity;
import zz.itcast.jiujinhui.activity.TiXianRecordActivity;
import zz.itcast.jiujinhui.activity.TradeRecordActivity;
import zz.itcast.jiujinhui.activity.ZongZiChanActivity;
import zz.itcast.jiujinhui.res.DateTest;
import zz.itcast.jiujinhui.res.NetUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;


public class personFragment extends BaseFragment {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.zongzichan)
	private LinearLayout zongzichan;
	@ViewInject(R.id.drink_record)
	private LinearLayout drink_record;
	@ViewInject(R.id.trade_record)
	private LinearLayout trade_record;
	@ViewInject(R.id.tixianRecord)
	private LinearLayout tixianRecord;
	@ViewInject(R.id.tixian)
	private Button tixian;
	@ViewInject(R.id.recharge)
	private Button recharge;
	@ViewInject(R.id.personInfo)
	private LinearLayout personInfo;
	@ViewInject(R.id.NickName)
	private TextView NickName;
	@ViewInject(R.id.rl_jiubi)
	private RelativeLayout rl_jiubi;
	@ViewInject(R.id.person_jiubi)
	private TextView person_jiubi;
	// 圆形图片
	@ViewInject(R.id.circleImabeView)
	private zz.itcast.jiujinhui.view.CircleImageView circleImabeView;

	private SharedPreferences sp;
	private Boolean firstClick_recharge;
	private double income;

  Handler handler=new Handler(){
	  
	  public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
		case 1:
			 person_jiubi.setText(df.format(income/100));
			break;

		default:
			break;
		}
		 
		  
	  };
  };
private String phonenum;
private DecimalFormat df;
boolean stopThread = false;
	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
		tv_back.setVisibility(view.GONE);
		tv__title.setText("个人中心");
		// 微信头像
		sp = getActivity().getSharedPreferences("user", 0);
		String headimgurl = sp.getString("headimg", null);
		Picasso.with(getActivity()).load(headimgurl).into(circleImabeView);
		// 微信昵称
		String nickNameString = sp.getString("nickname", null);
		NickName.setText(nickNameString);
		final String unionString=sp.getString("unionid", null);
		//酒币
	  /* String jiubinum=sp.getString("jiubi", null);
	   if (jiubinum==null) {
		rl_jiubi.setVisibility(view.GONE);
	}else {
 		person_jiubi.setText(jiubinum+"");
	}*/
		
	   new Thread(new Runnable() {

			private InputStream is;

			@Override
			public void run() {
				while (!stopThread) {
					try {
						String urlpath = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
								+ unionString + "&dgid=DG161027140008895";
						HttpsURLConnection conn = NetUtils.httpsconnNoparm(
								urlpath, "GET");
						// 若连接服务器成功，返回数据
						int code = conn.getResponseCode();
						if (code == 200) {

							is = conn.getInputStream();
							String json = NetUtils.readString(is);
							// 解析json
							parsonJson(json);
							stopThread=true;
							is.close();
						}

					} catch (Exception e) {
						// TODO: handle exception
					} finally{
						if (is!=null) {
							try {
								is.close();
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
	
	protected void parsonJson(String json) {
		// TODO Auto-generated method stub
		try {
			df = new DecimalFormat("#0.00");
			JSONObject jsonObject = new JSONObject(json);
			income = jsonObject.getDouble("income");
			Message message=Message.obtain();
			handler.sendEmptyMessage(1);
			sp.edit().putString("income", income+"").commit();
			phonenum = jsonObject.getString("mobile");
			sp.edit().putString("mobile", phonenum).commit();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initData() {
		
		
		
	}


	


	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.frag_person;
	}

	@Override
	public void initListener() {
		zongzichan.setOnClickListener(this);
		drink_record.setOnClickListener(this);
		trade_record.setOnClickListener(this);
		tixianRecord.setOnClickListener(this);
		tixian.setOnClickListener(this);
		recharge.setOnClickListener(this);
		personInfo.setOnClickListener(this);
		

	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zongzichan:// 总资产
			Intent intent0 = new Intent(getActivity(), ZongZiChanActivity.class);
			startActivity(intent0);
			break;
		case R.id.drink_record:// 酒币记录
			Intent intent1 = new Intent(getActivity(),
					DrinkRecordActivity.class);
			startActivity(intent1);
			break;
		case R.id.trade_record:// 交易记录
			Intent intent2 = new Intent(getActivity(),
					TradeRecordActivity.class);
			startActivity(intent2);

			break;
		case R.id.tixianRecord:// 提现记录
			Intent intent3 = new Intent(getActivity(),
					TiXianRecordActivity.class);
			
			
			startActivity(intent3);
			break;
		case R.id.tixian:// 点击提现
		/*	firstClick_recharge = true;
			sp.edit().putBoolean("recharge", firstClick_recharge).commit();
		 Boolean	firstClick = sp.getBoolean("recharge", false);
			if (firstClick) {
				Intent intent8 = new Intent(getActivity(),
						SmsNumberActivity.class);
				intent8.putExtra("sms", "tixian");
				startActivity(intent8);
				// 短信验证成功则跳转到提现页面
				firstClick_recharge = false;
				sp.edit().putBoolean("recharge", firstClick_recharge).commit();
			} else {
				Intent intent4 = new Intent(getActivity(),
						MyTiXianActivity.class);
				startActivity(intent4);
			}*/
			DateTest date = new DateTest();
			Date date2=new Date();
			Calendar cal = Calendar.getInstance();
			boolean flag2 = date.isNowDate(date2,cal);
			if (flag2 == true) {
				// 符合交易时间
				Intent intent4 = new Intent(getActivity(),
						MyTiXianActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("mobile", phonenum);
				bundle.putString("money", df.format(income/100));
				intent4.putExtras(bundle);
				startActivity(intent4);

			} else {
				/*LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = (View) inflater.inflate(R.layout.timeout_tixian_person,
						null);
				final AlertDialog builder = new AlertDialog.Builder(getActivity())
						.create();
				builder.setView(view, 0, 0, 0, 0);
				builder.setCancelable(false);
				builder.show();
				RelativeLayout haode =    (RelativeLayout) view
						.findViewById(R.id.haode);
				haode.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) { // TODO Auto-generated
						builder.dismiss();
					}
				});*/
				Toast.makeText(getActivity(), "非交易时间无法提现", 0).show();

			}
			
			
			
			break;

		case R.id.recharge:// 点击充值
			// TODO
		/*	//如果第一次进入则进短信验证页面
			firstClick_recharge = true;
			sp.edit().putBoolean("recharge", firstClick_recharge).commit();
			Boolean	firstClick1 = sp.getBoolean("recharge", false);
			if (firstClick1) {
				// 进入短信验证页面
				Intent intent7 = new Intent(getActivity(),
						SmsNumberActivity.class);
				intent7.putExtra("sms", "recharge");
				startActivity(intent7);
				// 短信验证成功则跳转到充值页面
				firstClick_recharge = false;
				sp.edit().putBoolean("recharge", firstClick_recharge).commit();

			} else {
				Intent intent5 = new Intent(getActivity(),
						ReChargeActivity.class);
				startActivity(intent5);
			}*/
			Intent intent5 = new Intent(getActivity(),
					ReChargeActivity.class);
			startActivity(intent5);
			break;
		case R.id.personInfo:// 进入个人信息页面

			Intent intent = new Intent(getActivity(), PerInfoActivity.class);
			intent.putExtra("shun", "shun");
			startActivityForResult(intent, 0);
			break;
		
		default:
			break;
		}

	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		stopThread=false;
		handler.removeMessages(1);
	}
}
