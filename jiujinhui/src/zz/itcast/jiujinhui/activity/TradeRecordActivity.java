package zz.itcast.jiujinhui.activity;

import java.util.ArrayList;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.TradeAdvanceFragment;
import zz.itcast.jiujinhui.fragment.TradeAllFragment;
import zz.itcast.jiujinhui.fragment.TradeBuyFragment;
import zz.itcast.jiujinhui.fragment.TradeSaleFragment;
import zz.itcast.jiujinhui.fragment.TradeTihuoFragment;
import zz.itcast.jiujinhui.fragment.TradeTransFragment;
import zz.itcast.jiujinhui.fragment.TraderengouFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TradeRecordActivity extends BaseActivity {
	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip tabs;
	@ViewInject(R.id.pager)
	private ViewPager pager;
	private ArrayList<Fragment> fragmentsList;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back) 
	private ImageView tv_back;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("交易记录");

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new TradeAllFragment());
		fragmentsList.add(new TraderengouFragment());
		fragmentsList.add(new TradeBuyFragment());
		fragmentsList.add(new TradeSaleFragment());
		fragmentsList.add(new TradeTihuoFragment());
		fragmentsList.add(new TradeTransFragment());
		fragmentsList.add(new TradeAdvanceFragment());

		
		
		pager.setAdapter(new MypagerAdapter(getSupportFragmentManager(),
				fragmentsList));
		tabs.setViewPager(pager);
		tabs.setShouldExpand(true);
	   // pager.setOffscreenPageLimit(4);
	}

	public class MypagerAdapter extends FragmentStatePagerAdapter {
		private ArrayList<Fragment> fragmentslist=null;

		public MypagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentslist = fragments;
		}

		public MypagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		private final String[] titles = { "全部", "认购", "买入","卖出","提货","转让","充值" };

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentslist.get(position);
		}
         @Override
        public int getItemPosition(Object object) {
        	// TODO Auto-generated method stub
        	return MypagerAdapter.POSITION_NONE;
        }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length;
		}
       
          
        
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
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

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.trade_record;
	}

}
