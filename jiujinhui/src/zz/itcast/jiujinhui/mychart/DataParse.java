package zz.itcast.jiujinhui.mychart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import zz.itcast.jiujinhui.bean.DomeBean;
import zz.itcast.jiujinhui.bean.DomeBean.DealpriceBean;
import zz.itcast.jiujinhui.bean.EverypriceBean;
import zz.itcast.jiujinhui.bean.MinutesBean;
import android.util.SparseArray;

/**
 * Created by Administrator on 2016/11/29. 对行情走势做数据处理的类
 */
public class DataParse {

	private ArrayList<MinutesBean> datas = new ArrayList<MinutesBean>();// 分时图总数据

	private ArrayList<EverypriceBean> datas_every = new ArrayList<EverypriceBean>();// 总数据
	// private SparseArray<String> xValuesLabel = new SparseArray<>();

	private float baseValue;// 基准
	private float permaxmin;// 最大差

	private SparseArray<String> xValuesLabel = new SparseArray<String>();
	private MinutesBean minutesDataBean;

	public void domeprice_date(List<DomeBean.DealpriceBean> dealprice) {

		for (int i = 0; i < dealprice.size(); i++) {
			EverypriceBean everybean = new EverypriceBean();
			everybean.beprice = dealprice.get(i).getRealprice() / 100;
			everybean.byprice = dealprice.get(i).getBuybackprice()/100;
			everybean.time = dealprice.get(i).getDealday().substring(5, dealprice.get(i).getDealday().length());

			datas_every.add(everybean);

		}

	}

	public void domeMinutes(List<DomeBean.TodaydealBean> todaydeal) {
		baseValue = todaydeal.get(0).getPrice();
		/*
		 * for (int i = 0; i < todaydeal.size(); i++) { if
		 * (todaydeal.get(i).getPrice() != -1) { MinutesBean minutesData = new
		 * MinutesBean(); minutesData.cjprice = todaydeal.get(i).getPrice() /
		 * 100; minutesData.time = todaydeal.get(i).getCreatetime()
		 * .substring(10, 16);
		 * 
		 * datas.add(minutesData); }
		 * 
		 * }
		 */

		for (int i = 0; i < 21; i++) {
			if (todaydeal.get(i).getPrice() != -1) {
				MinutesBean minutesData = new MinutesBean();
				minutesData.cjprice = todaydeal.get(i).getPrice() / 100;
				minutesData.time = todaydeal.get(i).getCreatetime()
						.substring(10, 16);

				datas.add(minutesData);
			} else {
				MinutesBean minutesData = new MinutesBean();

				// * minutesData.cjprice =0; minutesData.time ="";
				minutesData.time = todaydeal.get(i).getCreatetime()
						.substring(10, 16);
				minutesData.cjprice = -1;

				datas.add(minutesData);

			}

		}

		for (int i = 36; i < 49; i++) {
			if (todaydeal.get(i).getPrice() != -1) {
				MinutesBean minutesData = new MinutesBean();
				minutesData.cjprice = todaydeal.get(i).getPrice() / 100;
				minutesData.time = todaydeal.get(i).getCreatetime()
						.substring(10, 16);

				datas.add(minutesData);
			} else {
				MinutesBean minutesData = new MinutesBean();

				// * minutesData.cjprice = 0; minutesData.time ="";
				minutesData.time = todaydeal.get(i).getCreatetime()
						.substring(10, 16);
				minutesData.cjprice = -1;

				datas.add(minutesData);

			}

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

	/*
	 * public float getMin() { return baseValue - (permaxmin * 1.05f); }
	 * 
	 * public float getMax() { return baseValue + (permaxmin * 1.05f); }
	 * 
	 * public float getPercentMax() { return (permaxmin * 1.05f) / baseValue; }
	 * 
	 * public float getPercentMin() { return -getPercentMax(); }
	 */
	public MinutesBean getMinutesDataBean() {
		return minutesDataBean;
	}

	public ArrayList<MinutesBean> getDatas() {
		return datas;
	}

	public ArrayList<EverypriceBean> getDatas_every() {
		return datas_every;
	}

	public SparseArray<String> getXValuesLabel() {
		return xValuesLabel;
	}

	public void clear() {
		datas.clear();
		datas_every.clear();
		xValuesLabel.clear();
		/*
		 * baseValue = 0; permaxmin = 0;
		 */
	}
}
