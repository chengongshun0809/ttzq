package zz.itcast.jiujinhui.activity;

import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class TixianSuccessActivity extends BaseActivity {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.money)
	private TextView money;
	@ViewInject(R.id.shouxumoney)
	private TextView shouxumoney;
	private String countString;
	private String shouxuString;
	@ViewInject(R.id.wancheng)
	private RelativeLayout wancheng;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#0.00");
		Bundle bundle = getIntent().getExtras();
		countString = bundle.getString("count");
		double count=Double.parseDouble(countString);
		shouxuString = bundle.getString("shouxufei");
		money.setText(df.format(count));
		shouxumoney.setText(shouxuString);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("提现详情");
		tv_back.setVisibility(View.GONE);
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.tixiansuccess;
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
		wancheng.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		
		case R.id.wancheng:
			
			Intent intent=new Intent(TixianSuccessActivity.this,MainActivity.class);
			startActivity(intent);
			Toast.makeText(getApplicationContext(), "提现申请成功", 0)
			.show();
			break;
		default:
			break;
		}
	}
}
