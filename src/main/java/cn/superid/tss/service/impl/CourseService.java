package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author DengrongGuan
 * @create 2017-12-21 下午2:09
 **/
@Service
public class CourseService implements ICourseService{

    @Autowired
    BusinessClient businessClient;

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    //TODO 3
    @Override
    public Map<String, Map> getUserCourses(long userId) {
        UserEntity userEntity = DStatement.build(UserEntity.class).id(userId).selectOne("departmentId");
        // 根据事务id获取该事务下我的某种特定类型的所有子事务？
//        List<AffairDTO> affairDTOS = businessClient.getMyChildrenAffair(userId,userEntity.getDepartmentId(),2);

        Map<String,Map> result = new LinkedHashMap<>();
        Map<String,List> courseSimpleMap = new LinkedHashMap<>();
        CourseSimple courseSimple1 = new CourseSimple();
        courseSimple1.setId(1);
        courseSimple1.setGrade("大一");
        courseSimple1.setName("计算系统基础");
        courseSimple1.setRoleType(0);
        courseSimple1.setTerm("2017");
        List<GroupSimple> groupSimpleList = new ArrayList<>();
        for (int i = 1;i<20;i++){
            groupSimpleList.add(GroupSimple.mockOtherGroup());
        }
        courseSimple1.setGroupSimpleList(groupSimpleList);
        CourseSimple courseSimple2 = new CourseSimple();
        courseSimple2.setId(2);
        courseSimple2.setGrade("大一");
        courseSimple2.setName("离散数学");
        courseSimple2.setRoleType(0);
        courseSimple2.setTerm("2017");
        List<GroupSimple> groupSimpleList2 = new ArrayList<>();
        for (int i = 1;i<20;i++){
            groupSimpleList2.add(GroupSimple.mockOtherGroup());
        }
        courseSimple2.setGroupSimpleList(groupSimpleList2);
        List<CourseSimple> courseSimples = new ArrayList<>();
        courseSimples.add(courseSimple1);
        courseSimples.add(courseSimple2);
        courseSimpleMap.put("大一",courseSimples);

        CourseSimple courseSimple3 = new CourseSimple();
        courseSimple3.setId(3);
        courseSimple3.setGrade("大二");
        courseSimple3.setName("操作系统");
        courseSimple3.setRoleType(1);
        courseSimple3.setTerm("2017");
        List<GroupSimple> groupSimpleList3 = new ArrayList<>();
        for (int i = 1;i<20;i++){
            GroupSimple groupSimple = new GroupSimple();
            groupSimple.setMine(false);
            groupSimple.setName("小组"+i);
            groupSimpleList3.add(groupSimple);
        }
        courseSimple3.setGroupSimpleList(groupSimpleList3);
        List<CourseSimple> courseSimples1 = new ArrayList<>();
        courseSimples1.add(courseSimple3);
        courseSimpleMap.put("大二",courseSimples1);
        result.put("2017 FALL",courseSimpleMap);
        result.put("2017 SUMMER",courseSimpleMap);
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
        List<UserEntity> userEntities = DStatement.build(UserEntity.class).in("id",userIds).selectList("id","number");
        Map<Long,String> userIdNumberMap = userEntities.stream().collect(Collectors.toMap(UserEntity::getId,a -> a.getNumber(),(k1,k2)->k1));
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
        if (null == inviteCode || inviteCode.length() == 0 || inviteCode.length() > 10 ){
            throw new ErrorCodeException(ResponseCode.PARAM_ERROR,"邀请码不能为空且长度必须小于10");
        }
        CourseEntity courseEntity = DStatement.build(CourseEntity.class).id(id).selectOne();
        if (courseEntity == null){
            throw new ErrorCodeException(ResponseCode.COURSE_NOT_EXIST,"课程不存在");
        }
        courseEntity.setInviteCode(inviteCode);
        courseEntity.update();
    }


}
