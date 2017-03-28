package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.w3c.dom.Text;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.bean.DomeBean;
import zz.itcast.jiujinhui.bean.MinutesBean;
import zz.itcast.jiujinhui.mychart.DataParse;
import zz.itcast.jiujinhui.mychart.MyXAxis;
import zz.itcast.jiujinhui.mychart.MyYAxis;
import zz.itcast.jiujinhui.res.DateTest;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.cache.MD5FileNameGenerator;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NowTradeRecoedFragment<ILineDataSet> extends BaseFragment {
	private SharedPreferences sp;

	@ViewInject(R.id.line_trade_chart)
	private zz.itcast.jiujinhui.mychart.MyLineChart lineChart;

	@ViewInject(R.id.tingpan_nodata_h)
	private RelativeLayout tingpan_nodata;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 1:
				// 获取数据展示
				DomeBean bean = (DomeBean) msg.obj;
				// Log.e("测试", bean.todaydeal.toString()+"");
				if (isAdded()) {
					ll_time_price.setVisibility(View.VISIBLE);
					setDatas(bean.getTodaydeal());
				}

				break;
			case 2:
				
				Toast.makeText(getActivity(), "当前网络异常,请检查更新！",
						0).show();
				break;
			default:
				break;
			}

		};

	};

	private String dgid;
	private String unionid;
	private SparseArray<String> stringSparseArray;

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		sp = getActivity().getSharedPreferences("user", 0);

		ViewUtils.inject(this, view);
		stringSparseArray = setXLabels();
		initLineChart();
		if (isAdded()) {
			initLineChart();
		}
	}

	public SparseArray<String> setXLabels() {
		SparseArray<String> xLabels = new SparseArray<String>();
		// xLabels.clear();

		xLabels.put(0, "09:00");
		xLabels.put(20, "11:30");
		xLabels.put(33, "15:00");

		return xLabels;
	}

	MyXAxis xAxisLine;
	MyYAxis axisRightLine;
	MyYAxis axisLeftLine;

	private void setDatas(List<DomeBean.TodaydealBean> todaydeal) {
		// TODO Auto-generated method stub
		lineChart.setDrawMarkerViews(true);
		// lineChart.setMarkerView(v);
		xAxisLine.setXLabels(stringSparseArray);
		mData = new DataParse();
		mData.domeMinutes(todaydeal);
		// 给实时设置初始值
		time.setText(mData.getDatas().get(0).time);
		price.setText(mData.getDatas().get(0).cjprice + "");

		ArrayList<Entry> lineCJEntries = new ArrayList<Entry>();
		ArrayList<String> dateList = new ArrayList<String>();
		for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
			// Log.e("todaydealfsafasfaf", mData.getDatas().size() + "");

			// Log.e("todaydeal", mData.getDatas().get(i).time + "");
			// Log.e("todaydeal_price", mData.getDatas().get(i).cjprice + "");
			// dateList.add(mData.getDatas().get(i).time);

			MinutesBean minutesBean = mData.getDatas().get(i);
			/*
			 * if (minutesBean.cjprice != -1) { lineCJEntries.add(new
			 * Entry(minutesBean.cjprice, i)); 
			 * dateList.add(mData.getDatas().get(i).time);
			 * Log.e("!=-1",mData.getDatas().get(i).time+""); } else {
			 * lineCJEntries.add(new Entry(Float.NaN, i));
			 * dateList.add(mData.getDatas().get(i).time); Log.e("==-1",
			 * mData.getDatas().get(i).time+""); }
			 */
			MinutesBean t = mData.getDatas().get(j);
			if (t == null) {
				lineCJEntries.add(new Entry(Float.NaN, i));
				dateList.add(i + "");
				// Log.e("==-1", mData.getDatas().get(i).time+"");
				continue;
			}
			lineCJEntries.add(new Entry(minutesBean.cjprice, i));
			dateList.add(mData.getDatas().get(i).time);
			//Log.e("!=-1", mData.getDatas().get(i).time + "");

		}
		//Log.e("dateList", dateList.size() + "");

		LineDataSet d1 = new LineDataSet(lineCJEntries, "成交价");
		d1.setDrawValues(false);// 显示折线上的数值

		d1.setCircleRadius(0);
		d1.setColor(getResources().getColor(R.color.minute_blue));
		d1.setHighLightColor(getResources().getColor(R.color.minute_yellow));
		
		// d1.setFillColor(getResources().getColor(R.color.minute_shadow));
		// Log.e("d1", d1+"");
		d1.setDrawFilled(true);
		d1.setAxisDependency(YAxis.AxisDependency.LEFT);
		List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		dataSets.add((ILineDataSet) d1);  

		LineData cd = new LineData(
				dateList,
				(List<com.github.mikephil.charting.interfaces.datasets.ILineDataSet>) dataSets);
		lineChart.setData(cd);
		lineChart.notifyDataSetChanged();

		// lineChart.animateX(1);

		lineChart.invalidate();
		lineChart
				.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

					@Override
					public void onValueSelected(Entry e, int dataSetIndex,
							Highlight h) { // TODO Auto-generated method stub
						/*
						 * Log.e("e.getXIndex()", e.getXIndex() + "");
						 * 
						 * Log.e(" e.getVal()", e.getVal() + "");
						 */
						price.setText(e.getVal() + "");
						int index = e.getXIndex();
						time.setText(mData.getDatas().get(index).time);

					}

					@Override
					public void onNothingSelected() {

					}
				});

	}

	private void initLineChart() {

		lineChart.setNoDataText("");
		lineChart.setNoDataTextDescription("");
		lineChart.setScaleEnabled(false);
		lineChart.setAlpha(0.5f);// 设置透明度
		lineChart.setDrawBorders(false);
		lineChart.setBorderWidth(1);
		lineChart
				.setBorderColor(getResources().getColor(R.color.minute_zhoutv));
		lineChart.setDescription("");
		lineChart.setDrawGridBackground(false);

		lineChart.getAxisRight().setDrawGridLines(false);
		lineChart.getAxisLeft().setDrawGridLines(false);
		lineChart.getXAxis().setDrawGridLines(false);
		// lineChart.setBackgroundColor(getResources().getColor(R.color.light));
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
		/* 折线图y轴左没有basevalue，调用系统的 */
		axisLeftLine.setLabelCount(5, true);
		axisLeftLine.setDrawLabels(true);
		// axisLeftLine.setEnabled(true);
		axisLeftLine.setDrawGridLines(false);
		/* 轴不显示 避免和border冲突 */
		axisLeftLine.setDrawAxisLine(true);
		axisLeftLine.setStartAtZero(false);

		// 右边y
		axisRightLine = lineChart.getAxisRight();
		axisRightLine.setLabelCount(1, true);
		axisRightLine.setDrawLabels(false);

		axisRightLine.setStartAtZero(false);
		axisRightLine.setDrawGridLines(false);
		axisRightLine.setDrawAxisLine(false);
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

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		// 获取数据

		dgid = getActivity().getIntent().getStringExtra("dealdgid");
		unionid = sp.getString("unionid", null);

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		DateTest dateTest = new DateTest();
		boolean flag = dateTest.isNowDate_nowtrade(date, cal);
		if (flag == true) {
			new Thread(new Runnable() {

				private InputStream iStream;

				@Override
				public void run() {

					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
							+ unionid + "&dgid=" + dgid;

					// String url_serviceinfo =
					// "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid=o9zBFwSKbKwfv2lXj0mLpKdRplS0&dgid=DG170112210454522";

					try {
						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");
						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							// Log.e("ssssssssssss", infojson);
							DomeBean bean = new Gson().fromJson(infojson,
									DomeBean.class);
							// Log.e("wangluo", bean.income + "");
							Message message = handler.obtainMessage();
							message.what = 1;
							message.obj = bean;
							handler.sendMessage(message);

						}else {
							Message message = handler.obtainMessage();
							message.what = 2;
						
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

		} else {

			// 停盘时间，数据为空
			lineChart.setVisibility(View.GONE);
			ll_time_price.setVisibility(View.GONE);
			tingpan_nodata.setVisibility(View.VISIBLE);

		}

	}

	@ViewInject(R.id.ll_time_price)
	private LinearLayout ll_time_price;// 时间价格提示

	@ViewInject(R.id.time)
	private TextView time;
	@ViewInject(R.id.price)
	private TextView price;

	private DataParse mData;

	@Override
	public void initListener() {

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.newtrade_record;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeMessages(1);

		super.onDestroy();
	}
}
