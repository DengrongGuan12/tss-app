package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.service.impl.UserService;
import cn.superid.tss.vo.PersonInfoPublic;
import cn.superid.tss.vo.UserInfo;
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
    UserService userService;
    @ApiOperation(value = "个人中心获取个人信息", response = UserInfo.class)
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public SimpleResponse getUserInfo(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId){
        return SimpleResponse.ok(userService.getUserInfo(userId));
    }

}
