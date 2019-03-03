package YangtzeUniversity.yuol.sanxia.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import YangtzeUniversity.yuol.sanxia.Adapter.ScheduleRecyclerAdapter;
import YangtzeUniversity.yuol.sanxia.Adapter.ScoreRecyclerAdapter;
import YangtzeUniversity.yuol.sanxia.Data.ClassScheduleData;
import YangtzeUniversity.yuol.sanxia.Data.ScoreData;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryClassEnd;
import YangtzeUniversity.yuol.sanxia.R;
import YangtzeUniversity.yuol.sanxia.Utils.ClassScheduleUtils;


public class ClassSchedule extends Fragment implements OnQueryClassEnd{

    //自定义数据
    private Context context;
    private View view;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    //课表助手类
    private ClassScheduleUtils utils;
    //适配器
    private ScheduleRecyclerAdapter adapter;
    private static final String TAG = "ClassSchedule";

    public ClassSchedule() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class_schedule, container, false);
        findView();
        setEvent();
        return view;
    }

    private void setEvent() {
        initUtils();
        //刷新事件
        refreshLayout.setOnRefreshListener((layout)->
                utils.getClassSchedule(this));
        //开启刷新事件
        refreshLayout.autoRefresh();
    }

    private void initUtils() {
        if (utils==null)
        utils = new ClassScheduleUtils();
    }

    private void findView() {
        refreshLayout = view.findViewById(R.id.fragment_class_schedule_refresh);
        recyclerView = view.findViewById(R.id.fragment_class_schedule_recycler);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onSuccess(List<ClassScheduleData> classScheduleData) {
        getActivity().runOnUiThread(()->{
            if(adapter==null){
                adapter = new ScheduleRecyclerAdapter(context,classScheduleData);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL,false));
                recyclerView.setAdapter(adapter);
            }else {
                adapter.refreshData(classScheduleData);
            }
            refreshLayout.finishRefresh();
        });
    }

    @Override
    public void onFail() {
        refreshLayout.finishRefresh();
    }


}
