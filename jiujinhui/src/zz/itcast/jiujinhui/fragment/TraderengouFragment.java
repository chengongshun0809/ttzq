package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
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
import com.lidroid.xutils.view.annotation.event.OnScroll;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.activity.TiXianRecordActivity.MyAdapter;
import zz.itcast.jiujinhui.fragment.TradeAllFragment.ListViewAdapter;
import zz.itcast.jiujinhui.res.NetUtils;
import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TraderengouFragment extends BaseFragment {

	/*@ViewInject(R.id.Rl_jindu_ren)
	private RelativeLayout Rl_jindu;*/
	@ViewInject(R.id.cominglistview_ren)
	private ListView listview;

	boolean stopThread = false;
	private SharedPreferences sp;
	private ListViewAdapter adapter;
	private String unionIDString;
	@ViewInject(R.id.tv_null_ren)
	private RelativeLayout tv_null;

	private LinearLayout footer;
	private Button bt_Msg;
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				/*Rl_jindu.setVisibility(View.GONE);*/
				loading_dialog.dismiss();
				adapter = new ListViewAdapter(list);
				adapter.appendData(orderlist);// 追加数据
				footer = (LinearLayout) inflater.inflate(R.layout.load_more,
						null);
				bt_Msg = (Button) footer.findViewById(R.id.load);
				footer.setVisibility(View.INVISIBLE);
				listview.addFooterView(footer, null, false);// 必须在setadapter之前调用
				adapter.notifyDataSetChanged();
				listview.setAdapter(adapter);

				listview.setSelection(sclectId);

				// TODO Auto-generated method stub

				bt_Msg.setText("加载更多");
				if (orderlist.size() < 30) {
					bt_Msg.setText("没有数据了");
					bt_Msg.setEnabled(false);
				}
				listview.setOnScrollListener(new OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						// TODO Auto-generated method stub
						if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
							// 滚动停止
							if (view.getLastVisiblePosition() == view.getCount() - 1) {
								footer.setVisibility(View.VISIBLE);
								bt_Msg.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										bt_Msg.setText("正在加载...");
										handler.postDelayed(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												listview.removeFooterView(footer);
												listview.setSelection(sclectId);
												visitService(CurrentpageNum);
												
												//bt_Msg.setText("加载更多");
												Log.e("kobe", "lebron");

											}
										}, 2000);

									}
								});

							}

						}

					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						sclectId = firstVisibleItem;
					}
				});

				break;
			case 2:
				/*Rl_jindu.setVisibility(View.GONE);*/
				loading_dialog.dismiss();
				listview.setVisibility(View.GONE);
				tv_null.setVisibility(View.VISIBLE);

				break;
			default:
				break;
			}

		};
	};
	private ArrayList<Map<String, Object>> list;
	int sclectId = 0;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		CurrentpageNum=1;
		visitService(CurrentpageNum);
		listview.setSelection(0);
	}

	int CurrentpageNum = 1;

	private void visitService(int page) {
		// TODO Auto-generated method stub

		if (orderlist.size() > 0) {// 必须将原来的数据清空,否则会将上一次的数据累加
			orderlist.clear();
		}
		pageString = page + "";
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {

				String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/mydeallist?unionid="
						+ unionIDString + "&type=1" + "&page=" + pageString;

				try {
					HttpsURLConnection connection = NetUtils.httpsconnNoparm(
							url_serviceinfo, "POST");

					int code = connection.getResponseCode();
					if (code == 200) {
						iStream = connection.getInputStream();
						String infojson = NetUtils.readString(iStream);
						JSONObject jsonObject = new JSONObject(infojson);
						// Log.e("我靠快快快快快快快", jsonObject.toString());
						parseJson(jsonObject);

						++CurrentpageNum;
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

	private ArrayList<Map<String, Object>> orderlist;

	// 解析数据
	protected void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		if (orderlist.size() > 0) {// 必须带上不然会成倍添加,在滑动过程中出现上下滚动的异常
			orderlist.clear();
		}

		try {
			Map<String, Object> map;

			JSONArray jsonlist = jsonObject.getJSONArray("orders");
			if (jsonlist.length() == 0) {
				handler.sendEmptyMessage(2);

			} else {
				for (int i = 0; i < jsonlist.length(); i++) {
					JSONObject jObject = (JSONObject) jsonlist.get(i);
					String danhao = jObject.getString("oid");
					String type = jObject.getString("type");
					String name = jObject.getString("pname");
					// 根据是否成功完成来判断状态
					String undonenum = jObject.getString("undonenum");
					String date = jObject.getString("createtime");
					DecimalFormat df = new DecimalFormat("#0.00");
					double total = jObject.getDouble("total");
					String number_total = jObject.getString("num");

					map = new HashMap<String, Object>();
					map.put("danhao", danhao);
					map.put("type", type);
					map.put("name", name);
					map.put("undonenum", undonenum);
					map.put("date", date);
					map.put("total", df.format(total / 100));
					map.put("number_total", number_total);
					orderlist.add(map);
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
	private String pageString;

	public class ListViewAdapter extends BaseAdapter {

		private List<Map<String, Object>> list = null;

		// 需要显示的数据，不应该使用new初始化,向上回滚的时候会出问题
		public ListViewAdapter(List<Map<String, Object>> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void appendData(List<Map<String, Object>> list) {

			// 分页加载关键
			this.list.addAll(list);// 不能换成this.list=list，这样只会显示当前页，以前的数据会覆盖
			adapter.notifyDataSetChanged();

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.trade_record_detail,
						null);
				holder.tv_danhao = (TextView) convertView
						.findViewById(R.id.danhao);
				holder.tv_dan_state = (TextView) convertView
						.findViewById(R.id.dan_state);
				holder.name_pro = (TextView) convertView
						.findViewById(R.id.name_pro);
				holder.msg_chengjiao = (TextView) convertView
						.findViewById(R.id.msg_chengjiao);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.tv_num = (TextView) convertView.findViewById(R.id.tvnum);
				holder.total = (TextView) convertView.findViewById(R.id.total);
				holder.tv_weichengjiao = (TextView) convertView
						.findViewById(R.id.tv_weichengjiao);
				holder.tv_weichengjiao_num = (TextView) convertView
						.findViewById(R.id.tv_weichengjiao_num);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// holder.tv.setText(list.get(position));

			holder.tv_danhao.setText(list.get(position).get("danhao") + "");
			holder.name_pro.setText(list.get(position).get("name") + "");
			holder.date.setText(list.get(position).get("date") + "");
			holder.total.setText(list.get(position).get("total") + "");
			holder.tv_num.setText(list.get(position).get("number_total") + "");
			String typString = (String) list.get(position).get("type");
			// 未成交
			String undonenum = (String) list.get(position).get("undonenum");
			// 判断type
			int type_int = Integer.parseInt(typString);
			switch (type_int) {

			case 1:
				holder.tv_dan_state.setText("认购完成");
				holder.msg_chengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao_num.setVisibility(View.GONE);
				holder.msg_chengjiao.setText("全部成交");
				break;

			default:
				break;
			}

			return convertView;
		}

		public class ViewHolder {
			TextView tv_danhao;
			TextView tv_dan_state;
			TextView name_pro;
			TextView msg_chengjiao;
			TextView date;
			TextView total;
			TextView tv_num;
			TextView tv_weichengjiao;
			TextView tv_weichengjiao_num;
		}

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}
	private Dialog loading_dialog = null;
	@Override
	public void initView(View view) {
		// TODO Afuto-generated method stub
		ViewUtils.inject(this, view);
		sp = getActivity().getSharedPreferences("user", 0);
		unionIDString = sp.getString("unionid", null);
		/*Rl_jindu.setVisibility(View.VISIBLE);*/
		loading_dialog=zz.itcast.jiujinhui.res.DialogUtil.createLoadingDialog(getActivity(), "加载中...");
		list = new ArrayList<Map<String, Object>>();
		tv_null.setVisibility(View.GONE);
		orderlist = new ArrayList<Map<String, Object>>();
		inflater = getActivity().getLayoutInflater();

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.traderengou_fragment;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		// data.clear();
		stopThread = false;
		/*
		 * handler.removeMessages(2); handler.removeMessages(1);
		 */
		handler.removeMessages(2);
        handler.removeMessages(1);
        listview.setSelection(0);
	}

}
