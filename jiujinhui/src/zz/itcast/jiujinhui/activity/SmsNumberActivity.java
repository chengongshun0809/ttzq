package zz.itcast.jiujinhui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class SmsNumberActivity extends BaseActivity {
      
	@ViewInject(R.id.SmsSubmit)
	private RelativeLayout SmsSubmit;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.smsnumberverification_activity;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		SmsSubmit.setOnClickListener(this);
		tv_back.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
              ViewUtils.inject(this);
              tv__title.setText("绑定手机号");
      		tv__title.setTextSize(22);
	}
        @Override
        public void onClick(View v) {
        	// TODO Auto-generated method stub
        	super.onClick(v);
        	switch (v.getId()) {
			case R.id.SmsSubmit:
			String extra = getIntent().getStringExtra("sms");
			if (extra.equals("tixian")) {
				Intent intent=new Intent(SmsNumberActivity.this,MyTiXianActivity.class);
				startActivity(intent);
				
			}else if (extra.equals("recharge")) {
				Intent intent1=new Intent(SmsNumberActivity.this,ReChargeActivity.class);
				startActivity(intent1);
			}
				finish();
				break;
			case R.id.tv_back:
				finish();
				break;
			default:
				break;
			}
        	
        	
        }
}
