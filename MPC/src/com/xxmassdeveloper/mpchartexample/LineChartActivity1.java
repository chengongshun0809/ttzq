package com.xxmassdeveloper.mpchartexample;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import zz.itcast.jiujinhui.bean.DataParse;
import zz.itcast.jiujinhui.bean.DomeBean;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.xxmassdeveloper.mpchartexample.custom.MyMarkerView;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

public class LineChartActivity1 extends DemoBase implements
		OnSeekBarChangeListener, OnChartGestureListener,
		OnChartValueSelectedListener {

	private LineChart mChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_linechart);
		initData();

		mChart = (LineChart) findViewById(R.id.chart1);
		mChart.setOnChartGestureListener(this);
		mChart.setOnChartValueSelectedListener(this);
		mChart.setDrawGridBackground(false);

		// no description text
		mChart.setDescription("");
		mChart.setNoDataTextDescription("You need to provide data for the chart.");

		// enable value highlighting
		mChart.setHighlightEnabled(true);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);
		// mChart.setScaleXEnabled(true);
		// mChart.setScaleYEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(true);

		// set an alternative background color
		// mChart.setBackgroundColor(Color.GRAY);

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

		// set the marker to the chart
		mChart.setMarkerView(mv);

		XAxis xAxis = mChart.getXAxis();

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.removeAllLimitLines(); // reset all limit lines to avoid
										// overlapping lines

		leftAxis.setStartAtZero(false);
		// leftAxis.setYOffset(20f);
		leftAxis.enableGridDashedLine(10f, 10f, 0f);

		// limit lines are drawn behind data (and not on top)
		leftAxis.setDrawLimitLinesBehindData(true);

		mChart.getAxisRight().setEnabled(false);

		mChart.getViewPortHandler().setMaximumScaleY(2f);
		mChart.getViewPortHandler().setMaximumScaleX(2f);

		// add data

		// mChart.setVisibleXRange(20);
		// mChart.setVisibleYRange(20f, AxisDependency.LEFT);
		// mChart.centerViewTo(20, 50, AxisDependency.LEFT);

		mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
		// mChart.invalidate();

		// get the legend (only possible after setting data)
		Legend l = mChart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		l.setForm(LegendForm.LINE);

		// // dont forget to refresh the drawing
		// mChart.invalidate();
	}

/*	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 1:
				// 获取数据展示

				setData();
				;

				break;

			default:
				break;
			}

		};

	};*/

	private void initData() {
		// TODO Auto-generated method stub

		RequestParams params = new RequestParams(
				"https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid=o9zBFwSKbKwfv2lXj0mLpKdRplS0&dgid=DG170112210454522");
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				DomeBean bean = new Gson().fromJson(result, DomeBean.class);
				setDatas(bean.getTodaydeal());
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.line, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionToggleValues: {
			for (DataSet<?> set : mChart.getData().getDataSets())
				set.setDrawValues(!set.isDrawValuesEnabled());

			mChart.invalidate();
			break;
		}
		case R.id.actionToggleHighlight: {
			if (mChart.isHighlightEnabled())
				mChart.setHighlightEnabled(false);
			else
				mChart.setHighlightEnabled(true);
			mChart.invalidate();
			break;
		}
		case R.id.actionToggleFilled: {

			ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart
					.getData().getDataSets();

			for (LineDataSet set : sets) {
				if (set.isDrawFilledEnabled())
					set.setDrawFilled(false);
				else
					set.setDrawFilled(true);
			}
			mChart.invalidate();
			break;
		}
		case R.id.actionToggleCircles: {
			ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart
					.getData().getDataSets();

			for (LineDataSet set : sets) {
				if (set.isDrawCirclesEnabled())
					set.setDrawCircles(false);
				else
					set.setDrawCircles(true);
			}
			mChart.invalidate();
			break;
		}
		case R.id.actionToggleCubic: {
			ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart
					.getData().getDataSets();

			for (LineDataSet set : sets) {
				if (set.isDrawCubicEnabled())
					set.setDrawCubic(false);
				else
					set.setDrawCubic(true);
			}
			mChart.invalidate();
			break;
		}
		case R.id.actionToggleStartzero: {
			mChart.getAxisLeft().setStartAtZero(
					!mChart.getAxisLeft().isStartAtZeroEnabled());
			mChart.getAxisRight().setStartAtZero(
					!mChart.getAxisRight().isStartAtZeroEnabled());
			mChart.invalidate();
			break;
		}
		case R.id.actionTogglePinch: {
			if (mChart.isPinchZoomEnabled())
				mChart.setPinchZoom(false);
			else
				mChart.setPinchZoom(true);

			mChart.invalidate();
			break;
		}
		case R.id.actionToggleAutoScaleMinMax: {
			mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
			mChart.notifyDataSetChanged();
			break;
		}
		case R.id.animateX: {
			mChart.animateX(3000);
			break;
		}
		case R.id.animateY: {
			mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
			break;
		}
		case R.id.animateXY: {
			mChart.animateXY(3000, 3000);
			break;
		}
		case R.id.actionToggleFilter: {

			// the angle of filtering is 35°
			Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER,
					35);

			if (!mChart.isFilteringEnabled()) {
				mChart.enableFiltering(a);
			} else {
				mChart.disableFiltering();
			}
			mChart.invalidate();
			break;
		}
		case R.id.actionSave: {
			if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
				Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
						Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getApplicationContext(), "Saving FAILED!",
						Toast.LENGTH_SHORT).show();

			// mChart.saveToGallery("title"+System.currentTimeMillis())
			break;
		}
		}
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		// redraw
		mChart.invalidate();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	private void setDatas(List<DomeBean.TodaydealBean> todaydeal) {

		
		 DataParse mData = new DataParse();
	        mData.domeMinutes(todaydeal);

		  ArrayList<Entry> lineCJEntries = new ArrayList<Entry>();
	        for (int i = 0; i < mData.getDatas().size(); i++) {
	            lineCJEntries.add(new Entry(mData.getDatas().get(i).cjprice, i));
	        }


		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(lineCJEntries, "成交价");
		// set1.setFillAlpha(110);
		// set1.setFillColor(Color.RED);

		// set the line to be drawn like this "- - - - - -"
		set1.enableDashedLine(10f, 5f, 0f);
		set1.enableDashedHighlightLine(10f, 5f, 0f);
		set1.setColor(Color.BLACK);
		set1.setCircleColor(Color.BLACK);
		set1.setLineWidth(1f);
		set1.setCircleSize(0.01f);
		set1.setDrawCircleHole(false);
		set1.setValueTextSize(9f);
		set1.setFillAlpha(65);
		set1.setFillColor(Color.BLACK);
		// set1.setDrawFilled(true);
		// set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
		// Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1); // add the datasets

		// create a data object with the datasets
		LineData data = new LineData(new String[105], dataSets);

		// set data
		mChart.setData(data);
	}

	@Override
	public void onChartLongPressed(MotionEvent me) {
		Log.i("LongPress", "Chart longpressed.");
	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {
		Log.i("DoubleTap", "Chart double-tapped.");
	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {
		Log.i("SingleTap", "Chart single-tapped.");
	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
			float velocityY) {
		Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: "
				+ velocityY);
	}

	@Override
	public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
		Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
	}

	@Override
	public void onChartTranslate(MotionEvent me, float dX, float dY) {
		Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		Log.i("Entry selected", e.toString());
		Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: "
				+ mChart.getHighestVisibleXIndex());
	}

	@Override
	public void onNothingSelected() {
		Log.i("Nothing selected", "Nothing selected.");
	}
}
