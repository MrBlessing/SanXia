package YangtzeUniversity.yuol.sanxia.Data;

public class ScoreData {
    //课程名称
    private String courseTitle;
    //课程成绩
    private String courseScore;
    //课程绩点
    private String courseGPA;
    //课程学分
    private String courseCredit;
    //课程学年
    private String schoolYear;
    //考试类型
    private String TestType;
    //所获学分
    private String mCourseCredit;

    public String getmCourseCredit() {
        return mCourseCredit;
    }

    public void setmCourseCredit(String mCourseCredit) {
        this.mCourseCredit = mCourseCredit;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(String courseScore) {
        this.courseScore = courseScore;
    }

    public String getCourseGPA() {
        return courseGPA;
    }

    public void setCourseGPA(String courseGPA) {
        this.courseGPA = courseGPA;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getTestType() {
        return TestType;
    }

    public void setTestType(String testType) {
        TestType = testType;
    }

    @Override
    public String toString() {
        String res = courseTitle+courseCredit+courseScore+courseGPA+mCourseCredit;
        return res;
    }
}
