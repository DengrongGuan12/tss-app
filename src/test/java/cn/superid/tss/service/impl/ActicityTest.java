package cn.superid.tss.service.impl;

import cn.superid.tss.BaseTest;
import cn.superid.tss.constant.ActivityType;
import cn.superid.tss.constant.HomeworkType;
import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.forms.AddHomeworkform;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.vo.Activity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

public class ActicityTest extends BaseTest{

    @Autowired
    IActivityService activityService;

    public static long courseId = 130008;
    public static long teacherRoleID = 920208;
    public static long teacherUserID = 30720;



    @Test
    //test pass
    public void testCreateActivity(){
        AddActivityForm form = new AddActivityForm();
        form.setTitle("第一次作业");
        form.setContent("{\"entityMap\":{},\"blocks\":[{\"key\":\"bnpcl\"," +
                "\"text\":\"软件工程导论\",\"type\":\"header-one\"," +
                "\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"8kk8d\",\"text\":\"      " +
                "软件工程的定义、历史\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}," +
                "{\"key\":\"69q2h\",\"text\":\"JAVA基础\",\"type\":\"header-one\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"69uqc\",\"text\":\"    " +
                " java的基础类库\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"2hchn\",\"text\":\"\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"9seun\",\"text\":\"\",\"type\":\"header-two\",\"depth\":0," +
                "\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}]}");
        form.setAttachments(null);
        activityService.createActivity(form, ActivityType.Teaching.getIndex(),courseId,teacherRoleID,teacherUserID);
    }


    @Test
    //test pass
    public void testCreateHomework(){
        AddHomeworkform homeworkform = new AddHomeworkform();
        homeworkform.setTitle("第一次作业");
        homeworkform.setContent("{\"entityMap\":{},\"blocks\":[{\"key\":\"bnpcl\"," +
                "\"text\":\"软件工程导论\",\"type\":\"header-one\"," +
                "\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"8kk8d\",\"text\":\"      " +
                "软件工程的定义、历史\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}," +
                "{\"key\":\"69q2h\",\"text\":\"JAVA基础\",\"type\":\"header-one\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"69uqc\",\"text\":\"    " +
                " java的基础类库\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"2hchn\",\"text\":\"\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"9seun\",\"text\":\"\",\"type\":\"header-two\",\"depth\":0," +
                "\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}]}");
        homeworkform.setAttachments(null);
        Timestamp deadline = new Timestamp(System.currentTimeMillis());

        String tsStr = "2018-01-09 17:50:59";
        try {
            deadline = Timestamp.valueOf(tsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        homeworkform.setDeadline(deadline);
        homeworkform.setHomeworkType(HomeworkType.GROUP.getIndex());
        activityService.createHomework(homeworkform,courseId,teacherRoleID,teacherUserID);


    }

    @Test
    //test pass
    public void testGetActivities(){
        List<Activity> activities = activityService.getAllActivites(courseId);
    }

    @Test
    //test pass
    public void testGetActivity(){
        Activity activity = activityService.getActivity(240503);

    }
}
