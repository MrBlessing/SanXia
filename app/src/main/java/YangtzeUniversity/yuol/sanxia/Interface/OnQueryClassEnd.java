package YangtzeUniversity.yuol.sanxia.Interface;

import YangtzeUniversity.yuol.sanxia.Data.ClassScheduleData;

/*查询课表之后的返回接口*/
public interface OnQueryClassEnd {
    void onSuccess(ClassScheduleData data);
    void onFail();
}
