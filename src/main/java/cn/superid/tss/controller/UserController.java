package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.vo.PersonInfoPublic;
import cn.superid.tss.vo.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author DengrongGuan
 * @create 2017-12-12 下午2:41
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {

    @ApiOperation(value = "个人中心获取个人信息", response = UserInfo.class)
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public SimpleResponse getUserInfo(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId){
        UserInfo userInfo = new UserInfo();
        userInfo.setDegree("本科");
        userInfo.setDepartment("软件学院");
        userInfo.setGender(0);
        userInfo.setGrade("2017级");
        userInfo.setNumber("MF1632020");
        userInfo.setRealName("管登荣");
        userInfo.setAvatar("http://mkpub.oss-cn-hangzhou.aliyuncs.com/user/1000103/small_sdfhYhsdb.png");
        userInfo.setMobile("15950570277");
        userInfo.setPersonInfoPublic(PersonInfoPublic.setFalse());
        userInfo.setType("student");
        return SimpleResponse.ok(userInfo);
    }

}
