package cn.superid.tss.service.impl;

import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.ICourseService;
import cn.superid.tss.util.DStatement;
import cn.superid.tss.util.ObjectUtil;
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
        // 根据事务id获取该事务下我的某种特定类型的所有子事务？

        return null;
    }

    @Override
    public long createCourse(CourseForm courseForm) {
        //TODO 创建事务,调用出错了该怎么中止流程并捕获异常？

        CourseEntity courseEntity = (CourseEntity) ObjectUtil.deepCopy(courseForm,CourseEntity.class);
        courseEntity.save();

        //TODO 创建课程资料文件夹，调用出错该怎么回滚？
        return 0;
    }

    @Override
    public void modifyCourse(CourseForm courseForm) {
        //TODO 修改事务

        CourseEntity courseEntity = (CourseEntity)ObjectUtil.deepCopy(courseForm,CourseEntity.class);
        courseEntity.update();
    }

    @Override
    public void setInviteCode(long id, String inviteCode) {
        CourseEntity courseEntity = DStatement.build(CourseEntity.class).id(id).selectOne();
        if (courseEntity == null){
            throw new ErrorCodeException(ResponseCode.COURSE_NOT_EXIST,"课程不存在");
        }
        courseEntity.setInviteCode(inviteCode);
        courseEntity.update();
    }

}
