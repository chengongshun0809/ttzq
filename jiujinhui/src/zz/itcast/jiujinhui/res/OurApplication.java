package zz.itcast.jiujinhui.res;

import android.app.Application;

public class OurApplication extends Application {

	  @Override
	    public void onCreate() {
	        super.onCreate();
	        CrashHandler handler = CrashHandler.getInstance();
	        handler.init(getApplicationContext()); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
	    }
	
	
	
}
