package cn.superid.tss.service;

import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;

import java.util.Map;

public interface ICourseService {
    Map<String,Map> getUserCourses(long userId);
    long createCourse(CourseForm courseForm);
    void modifyCourse(CourseForm courseForm);
    void setInviteCode(long id,String inviteCode);
}
