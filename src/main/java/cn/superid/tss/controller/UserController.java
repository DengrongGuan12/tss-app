package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.utils.auth.PermissionConstants;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.UserInfo;
import com.blueskykong.auth.starter.annotation.PreAuth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DengrongGuan
 * @create 2017-12-12 下午2:41
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @ApiOperation(value = "个人中心获取个人信息", response = UserInfo.class)
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public SimpleResponse getUserInfo(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId){
        return SimpleResponse.ok(userService.getUserInfo(userId));
    }
    @ApiOperation(value = "教务员获取本学院的所有老师", response = Role.class)
    @RequestMapping(value = "/getTeachersOfDepartment", method = RequestMethod.GET)
    @PreAuth(PermissionConstants.CREATE_ROLE)
    public SimpleResponse getTeachersOfDepartment(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId){

        long departmentId = userService.getDepartmentIdOfUser(userId);
        return SimpleResponse.ok(roleService.getTeachersOfDepartment(departmentId));
    }


}
