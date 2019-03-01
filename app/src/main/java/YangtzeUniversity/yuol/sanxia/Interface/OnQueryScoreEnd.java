package YangtzeUniversity.yuol.sanxia.Interface;

import java.util.List;

import YangtzeUniversity.yuol.sanxia.Data.ScoreData;

/*该类用于查询数据获取结果之后的回调接口*/
public interface OnQueryScoreEnd {
    //数据查询成功
    void onSuccess(List<ScoreData> scoreData);
    void onFail();
}
