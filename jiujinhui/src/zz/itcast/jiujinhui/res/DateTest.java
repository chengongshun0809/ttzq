package zz.itcast.jiujinhui.res;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;

public class DateTest {

	public DateTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static long nowtime;



	@SuppressLint("SimpleDateFormat")
	public boolean isNowDate(long date, Calendar cal) {
		boolean flag = false;
		nowtime =date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String now_time = sdf.format(date);
		String nyr = now_time.substring(0, 11);

		String start1 = nyr + "09:00";
		String timeEnd1 = nyr + "12:30";

		String start2 = nyr + "13:00";
		String timeEnd2 = nyr + "18:00";

		try {
			long start11 = sdf.parse(start1).getTime();
			long timeEnd11 = sdf.parse(timeEnd1).getTime();

			long start22 = sdf.parse(start2).getTime();
			long timeEnd22 = sdf.parse(timeEnd2).getTime();

			// 判断是周几

			// cal.setTime(date);
			int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
			if (week == 0 || week == 6) {

				flag = false;

			} else {
				if (start11 <= nowtime && nowtime <= timeEnd22) {

					if (start11 <= nowtime && nowtime <= timeEnd11
							|| start22 <= nowtime && nowtime <= timeEnd22) {

						// showBuyDialog();
						flag = true;
					} else {
						// 未到开市时间
						flag = false;
					}

				} else {

					flag = false;

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

	public boolean isNowDate_nowtrade(Date date, Calendar cal) {
		boolean flag = false;

		nowtime = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now_time = sdf.format(date);
		String nyr = now_time.substring(0, 11);

		String start1 = nyr + "09:00:10";
		String timeEnd1 = nyr + "11:30:00";

		String start2 = nyr + "13:30:00";
		String timeEnd2 = nyr + "15:00:00";

		try {
			long start11 = sdf.parse(start1).getTime();
			long timeEnd11 = sdf.parse(timeEnd1).getTime();

			long start22 = sdf.parse(start2).getTime();
			long timeEnd22 = sdf.parse(timeEnd2).getTime();

			// 判断是周几

			// cal.setTime(date);
			int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			Log.e("sfa", week + "");
			if (week == 0 || week == 6) {
				/*
				 * if (start11 <= nowtime && nowtime <= timeEnd22) {
				 * 
				 * flag = true; } else { // 未到开市时间
				 * 
				 * flag = false;
				 * 
				 * }
				 */
				flag = false;

			} else {
				if (start11 <= nowtime && nowtime <= timeEnd22) {

					flag = true;

				} else {

					flag = false;

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

}
