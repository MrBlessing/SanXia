package YangtzeUniversity.yuol.sanxia.Utils;


import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import YangtzeUniversity.yuol.sanxia.Data.Urls;
import YangtzeUniversity.yuol.sanxia.Interface.MyCallBack;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestUtils {

    static OkHttpClient client ;
    private static final String TAG = "RequestUtils";

    //用于发送请求
    static void sendRequest(Request request, MyCallBack callBack){
        initHttpClient();
        client.newCall(request).enqueue(callBack);
    }

    //用于携带Cookie发送请求,该方法携带的cookie为登陆Cookie
    static void sendRequest(String url, MyCallBack callBack){
        initHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("cookie",getCookieString())
                .build();
        client.newCall(request).enqueue(callBack);
    }

    //初始化代理类
    static void initHttpClient() {
        if(client==null){
            client = new OkHttpClient.Builder()
                    //持久化Cookie
                    .cookieJar(new CookieJar() {
                        private final Map<String,List<Cookie>> container = new HashMap<>();
                        private final FileUtils fileUtils = new FileUtils();
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            container.put(url.url().toString(),cookies);
                            Log.d(TAG, "saveFromResponse: "+cookies.get(0).domain());
                            //将Cookie储存到本地
                            fileUtils.saveByPreference(url.url().toString(),getCookieString(url.url().toString()));
                        }
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookie = container.get(url.url().toString());
                            if(cookie==null){
                                //从本地读取cookie
                                List<Cookie> local = new ArrayList<>();
                                String localString = fileUtils.ReadByPreference(url.url().toString());
                                Cookie localCookie = stringToCookie(localString);
                                if(localCookie!=null)
                                    local.add(localCookie);
                                return local;
                            }else {
                                return  cookie;
                            }

                        }
                    })
                    .build();
        }
    }

    //用于将Cookie的集合转化成字符串
    static String getCookieString(String url){
        List<Cookie> cookies = client.cookieJar().loadForRequest(HttpUrl.get(url));
        return cookies.get(0).name()+"="+cookies.get(0).value();
    }
    //得到默认的Cookie(登陆cookie)
    static String getCookieString(){
        List<Cookie> cookies = client.cookieJar().loadForRequest(HttpUrl.get(Urls.login_securityCode));
        return cookies.get(0).name()+"="+cookies.get(0).value();
    }

    //将CookieString组装成Cookie
    private static Cookie stringToCookie(String s){
        Cookie cookie=null;
        if(!TextUtils.isEmpty(s)){
            String temp[] = s.split("=");
            String name = temp[0];
            String value = temp[1];
            cookie = new Cookie.Builder()
                    .name(name)
                    .value(value)
                    .domain("210.42.38.26")
                    .build();
        }
        return cookie;
    }

}
