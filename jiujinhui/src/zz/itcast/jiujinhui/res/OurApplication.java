package zz.itcast.jiujinhui.res;

import android.app.Application;
import android.content.Context;

public class OurApplication extends Application {
	 private static Context context;
	  @Override
	    public void onCreate() {
	        super.onCreate();
	        CrashHandler handler = CrashHandler.getInstance();
	        handler.init(getApplicationContext()); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
	        context = this;
	  
	  
	  }
	
	    public static Context getContext() {
	        return context;
	     }
	
}
