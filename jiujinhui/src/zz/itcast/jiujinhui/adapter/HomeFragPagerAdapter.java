package zz.itcast.jiujinhui.adapter;

import java.util.List;

import zz.itcast.jiujinhui.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.BitmapUtils;

/**
 * @Description 首页轮播图的适配器
 * @author dp
 * @date 2016-3-11 下午1:47:16
 * @param <String>
 */
public class HomeFragPagerAdapter extends PagerAdapter {
	private List<String> data;
	private Context context;
	private BitmapUtils bitmapUtils;

	public HomeFragPagerAdapter(Context ctx, List<String> data) {
		this.data = data;
		this.context = ctx;
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// // 注意在此不要做任何操作，因为我们需要实现向左滑动，否则会产生IndexOutOfBoundsException
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		String imgUrl = data.get(position % data.size());
		View view = View.inflate(context, R.layout.item_viewpager_home_frag,
				null);
		if (!TextUtils.isEmpty(imgUrl)) {
			bitmapUtils.display(view, imgUrl);
		}
		container.addView(view);
		return view;
	}

	@Override
	public int getCount() {
		return  data.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
