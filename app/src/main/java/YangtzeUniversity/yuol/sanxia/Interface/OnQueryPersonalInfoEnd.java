package YangtzeUniversity.yuol.sanxia.Interface;

import YangtzeUniversity.yuol.sanxia.Data.PersonalInfoData;
/*用于身份信息查询结束之后的回调接口*/

public interface OnQueryPersonalInfoEnd {
    void onSuccess(PersonalInfoData info);
    void onFail();
}
