package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
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
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EveryDayTradeRecordFragment extends BaseFragment {

	private SharedPreferences sp;
	boolean stopThread = false;
	@ViewInject(R.id.line_chart)
	private lecho.lib.hellocharts.view.LineChartView lineChart;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			
			case 1:
				UpdateUI();

				break;
			case 2:
				initLineChart();
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

		initLineChart();// 初始化
		
	}

	private LineChartView lineChartView;
	private List<PointValue> begindealValues = new ArrayList<PointValue>();
	private List<PointValue> buybackdealValues = new ArrayList<PointValue>();
	private List<AxisValue> axisValuesX = new ArrayList<AxisValue>();

	// 画图表
	protected void UpdateUI() {
		// TODO Auto-generated method stub

		getAxisLables();// 获取x轴的标注 // getAyisLables();// 获取y轴的刻度;
		

		handler.sendEmptyMessage(2);
	}

	private void initLineChart() {
		// TODO Auto-generated method stub
		List<Line> lines = new ArrayList<Line>();
		Line line = new Line(begindealValues);
		Line line1 = new Line(buybackdealValues);

		line.setShape(ValueShape.CIRCLE);// 折线图上每个数据点的形状 这里是圆形 （有三种
											// ：ValueShape.SQUARE
											// ValueShape.CIRCLE
									// ValueShape.DIAMOND）
		line.setCubic(false);// 曲线是否平滑，即是曲线还是折线
		line.setFilled(false);// 是否填充曲线的面积
		 line.setHasLabels(true);// 曲线的数据坐标是否加上备注
		//
		line.setHasPoints(true);

		 line.setPointRadius(0);
		//line.setHasLabels(false);
		 line.setHasLabelsOnlyForSelected(true);
		// 点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line.setHasLines(true);// 是否用线显示。如果为false 则没有曲线只有点显示
		// line.setHasPoints(true);// 是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
		line.setColor(Color.RED);
		// 圆点半径
		// line.setPointRadius(2);
		line.setStrokeWidth(1);// 设置折线宽度
		line.setHasLabelsOnlyForSelected(true);
		// 回购的曲线
		line1.setShape(ValueShape.CIRCLE);// 折线图上每个数据点的形状 这里是圆形 （有三种
		// ：ValueShape.SQUARE
		// ValueShape.CIRCLE
		// ValueShape.DIAMOND）
		line1.setCubic(false);// 曲线是否平滑，即是曲线还是折线
		line1.setFilled(false);// 是否填充曲线的面积
		line1.setHasLabels(true);// 曲线的数据坐标是否加上备注
		//
		line1.setHasPoints(true);

		line1.setPointRadius(0);
		//line.setHasLabels(false);
		line1.setHasLabelsOnlyForSelected(true);
		// 点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line1.setHasLines(true);// 是否用线显示。如果为false 则没有曲线只有点显示
		// line.setHasPoints(true);// 是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
		line1.setColor(Color.BLACK);
		// 圆点半径
		// line.setPointRadius(2);
		line1.setStrokeWidth(1);// 设置折线宽度
		line1.setHasLabelsOnlyForSelected(true);

		lines.add(line);
		lines.add(line1);
		LineChartData data = new LineChartData();
		data.setLines(lines);

		// 坐标轴
		Axis axisX = new Axis(); // X轴
		axisX.setHasTiltedLabels(true); // X坐标轴字体是斜的显示还是直的，true是斜的显示
		axisX.setTextColor(Color.GRAY); // 设置字体颜色
		axisX.setName("红色线:成交价         黑色线:转让价"); // 表格名称
		axisX.setTextSize(10);// 设置字体大小
		//axisX.setMaxLabelChars(6); // 最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
		axisX.setValues(axisValuesX); // 填充X轴的坐标值
		data.setAxisXBottom(axisX); // x 轴在底部
		// data.setAxisXTop(axisX); //x 轴在顶部
		axisX.setHasLines(true); // x 轴分割

		// Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
		Axis axisY = new Axis(); // Y轴
		axisY.setName("");// y轴标注
		axisY.setTextSize(10);// 设置字体大小
		axisY.setHasLines(true);
		data.setAxisYLeft(axisY); // Y轴设置在左边
		// data.setAxisYRight(axisY); //y轴设置在右边

	/*	data.setValueLabelBackgroundEnabled(true);
		data.setValueLabelsTextColor(Color.RED);*/

		// 设置行为属性，支持缩放、滑动以及平移
		lineChart.setInteractive(true);
		lineChart.setZoomEnabled(false);
		lineChart.setScrollEnabled(true);
		lineChart.setLineChartData(data);
		lineChart.setValueSelectionEnabled(true);
		lineChart.setValueTouchEnabled(true);
		lineChart.setVisibility(View.VISIBLE);
		// lineChart.startDataAnimation();
		lineChart.setViewportCalculationEnabled(true);
		Animation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1000);
		lineChart.startAnimation(animation);
	}

/*	// 图中每个点的显示
	private void getAxisPoints() {

		for (int i = 0; i < beginpriceList.size(); i++) { //
			begindealValues.add(new PointValue(i, beginpriceList.get(i)));
			// Log.e("eeeeeeeeeeeeee", "ssss");
			buybackdealValues.add(new PointValue(i, buybackpriceList.get(i)));
		}
	}*/

	// X轴刻度的显示
	private void getAxisLables() { // TODO Auto- generated method
		for (int i = 0; i < timeList.size(); i++) {
			begindealValues.add(new PointValue(i, beginpriceList.get(i)));
			// Log.e("eeeeeeeeeeeeee", "ssss");
			buybackdealValues.add(new PointValue(i, buybackpriceList.get(i)));
			axisValuesX.add(new AxisValue(i).setValue(i).setLabel(timeList.get(i)));

			// Log.e("eeeeeeeeeeeeee", "sssfsdfsas");
		}

	}

	public static List<String> timeList;
	public static List<Float> beginpriceList;
	public static List<Float> buybackpriceList;

	// String[] times = { "09:00", "11:30", "15:00", "22:00" };
	private String stateString;

	

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

						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");

						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("ssssssssss", jsonObject.toString());
							parseJson(jsonObject);
						
							connection.disconnect();
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
		handler.removeMessages(2);
		 timeList=null;
		 beginpriceList=null;
		 buybackpriceList=null;
		 begindealValues.clear();
		 buybackdealValues.clear();
		
		
		super.onDestroy();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
}
