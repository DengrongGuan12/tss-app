package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.vo.CourseDetail;
import cn.superid.tss.vo.CourseSimple;
import cn.superid.tss.vo.GroupSimple;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午4:36
 **/
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @ApiOperation(value="获取我的课程列表",response = CourseSimple.class)
    @RequestMapping(value = "/getMyCourses", method = RequestMethod.GET)
    public SimpleResponse getMyCourses(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                       @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId){
        Map<String,Map> result = new LinkedHashMap<>();
        Map<String,List> courseSimpleMap = new LinkedHashMap<>();
        CourseSimple courseSimple1 = new CourseSimple();
        courseSimple1.setGrade("大一");
        courseSimple1.setName("计算系统基础");
        courseSimple1.setRoleType(UserType.getName(0));
        courseSimple1.setTerm("2017");
        List<GroupSimple> groupSimpleList = new ArrayList<>();
        for (int i = 1;i<20;i++){
            GroupSimple groupSimple = new GroupSimple();
            groupSimple.setMine(false);
            groupSimple.setName("小组"+i);
            groupSimpleList.add(groupSimple);
        }
        courseSimple1.setGroupSimpleList(groupSimpleList);
        CourseSimple courseSimple2 = new CourseSimple();
        courseSimple2.setGrade("大一");
        courseSimple2.setName("离散数学");
        courseSimple2.setRoleType(UserType.getName(0));
        courseSimple2.setTerm("2017");
        List<GroupSimple> groupSimpleList2 = new ArrayList<>();
        for (int i = 1;i<20;i++){
            GroupSimple groupSimple = new GroupSimple();
            groupSimple.setMine(false);
            groupSimple.setName("小组"+i);
            groupSimpleList2.add(groupSimple);
        }
        courseSimple2.setGroupSimpleList(groupSimpleList2);
        List<CourseSimple> courseSimples = new ArrayList<>();
        courseSimples.add(courseSimple1);
        courseSimples.add(courseSimple2);
        courseSimpleMap.put("大一",courseSimples);

        CourseSimple courseSimple3 = new CourseSimple();
        courseSimple3.setGrade("大二");
        courseSimple3.setName("操作系统");
        courseSimple3.setRoleType(UserType.getName(1));
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
        return SimpleResponse.ok(result);
    }

    @ApiOperation(value = "获取课程信息",response = CourseDetail.class)
    @RequestMapping(value = "/getCourseDetail", method = RequestMethod.GET)
    public SimpleResponse getCourseDetail(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                          @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                          @RequestParam(value = "courseId") long courseId){
        return SimpleResponse.ok(new CourseDetail());
    }

    @ApiOperation(value = "获取所有课程", response = CourseSimple.class)
    @RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
    public SimpleResponse getAllCourses(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                        @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId){
        Map<String,Map> result = new LinkedHashMap<>();
        Map<String,List> courseSimpleMap = new LinkedHashMap<>();
        CourseSimple courseSimple1 = new CourseSimple();
        courseSimple1.setGrade("大一");
        courseSimple1.setName("计算系统基础");
        courseSimple1.setRoleType(UserType.getName(0));
        courseSimple1.setTerm("2017");
        CourseSimple courseSimple2 = new CourseSimple();
        courseSimple2.setGrade("大一");
        courseSimple2.setName("离散数学");
        courseSimple2.setRoleType(UserType.getName(0));
        courseSimple2.setTerm("2017");
        List<CourseSimple> courseSimples = new ArrayList<>();
        courseSimples.add(courseSimple1);
        courseSimples.add(courseSimple2);
        courseSimpleMap.put("大一",courseSimples);
        CourseSimple courseSimple3 = new CourseSimple();
        courseSimple3.setGrade("大二");
        courseSimple3.setName("操作系统");
        courseSimple3.setRoleType(UserType.getName(1));
        courseSimple3.setTerm("2017");
        List<CourseSimple> courseSimples1 = new ArrayList<>();
        courseSimples1.add(courseSimple3);
        courseSimpleMap.put("大二",courseSimples1);
        result.put("2017 FALL",courseSimpleMap);
        result.put("2017 SUMMER",courseSimpleMap);
        return SimpleResponse.ok(result);
    }


}
