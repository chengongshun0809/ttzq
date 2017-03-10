package zz.itcast.jiujinhui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class SaleSuccessActivity extends BaseActivity {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.wancheng)
	private RelativeLayout wancheng;
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.sale_success;
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("卖出");
		tv_back.setVisibility(View.GONE);
		wancheng.setOnClickListener(this);
	}
     @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	super.onClick(v);
    	switch (v.getId()) {
		case R.id.wancheng:
			Toast.makeText(getApplicationContext(), "恭喜您，卖出成功", 0).show();
			finish();
			break;

		default:
			break;
		}
    	
    }
     
     
     
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	
}