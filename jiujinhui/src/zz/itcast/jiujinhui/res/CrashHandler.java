package zz.itcast.jiujinhui.res;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler instance; // 单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例

	private CrashHandler() {
	}

	public synchronized static CrashHandler getInstance() { // 同步方法，以免单例多线程环境下出现异常
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}

	public void init(Context ctx) { // 初始化，把当前对象设置成UncaughtExceptionHandler处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) { // 当有未处理的异常发生时，就会来到这里。。
		Log.d("Sandy", "uncaughtException, thread: " + thread + " name: "
				+ thread.getName() + " id: " + thread.getId() + "exception: "
				+ ex);
           
		 if (instance != null) {  
	            //如果用户没有处理则让系统默认的异常处理器来处理  
			 instance.uncaughtException(thread, ex);  
	        } else {  
	            try {  
	                Thread.sleep(3000);  
	            } catch (InterruptedException e) {  
	               // Log.e(TAG, "error : ", e);  
	            }  
	            //退出程序  
	            android.os.Process.killProcess(android.os.Process.myPid());  
	            System.exit(1);  
	        }  

	}

}
