package YangtzeUniversity.yuol.sanxia.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import YangtzeUniversity.yuol.sanxia.Adapter.ScoreRecyclerAdapter;
import YangtzeUniversity.yuol.sanxia.Data.ScoreData;
import YangtzeUniversity.yuol.sanxia.Interface.OnQueryScoreEnd;
import YangtzeUniversity.yuol.sanxia.R;
import YangtzeUniversity.yuol.sanxia.Utils.ScoreUtils;

public class Score extends Fragment implements OnQueryScoreEnd {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    //RecyclerView适配器
    ScoreRecyclerAdapter adapter;
    //工具类实例
    private ScoreUtils scoreUtils;
    //XML实例
    private View view;
    //当前活动实例
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
    }

    public static Score newInstance(String param1, String param2) {
        Score fragment = new Score();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_score, container, false);
        findView();
        setEvent();
        return view;
    }

    private void findView() {
        recyclerView = view.findViewById(R.id.fragment_score_recyclerView);
        refreshLayout = view.findViewById(R.id.fragment_score_refresh);
    }

    private void setEvent() {

        //初始化工具类
        initUtils();
        //刷新事件
        refreshLayout.setOnRefreshListener((layout)->{
            scoreUtils.getScore(this);
        });
        refreshLayout.autoRefresh();
    }

    private void initUtils() {
        if(scoreUtils==null)
            scoreUtils =  new ScoreUtils();
    }

    @Override
    public void onSuccess(List<ScoreData> scoreData) {
        getActivity().runOnUiThread(()->{
            if(adapter==null){
                adapter = new ScoreRecyclerAdapter(scoreData,context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL,false));
                recyclerView.setAdapter(adapter);
            }else {
                adapter.refreshData(scoreData);
            }
            refreshLayout.finishRefresh();
        });
    }

    @Override
    public void onFail() {
        refreshLayout.finishRefresh();
    }
}
