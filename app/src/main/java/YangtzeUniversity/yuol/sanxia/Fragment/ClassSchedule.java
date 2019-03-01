package YangtzeUniversity.yuol.sanxia.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public ClassSchedule() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new ClassScheduleUtils().getClassSchedule(new OnQueryClassEnd() {
            @Override
            public void onSuccess(ClassScheduleData data) {

            }

            @Override
            public void onFail() {

            }
        });
        return inflater.inflate(R.layout.fragment_class_schedule, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
