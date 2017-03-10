package zz.itcast.jiujinhui.bean;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.jiujinhui.bean.DomeBean;
import zz.itcast.jiujinhui.bean.MinutesBean;
import android.util.SparseArray;

/**
 * Created by Administrator on 2016/11/29. 对行情走势做数据处理的类
 */
public class DataParse {

	private ArrayList<MinutesBean> datas = new ArrayList<MinutesBean>();// 分时图总数据

	// private SparseArray<String> xValuesLabel = new SparseArray<>();

	private float baseValue;// 基准
	private float permaxmin;// 最大差

	private SparseArray<String> xValuesLabel = new SparseArray<String>();
	private MinutesBean minutesDataBean;

	public void domeMinutes(List<DomeBean.TodaydealBean> todaydeal) {
		baseValue = todaydeal.get(0).getPrice();
		for (int i = 0; i < todaydeal.size(); i++) {
			if (todaydeal.get(i).getPrice() != -1) {
				MinutesBean minutesData = new MinutesBean();
				minutesData.cjprice = todaydeal.get(i).getPrice()/100;
				minutesData.time = todaydeal.get(i).getCreatetime().substring(10, 16);
				double cha = minutesData.cjprice - baseValue;
				if (Math.abs(cha) > permaxmin) {
					permaxmin = (float) Math.abs(cha);
				}

				datas.add(minutesData);
			}

		}
		if (permaxmin == 0) {
			permaxmin = baseValue * 0.02f;
		}

	}

	/**
	 * 将时间转换为需要的格式
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateToString(String time) {
		time = time.substring(2, 4) + "-" + time.substring(4, 6) + "-"
				+ time.substring(6);
		return time;
	}

	public float getBaseValue() {
		return baseValue;
	}

	public float getMin() {
		return baseValue - (permaxmin * 1.05f);
	}

	public float getMax() {
		return baseValue + (permaxmin * 1.05f);
	}

	public float getPercentMax() {
		return (permaxmin * 1.05f) / baseValue;
	}

	public float getPercentMin() {
		return -getPercentMax();
	}

	public MinutesBean getMinutesDataBean() {
		return minutesDataBean;
	}

	public ArrayList<MinutesBean> getDatas() {
		return datas;
	}

	public SparseArray<String> getXValuesLabel() {
		return xValuesLabel;
	}

	public void clear() {
		datas.clear();
		xValuesLabel.clear();
		baseValue = 0;
		permaxmin = 0;
	}
}
