package YangtzeUniversity.yuol.sanxia.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import YangtzeUniversity.yuol.sanxia.Data.KeyString;
import YangtzeUniversity.yuol.sanxia.Data.PersonalInfoData;
import YangtzeUniversity.yuol.sanxia.Data.Urls;
import YangtzeUniversity.yuol.sanxia.Interface.MyCallBack;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryPersonalInfoEnd;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class PersonalInfoUtils {

    public void getPersonalInfo(OnQueryPersonalInfoEnd onGetInfoEnd){
        RequestUtils.sendRequest(Urls.personal_info, new MyCallBack() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if(res.contains(KeyString.loginFail)){
                    onGetInfoEnd.onFail();
                }else{
                    PersonalInfoData info = parseData(res);
                    onGetInfoEnd.onSuccess(info);
                }
            }
        });
    }

    //解析结果，获取身份信息
    private PersonalInfoData parseData(String info) {
        PersonalInfoData personalInfo = new PersonalInfoData();
        Document html = Jsoup.parse(info);
        for(int i=1;i<15;i++){
            Elements span = html.select("span[id=ctl00_MainContentPlaceHolder_Label"+i+"]");
            switch (i){
                //班级
                case 1 : personalInfo.setGrade(span.text());break;
                //学号
                case 2 : personalInfo.setId(span.text());break;
                //姓名
                case 3 : personalInfo.setName(span.text());break;
                //性别
                case 4 : personalInfo.setSex(span.text());break;
                //出生日期
                case 5 : personalInfo.setBirthday(span.text());break;
                //政治面貌
                case 6 : personalInfo.setPolicy(span.text());break;
                //民族
                case 7 : personalInfo.setNation(span.text());break;
                //院系
                case 8 : personalInfo.setFaculty(span.text());break;
                //专业
                case 9 : personalInfo.setMajor(span.text());break;
                //专业方向
                case 10 : personalInfo.setMajorDirection(span.text());break;
                //联系电话
                case 14 : personalInfo.setPhoneNumber(span.text());break;
                default:break;
            }
        }
        return personalInfo;
    }
}
