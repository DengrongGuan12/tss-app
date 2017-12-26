package cn.superid.tss.service;

import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.forms.AddHomeworkform;
import cn.superid.tss.vo.Activity;
import cn.superid.tss.vo.Homework;

import java.util.List;

public interface IActivityService {

    List<Activity> getAllActivites(long courseId);

    long createActivity(AddActivityForm form,int type,long courseId,long roleId,long userId);

    long createHomework(AddHomeworkform form,long courseId,long roleId,long userId);

    Activity getActivity(long activityId);

    Homework getHomework(long homeworkId);

}
