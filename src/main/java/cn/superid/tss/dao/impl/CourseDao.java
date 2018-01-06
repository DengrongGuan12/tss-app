package cn.superid.tss.dao.impl;

import cn.superid.tss.dao.ICourseDao;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.util.DStatement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDao implements ICourseDao{

    @Override
    public String getInviteCode(long courseId) {
        CourseEntity entity = DStatement.build(CourseEntity.class).id(courseId).selectOne("invite_code");
        return entity.getInviteCode();
    }

    @Override
    public void addCourse(CourseEntity courseEntity) {
        courseEntity.save();
    }

    @Override
    public List<CourseEntity> selectCoursesByIds(Long[] ids) {
        return DStatement.build(CourseEntity.class).in("id", ids).selectList();
    }

    @Override
    public CourseEntity selectCourseById(Long id) {
        return DStatement.build(CourseEntity.class).id(id).selectOne();
    }
}
