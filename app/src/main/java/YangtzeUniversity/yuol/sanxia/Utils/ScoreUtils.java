package YangtzeUniversity.yuol.sanxia.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import YangtzeUniversity.yuol.sanxia.Data.KeyString;
import YangtzeUniversity.yuol.sanxia.Data.ScoreData;
import YangtzeUniversity.yuol.sanxia.Data.Urls;
import YangtzeUniversity.yuol.sanxia.Interface.MyCallBack;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryScoreEnd;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class ScoreUtils {
    //按所学课程排序成绩
    public final String sortByCourse = "RadioButton2";
    //按学年学期排序课程
    public final String sortByYearAndTerm = "RadioButton1";
    //学年
    private String schoolYear="0";
    //学期
    private String schoolTerm="0";

    //查询方式,默认按学年排序
    private String sortType = sortByCourse;

    public void getScore(OnQueryScoreEnd getScoreEnd){
        /*第一步、请求成绩查询网址，获取隐藏域内容*/
        RequestUtils.sendRequest(Urls.course_score, new MyCallBack() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if (res.contains(KeyString.loginFail)){
                    getScoreEnd.onFail();
                }else {
                    Document html = Jsoup.parse(res);
                    Elements form = html.select("form[name=aspnetForm]");
                    Elements input = form.select("input");

                    /*第二步、获得学年信息*/
                    List<String> years = getYear(res);
                    /*第三步、完成表单*/
                    FormBody body = buildForm(input);

                    /*"第四步、发送请求，获取结果"*/
                    Request request1 = new Request.Builder()
                            .url(Urls.course_score)
                            .header("cookie",RequestUtils.getCookieString())
                            .post(body)
                            .build();
                    Response response1 =  RequestUtils.client.newCall(request1).execute();

                    /*第五步、判断状态，解析结果获取成绩*/
                    String res1 = response1.body().string();
                    if (res1.contains("你尚未登录系统！请先登录为合法用户")){
                        getScoreEnd.onFail();
                    }else{
                        List<ScoreData> scoreData = parseRes(res1);
                        //将学年信息放进scoreData中
                        if(years.size() == scoreData.size()){
                            for(int i=0;i<years.size();i++){
                                scoreData.get(i).setSchoolYear(years.get(i));
                            }
                        }
                        getScoreEnd.onSuccess(scoreData);
                    }
                }
            }
        });
    }

    private List<String> getYear(String res) {
        List<String> years = new ArrayList<>();
        Document document = Jsoup.parse(res);
        Elements table = document.select("table[class=GridViewStyle]");
        Elements td = table.select("td");
        int count=1;
        for(Element e : td){
            switch (count%8){
                //学年信息
                case 1 : years.add(e.text());break;
            }
            count++;
        }
        return years;
    }

    private FormBody buildForm(Elements input) {
        FormBody.Builder builder = new FormBody.Builder();
        for(Element e : input){
            if("__VIEWSTATE".equals(e.attr("name")))
                builder.add("__VIEWSTATE", e.attr("value"));
            if("__EVENTTARGET".equals(e.attr("name")))
                builder.add("__EVENTTARGET",e.attr("value"));
            if("__EVENTARGUMENT".equals(e.attr("name")))
                builder.add("__EVENTTARGET",e.attr("value"));
            if("__EVENTVALIDATION".equals(e.attr("name")))
                builder.add("__EVENTVALIDATION",e.attr("value"));
        }
        builder.add("ctl00$MainContentPlaceHolder$School_Year",schoolYear);
        builder.add("ctl00$MainContentPlaceHolder$School_Term",schoolTerm);
        builder.add("ctl00$MainContentPlaceHolder$score_q",sortType);
        builder.add("ctl00$MainContentPlaceHolder$CheckBox1","on");
        builder.add("ctl00$MainContentPlaceHolder$BtnSearch.x","35");
        builder.add("ctl00$MainContentPlaceHolder$BtnSearch.y","10");
        return builder.build();
    }

    //设置学年和学期
    public void setYearAndTerm(String y, String t){
        schoolTerm = t;
        schoolYear = y;
    }

    //设置排序方式
    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    //解析成绩数据
    private List<ScoreData> parseRes(String res){
        List<ScoreData> scoreDatas = new ArrayList<>();
        Document document = Jsoup.parse(res);
        Elements table = document.select("table[class=GridViewStyle]");
        Elements td = table.select("td");
        int count=1;
        ScoreData scoreData = null;
        for(Element e : td){
            switch (count%5){
                //课程名称
                case 1 :
                    scoreData =new ScoreData();
                    scoreData.setCourseTitle(e.text());break;
                //课程学分
                case 2 : scoreData.setCourseCredit(e.text());break;
                //考试成绩
                case 3 : scoreData.setCourseScore(e.text());break;
                //所获学分
                case 4 : scoreData.setmCourseCredit(e.text());break;
                //绩点
                case 0 : scoreData.setCourseGPA(e.text());
                    scoreDatas.add(scoreData);
                    break;
            }
            count++;
        }
        return scoreDatas;
    }

}
