package zz.itcast.jiujinhui.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

public class ForgetPwdActivity extends BaseActivity {
    @ViewInject(R.id.compMod)
    private RelativeLayout compMod;
	

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.forgetpassword;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		compMod.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
            ViewUtils.inject(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
            switch (v.getId()) {
			case R.id.compMod:
				//修改完成的逻辑：当点击修改完成按钮时候，若修改成功，则finish
				
				
				finish();
				
				break;

			default:
				break;
			}
	}
}
