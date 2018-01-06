package cn.superid.tss.dao;

import cn.superid.tss.model.CourseEntity;

import java.util.List;

public interface ICourseDao {

    String getInviteCode(long courseId);

    void addCourse(CourseEntity courseEntity);

    List<CourseEntity> selectCoursesByIds(Long[] ids);

    CourseEntity selectCourseById(Long id);
}
