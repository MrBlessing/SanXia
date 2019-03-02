package YangtzeUniversity.yuol.sanxia.Interface;

import java.util.List;

import YangtzeUniversity.yuol.sanxia.Data.ClassScheduleData;

/*查询课表之后的返回接口*/
public interface OnQueryClassEnd {
    void onSuccess(List<ClassScheduleData> data);
    void onFail();
}
