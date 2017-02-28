package zz.itcast.jiujinhui.res;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

public class ActivityCollector {
	 public static List<FragmentActivity> activities = new ArrayList<FragmentActivity>();

	    public static void addActivity(FragmentActivity activity) {
	        activities.add(activity);
	    }

	    public static void removeActivity(FragmentActivity activity) {
	        activities.remove(activity);
	    }

	    public static void finishAll() {
	        for (Activity activity : activities) {
	            if (!activity.isFinishing()) {
	                activity.finish();
	            }
	        }
	    }
}
