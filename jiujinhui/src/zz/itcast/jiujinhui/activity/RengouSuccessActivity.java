package zz.itcast.jiujinhui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class RengouSuccessActivity extends BaseActivity {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.wancheng)
	private RelativeLayout wancheng;
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.rengousuccess;
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("认购");
		tv_back.setVisibility(View.GONE);
		wancheng.setOnClickListener(this);
	}
     @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	super.onClick(v);
    	switch (v.getId()) {
		case R.id.wancheng:
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
