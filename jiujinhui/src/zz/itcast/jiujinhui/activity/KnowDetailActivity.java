package zz.itcast.jiujinhui.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class KnowDetailActivity extends BaseActivity {
  
	@ViewInject(R.id.webview)
	private WebView webview;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.know_detail_activity;
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		
		tv__title.setText("五年经典投资");
		tv__title.setTextSize(22);
	}
	

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				webview.getSettings().setJavaScriptEnabled(true); 
				webview.loadUrl("http://www.4001149114.com/NLJJ/shop/uploadpic"
						);
			}
		}).start();
		
		
	
	}
	

}
