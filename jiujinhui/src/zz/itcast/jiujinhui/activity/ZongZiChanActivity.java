package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZongZiChanActivity extends BaseActivity {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.Rl_jindu)
	private RelativeLayout Rl_jindu;
	@ViewInject(R.id.ll_content)
	private LinearLayout ll_content;
	boolean stopThread = false;
	private SharedPreferences sp;
	private String unionidString;
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {
					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/mysub?unionid="
							+ unionidString;

					try {
						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");

						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("我靠快快快快快快快", jsonObject.toString());
							parseJson(jsonObject);
							stopThread=true;

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
	
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			Rl_jindu.setVisibility(View.GONE);
			UpdateUI();
			break;

		default:
			break;
		}	
			
		};
		
		
	};
	private LayoutInflater inflater;
	private JSONArray jsonArray;
	private int length;
	private DecimalFormat       df;
	private void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
	     try {
			jsonArray = jsonObject.getJSONArray("dealdatas");
		        length = jsonArray.length();
		        Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
	     
				
				
	     
	     } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	boolean firstclick=true;
	protected void UpdateUI() {
		// TODO Auto-generated method stub
		for (int i = 0; i < length; i++) {
			View view=inflater.inflate(R.layout.zongzichan_detail, null);
			//酒金窖名字
			TextView name=(TextView) view.findViewById(R.id.jiujiao_name);
			//总收益
			TextView zshouyi=(TextView) view.findViewById(R.id.zshouyi);
			//总资产
			TextView zong_assists=(TextView) view.findViewById(R.id.zongzichan);
			TextView shengyu=(TextView) view.findViewById(R.id.shengyu);
			TextView saling=(TextView) view.findViewById(R.id.naichuzhong);
			TextView buying=(TextView) view.findViewById(R.id.buying);
			TextView finished_chengjiao=(TextView) view.findViewById(R.id.finished_chengjiao);
			TextView finished_tihuo=(TextView) view.findViewById(R.id.finished_tihuo);
			TextView finished_trans=(TextView) view.findViewById(R.id.finished_trans);
			RelativeLayout rl_lookMore=(RelativeLayout) view.findViewById(R.id.rl_lookMore);
		     final LinearLayout ll_lookvalue=(LinearLayout) view.findViewById(R.id.ll_lookvalue);
			//获取数据
			try {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
			    //总收益
			      df = new DecimalFormat("#0.00");
				
				String zongshouyi=jsonObject.getString("buyintotal");
				//名字
				String jiujiaoname=jsonObject.getString("owner");
			     //总资产
				String total=jsonObject.getString("subnum");
				//剩余资产
				int left_total=jsonObject.getInt("stock");
				//卖出中
				int saleingsString=jsonObject.getInt("putnum");
				//买入中
				String  buyingString=jsonObject.getString("getnum");
			    //已成交
				String chengjiaoString=jsonObject.getString("dealnum");
				//已提货
				String tihuo_num=jsonObject.getString("consumenum");
				//已转让
				String trans_num=jsonObject.getString("buybacknum");
			double shou_d=Double.parseDouble(zongshouyi);
			
			zshouyi.setText(df.format(shou_d/100));
				name.setText(jiujiaoname);
				zong_assists.setText((left_total+saleingsString)+"");
				shengyu.setText(left_total+"");
				saling.setText(saleingsString+"");
				buying.setText(buyingString);
				finished_chengjiao.setText(chengjiaoString);
				finished_tihuo.setText(tihuo_num);
				finished_trans.setText(trans_num);
				rl_lookMore.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (firstclick) {
							ll_lookvalue.setVisibility(View.VISIBLE);
							firstclick=false;
						}else {
							ll_lookvalue.setVisibility(View.GONE);
							firstclick=true;
						}
						
						
					}
				});
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ll_content.addView(view);
			
		
		}
		
	}
	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("个人资产");
		sp = getSharedPreferences("user", 0);
		unionidString = sp.getString("unionid", null);
		Rl_jindu.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_back:
           finish();
			break;

		default:
			break;
		}
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.zongzichan_activity;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		stopThread = false;
		handler.removeMessages(1);
	}
}
