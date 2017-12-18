package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.vo.StudentInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author DengrongGuan
 * @create 2017-12-12 下午2:41
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {

    @ApiOperation(value = "个人中心获取学生个人信息", response = StudentInfo.class)
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public SimpleResponse getUserInfo(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDegree("本科");
        studentInfo.setDepartment("软件学院");
        studentInfo.setGender(0);
        studentInfo.setGrade("2017级");
        studentInfo.setStuNumber("MF1632020");
        studentInfo.setRealName("管登荣");
        return SimpleResponse.ok(studentInfo);
    }

}
