package YangtzeUniversity.yuol.sanxia.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import YangtzeUniversity.yuol.sanxia.Data.PersonalInfoData;
import YangtzeUniversity.yuol.sanxia.R;
import YangtzeUniversity.yuol.sanxia.Utils.BasedActivity;
import YangtzeUniversity.yuol.sanxia.Utils.LoginUtils;

public class StartActivity extends BasedActivity {
    private boolean loginState = false;
    //储存个人信息
    private PersonalInfoData info;
    //限时跳转
    private CountDownTimer timer = new CountDownTimer(2000,3000) {
        @Override
        public void onTick(long l) {

        }
        @Override
        public void onFinish() {
            //根据登陆状态跳转不同的页面
            if (loginState){
                //一个小彩蛋
                if("明美".equals(info.getName())){
                    Toast.makeText(StartActivity.this, "欢迎你回来,亲爱的!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(StartActivity.this, "欢迎你回来," + info.getName() + "!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent( StartActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(StartActivity.this,Login.class);
                startActivity(intent);
            }
            StartActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strar);
        //检查用户登陆状态
        new LoginUtils().isOnline(new LoginUtils.OnlineCheck() {
            @Override
            public void offLine() {
                loginState = false;
            }

            @Override
            public void onLine(PersonalInfoData info) {
                loginState = true;
                StartActivity.this.info = info;
            }
        });
        //加载背景图
        ImageView imageView = findViewById(R.id.start_image);
        Glide.with(this).load(R.drawable.bg_login).into(imageView);
        //开始计时
        timer.start();
    }
}
