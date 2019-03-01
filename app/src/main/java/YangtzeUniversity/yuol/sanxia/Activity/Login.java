package YangtzeUniversity.yuol.sanxia.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import YangtzeUniversity.yuol.sanxia.R;
import YangtzeUniversity.yuol.sanxia.Utils.BasedActivity;
import YangtzeUniversity.yuol.sanxia.Utils.LoginUtils;

public class Login extends BasedActivity {

    //登陆按钮
    private Button go;
    //验证码图片
    private ImageView securityCodeImage;
    //学号输入框
    private EditText account;
    //密码输入框
    private EditText password;
    //验证码输入框
    private EditText securityCode;
    //登陆助手
    LoginUtils loginUtils ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (loginUtils==null)
            loginUtils = new LoginUtils();
        findView();
        setEvent();
    }

    private void setEvent() {
        //显示出验证码
        loginUtils.getCookieAndSecurityCode((bitmap)->
                runOnUiThread(()->
                        Glide.with(Login.this).load(bitmap).into(securityCodeImage))
        );

        //登陆按钮的点击事件
        go.setOnClickListener((v)->{
            //开始登陆
            loginUtils.Login(account.getText().toString(), password.getText().toString(), securityCode.getText().toString(), new LoginUtils.onLoginEnd() {
                @Override
                public void onAccountError() {
                    showDialog("登陆失败","请检查你的账号",(d,w)->{});
                }

                @Override
                public void onPasswordError() {
                    showDialog("登陆失败","请检查你的账号或密码",(d,w)->{});
                }

                @Override
                public void onSecurityCodeError() {
                    showDialog("登陆失败","验证码输入错误",(d,w)->{});
                }

                @Override
                public void onLoginSuccess(String info) {
                    showDialog("登陆成功",info,(d,w)->{
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                        Login.this.finish();
                    });
                }
            }) ;
        });
    }

    private void showDialog(String title, String message,DialogInterface.OnClickListener listener) {
       runOnUiThread(()->{
           new AlertDialog.Builder(this)
                   .setTitle(title)
                   .setMessage("\n\t"+message)
                   .setCancelable(false)
                   .setPositiveButton("嗯，好的", listener)
                   .create()
                   .show();
       });
    }

    private void findView() {
        go  = findViewById(R.id.login_go);
        securityCodeImage = findViewById(R.id.login_securityCodeImage);
        account = findViewById(R.id.login_account);
        password = findViewById(R.id.login_password);
        securityCode = findViewById(R.id.login_securityCode);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
            // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
