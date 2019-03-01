package YangtzeUniversity.yuol.sanxia.Data;

public class ClassScheduleData {
    //上午第一节课
    private OneTime firstLesson;
    //上午第二节课
    private OneTime secondLesson;
    //下午第一节课
    private OneTime thirdLesson;
    //下午第二节
    private OneTime fourLesson;
    //晚上第一节
    private OneTime fifthLesson;
    //晚上第二节
    private OneTime sixthLesson;

    public OneTime getFirstLesson() {
        return firstLesson;
    }

    public void setFirstLesson(OneTime firstLesson) {
        this.firstLesson = firstLesson;
    }

    public OneTime getSecondLesson() {
        return secondLesson;
    }

    public void setSecondLesson(OneTime secondLesson) {
        this.secondLesson = secondLesson;
    }

    public OneTime getThirdLesson() {
        return thirdLesson;
    }

    public void setThirdLesson(OneTime thirdLesson) {
        this.thirdLesson = thirdLesson;
    }

    public OneTime getFourLesson() {
        return fourLesson;
    }

    public void setFourLesson(OneTime fourLesson) {
        this.fourLesson = fourLesson;
    }

    public OneTime getFifthLesson() {
        return fifthLesson;
    }

    public void setFifthLesson(OneTime fifthLesson) {
        this.fifthLesson = fifthLesson;
    }

    public OneTime getSixthLesson() {
        return sixthLesson;
    }

    public void setSixthLesson(OneTime sixthLesson) {
        this.sixthLesson = sixthLesson;
    }

    //每一个时间段不同天的课程
    public static class OneTime{
        //周一
        private String monday;
        //周二
        private String tuesday;
        //周三
        private String wednesday;
        //周四
        private String thursday;
        //周五
        private String friday;
        //周六
        private String saturday;
        //周日
        private String sunday;

        public String getMonday() {
            return monday;
        }

        public void setMonday(String monday) {
            this.monday = monday;
        }

        public String getTuesday() {
            return tuesday;
        }

        public void setTuesday(String tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesday() {
            return wednesday;
        }

        public void setWednesday(String wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursday() {
            return thursday;
        }

        public void setThursday(String thursday) {
            this.thursday = thursday;
        }

        public String getFriday() {
            return friday;
        }

        public void setFriday(String friday) {
            this.friday = friday;
        }

        public String getSaturday() {
            return saturday;
        }

        public void setSaturday(String saturday) {
            this.saturday = saturday;
        }

        public String getSunday() {
            return sunday;
        }

        public void setSunday(String sunday) {
            this.sunday = sunday;
        }
    }
}
