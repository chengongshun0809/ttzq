package zz.itcast.jiujinhui.res;

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
	public  boolean isNowDate(Date  date,Calendar cal ) {
		boolean flag=false;
		//Date date = new Date();
		nowtime = date.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String now_time = sdf.format(date);
		String nyr = now_time.substring(0, 11);

		String start1 = nyr + "09:00";
		String timeEnd1 = nyr + "11:30";

		String start2 = nyr + "13:30";
		String timeEnd2 = nyr + "15:00";

		String start3 = nyr + "20:30";
		String timeEnd3 = nyr + "22:00";

		String start4 = nyr + "20:30";
		String timeEnd4 = nyr + "22:00";
		try {
			long start11 = sdf.parse(start1).getTime();
			long timeEnd11 = sdf.parse(timeEnd1).getTime();

			long start22 = sdf.parse(start2).getTime();
			long timeEnd22 = sdf.parse(timeEnd2).getTime();

			long start33 = sdf.parse(start3).getTime();
			long timeEnd33 = sdf.parse(timeEnd3).getTime();

			long start44 = sdf.parse(start4).getTime();
			long timeEnd44 = sdf.parse(timeEnd4).getTime();
			// 判断是周几
			
			//cal.setTime(date); 
			int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			Log.e("sfa", week+"");
			if (week == 0||week == 6) {
				if (start33 <= nowtime && nowtime <= timeEnd33) {
					
					flag=true;
				} else {
					// 未到开市时间

					flag=false;

				}

			} else {
				if (start11 <= nowtime && nowtime <= timeEnd11
						|| start22 <= nowtime && nowtime <= timeEnd22
						|| start33 <= nowtime && nowtime <= timeEnd33) {
                    
					// showBuyDialog();
					flag=true;
				} else {
					// 未到开市时间
					flag=false;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

}
