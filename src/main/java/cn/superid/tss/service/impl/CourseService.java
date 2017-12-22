package cn.superid.tss.service.impl;

import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.ICourseService;
import cn.superid.tss.util.DStatement;
import cn.superid.tss.vo.CourseDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author DengrongGuan
 * @create 2017-12-21 下午2:09
 **/
@Service
public class CourseService implements ICourseService{
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    @Override
    public Map<String, Map> getUserCourses(long userId) {
        UserEntity userEntity = DStatement.build(UserEntity.class).id(userId).selectOne("departmentId");
        // 根据事务id获取
        return null;
    }
}
