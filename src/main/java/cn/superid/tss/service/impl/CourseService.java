package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.AffairDTO;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.*;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.ICourseService;
import cn.superid.tss.util.DStatement;
import cn.superid.tss.util.ObjectUtil;
import cn.superid.tss.vo.CourseDetail;
import cn.superid.tss.vo.CourseSimple;
import cn.superid.tss.vo.GroupSimple;
import cn.superid.tss.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DengrongGuan
 * @create 2017-12-21 下午2:09
 **/
@Service
public class CourseService implements ICourseService {

    @Autowired
    BusinessClient businessClient;

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private CourseSimple courseSimple;

    @Override
    public Map<String, Map> getUserCourses(long userId) {
        Map<String, Map> result = new TreeMap<>((String o1, String o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            String[] o1s = o1.split(" ");
            String[] o2s = o2.split(" ");
            return Integer.parseInt(o1s[0]) * 10 + SeasonType.getIndex(o1s[1]) > Integer.parseInt(o2s[0]) * 10 + SeasonType.getIndex(o2s[1]) ? -1 : 1;
        });
        UserEntity userEntity = DStatement.build(UserEntity.class).id(userId).selectOne("departmentId");
        //TODO 2 根据事务id获取该事务下我的某种特定类型的所有子事务,需要包含我在本事务中扮演的角色
        List<AffairDTO> affairDTOS = businessClient.getMyChildrenAffair(userId, userEntity.getDepartmentId(), AffairType.COURSE.getIndex());
        Map<Long, String> affairIdNameMap = affairDTOS.stream().collect(Collectors.toMap(AffairDTO::getId, a -> a.getName(), (k1, k2) -> k1));
        Long[] affairIds = affairDTOS.stream().map(affairDTO -> affairDTO.getId()).toArray(Long[]::new);
        List<CourseEntity> courseEntities = DStatement.build(CourseEntity.class).in("id", affairIds).selectList();
        courseEntities.stream().forEach(courseEntity -> {
            String term = courseEntity.getYear() + " " + SeasonType.getName(courseEntity.getSeason());
            String grade = GradeType.getName(courseEntity.getGrade());
            CourseSimple courseSimple = new CourseSimple(courseEntity);
            courseSimple.setName(affairIdNameMap.get(courseEntity.getId()));
            //TODO 2 如果是助教和学生获取课程下我的小组，如果是教师获取所有小组 性能问题
            List<AffairDTO> groupAffairs = businessClient.getChildrenAffairByType(courseEntity.getId(), AffairType.GROUP.getIndex());
            //TODO 2 可能需要判断我是否在这个小组里扮演角色
            List<GroupSimple> groupSimples = groupAffairs.stream().map(affairDTO -> new GroupSimple(affairDTO.getId(), affairDTO.getName(), false)).collect(Collectors.toList());
            courseSimple.setGroupSimpleList(groupSimples);
            if (result.containsKey(term)) {
                Map<String, List> gradeCourseMap = result.get(term);
                if (gradeCourseMap.containsKey(grade)) {
                    List<CourseSimple> courseSimples = gradeCourseMap.get(grade);
                    courseSimples.add(courseSimple);
                } else {
                    List<CourseSimple> courseSimples = new ArrayList<>();
                    courseSimples.add(courseSimple);
                    gradeCourseMap.put(grade, courseSimples);
                }
            } else {
                Map<String, List> gradeCourseMap = new TreeMap<>((String o1, String o2) -> {
                    if (o1.equals(o2)) {
                        return 0;
                    }
                    return GradeType.getIndex(o1) > GradeType.getIndex(o2) ? 1 : -1;
                });
                List<CourseSimple> courseSimples = new ArrayList<>();
                courseSimples.add(courseSimple);
                gradeCourseMap.put(grade, courseSimples);
                result.put(term, gradeCourseMap);
            }
        });
        return result;
    }

    @Override
    public CourseDetail getCourseDetail(long courseId) {
        //TODO 2 获取事务详情

        CourseEntity courseEntity = DStatement.build(CourseEntity.class).id(courseId).selectOne();
        CourseDetail courseDetail = new CourseDetail(courseEntity);

        //TODO 3 获取一个课程的所有教师(获取一个事务下某种特定类型的角色)
        List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(courseId, UserType.TEACHER.getIndex());
        Long[] userIds = roleInfoDTOS.stream().map(roleInfoDTO -> roleInfoDTO.getUserId()).toArray(Long[]::new);
        List<UserEntity> userEntities = DStatement.build(UserEntity.class).in("id", userIds).selectList("id", "number");
        Map<Long, String> userIdNumberMap = userEntities.stream().collect(Collectors.toMap(UserEntity::getId, a -> a.getNumber(), (k1, k2) -> k1));
        List<Role> teachers = new ArrayList<>();
        roleInfoDTOS.stream().forEach(roleInfoDTO -> {
            Role role = new Role(roleInfoDTO);
            role.setNumber(userIdNumberMap.get(role.getUserId()));
            teachers.add(role);
        });
        courseDetail.setTeachers(teachers);
        return courseDetail;
    }


    @Override
    public long createCourse(CourseForm courseForm) {
        //TODO 3 创建事务,调用出错了该怎么中止流程并捕获异常？
        
        CourseEntity courseEntity = (CourseEntity) ObjectUtil.deepCopy(courseForm, CourseEntity.class);
        courseEntity.save();

        //TODO 3 创建课程资料文件夹，调用出错该怎么回滚？
        return 0;
    }

    @Override
    public void modifyCourse(CourseForm courseForm) {
        //TODO 3 修改事务

        CourseEntity courseEntity = (CourseEntity) ObjectUtil.deepCopy(courseForm, CourseEntity.class);
        courseEntity.update();
    }

    @Override
    public void setInviteCode(long id, String inviteCode) {
        if (null == inviteCode || inviteCode.length() == 0 || inviteCode.length() > 10) {
            throw new ErrorCodeException(ResponseCode.PARAM_ERROR, "邀请码不能为空且长度必须小于10");
        }
        CourseEntity courseEntity = DStatement.build(CourseEntity.class).id(id).selectOne();
        if (courseEntity == null) {
            throw new ErrorCodeException(ResponseCode.COURSE_NOT_EXIST, "课程不存在");
        }
        courseEntity.setInviteCode(inviteCode);
        courseEntity.update();
    }


}
