package zz.itcast.jiujinhui.wxapi;


import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.activity.MainActivity;
import zz.itcast.jiujinhui.activity.ReChargeActivity;
import zz.itcast.jiujinhui.res.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		
		if (resp.errCode==0) {
			Toast.makeText(WXPayEntryActivity.this, "恭喜您充值成功,可以开心购买啦", 0).show();
			Intent intent=new Intent(WXPayEntryActivity.this,MainActivity.class);
			startActivity(intent);
			
			finish();
		}
		if (resp.errCode==-1) {
			Log.e("zhifu ", resp.toString());
			Toast.makeText(WXPayEntryActivity.this, "支付错误", Toast.LENGTH_SHORT).show();
			finish();
		}
		if (resp.errCode==-2) {
			Toast.makeText(WXPayEntryActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
			finish();
		}
		

		
	}
}