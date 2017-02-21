package zz.itcast.jiujinhui.fragment;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.activity.LoginActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NoLoginPersonFragment extends BaseFragment {
	@ViewInject(R.id.btn_nologin_fragment)
	private Button btn_nologin_fragment;
	
	@ViewInject (R.id.tv__title)
	private TextView tv_title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.zongzichan)
	private LinearLayout zongzichan;
	@ViewInject(R.id.drink_record)
	private LinearLayout drink_record;
	@ViewInject(R.id.trade_record)
	private LinearLayout trade_record;
	@ViewInject(R.id.tixianRecord)
	private LinearLayout tixianRecord;
	@ViewInject(R.id.tixian)
	private Button tixian;
	@ViewInject(R.id.recharge)
	private Button recharge;
	
	
	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
		tv_title.setText("个人中心");
		tv_back.setVisibility(view.GONE);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		btn_nologin_fragment.setOnClickListener(this);
		zongzichan.setOnClickListener(this);
		drink_record.setOnClickListener(this);
		trade_record.setOnClickListener(this);
		tixianRecord.setOnClickListener(this);
		tixian.setOnClickListener(this);
		recharge.setOnClickListener(this);
	}

	
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.nologinperson_fragment;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_nologin_fragment:
			Intent intent0 = new Intent(getActivity(), LoginActivity.class);
			intent0.putExtra("happy", "happy");
			startActivityForResult(intent0, 3);

			break;
		case R.id.zongzichan:// 总资产
			Intent intent1 = new Intent(getActivity(), LoginActivity.class);
			intent1.putExtra("happy", "happy");
			startActivityForResult(intent1, 3);

			break;
		case R.id.drink_record:// 酒币记录
			Intent intent2 = new Intent(getActivity(), LoginActivity.class);
			intent2.putExtra("happy", "happy");
			startActivityForResult(intent2, 3);

			break;
		case R.id.trade_record:// 交易记录
			Intent intent3 = new Intent(getActivity(), LoginActivity.class);
			intent3.putExtra("happy", "happy");
			startActivityForResult(intent3, 3);


			break;
		case R.id.tixianRecord:// 提现记录
			Intent intent4 = new Intent(getActivity(), LoginActivity.class);
			intent4.putExtra("happy", "happy");
			startActivityForResult(intent4, 3);

			break;
		case R.id.tixian:// 点击提现
			Intent intent5 = new Intent(getActivity(), LoginActivity.class);
			intent5.putExtra("happy", "happy");
			startActivityForResult(intent5, 3);

			break;

		case R.id.recharge:// 点击充值
			Intent intent6 = new Intent(getActivity(), LoginActivity.class);
			intent6.putExtra("happy", "happy");
			startActivityForResult(intent6, 3);

			break;
		default:
			break;
		}

		super.onClick(v);
	}
}
