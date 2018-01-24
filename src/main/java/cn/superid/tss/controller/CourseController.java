package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.utils.auth.PermissionConstants;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.constant.SeasonType;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.service.ICourseService;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.vo.CourseDetail;
import cn.superid.tss.vo.CourseSimple;
import cn.superid.tss.vo.GroupSimple;
import cn.superid.tss.vo.Role;
import com.blueskykong.auth.starter.annotation.PreAuth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午4:36
 **/
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    ICourseService courseService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserService userService;

    @ApiOperation(value = "获取我的课程列表", response = CourseSimple.class)
    @RequestMapping(value = "/getMyCourses", method = RequestMethod.GET)

    public SimpleResponse getMyCourses(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                       @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId) {
//        Map<String, Map> result = new TreeMap<>();
//        Map<String, List> courseSimpleMap = new LinkedHashMap<>();
//        CourseSimple courseSimple1 = new CourseSimple();
//        courseSimple1.setId(1);
//        courseSimple1.setGrade("大一");
//        courseSimple1.setName("计算系统基础");
//        courseSimple1.setRoleType(0);
//        courseSimple1.setTerm("2017");
//        List<GroupSimple> groupSimpleList = new ArrayList<>();
//        for (int i = 1; i < 20; i++) {
//            groupSimpleList.add(GroupSimple.mockOtherGroup(i));
//        }
//        courseSimple1.setGroupSimpleList(groupSimpleList);
//        CourseSimple courseSimple2 = new CourseSimple();
//        courseSimple2.setId(2);
//        courseSimple2.setGrade("大一");
//        courseSimple2.setName("离散数学");
//        courseSimple2.setRoleType(0);
//        courseSimple2.setTerm("2017");
//        List<GroupSimple> groupSimpleList2 = new ArrayList<>();
//        for (int i = 1; i < 20; i++) {
//            groupSimpleList2.add(GroupSimple.mockOtherGroup(i));
//        }
//        courseSimple2.setGroupSimpleList(groupSimpleList2);
//        List<CourseSimple> courseSimples = new ArrayList<>();
//        courseSimples.add(courseSimple1);
//        courseSimples.add(courseSimple2);
//        courseSimpleMap.put("大一", courseSimples);
//
//        CourseSimple courseSimple3 = new CourseSimple();
//        courseSimple3.setId(3);
//        courseSimple3.setGrade("大二");
//        courseSimple3.setName("操作系统");
//        courseSimple3.setRoleType(1);
//        courseSimple3.setTerm("2017");
//        List<GroupSimple> groupSimpleList3 = new ArrayList<>();
//        for (int i = 1; i < 20; i++) {
//            groupSimpleList3.add(GroupSimple.mockOtherGroup(i));
//        }
//        courseSimple3.setGroupSimpleList(groupSimpleList3);
//        List<CourseSimple> courseSimples1 = new ArrayList<>();
//        courseSimples1.add(courseSimple3);
//        courseSimpleMap.put("大二", courseSimples1);
//        result.put("2017 FALL", courseSimpleMap);
//        result.put("2017 SUMMER", courseSimpleMap);
        Map<String,Map> result = courseService.getUserCourses(userId);
        return SimpleResponse.ok(result);
    }

    @ApiOperation(value = "获取课程信息", response = CourseDetail.class)
    @RequestMapping(value = "/getCourseDetail", method = RequestMethod.GET)

    public SimpleResponse getCourseDetail(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                          @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                          @RequestParam(value = "courseId") long courseId) {
        //调用courseService
        CourseDetail courseDetail = courseService.getCourseDetail(courseId);
        //调用roleService 设置我在课程中的角色
        Role role = roleService.getRoleInAffair(courseId,userId);
        if (role != null) {
            courseDetail.setRoleType(role.getRoleType());
        }else{
            courseDetail.setRoleType(UserType.NULL.getIndex());
        }
        return SimpleResponse.ok(courseDetail);
//        return SimpleResponse.ok(CourseDetail.mockCourseDetail());
    }

    @ApiOperation(value = "获取所有课程", response = CourseSimple.class)
    @RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
    public SimpleResponse getAllCourses(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                        @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId) {
//        Map<String, Map> result = new LinkedHashMap<>();
//        Map<String, List> courseSimpleMap = new LinkedHashMap<>();
//        CourseSimple courseSimple1 = new CourseSimple();
//        courseSimple1.setId(1);
//        courseSimple1.setGrade("大一");
//        courseSimple1.setName("计算系统基础");
//        courseSimple1.setRoleType(0);
//        courseSimple1.setTerm("2017");
//        CourseSimple courseSimple2 = new CourseSimple();
//        courseSimple2.setId(2);
//        courseSimple2.setGrade("大一");
//        courseSimple2.setName("离散数学");
//        courseSimple2.setRoleType(0);
//        courseSimple2.setTerm("2017");
//        List<CourseSimple> courseSimples = new ArrayList<>();
//        courseSimples.add(courseSimple1);
//        courseSimples.add(courseSimple2);
//        courseSimpleMap.put("大一", courseSimples);
//        CourseSimple courseSimple3 = new CourseSimple();
//        courseSimple3.setId(3);
//        courseSimple3.setGrade("大二");
//        courseSimple3.setName("操作系统");
//        courseSimple3.setRoleType(1);
//        courseSimple3.setTerm("2017");
//        List<CourseSimple> courseSimples1 = new ArrayList<>();
//        courseSimples1.add(courseSimple3);
//        courseSimpleMap.put("大二", courseSimples1);
//        result.put("2017 FALL", courseSimpleMap);
//        result.put("2017 SUMMER", courseSimpleMap);
        // 获取我所在的院系id
        long departmentId = userService.getDepartmentIdOfUser(userId);
        Map<String,Map> result = courseService.getCoursesOfDepartment(departmentId);
        return SimpleResponse.ok(result);
    }

    @ApiOperation(value = "创建课程", response = SimpleResponse.class)
    @RequestMapping(value = "/createCourse", method = RequestMethod.POST)
//    @PreAuth(value = PermissionConstants.CREATE_AFFAIR)
    public SimpleResponse createCourse(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                       @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                       @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long departmentId,
                                       @RequestBody CourseForm course) {
        long courseId = courseService.createCourse(course, roleId, departmentId, userId);
        return SimpleResponse.ok(courseId);
    }

    @ApiOperation(value = "修改课程", response = SimpleResponse.class, notes = "所有值都必传，不管有没有改变")
    @RequestMapping(value = "/modifyCourse", method = RequestMethod.POST)
//    @PreAuth(value = PermissionConstants.SET_AFFAIR_INFO)
    public SimpleResponse modifyCourse(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                       @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                       @RequestBody CourseForm course) {
        courseService.modifyCourse(course, roleId);
        return SimpleResponse.ok(null);
    }

    @ApiOperation(value = "设置课程邀请码", response = SimpleResponse.class, notes = "id, inviteCode 必传")
    @RequestMapping(value = "/setInviteCode", method = RequestMethod.POST)
//    @PreAuth(value = PermissionConstants.SHARE_AFFAIR)
    public SimpleResponse setInviteCode(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                        @RequestParam long id,
                                        @RequestParam String inviteCode) {

        courseService.setInviteCode(id, inviteCode);
        return SimpleResponse.ok(null);
    }


}
