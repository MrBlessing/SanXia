package YangtzeUniversity.yuol.sanxia.CustomizedView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import YangtzeUniversity.yuol.sanxia.R;


public class MyActionBar extends FrameLayout {
    private ImageView leftImage;
    private ImageView rightImage;
    private TextView title;
    public MyActionBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //将自定义布局加载到该布局中
        LayoutInflater.from(context).inflate(R.layout.my_action_bar,this);
        leftImage = findViewById(R.id.action_bar_leftImage);
        rightImage = findViewById(R.id.action_bar_rightImage);
        title = findViewById(R.id.action_bar_title);
    }

    public void setTitle(String t) {
        this.title.setText(t);
    }

    public void setLeftImage(int image) {
        Glide.with(this)
                .load(image)
                .transform(new CircleCrop())
                .into(leftImage);
    }
    public void setRightImage(int image) {
        Glide.with(this)
                .load(image)
                .transform(new CircleCrop())
                .into(rightImage);
    }

    public void setLeftImage(String url) {
        Glide.with(this)
                .load(url)
                .transform(new CircleCrop())
                .into(leftImage);
    }
    public void setRightImage(String url) {
        Glide.with(this)
                .load(url)
                .transform(new CircleCrop())
                .into(rightImage);
    }

    public void setLeftImageClickEvent(OnClickListener listener){
        leftImage.setOnClickListener(listener);
    }
    public void setRightImageClickEvent(OnClickListener listener){
        rightImage.setOnClickListener(listener);
    }
}
