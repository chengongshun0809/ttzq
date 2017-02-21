package zz.itcast.jiujinhui.res;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.json.JSONObject;

import android.app.DownloadManager.Request;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;



public class NetUtils {
	private static SSLSocketFactory ssf;
	private static URL url;
	private static HttpsURLConnection conn;

	public static byte[] readBytes(InputStream is) {
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			baos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readString(InputStream is) {
		return new String(readBytes(is));
	}

	public static SSLSocketFactory getssf() {

		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tm, new java.security.SecureRandom());
			ssf = sslContext.getSocketFactory();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ssf;

	}

	/*public static HttpsURLConnection httpsconn(String urlpath,
			JSONObject jsonObject,String RequestMethod) {
		try {
			url = new URL(urlpath);
			SSLSocketFactory ssf =NetUtils.getssf();
			 conn=(HttpURLConnection) url.openConnection();
			//conn = (HttpsURLConnection) url.openConnection();
			//conn.setSSLSocketFactory(ssf);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 设置允许输出
			conn.setDoOutput(true);
			conn.setRequestMethod(RequestMethod);
			// 设置User-Agent: Fiddler
			conn.setRequestProperty("ser-Agent", "Fiddler");
			// 设置contentType
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(String.valueOf(jsonObject).getBytes());
			os.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}*/
	public static HttpsURLConnection httpsconnNoparm(String urlpath,
			String RequestMethod) {
		try {
			url = new URL(urlpath);
			
			SSLSocketFactory ssf =NetUtils.getssf();
			
			//conn = (HttpURLConnection) url.openConnection();
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setConnectTimeout(300000);//设置连接主机超时（单位：毫秒）
			conn.setReadTimeout(300000);//设置从主机读取数据超时（单位：毫秒）
			// 设置允许输出
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "close");
			conn.setRequestMethod(RequestMethod);
			// 设置User-Agent: Fiddler
			conn.setRequestProperty("ser-Agent", "Fiddler");
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置contentType
			conn.setRequestProperty("Content-Type", "application/json");
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}


}
