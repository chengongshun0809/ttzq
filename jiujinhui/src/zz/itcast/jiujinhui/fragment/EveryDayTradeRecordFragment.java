package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.bean.DomeBean;
import zz.itcast.jiujinhui.bean.DomeBean.DealpriceBean;
import zz.itcast.jiujinhui.mychart.DataParse;
import zz.itcast.jiujinhui.mychart.MyXAxis;
import zz.itcast.jiujinhui.mychart.MyYAxis;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EveryDayTradeRecordFragment extends BaseFragment {

	private SharedPreferences sp;
	@ViewInject(R.id.line_trade_chart_every)
	private zz.itcast.jiujinhui.mychart.MyLineChart lineChart;
	@ViewInject(R.id.tingpan_nodata)
	private RelativeLayout tingpan_nodata;

	@ViewInject(R.id.ll_time_price_every)
	private LinearLayout ll_time_price_every;// 闭盘时隐藏

	@ViewInject(R.id.date_every)
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
				//Log.e("测试", bean.getDealprice().toString() + "");
				if (isAdded()) {
					setDatas(bean.getDealprice());
				}

				break;

			default:
				break;
			}

		}

	};
	private String dgid;
	private String unionid;

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub

		sp = getActivity().getSharedPreferences("user", 0);

		ViewUtils.inject(this, view);
		if (isAdded()) {
			initLineChart();
		}

	}


	private SparseArray<String> stringSparseArray;

	private void setDatas(List<DealpriceBean> dealprice) {
		// TODO Auto-generated method stub
		lineChart.setDrawMarkerViews(true);

		mData = new DataParse();
		mData.domeprice_date(dealprice);
		date.setText(mData.getDatas_every().get(0).time);
		price_cheng.setText(mData.getDatas_every().get(0).beprice + "");
		price_trans.setText(mData.getDatas_every().get(0).byprice + "");

		// stringSparseArray = setXLabelss();
		SparseArray<String> xLabels = new SparseArray<String>();

		xLabels.put(0, mData.getDatas_every().get(0).time);

		xLabels.put(mData.getDatas_every().size() / 2, mData.getDatas_every()
				.get(mData.getDatas_every().size() / 2).time);

		xLabels.put(mData.getDatas_every().size() - 1, mData.getDatas_every()
				.get(mData.getDatas_every().size() - 1).time);

		xAxisLine.setXLabels(xLabels);

		ArrayList<Entry> lineCJEntries = new ArrayList<Entry>();
		ArrayList<Entry> lineTransEntries = new ArrayList<Entry>();
		ArrayList<String> dateList = new ArrayList<String>();

		for (int i = 0; i < mData.getDatas_every().size(); i++) {
			// Log.e("chengjiaojiagge", mData.getDatas_every().get(i).beprice + "");
			
			
			lineCJEntries.add(new Entry(mData.getDatas_every().get(i).beprice,
					i));
			lineTransEntries.add(new Entry(
					mData.getDatas_every().get(i).byprice, i));

			dateList.add(mData.getDatas_every().get(i).time);

		}

		LineDataSet d1 = new LineDataSet(lineCJEntries, "成交价");
		LineDataSet d2 = new LineDataSet(lineTransEntries, "转让价");
		d1.setDrawValues(false);
		d2.setDrawValues(false);
		d1.setCircleRadius(0);
		d2.setCircleRadius(0);

		d1.setColor(getResources().getColor(R.color.minute_blue));
		d2.setColor(getResources().getColor(R.color.minute_zhoutv));

		d1.setHighLightColor(getResources().getColor(R.color.minute_yellow));
		d2.setHighLightColor(getResources().getColor(R.color.minute_black)); //
		d2.setHighlightEnabled(false);

		d1.setDrawFilled(false);
		d1.setAxisDependency(YAxis.AxisDependency.LEFT);
		d2.setAxisDependency(YAxis.AxisDependency.LEFT);
		d2.setDrawFilled(false);
		ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
		sets.add(d1);
		sets.add(d2);
		// 注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断 LineData cd = new
		if (dateList != null && sets != null) {
			LineData cd = new LineData(dateList, sets);
			lineChart.setData(cd);
			lineChart.notifyDataSetChanged();
			lineChart.invalidate();// 刷新图
		}
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
					
						
						int index = e.getXIndex();
						price_cheng.setText(mData.getDatas_every().get(index).beprice + "");
						//Log.e("成交价", mData.getDatas_every().get(index).beprice  + "");
						date.setText(mData.getDatas_every().get(index).time);
						price_trans
								.setText(mData.getDatas_every().get(index).byprice
										+ "");
						//Log.e("转让价", mData.getDatas_every().get(index).byprice + "");
					}

					@Override
					public void onNothingSelected() {

					}
				});

	}

	MyXAxis xAxisLine;
	MyYAxis axisRightLine;
	MyYAxis axisLeftLine;
	private DataParse mData;

	private void initLineChart() {
		// TODO Auto-generated method stub
		lineChart.setNoDataText("");
		lineChart.setNoDataTextDescription("");
		lineChart.setScaleEnabled(false);
		lineChart.setAlpha(0.8f);
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
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {

				String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
						+ unionid + "&dgid=" + dgid;

				// String url_serviceinfo =
				// "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid=o9zBFwSKbKwfv2lXj0mLpKdRplS0&dgid=DG170112210454522";

				try {
					HttpsURLConnection connection = NetUtils.httpsconnNoparm(
							url_serviceinfo, "POST");
					int code = connection.getResponseCode();
					if (code == 200) {
						iStream = connection.getInputStream();
						String infojson = NetUtils.readString(iStream);
						// Log.e("ssssssssssss", infojson);
						DomeBean bean = new Gson().fromJson(infojson,
								DomeBean.class);
						//Log.e("11111dealprice1", bean.dealprice.get(1) + "");
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
}
