package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.NetUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressLint("ResourceAsColor")
public class SaleChartFragment extends BaseFragment {

	@ViewInject(R.id.listview)
	private ListView listView;
	boolean stopThread = false;
	int index = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				data.clear();
				data.addAll(list);
				adapter = new MyAdapter();
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();

				handler.sendEmptyMessage(1);
			case 1:

				handler.removeMessages(1);
				scrolllistview();
				handler.sendEmptyMessageDelayed(1, 1000);

				break;
			default:
				break;
			}

		};

	};
	// 禁止listview手动滑动

	private JSONArray jsonArraylist;

	private String createtime;

	private String num;

	private String price;

	private String state;

	private SharedPreferences sp;
	private String dgid;
	private String unionid;
	@ViewInject(R.id.sale_price)
	private TextView sale_price;
	@ViewInject(R.id.sale_count)
	private TextView sale_count;
	@ViewInject(R.id.state)
	private TextView Salestate;
	@ViewInject(R.id.date)
	private TextView date;
	public List<Map<String, Object>> list = null;
	private MyAdapter adapter;

	private List<Map<String, Object>> data = null;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		dgid = getActivity().getIntent().getStringExtra("dealdgid");
		unionid = sp.getString("unionid", null);

		// 获取数据
		new Thread(new Runnable() {

			private InputStream iStream = null;

			@Override
			public void run() {
				while (!stopThread) {

					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
							+ unionid + "&dgid=" + dgid;

					HttpsURLConnection connection = NetUtils.httpsconnNoparm(
							url_serviceinfo, "POST");
					int code;
					try {
						code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("ssssssssss", jsonObject.toString());
							parseJson(jsonObject);
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
		// listView.invalidate();
		data = new ArrayList<Map<String, Object>>();
		// 禁止listView手动滚动
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {

				case MotionEvent.ACTION_MOVE:
					return true;

				default:
					break;
				}

				return true;
			}
		});
	}

	protected void scrolllistview() {
		// TODO Auto-generated method stub
		// int totaloff = listView.getMeasuredHeight();

		if (index < 3 * data.size()) {
			listView.smoothScrollBy(10, 0);

			index += 1;

		} else {
			listView.smoothScrollToPosition(0);
			index = 0;

		}
	}

	// listview自动滚动

	// 刷新数据

	protected void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		// 卖出数据解析 state:1代表卖出未成交，2代表卖出已成交
		try {
			jsonArraylist = jsonObject.getJSONArray("buyout");
			list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			for (int i = 0; i < jsonArraylist.length(); i++) {
				JSONObject object = (JSONObject) jsonArraylist.get(i);
				createtime = object.getString("createtime");
				num = object.getString("num");
				price = object.getString("price");
				state = object.getString("state");
				map = new HashMap<String, Object>();
				DecimalFormat df = new DecimalFormat("#0.00");
				map.put("price", df.format(Double.valueOf(price) / 100));
				map.put("createtime",
						createtime.substring(5, createtime.length()));
				map.put("num", num);
				map.put("state", state);
				list.add(map);
				Message message = handler.obtainMessage();
				message.what = 0;
				handler.sendMessage(message);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	LayoutInflater inflater;

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);

		sp = getActivity().getSharedPreferences("user", 0);
		/*
		 * Message message = handler.obtainMessage(); message.what = 1;
		 * handler.sendMessage(message);
		 */
		inflater = getActivity().getLayoutInflater();
	}

	class ViewHolder {
		public TextView saleprice;
		private TextView salecount;
		private TextView salestate;
		private TextView saledate;

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
			// 获取数据集中与指定索引对应的数据项
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

			// 如果缓存
			if (convertView == null) {
				holder = new ViewHolder();
				// 根据自定义的item布局加载布局
				convertView = inflater.inflate(
						R.layout.trade_service_listview_item, null);
				holder.saleprice = (TextView) convertView
						.findViewById(R.id.sale_price);
				holder.salecount = (TextView) convertView
						.findViewById(R.id.sale_count);
				holder.salestate = (TextView) convertView
						.findViewById(R.id.state);
				holder.saledate = (TextView) convertView
						.findViewById(R.id.date);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.saleprice.setText(list.get(position).get("price") + "");
			holder.salecount.setText(list.get(position).get("num") + "");
			if ("1".equals(list.get(position).get("state") + "")) {
				holder.salestate.setText("未成交");
				holder.salestate.setTextColor(Color.RED);
				holder.saleprice.setTextColor(Color.RED);
				holder.salecount.setTextColor(Color.RED);

			} else if ("2".equals(list.get(position).get("state") + "")) {
				holder.salestate.setText("已成交");
				holder.salestate.setTextColor(Color.BLUE);
				holder.saleprice.setTextColor(Color.BLUE);
				holder.salecount.setTextColor(Color.BLUE);

			} else if ("0".equals(list.get(position).get("state") + "")) {
				holder.salestate.setText("部分成交");
				holder.salestate.setTextColor(Color.RED);
				holder.saleprice.setTextColor(Color.RED);
				holder.salecount.setTextColor(Color.RED);

			}

			holder.saledate.setText(list.get(position).get("createtime") + "");
			return convertView;
		}

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.salechart_fragment;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopThread = false;
		handler.removeMessages(0);
		handler.removeMessages(1);
		super.onDestroy();

	}
}
