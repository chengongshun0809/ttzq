package zz.itcast.jiujinhui.mychart;

import java.text.DecimalFormat;

import zz.itcast.jiujinhui.R;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;

/**
 * author：ajiang
 * mail：1025065158@qq.com
 * blog：http://blog.csdn.net/qqyanjiang
 */
public class MyRightMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private DecimalFormat mFormat;
    public MyRightMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat = new DecimalFormat("#0.00");
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);

    }

    public void setData(float yValForXIndex2){
        this.num=yValForXIndex2;
    }
   

	@Override
	public int getXOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public void refreshContent(Entry e,
			com.github.mikephil.charting.highlight.Highlight highlight) {
		// TODO Auto-generated method stub
		
	}
}
