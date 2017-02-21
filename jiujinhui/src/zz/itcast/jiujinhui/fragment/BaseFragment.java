package zz.itcast.jiujinhui.fragment;

import zz.itcast.jiujinhui.res.Tools;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), getLayoutResID(), null);
		initView(view);
		initListener();
		initData();
		return view;

	}

	public abstract void initData() ;

	public abstract void initListener() ;

	public abstract void initView(View view) ;

	public abstract int getLayoutResID();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (Tools .isFastDoubleClick()) {
	        return;
	    }
	}

}
