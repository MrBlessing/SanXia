package YangtzeUniversity.yuol.sanxia.Utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import YangtzeUniversity.yuol.sanxia.Data.ClassScheduleData;
import YangtzeUniversity.yuol.sanxia.Data.KeyString;
import YangtzeUniversity.yuol.sanxia.Data.Urls;
import YangtzeUniversity.yuol.sanxia.Interface.MyCallBack;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryClassEnd;
import okhttp3.Call;
import okhttp3.Response;

public class ClassScheduleUtils {

   public void getClassSchedule(OnQueryClassEnd classEnd){
       RequestUtils.sendRequest(Urls.class_schedule, new MyCallBack() {
          @Override
          public void onResponse(Call call, Response response) throws IOException {
             String res = response.body().string();
             if(res.contains(KeyString.loginFail)){
                classEnd.onFail();
             }else{
                //对数据进行解析
                List<ClassScheduleData> data = parseRes(res);
                classEnd.onSuccess(data);
             }
          }
       });
   }

   //该方法用于对数据进行解析
   private List<ClassScheduleData> parseRes(String res) {
      List<ClassScheduleData>data = new ArrayList<>();

      //首先提取出表格元素
      Document html =  Jsoup.parse(res);
      Elements table = html.select("table[class=GridViewStyle]");

      //剔出此table中的所有tr,合计有7个tr，第一个tr无用
      Elements tr = table.select("tr");
      //将每一个tr进行解析
      for(int i = 0; i<tr.size();i++){
         if(i!=0){
            data.add(parseEachTr(tr.get(i)));
         }
      }
      return data;
   }

   //解析每一行的数据，得到每一个时间段的课程
   private ClassScheduleData parseEachTr(Element element){
      ClassScheduleData data = new ClassScheduleData();
      Elements td = element.select("td");
      //<td>第一～二节</td>
      // <td>&nbsp;</td>
      // <td>VB程序设计语言(9班)<br>J-3515&nbsp;1-16周&nbsp; <br>袁国刚等</td>
      // <td>基础法语(二)(2班)<br>W-1313&nbsp;1-17周&nbsp; <br>董梦园等</td>
      // <td>VB程序设计语言(9班)<br>J-1312一机房一区&nbsp;1-16周&nbsp;双周<br>袁国刚等</td>
      // <td>基础法语(二)(2班)<br>W-1308&nbsp;1-17周&nbsp; <br>董梦园等</td>
      // <td>&nbsp;</td>
      // <td>&nbsp;</td>
      //对每一个td进行解析
      for(int i = 0; i<td.size();i++){
         switch (i){
            case 0 :
               data.setTime(trimText(td.get(i).text()));
               break;
            case 1 :
               data.setMonday(trimText(td.get(i).text()));
               break;
            case 2 :
               data.setTuesday(trimText(td.get(i).text()));
               break;
            case 3 :
               data.setWednesday(trimText(td.get(i).text()));
               break;
            case 4 :
               data.setThursday(trimText(td.get(i).text()));
               break;
            case 5 :
               data.setFriday(trimText(td.get(i).text()));
               break;
            case 6 :
               data.setSaturday(trimText(td.get(i).text()));
               break;
            case 7 :
               data.setSunday(trimText(td.get(i).text()));
               break;
            default: break;
         }
      }
      return data;
   }

   //修整文字,删除网页中的一些标签
   private String trimText(String text){
      String res = text;
      if(text.contains("&nbsp")){
         res = res.replace("&nbsp;"," ");
      }
      if(text.contains("<br>")){
         res = res.replace("<br>","\n");
      }
      return res;
   }
}
