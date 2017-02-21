package zz.itcast.jiujinhui.activity;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.BaseFragment;
import zz.itcast.jiujinhui.fragment.DrinkIncomeFragment;
import zz.itcast.jiujinhui.fragment.TradeAllFragment;
import zz.itcast.jiujinhui.fragment.drinkzhichuFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DrinkRecordActivity extends BaseActivity {
	@ViewInject(R.id.radiogroup)
	private RadioGroup radiogroup;
	@ViewInject(R.id.rb_income)
	private RadioButton rb_income;
	@ViewInject(R.id.rb_zhichu)
	private RadioButton rb_zhichu;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	private List<BaseFragment> fragments;
	private FragmentManager fm;
	

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.drink_record;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new DrinkIncomeFragment());
		fragments.add(new drinkzhichuFragment());
		
		radiogroup.check(R.id.rb_income);

	}

	int currentItem;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		fm = getSupportFragmentManager();
		tv__title.setText("我的酒币");
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {

				case R.id.rb_income:
					currentItem = 0;// ������Ǯ
					break;
				case R.id.rb_zhichu:
					currentItem = 1;// ��������
					break;
				
				default:
					break;

				}
				fm.beginTransaction()
						.replace(R.id.fl, fragments.get(currentItem)).commit();
			}
		});
		tv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
