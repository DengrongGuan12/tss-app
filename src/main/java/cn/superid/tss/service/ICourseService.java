package cn.superid.tss.service;

import cn.superid.tss.constant.UserType;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.vo.CourseDetail;
import cn.superid.tss.vo.Role;

import java.util.List;
import java.util.Map;

public interface ICourseService {
    Map<String, Map> getUserCourses(long userId);

    Map<String, Map> getCoursesOfDepartment(long departmentId);

    CourseDetail getCourseDetail(long courseId);

    long createCourse(CourseForm courseForm, long roleId, long departmentId, long userId);

    void modifyCourse(CourseForm courseForm, long roleId);

    void setInviteCode(long id, String inviteCode);
}
