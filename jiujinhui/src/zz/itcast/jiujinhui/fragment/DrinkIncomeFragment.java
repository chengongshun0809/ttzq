package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.bean.Income;
import zz.itcast.jiujinhui.fragment.BuyChartFragment.ViewHolder;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DrinkIncomeFragment extends BaseFragment {

	
	@ViewInject(R.id.Rl_jindu_shouru)
	private RelativeLayout Rl_jindu;
	@ViewInject(R.id.cominglistview)
	private ListView cominglistview;
	@ViewInject(R.id.tv_null_shouru)
	private RelativeLayout tv_null_shouru;
	private SharedPreferences sp;
	
	private String unionString;
	private MyAdapter adapter;
	private ArrayList<Map<String, Object>> data=null;
	Handler handler = new Handler() {

		

		public void handleMessage(android.os.Message msg) {

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
				tv_null_shouru.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

		};
	};
	boolean stopThread = false;
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {
				String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/myincome?unionid="
						+ unionString;

				try {
					HttpsURLConnection connection = NetUtils.httpsconnNoparm(
							url_serviceinfo, "POST");

					int code = connection.getResponseCode();
					if (code == 200) {
						iStream = connection.getInputStream();
						String infojson = NetUtils.readString(iStream);
						JSONObject jsonObject = new JSONObject(infojson);
						//Log.e("aaassssssssss", jsonObject.toString());
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

		inflater = getActivity().getLayoutInflater();

	}

	LayoutInflater inflater;


	class ViewHolder {
		public TextView danhao;
		private TextView jiubi;
		private TextView date;
		private TextView msg;

	}

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
				convertView = inflater.inflate(R.layout.drinkincome_item, null);
				holder.danhao = (TextView) convertView
						.findViewById(R.id.danhao);
				holder.jiubi = (TextView) convertView
						.findViewById(R.id.jiu_moneny);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.msg = (TextView) convertView.findViewById(R.id.msg);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.danhao.setText(data.get(position).get("danhao")+"");
			holder.jiubi.setText(data.get(position).get("jiubi")+"");
			holder.date.setText(data.get(position).get("date")+"");
			holder.msg.setText(data.get(position).get("msg")+"");
			
			return convertView;

		}

	}

	JSONArray jsonArray2 = new JSONArray();

	private ArrayList<Map<String, Object>> incomeslist;
	private JSONArray jsonArray;



	protected void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		try {
			
			Map<String, Object> map;
			incomeslist = new ArrayList<Map<String, Object>>();
			jsonArray = jsonObject.getJSONArray("incomes");
            if (jsonArray.length()==0) {
				handler.sendEmptyMessage(2);
			}else {
				
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jObject = (JSONObject) jsonArray.get(i);
				
				double jiubi = jObject.getDouble("total");
				// 酒币
				if (jiubi>0) {
					// 单号
					String danhao = jObject.getString("oid");
					
					
					// 日期
					String date = jObject.getString("createtime");
					String msg = jObject.getString("msg");
					DecimalFormat df = new DecimalFormat("#0.00");
					map = new HashMap<String, Object>();
					map.put("danhao", danhao);
					map.put("jiubi", df.format(Double.valueOf(jiubi) / 100));
					map.put("date", date);
					map.put("msg", msg);
					incomeslist.add(map);
				}
				
				
				
			}
		//	Log.e("收入", incomeslist.toString());
			
			Message message=handler.obtainMessage();
			message.what=1;
			handler.sendMessage(message);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		data = new ArrayList<Map<String, Object>>();
	}
@Override
public void onDestroyView() {
	// TODO Auto-generated method stub
	super.onDestroyView();
	data.clear();
	
}
	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this,view);
		sp = getActivity().getSharedPreferences("user", 0);
		unionString = sp.getString("unionid", null);
		Rl_jindu.setVisibility(View.VISIBLE);
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.drinkincome_fragment;
	}
 @Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
	stopThread=false;
	handler.removeMessages(1);
}
}
