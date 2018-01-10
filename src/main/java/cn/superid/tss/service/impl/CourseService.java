package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.client.FileClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.AffairDTO;
import cn.superid.common.rest.dto.business.AffairDetailDTO;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.common.rest.forms.AffairCreateForm;
import cn.superid.common.rest.forms.AffairModifyForm;
import cn.superid.id_client.IdClient;
import cn.superid.tss.constant.*;
import cn.superid.tss.dao.ICourseDao;
import cn.superid.tss.dao.IUserDao;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.ICourseService;
import cn.superid.tss.service.IGroupService;
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

    @Autowired
    FileClient fileClient;

    @Autowired
    IdClient idClient;

    @Autowired
    IGroupService groupService;

    @Autowired
    ICourseDao courseDao;

    @Autowired
    IUserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Override
    public Map<String, Map> getUserCourses(long userId) {
        UserEntity userEntity = userDao.getUserEntity(userId);
        List<AffairDTO> affairDTOS = businessClient.getMyChildrenAffair(userId, userEntity.getDepartmentId(), AffairType.COURSE.getIndex(), StateType.NORMAL.getIndex(), false);
        return parseAffairsToCourses(affairDTOS,true,userId);
    }

    @Override
    public Map<String, Map> getCoursesOfDepartment(long departmentId) {
        List<AffairDTO> affairDTOS = businessClient.getChildrenAffairByType(departmentId,AffairType.COURSE.getIndex(),StateType.NORMAL.getIndex(), false);
        return parseAffairsToCourses(affairDTOS,false);
    }

    private Map<String, Map> parseAffairsToCourses(List<AffairDTO> affairDTOS, boolean needGroup, long ... userId){
        Map<String, Map> result = new TreeMap<>((String o1, String o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            String[] o1s = o1.split(" ");
            String[] o2s = o2.split(" ");
            return Integer.parseInt(o1s[0]) * 10 + SeasonType.getIndex(o1s[1]) > Integer.parseInt(o2s[0]) * 10 + SeasonType.getIndex(o2s[1]) ? -1 : 1;
        });
        Map<Long, AffairDTO> affairIdMap = affairDTOS.stream().collect(Collectors.toMap(AffairDTO::getId, a -> a, (k1, k2) -> k1));
        Long[] affairIds = affairDTOS.stream().map(affairDTO -> affairDTO.getId()).toArray(Long[]::new);
        List<CourseEntity> courseEntities = courseDao.selectCoursesByIds(affairIds);
        courseEntities.stream().forEach(courseEntity -> {
            AffairDTO affairDTO = affairIdMap.get(courseEntity.getId());
            RoleInfoDTO roleInfoDTO = affairDTO.getRoleInfoVO();
            String term = courseEntity.getYear() + " " + SeasonType.getName(courseEntity.getSeason());
            String grade = GradeType.getName(courseEntity.getGrade());
            CourseSimple courseSimple = new CourseSimple(courseEntity);
            courseSimple.setName(affairDTO.getName());
            if (roleInfoDTO == null){
                courseSimple.setRoleType(UserType.NULL.getIndex());
            }else{
                courseSimple.setRoleType(roleInfoDTO.getMold());
            }
            //TODO 3 性能问题
            if (needGroup){
                List<? extends GroupSimple> groups;
                if (roleInfoDTO.getMold() == UserType.STUDENT.getIndex() || roleInfoDTO.getMold() == UserType.TUTOR.getIndex()){
                    groups = groupService.getMyGroups(courseEntity.getId(),userId[0],false);
                }else {
                    // 老师获取所有小组
                    groups = groupService.getAllGroups(courseEntity.getId(), false);
                }
                List<GroupSimple> groupSimples = groups.stream().map(g -> (GroupSimple)g).collect(Collectors.toList());
//            List<AffairDTO> groupAffairs = businessClient.getChildrenAffairByType(courseEntity.getId(), AffairType.GROUP.getIndex(), false);
//            List<GroupSimple> groupSimples = groupAffairs.stream().map(affair -> new GroupSimple(affair.getId(), affair.getName(), false)).collect(Collectors.toList());
                courseSimple.setGroupSimpleList(groupSimples);
            }
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
        //获取事务详情
        AffairDetailDTO affairDetailDTO = businessClient.getAffairDetail(courseId);
        CourseEntity courseEntity = courseDao.selectCourseById(courseId);
        if (affairDetailDTO == null || courseEntity == null){
            throw new ErrorCodeException(ResponseCode.COURSE_NOT_EXIST,courseId+" 课程不存在");
        }
        CourseDetail courseDetail = new CourseDetail(courseEntity);

        courseDetail.setName(affairDetailDTO.getName());
        courseDetail.setDescription(affairDetailDTO.getDescription());

        //获取一个课程的所有教师(获取一个事务下某种特定类型的角色)
        List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(courseId, UserType.TEACHER.getIndex(), StateType.NORMAL.getIndex());
        Long[] userIds = roleInfoDTOS.stream().map(roleInfoDTO -> roleInfoDTO.getUserId()).toArray(Long[]::new);
        List<UserEntity> userEntities = userDao.selectUsersByIds(userIds,"id","number");
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
    public long createCourse(CourseForm courseForm, long roleId, long departmentId, long userId) {
        //TODO 3 创建事务,调用出错了该怎么中止流程并捕获异常？
        long courseId = idClient.nextId("business","affair");
        AffairCreateForm affairCreateForm = new AffairCreateForm();
        affairCreateForm.setId(courseId);
        affairCreateForm.setName(courseForm.getName());
        affairCreateForm.setDescription(courseForm.getDescription());
        affairCreateForm.setAffairMold(AffairType.COURSE.getIndex());
        affairCreateForm.setParentAffairId(departmentId);
        affairCreateForm.setUserId(userId);
        affairCreateForm.setOperationRoleId(roleId);
        affairCreateForm.setOwnerRoleTitle(UserType.DEAN.getName());
        affairCreateForm.setOwnerRoleMold(UserType.DEAN.getIndex());
        SimpleResponse simpleResponse = businessClient.createAffair(affairCreateForm);
        if (simpleResponse.getCode() != 0){
            throw new ErrorCodeException(simpleResponse.getCode(),simpleResponse.getException());
        }
        //TODO 3 创建课程资料文件夹，调用出错该怎么回滚？
        long folderId = idClient.nextId("file","file");
        fileClient.addFolder(0,"课程资料", roleId, courseId,folderId);
        CourseEntity courseEntity = (CourseEntity) ObjectUtil.deepCopy(courseForm, CourseEntity.class);
        courseEntity.setId(courseId);
        courseEntity.setDefaultFolder(folderId);
        courseDao.addCourse(courseEntity);
        return courseId;
    }

    @Override
    public void modifyCourse(CourseForm courseForm, long roleId) {
        AffairModifyForm affairModifyForm = new AffairModifyForm();
        affairModifyForm.setOperationRoleId(roleId);
        affairModifyForm.setId(courseForm.getId());
        affairModifyForm.setName(courseForm.getName());
        affairModifyForm.setDescription(courseForm.getDescription());
        businessClient.modifyAffair(affairModifyForm);
        //TODO 2 回滚操作
        CourseEntity courseEntity = (CourseEntity) ObjectUtil.deepCopy(courseForm, CourseEntity.class);
        courseEntity.update();
    }

    @Override
    public void setInviteCode(long id, String inviteCode) {
        if (null == inviteCode || inviteCode.length() == 0 || inviteCode.length() > 10) {
            throw new ErrorCodeException(ResponseCode.PARAM_ERROR, "邀请码不能为空且长度必须小于10");
        }
        CourseEntity courseEntity = courseDao.selectCourseById(id);
        if (courseEntity == null) {
            throw new ErrorCodeException(ResponseCode.COURSE_NOT_EXIST, "课程不存在");
        }
        courseEntity.setInviteCode(inviteCode);
        courseEntity.update();
    }


}
