package cn.superid.tss.service.impl;

import cn.superid.tss.BaseTest;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.vo.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceTest extends BaseTest{
    @Autowired
    IUserService userService;

    @Test
    public void testGetUserInfo() {
        UserInfo userInfo = userService.getUserInfo(20203L);
        System.out.println(userInfo.getAvatar());
    }

}