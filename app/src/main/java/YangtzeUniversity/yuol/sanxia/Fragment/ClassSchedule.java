package YangtzeUniversity.yuol.sanxia.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import YangtzeUniversity.yuol.sanxia.Adapter.ScheduleRecyclerAdapter;
import YangtzeUniversity.yuol.sanxia.Data.ClassScheduleData;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryClassEnd;
import YangtzeUniversity.yuol.sanxia.R;
import YangtzeUniversity.yuol.sanxia.Utils.ClassScheduleUtils;


public class ClassSchedule extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ClassSchedule";
    private String mParam1;
    private String mParam2;

    //自定义数据
    private Context context;
    private View view;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    //课表助手类
    private ClassScheduleUtils utils;

    public ClassSchedule() {

    }

    public static ClassSchedule newInstance(String param1, String param2) {
        ClassSchedule fragment = new ClassSchedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        //开始获取课表
        utils.getClassSchedule(new OnQueryClassEnd() {
            @Override
            public void onSuccess(List<ClassScheduleData> data) {
                getActivity().runOnUiThread(()->{
                    ScheduleRecyclerAdapter adapter = new ScheduleRecyclerAdapter(context,data);
                    recyclerView.setAdapter(adapter);
                });
            }

            @Override
            public void onFail() {

            }
        });
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


}
