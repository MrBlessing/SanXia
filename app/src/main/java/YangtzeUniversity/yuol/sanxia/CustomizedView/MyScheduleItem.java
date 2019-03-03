package YangtzeUniversity.yuol.sanxia.CustomizedView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import YangtzeUniversity.yuol.sanxia.R;

public class MyScheduleItem extends FrameLayout {
    private  CardView cardView;
    private TextView textView;
    private static final String TAG = "MyScheduleItem";
    //随机颜色表
    private static List<Integer> colorBox = new ArrayList<>();
    //字符串和颜色对应的集合
    private static Map<String,Integer> sAndC = new HashMap<>();

    public MyScheduleItem( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        //将自定义布局加载
        View view = LayoutInflater.from(context).inflate(R.layout.my_schedule_item,this);
        cardView = view.findViewById(R.id.my_schedule_item_card);
        textView = view.findViewById(R.id.my_schedule_item_text);
    }

    //设置显示文字
    public void setText(String text) {
        textView.setText(text);
        setCardBgColor(text);
//        //设置CardView的透明度,半透明
//        cardView.getBackground().setAlpha(125);
    }

    //设置背景颜色
    private void setCardBgColor(String text){
        //根据字符串的前4个字给背景填色
        String s = text.substring(0,4);
        int color = 0 ;
        //集合中没有这个字符串
        if(sAndC.get(s)==null){
            if(colorBox.size()==0){
                //颜色不够选的情况下填充颜色盒子
                fillColorBox();
            }
            //颜色够选的话用第一个颜色，并将这种颜色从颜色盒子中移除
            color = colorBox.remove(0);
            //将颜色和字符串建立联系
            sAndC.put(s,color);
        }else {
            color = sAndC.get(s);
        }
        cardView.setBackgroundColor(getResources().getColor(color));
    }

    //设置卡片可视
    public void setContainerVisible(int flag){
        cardView.setVisibility(flag);
    }

    //填充颜色盒子
    private void fillColorBox() {
        if(colorBox.size()==0){
            colorBox.add(R.color.chengHuang);
            colorBox.add(R.color.congHuang);
            colorBox.add(R.color.xingHong);
            colorBox.add(R.color.hunHuang);
            colorBox.add(R.color.yu);
            colorBox.add(R.color.weiNan);
            colorBox.add(R.color.dinXiang);
            colorBox.add(R.color.ziTang);
        }
    }

}
