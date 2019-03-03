package YangtzeUniversity.yuol.sanxia.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import YangtzeUniversity.yuol.sanxia.Data.ScoreData;
import YangtzeUniversity.yuol.sanxia.R;

public class ScoreRecyclerAdapter extends RecyclerView.Adapter<ScoreRecyclerAdapter.MyLocalViewHolder> {

    private List<ScoreData> data ;
    private Context context;

    public ScoreRecyclerAdapter(List<ScoreData> scoreDataList, Context context){
        data = scoreDataList==null?new ArrayList<>():scoreDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyLocalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_score,viewGroup,false);
        return new MyLocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLocalViewHolder myLocalViewHolder, int i) {
        myLocalViewHolder.courseTitle.setText(data.get(i).getCourseTitle());
        myLocalViewHolder.courseScore.setText("课程成绩: "+data.get(i).getCourseScore());
        myLocalViewHolder.courseCredit.setText("课程学分: "+data.get(i).getCourseCredit());
        myLocalViewHolder.courseGPA.setText("绩点: "+data.get(i).getCourseGPA());
        myLocalViewHolder.mCourseCredit.setText("所获学分: "+data.get(i).getmCourseCredit());
        myLocalViewHolder.schoolYear.setText("课程学年: "+ data.get(i).getSchoolYear());

        //根据分数设置字体的颜色
        int color = R.color.black;
        try{
            int score = Integer.parseInt(data.get(i).getCourseScore());
            if(score>=90){
                color = R.color.red1;
            }else if(score>=80){
                color = R.color.red2;
            }else if(score>=70){
                color = R.color.red3;
            }else if(score>=60){
              color = R.color.yellow1;
            }else{
               color = R.color.yellow2;
            }
        }catch(NumberFormatException e)
        {
            //如果分数是汉字，那就另行处理
            if(data.get(i).getCourseScore().equals("优")){
                color = R.color.red1;
            }
        }
        int realColor = context.getResources().getColor(color);
        myLocalViewHolder.courseScore.setTextColor(realColor);
        myLocalViewHolder.colorView.setBackgroundColor(realColor);
    }

    //更新数据
    public void refreshData(List<ScoreData> data){
        this.data = data==null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyLocalViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTitle;
        private TextView courseScore;
        private TextView courseGPA;
        private TextView courseCredit;
        private TextView mCourseCredit;
        private TextView schoolYear;
        private View colorView;
        public MyLocalViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.recycler_item_score_courseTitle);
            courseScore = itemView.findViewById(R.id.recycler_item_score_courseScore);
            courseCredit = itemView.findViewById(R.id.recycler_item_score_courseCredit);
            courseGPA = itemView.findViewById(R.id.recycler_item_score_courseGPA);
            mCourseCredit = itemView.findViewById(R.id.recycler_item_score_mCourseCredit);
            schoolYear = itemView.findViewById(R.id.recycler_item_score_schoolYear);
            colorView = itemView.findViewById(R.id.recycler_item_score_colorView);
        }
    }
}
