package YangtzeUniversity.yuol.sanxia.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import YangtzeUniversity.yuol.sanxia.CustomizedView.MyScheduleItem;
import YangtzeUniversity.yuol.sanxia.Data.ClassScheduleData;
import YangtzeUniversity.yuol.sanxia.R;

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.MyLocalViewHolder> {

    //课表信息
    private List<ClassScheduleData> data;
    //第一行的时间信息
    private List<String> timeData;
    private Context context;
    private static final String TAG = "ScheduleRecyclerAdapter";

    public ScheduleRecyclerAdapter(Context c , List<ClassScheduleData> d){
        data = d;
        context = c;
        timeData = new ArrayList<>();
        timeData.add("第一大节");
        timeData.add("第二大节");
        timeData.add("第三大节");
        timeData.add("第四大节");
        timeData.add("第五大节");
        timeData.add("第六大节");
    }

    @NonNull
    @Override
    public MyLocalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_class_schedule,viewGroup,false);
        return new MyLocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLocalViewHolder myLocalViewHolder, int i) {
        //当前的数据
        ClassScheduleData curData = data.get(i);

        //第一列
        myLocalViewHolder.time.setText(timeData.get(i));

        setItemText(myLocalViewHolder.monday,curData.getMonday());
        setItemText(myLocalViewHolder.tuesday,curData.getTuesday());
        setItemText(myLocalViewHolder.wednesday,curData.getWednesday());
        setItemText(myLocalViewHolder.thursday,curData.getThursday());
        setItemText(myLocalViewHolder.friday,curData.getFriday());
        setItemText(myLocalViewHolder.saturday,curData.getSaturday());
        setItemText(myLocalViewHolder.sunday,curData.getSunday());
    }

    private void setItemText(MyScheduleItem view,String text) {
        if(TextUtils.isEmpty(text)){
            view.setContainerVisible(View.GONE);
        }
        else{
            view.setText(text);
            view.setContainerVisible(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //更新数据
    public void refreshData(List<ClassScheduleData> data){
        this.data = data==null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    class MyLocalViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        MyScheduleItem monday;
        MyScheduleItem tuesday;
        MyScheduleItem wednesday;
        MyScheduleItem thursday;
        MyScheduleItem friday;
        MyScheduleItem saturday;
        MyScheduleItem sunday;
        MyLocalViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.recycler_item_class_schedule_time);
            monday = itemView.findViewById(R.id.recycler_item_class_schedule_monday);
            tuesday = itemView.findViewById(R.id.recycler_item_class_schedule_tuesday);
            wednesday = itemView.findViewById(R.id.recycler_item_class_schedule_wednesday);
            thursday = itemView.findViewById(R.id.recycler_item_class_schedule_thursday);
            friday = itemView.findViewById(R.id.recycler_item_class_schedule_friday);
            saturday = itemView.findViewById(R.id.recycler_item_class_schedule_saturday);
            sunday = itemView.findViewById(R.id.recycler_item_class_schedule_sunday);
        }
    }
}
