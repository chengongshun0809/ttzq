package zz.itcast.jiujinhui.view;

import zz.itcast.jiujinhui.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class FinishProjectPopupWindows extends PopupWindow {
	private static final String TAG = "FinishProjectPopupWindows";  
	  
    private View mView;

	private RadioButton rb_friend;

	private RadioButton rb_friendcircle;

	private RelativeLayout cancel;  
   
  
    public FinishProjectPopupWindows(Activity context,  
            OnClickListener itemsOnClick) {  
        super(context);  
          
        
           
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
         mView = inflater.inflate(R.layout.popupwindow_share, null);    
           
        
         rb_friend = (RadioButton) mView.findViewById(R.id.rb_friend);
         
         rb_friendcircle = (RadioButton) mView.findViewById(R.id.rb_friendcircle);
           
         cancel = (RelativeLayout) mView.findViewById(R.id.cancel);
         
         
         // 设置按钮监听  
        cancel.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View v) {  
               
               dismiss();  
            }          
         });  
         rb_friend.setOnClickListener(itemsOnClick);  
         rb_friendcircle.setOnClickListener(itemsOnClick);  
        
          
        //设置PopupWindow的View    
            this.setContentView(mView);    
            //设置PopupWindow弹出窗体的宽    
            this.setWidth(LayoutParams.MATCH_PARENT);    
            //设置PopupWindow弹出窗体的高    
            this.setHeight(LayoutParams.WRAP_CONTENT);    
            //设置PopupWindow弹出窗体可点击    
             this.setFocusable(true);    
            //设置SelectPicPopupWindow弹出窗体动画效果    
            this.setAnimationStyle(R.style.Animation);  
            //实例化一个ColorDrawable颜色为半透明    
            ColorDrawable dw = new ColorDrawable(00000000);    
            //设置SelectPicPopupWindow弹出窗体的背景    
            this.setBackgroundDrawable(dw);   
    }  
}
