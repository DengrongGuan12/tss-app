package cn.superid.tss.dao.impl;

import cn.superid.tss.dao.ICourseDao;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.util.DStatement;
import org.springframework.stereotype.Component;

@Component
public class CourseDao implements ICourseDao{

    @Override
    public String getInviteCode(long courseId) {
        CourseEntity entity = DStatement.build(CourseEntity.class).id(courseId).selectOne("invite_code");
        return entity.getInviteCode();
    }
}
