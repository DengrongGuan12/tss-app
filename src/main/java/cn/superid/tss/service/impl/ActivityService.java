package cn.superid.tss.service.impl;

import cn.superid.tss.constant.ActivityType;
import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.forms.AddHomeworkform;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.vo.Activity;
import cn.superid.tss.vo.Homework;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ActivityService implements IActivityService{

    @Override
    public List<Activity> getAllActivites(long courseId) {
        List<Activity> activities = new LinkedList<>();

        activities.add(Activity.mockActivity("第一次作业","根据第一次课程内容完成以下作业要求",
                ActivityType.Homework.getIndex()));

        activities.add(Activity.mockActivity("第一次课程","软件工程导论:软件工程的定义；软件工程的历史",
                ActivityType.Teaching.getIndex()));

        return activities;
    }

    @Override
    public long createActivity(AddActivityForm form, int type, long courseId, long roleId, long userId) {
        //调用superID创建发布接口
        //塞进自己的数据库里
        return 0;
    }

    @Override
    public long createHomework(AddHomeworkform form, long courseId, long roleId, long userId) {
        //判断是什么类型的作业，如果是发布给小组的作业需要做特殊处理
        //调用superID创建发布接口
        //塞进自己的数据库里

        return 0;
    }

    @Override
    public Activity getActivity(long activityId) {
        //TODO
        //获得具体发布
        return Activity.mockActivity("第一次作业","根据第一次课堂内容完成如下",ActivityType.Homework.getIndex());
    }

    @Override
    public Homework getHomework(long homeworkId) {
        return null;
    }
}
