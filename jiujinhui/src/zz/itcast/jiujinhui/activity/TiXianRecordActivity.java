package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.DrinkIncomeFragment.MyAdapter;

import zz.itcast.jiujinhui.res.NetUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TiXianRecordActivity extends BaseActivity {
	
	@ViewInject(R.id.Rl_jindu_tixian)
	private RelativeLayout Rl_jindu;
	@ViewInject(R.id.cominglistview)
	private ListView cominglistview;
	@ViewInject(R.id.tv_null_tixian)
	private RelativeLayout tv_null_tixian;
	private String unionString;
	private SharedPreferences sp;
	private MyAdapter adapter;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	
	@ViewInject(R.id.msg_chengjiao)
	
	private RelativeLayout Rl_probar;
	private ArrayList<Map<String, Object>> data = null;
	Handler handler = new Handler() {

		public void handleMessage( android.os.Message msg) {

			switch (msg.what) {
			case 1:
				data.clear();
				data.addAll(incomeslist);
				Rl_jindu.setVisibility(View.GONE);
				adapter = new MyAdapter();
				cominglistview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				
				
				break;
			case 2:
				Rl_jindu.setVisibility(View.GONE);
				cominglistview.setVisibility(View.GONE);
				tv_null_tixian.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

		};
	};

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {
					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/mywithdraw?unionid="
							+ unionString;

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
		inflater = getLayoutInflater();

	}

	private ArrayList<Map<String, Object>> incomeslist;

	protected void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> map;
			incomeslist = new ArrayList<Map<String, Object>>();
			jsonArray = jsonObject.getJSONArray("mchpays");
			if (jsonArray.length()==0) {
				handler.sendEmptyMessage(2);
			}else {
				
			
			
			for (int i = 0; i < jsonArray.length(); i++) {
				// 单号
				JSONObject jObject = (JSONObject) jsonArray.get(i);
				danhao = jObject.getString("mid");
				date = jObject.getString("createtime");
				oktime = jObject.getString("operatetime");
				DecimalFormat df = new DecimalFormat("#0.00");
				moneny = jObject.getDouble("total");
				phonenumber = jObject.getString("mobile");
				name = jObject.getString("name");
				state = jObject.getString("state");
				info = jObject.getString("msg");
				Log.e("info", info);
				map = new HashMap<String, Object>();
				map.put("danhao", danhao);
				map.put("date", date);
				map.put("state", state);
				map.put("oktime", oktime);
				map.put("name", name);
				map.put("infoing", info);
				map.put("phonenumber", phonenumber);
				map.put("moneny", df.format(Double.valueOf(moneny)/100));
				
				
				
				incomeslist.add(map);
				//Log.e("map", incomeslist.toString());
				
				
			}
			Message message = handler.obtainMessage();
			message.what = 1;
			handler.sendMessage(message);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	LayoutInflater inflater;

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			// 重用converview
			if (convertView == null) {
				holder = new ViewHolder();
				// 根据自定义的item布局加载布局
				convertView = inflater.inflate(R.layout.mytixian_item, null);
				holder.danhao = (TextView) convertView
						.findViewById(R.id.danhao);

				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.state = (TextView) convertView.findViewById(R.id.state);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.danhao.setText(data.get(position).get("danhao") + "");

			holder.date.setText(data.get(position).get("date") + "");
			if ("0".equals(data.get(position).get("state"))) {
				holder.state.setText("成功");
				holder.state.setTextColor(Color.BLUE);
			} else if ("2".equals(data.get(position).get("state"))) {
				holder.state.setText("失败");
				holder.state.setTextColor(Color.RED);
			}else if ("1".equals(data.get(position).get("state"))) {
				holder.state.setText("处理中");
				holder.state.setTextColor(Color.YELLOW);
			}
			cominglistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(TiXianRecordActivity.this,MyTixiandetailActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("name", data.get(position).get("name")+"");
					bundle.putString("phonenumber",data.get(position).get("phonenumber")+"");
					bundle.putString("moneny",data.get(position).get("moneny")+"");
					bundle.putString("state", data.get(position).get("state")+"");
					bundle.putString("operatetime",data.get(position).get("oktime")+"");
					bundle.putString("infoing", data.get(position).get("infoing")+"");
					intent.putExtras(bundle);
					startActivity(intent);
					
					
				}
				
				
				
			});
			
			return convertView;
		}
		
	}

	class ViewHolder {
		public TextView danhao;

		private TextView date;
		private TextView state;

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		sp = getSharedPreferences("user", 0);
		unionString = sp.getString("unionid", null);
		
		data = new ArrayList<Map<String, Object>>();
		tv__title.setText("我的提现");
		
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
		return R.layout.tixianrecord_activity;
	}

	boolean stopThread = false;
	private JSONArray jsonArray;
	private String danhao;
	private String date;
	private String oktime;
	private double moneny;
	private String phonenumber;
	private String name;
	private String state;
	private String info;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopThread = false;
		//handler.removeMessages(1);
		super.onDestroy();
	}

}
