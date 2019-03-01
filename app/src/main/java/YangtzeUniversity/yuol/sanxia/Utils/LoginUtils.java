package YangtzeUniversity.yuol.sanxia.Utils;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import YangtzeUniversity.yuol.sanxia.Data.PersonalInfoData;
import YangtzeUniversity.yuol.sanxia.Data.Urls;
import YangtzeUniversity.yuol.sanxia.Interface.MyCallBack;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryPersonalInfoEnd;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class LoginUtils {
    private static final String TAG = "LoginUtils";

    //获取个人信息的工具类
    private PersonalInfoUtils personalInfoUtils;

    public LoginUtils(){
        //初始化工具类
        personalInfoUtils = new PersonalInfoUtils();
    }

    //登陆操作
    public void Login(String account , String password , String securityCode , onLoginEnd loginEnd){
        /*第一步、访问登陆页面，获取其中的隐藏域*/
        Request request = new Request.Builder()
                .url(Urls.login)
                .build();
        RequestUtils.sendRequest(request,new MyCallBack() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //得到隐藏域中的value
                Document html = Jsoup.parse(response.body().string());
                Elements form = html.select("form[name=Form1]");
                Elements input = form.select("input");

                /*第二步、生成登陆的表单*/
                FormBody.Builder builder = new FormBody.Builder()
                        .add("txtUserName", account)
                        .add("txtPassword", password)
                        .add("CheckCode", securityCode)
                        .add("btnLogin.x", "40")
                        .add("btnLogin.y", "16");
                for(Element e : input){
                    if("__VIEWSTATE".equals(e.attr("name"))){
                        builder.add("__VIEWSTATE", e.attr("value"));
                    }
                    if("__EVENTVALIDATION".equals(e.attr("name"))){
                        builder.add("__EVENTVALIDATION",e.attr("value"));
                    }
                }
                FormBody body = builder.build();

                /*第三步、利用验证码中的cookie访问登陆请求页面*/
                String cookie = RequestUtils.getCookieString(Urls.login_securityCode);
                Request request1 = new Request.Builder()
                        .url(Urls.login_request)
                        .post(body)
                        .header("cookie",cookie)
                        .build();
                Response response1 = RequestUtils.client.newCall(request1).execute();

                /*第四步、判断登陆结果,获取登陆信息*/
                String res = response1.body().string();
                if(res.contains("验证码不对"))
                    loginEnd.onSecurityCodeError();
                if (res.contains(account+"不合法"))
                    loginEnd.onAccountError();
                if (res.contains("请检查用户名和密码是否输入正确"))
                    loginEnd.onPasswordError();
                if(res.contains("即时事务及公告通知查询")){
                    //获取登陆信息
                    //<span id="ctl00_lblSignIn">2018101703明美</span>
                    Document document = Jsoup.parse(res);
                    Elements info = document.select("span[id=ctl00_lblSignIn]");
                   loginEnd.onLoginSuccess(info.text());
                }
            }
        });
    }

    //得到验证码和cookie
    public void getCookieAndSecurityCode(ShowImage showImage) {
        Request request = new Request.Builder()
                .url(Urls.login_securityCode)
                .build();

        RequestUtils.sendRequest(request,new MyCallBack(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //显示出验证码
                showImage.showYZM(response.body().bytes());
            }
        });
    }

    //检查是否在线
    public void isOnline(OnlineCheck check){

        RequestUtils.initHttpClient();
        String cookie =new FileUtils().ReadByPreference(Urls.login_securityCode);
        //没有cookie就未登陆
        if(TextUtils.isEmpty(cookie)){
            check.offLine();
        }else{
            //有cookie尝试获取个人信息
            personalInfoUtils.getPersonalInfo(new OnQueryPersonalInfoEnd() {
                @Override
                public void onSuccess(PersonalInfoData info) {
                    check.onLine(info);
                }

                @Override
                public void onFail() {
                    check.offLine();
                }
            });
        }
    }

    //给外部回调在线检查结果的接口
    public interface OnlineCheck{
        void offLine();
        void onLine(PersonalInfoData info);
    }

    //给外部设置验证码图片的调用接口
    public interface ShowImage{
        void showYZM(byte[] bitmap);
    }

    //给外部处理登陆结果的回调接口
    public interface onLoginEnd{
        void onAccountError();
        void onPasswordError();
        void onSecurityCodeError();
        void onLoginSuccess(String info);
    }
}
