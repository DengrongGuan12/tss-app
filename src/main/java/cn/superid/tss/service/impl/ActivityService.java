package cn.superid.tss.service.impl;

import cn.superid.tss.constant.ActivityType;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.vo.Activity;
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
                ActivityType.Homework.name()));

        activities.add(Activity.mockActivity("第一次课程","软件工程导论:软件工程的定义；软件工程的历史",
                ActivityType.Teaching.name()));

        return null;
    }
}
