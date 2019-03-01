package YangtzeUniversity.yuol.sanxia.Activity;

import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import YangtzeUniversity.yuol.sanxia.CustomizedView.MyActionBar;
import YangtzeUniversity.yuol.sanxia.Fragment.ClassSchedule;
import YangtzeUniversity.yuol.sanxia.Fragment.Home;
import YangtzeUniversity.yuol.sanxia.Fragment.Score;
import YangtzeUniversity.yuol.sanxia.R;
import YangtzeUniversity.yuol.sanxia.Utils.BasedActivity;

public class MainActivity extends BasedActivity {

    private BottomNavigationView bottomNavigationView;
    private MyActionBar actionBar;
    private DrawerLayout drawerLayout;
    //定义3个碎片
    private Home home;
    private ClassSchedule schedule;
    private Score score;
    //事务管理类
    private FragmentManager manager;
    //当前显示的碎片
    private Fragment currentFragment;
    //用于返回时计时
    private long first = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setEvent();
    }

    private void setEvent() {
        //初始化状态栏
        initActionBar();
        //初始化碎片
        initFragment();
        //初始化导航栏
        initNavigation();
        //设置底部栏的选择事件
        bottomNavigationView.setOnNavigationItemSelectedListener((item)->{
            switch (item.getItemId()){
                case R.id.bottom_navigation_home :
                    actionBar.setTitle("首页");
                    if(home==null) home = new Home();
                    changeFragment(home);
                    break;
                case R.id.bottom_navigation_personal:
                    actionBar.setTitle("个人");
                    if(schedule==null) schedule = new ClassSchedule();
                    changeFragment(schedule);
                    break;
                case R.id.bottom_navigation_Score:
                    actionBar.setTitle("成绩");
                    if (score == null) score = new Score();
                    changeFragment(score);

            }
            return true;
        });
    }

    private void initNavigation() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_navigation_header,null);
        ImageView imageView = header.findViewById(R.id.nav_header_image);
        TextView name = header.findViewById(R.id.nav_header_name);
        TextView id = header.findViewById(R.id.nav_header_id);

    }

    private void initFragment() {

        //开启碎片管理类
        manager = getSupportFragmentManager();
        //默认展现home碎片
        home = new Home();
        manager.beginTransaction().add(R.id.main_container,home).commit();
        currentFragment = home;
    }

    private void initActionBar() {
        actionBar.setLeftImage(R.drawable.headimage);
        actionBar.setTitle("首页");
        actionBar.setLeftImageClickEvent((view)->
            drawerLayout.openDrawer(GravityCompat.START)
        );
    }

    private void findView() {
        bottomNavigationView = findViewById(R.id.main_navigation);
        actionBar = findViewById(R.id.main_actionbar);
        drawerLayout = findViewById(R.id.main_drawer);
    }

    //更换fragment
    private void changeFragment(Fragment to){
        FragmentTransaction transaction = manager.beginTransaction();
        //是当前的碎片，就不必转换
        if(currentFragment == to) return ;
        if(!to.isAdded()) transaction.add(R.id.main_container,to);
        transaction
                .hide(currentFragment)
                .show(to)
                .commit();
        currentFragment = to;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if((System.currentTimeMillis()-first)<2000){
                        finish();
                    }else {
                        first = System.currentTimeMillis();
                        Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
