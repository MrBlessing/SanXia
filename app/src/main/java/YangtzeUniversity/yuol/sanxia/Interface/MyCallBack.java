package YangtzeUniversity.yuol.sanxia.Interface;

import android.widget.Toast;

import java.io.IOException;

import YangtzeUniversity.yuol.sanxia.Utils.ActivityCollector;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*该抽象类用于减少CallBack中的重复代码*/
public abstract class MyCallBack implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        ActivityCollector.currentActivity().runOnUiThread(()->{
            Toast.makeText(ActivityCollector.currentActivity(),"网络连接失败",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public abstract void onResponse(Call call, Response response) throws IOException ;

}
