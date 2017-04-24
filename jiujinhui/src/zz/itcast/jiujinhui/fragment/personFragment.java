package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
import zz.itcast.jiujinhui.activity.TiXianRecordActivity;
import zz.itcast.jiujinhui.activity.TradeRecordActivity;
import zz.itcast.jiujinhui.activity.ZongZiChanActivity;
import zz.itcast.jiujinhui.res.DateTest;
import zz.itcast.jiujinhui.res.NetUtils;
import zz.itcast.jiujinhui.res.OurApplication;
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
			 
			 if (phonenum!=null) {
				 conn.disconnect();
				 loading_dialog.dismiss();
				 person_jiubi.setText(df.format(income/100));
			}
			 
			
			break;

		default:
			break;
		}
		 
		  
	  };
  };
  private Dialog dialog_NO;
private String phonenum;
private DecimalFormat df;
private HttpsURLConnection conn;

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
		tv_back.setVisibility(view.GONE);
		tv__title.setText("个人中心");
		// 微信头像
		sp =OurApplication.getContext().getSharedPreferences("user", 0);
		String headimgurl = sp.getString("headimg", null);
		
		if (headimgurl!=null) {
			Picasso.with(OurApplication.getContext()).load(headimgurl).into(circleImabeView);
		}
		
		
		// 微信昵称
		String nickNameString = sp.getString("nickname", null);
		if (nickNameString!=null) {
			NickName.setText(nickNameString);
		}
		
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
				
					try {
						String urlpath = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
								+ unionString + "&dgid=DG161027140008895";
						conn = NetUtils.httpsconnNoparm(
								urlpath, "GET");
						// 若连接服务器成功，返回数据
						int code = conn.getResponseCode();
						if (code == 200) {

							is = conn.getInputStream();
							String json = NetUtils.readString(is);
							// 解析json
							parsonJson(json);
						}else {
							conn.disconnect();
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
			
			
		}).start();
	   
	   
	   
	   
	   
	   
	  
	}
	
	protected void parsonJson(String json) {
		
		// TODO Auto-generated method stub
		try {
			if (json!=null) {
				df = new DecimalFormat("#0.00");
				JSONObject jsonObject = new JSONObject(json);
				income = jsonObject.getDouble("income");
			
				sp.edit().putString("income", df.format(income/100)).commit();
				phonenum = jsonObject.getString("mobile");
				if (phonenum!=null) {
					sp.edit().putString("mobile", phonenum).commit();
				}
				
				  
				Message message=Message.obtain();
				handler.sendEmptyMessage(1);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Dialog loading_dialog = null;
	@Override
	public void initData() {
		loading_dialog=zz.itcast.jiujinhui.res.DialogUtil.createLoadingDialog(getActivity(), "加载中...");
		
		
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
	     boolean isaliv=true;
		
              @Override
            public void onResume() {
            	// TODO Auto-generated method stub
            	super.onResume();
           
            	
				//判断当前页面是否联网
				ConnectivityManager connectivityManager=(ConnectivityManager)OurApplication.getContext().getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);
				
				NetworkInfo info=connectivityManager.getActiveNetworkInfo();
				if(info!=null&&info.isAvailable()){
					isaliv=true;
					
					
				}else{
					isaliv=false;
					Toast.makeText(OurApplication.getContext(),"无网络连接",Toast.LENGTH_SHORT).show();
					
				}
					
              
              
              }
              
              
              
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zongzichan:// 总资产
			Intent intent0 = new Intent(OurApplication.getContext(), ZongZiChanActivity.class);
			startActivity(intent0);
			break;
		case R.id.drink_record:// 酒币记录
			Intent intent1 = new Intent(OurApplication.getContext(),
					DrinkRecordActivity.class);
			startActivity(intent1);
			break;
		case R.id.trade_record:// 交易记录
			Intent intent2 = new Intent(OurApplication.getContext(),
					TradeRecordActivity.class);
			startActivity(intent2);

			break;
		case R.id.tixianRecord:// 提现记录
			Intent intent3 = new Intent(OurApplication.getContext(),
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
			ConnectivityManager connectivityManager=(ConnectivityManager)OurApplication.getContext().getSystemService(OurApplication.getContext().CONNECTIVITY_SERVICE);
			
			NetworkInfo info=connectivityManager.getActiveNetworkInfo();
			if(info!=null&&info.isAvailable()){
				isaliv=true;
				
				
			}else{
				isaliv=false;
				
				
			}
				
			
			
			
			if(isaliv==true){
				DateTest dateTest = new DateTest();
				Calendar cal = Calendar.getInstance();
				long datet=new Date().getTime();

				
				boolean flag3 = dateTest.isNowDate(datet, cal);
				if (flag3 == true) {
					// 符合交易时间
					Intent intent4 = new Intent(OurApplication.getContext(),
							MyTiXianActivity.class);
					
					startActivity(intent4);

				} else {
					LayoutInflater inflater = LayoutInflater.from(getActivity());
					View view = (View) inflater.inflate(
							R.layout.timeout_tixiantime, null);
					final AlertDialog builder = new AlertDialog.Builder(getActivity())
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
				
				Toast.makeText(OurApplication.getContext(), "无网络连接", 0).show();
				
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
			Intent intent5 = new Intent(OurApplication.getContext(),
					ReChargeActivity.class);
			startActivity(intent5);
			break;
		case R.id.personInfo:// 进入个人信息页面

			Intent intent = new Intent(OurApplication.getContext(), PerInfoActivity.class);
			/*intent.putExtra("shun", "shun");
			startActivityForResult(intent, 2);*/
			startActivity(intent);
			break;
		
		default:
			break;
		}

	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		handler.removeMessages(1);
        conn.disconnect();
          
	}
	
	
	
	
	
	
	
	
	
	
}
