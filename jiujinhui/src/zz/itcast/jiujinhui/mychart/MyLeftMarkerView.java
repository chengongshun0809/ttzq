package zz.itcast.jiujinhui.mychart;

import java.text.DecimalFormat;

import zz.itcast.jiujinhui.R;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * author：ajiang
 * mail：1025065158@qq.com
 * blog：http://blog.csdn.net/qqyanjiang
 */
public class MyLeftMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private  DecimalFormat mFormat;
    public MyLeftMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat=new DecimalFormat("#0.00");
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);
    }

    public void setData(float num){

        this.num=num;
    }
   /* @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(mFormat.format(num));
    }*/

   

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
	public void refreshContent(Entry e, Highlight highlight) {
		// TODO Auto-generated method stub
		
	}
}