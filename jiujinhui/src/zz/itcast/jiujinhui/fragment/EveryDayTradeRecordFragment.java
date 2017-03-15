package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

import org.json.JSONArray;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.bean.DomeBean;
import zz.itcast.jiujinhui.mychart.MyXAxis;
import zz.itcast.jiujinhui.mychart.MyYAxis;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EveryDayTradeRecordFragment extends BaseFragment {

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.new_trade_every;
	}/*

	private SharedPreferences sp;
	boolean stopThread = false;
	@ViewInject(R.id.line_trade_chart)
	private zz.itcast.jiujinhui.mychart.MyLineChart lineChart;
	@ViewInject(R.id.tingpan_nodata)
	private RelativeLayout tingpan_nodata;

	@ViewInject(R.id.ll_time_price_every)
	private LinearLayout ll_time_price_every;// 闭盘时隐藏

	@ViewInject(R.id.date)
	// 日期
	private TextView date;
	@ViewInject(R.id.ll_cheng)
	private LinearLayout ll_cheng;

	@ViewInject(R.id.price_cheng)
	private TextView price_cheng;// 成交价

	@ViewInject(R.id.ll_trans)
	private LinearLayout ll_trans;
	@ViewInject(R.id.price_trans)
	// 转让价
	private TextView price_trans;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				// 获取数据展示
				DomeBean bean = (DomeBean) msg.obj;
				// Log.e("测试", bean.todaydeal.toString()+"");

				//setDatas(bean.getDealprice() );
				break;

			default:
				break;
			}

		};

	};
	private JSONArray jsonArraylist;
	private String dgid;
	private String unionid;

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		sp = getActivity().getSharedPreferences("user", 0);

		ViewUtils.inject(this, view);

		initLineChart();

	}

	MyXAxis xAxisLine;
	MyYAxis axisRightLine;
	MyYAxis axisLeftLine;

	public void setDatas(List<DomeBean.DealpriceBean> dealprice) {
		// TODO Auto-generated method stub
		// lineChart.setDrawMarkerViews(true);
		// 设置初始值
		date.setText(timeList2.get(0));
		price_cheng.setText(beginpriceList2.get(0) + "");
		price_trans.setText(buybackpriceList2.get(0) + "");
		Log.e("beginpriceList2", beginpriceList2.get(0) + "");
		ArrayList<Entry> lineCJEntries = new ArrayList<Entry>();
		ArrayList<Entry> lineTransEntries = new ArrayList<Entry>();

		for (int i = 0; i < timeList.size(); i++) {

			lineCJEntries.add(new Entry(beginpriceList2.get(i), i));

			lineTransEntries.add(new Entry(buybackpriceList2.get(i), i));

		}
		LineDataSet d1 = new LineDataSet(lineCJEntries, "成交价");
		LineDataSet d2 = new LineDataSet(lineTransEntries, "转让价");
		d1.setDrawValues(false);// 显示折线上的数值  
		d2.setDrawValues(false);// 显示折线上的数值
		
		d1.setCircleRadius(0);
		d2.setCircleRadius(0);
		d1.setColor(getResources().getColor(R.color.minute_blue));
		d2.setColor(getResources().getColor(R.color.minute_black));
		d1.setAxisDependency(YAxis.AxisDependency.LEFT);
		d2.setAxisDependency(YAxis.AxisDependency.LEFT);
		ArrayList<ILineDataSet> sets1 = new ArrayList<ILineDataSet>();
		sets1.add(d1);
		//LineData cd = new LineData(timeList2, sets1);
		
		
		ArrayList<ILineDataSet> sets2 = new ArrayList<ILineDataSet>();
		sets2.add(d2);
		//LineData cd1 = new LineData(timeList2, sets2);
		//lineChart.setData(cd);
		//lineChart.setData(cd1);
		lineChart.notifyDataSetChanged();
		lineChart.invalidate();

	}

	private void initLineChart() {
		// TODO Auto-generated method stub
		lineChart.setScaleEnabled(false);
		lineChart.setAlpha(0.8f);
		lineChart.setDrawBorders(true);
		lineChart.setBorderWidth(1);
		lineChart
				.setBorderColor(getResources().getColor(R.color.minute_zhoutv));
		lineChart.setDescription("");
		lineChart.setDrawGridBackground(false);

		lineChart.getAxisRight().setDrawGridLines(false);
		lineChart.getAxisLeft().setDrawGridLines(false);
		lineChart.getXAxis().setDrawGridLines(false);
		lineChart.setBackgroundColor(getResources().getColor(R.color.light));
		Legend lineChartLegend = lineChart.getLegend();
		lineChartLegend.setEnabled(false);

		// x轴
		xAxisLine = lineChart.getXAxis();
		xAxisLine.setDrawLabels(true);
		// xAxisLine.setEnabled(true);
		xAxisLine.setDrawAxisLine(true);// 设置显示x轴
		xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
		// xAxisLine.setLabelsToSkip(59);
		xAxisLine.setAvoidFirstLastClipping(true);

		// 左边y
		axisLeftLine = lineChart.getAxisLeft();
		// 折线图y轴左没有basevalue，调用系统的
		axisLeftLine.setLabelCount(5, true);
		axisLeftLine.setDrawLabels(true);
		// axisLeftLine.setEnabled(true);
		axisLeftLine.setDrawGridLines(false);
		// 轴不显示 避免和border冲突
		axisLeftLine.setDrawAxisLine(false);
		axisLeftLine.setStartAtZero(false);

		// 背景线
		xAxisLine
				.setGridColor(getResources().getColor(R.color.minute_grayLine));
		xAxisLine.setAxisLineColor(getResources().getColor(
				R.color.minute_grayLine));
		xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
		axisLeftLine.setGridColor(getResources().getColor(
				R.color.minute_grayLine));
		axisLeftLine.setTextColor(getResources()
				.getColor(R.color.minute_zhoutv));
		axisRightLine.setAxisLineColor(getResources().getColor(
				R.color.minute_grayLine));
		axisRightLine.setTextColor(getResources().getColor(
				R.color.minute_zhoutv));

		axisLeftLine.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis yAxis) {
				DecimalFormat mFormat = new DecimalFormat("#0.00");
				return mFormat.format(value);
			}
		});
	}

	public static List<String> timeList;
	public static List<Float> beginpriceList;
	public static List<Float> buybackpriceList;

	protected void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		// 交易曲线
		try {
			jsonArraylist = jsonObject.getJSONArray("dealprice");
			timeList = new ArrayList<String>();
			beginpriceList = new ArrayList<Float>();
			buybackpriceList = new ArrayList<Float>();
			for (int i = 0; i < jsonArraylist.length(); i++) {
				JSONObject object = (JSONObject) jsonArraylist.get(i);
				// 开始价格
				double beginprice = object.getDouble("beginprice");
				beginpriceList.add((float) beginprice / 100);
				// 回购价
				double buybackprice = object.getDouble("buybackprice");
				buybackpriceList.add((float) buybackprice / 100);

				String dealday = object.getString("dealday");
				timeList.add(dealday.substring(5, dealday.length()));

			}

			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		initDatas();
	}

	public void initDatas() {
		// TODO Auto-generated method stub
		// 获取数据

		dgid = getActivity().getIntent().getStringExtra("dealdgid");
		unionid = sp.getString("unionid", null);
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {

				String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
						+ unionid + "&dgid=" + dgid;

				try {

					HttpsURLConnection connection = NetUtils.httpsconnNoparm(
							url_serviceinfo, "POST");

					int code = connection.getResponseCode();
					if (code == 200) {
						iStream = connection.getInputStream();
						String infojson = NetUtils.readString(iStream);
						//JSONObject jsonObject = new JSONObject(infojson);
						// Log.e("ssssssssss", jsonObject.toString());
						parseJson(jsonObject);

						connection.disconnect();
						// Log.e("ssssssssssss", infojson);
						DomeBean bean = new Gson().fromJson(infojson,
								DomeBean.class);
						Log.e("wangluo", bean.income + "");
						Message message = handler.obtainMessage();
						message.what = 1;
						message.obj = bean;
						handler.sendMessage(message);
						
						
						
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

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.new_trade_every;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		handler.removeMessages(1);

		
		 * timeList = null; beginpriceList = null; buybackpriceList = null;
		 

		super.onDestroy();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
*/}
