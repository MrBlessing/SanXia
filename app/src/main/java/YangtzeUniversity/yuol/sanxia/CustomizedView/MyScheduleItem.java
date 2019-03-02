package YangtzeUniversity.yuol.sanxia.CustomizedView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import YangtzeUniversity.yuol.sanxia.R;

public class MyScheduleItem extends FrameLayout {
    private  CardView cardView;
    private TextView textView;

    //随机颜色表


    public MyScheduleItem( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        //将自定义布局加载
        View view = LayoutInflater.from(context).inflate(R.layout.my_schedule_item,this);
        cardView = view.findViewById(R.id.my_schedule_item_card);
        textView = view.findViewById(R.id.my_schedule_item_text);

        //设置CardView的透明度,半透明
        cardView.getBackground().setAlpha(125);

        //设置CardView的背景颜色，使用


    }

    public void setBackGround(int color) {
        cardView.setBackgroundColor(color);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setContainerVisible(int flag){
        cardView.setVisibility(flag);
    }
}
