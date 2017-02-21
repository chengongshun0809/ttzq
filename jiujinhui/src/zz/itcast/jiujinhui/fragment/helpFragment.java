package zz.itcast.jiujinhui.fragment;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class helpFragment extends BaseFragment {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	
	
	
	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
		tv_back.setVisibility(view.GONE);
		tv__title.setText("关于我们");
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.frag_help;
	}

}
