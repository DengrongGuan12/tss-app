package cn.superid.tss.service.impl;

import cn.superid.tss.WebApplication;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.vo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApplication.class)
@WebAppConfiguration
public class UserServiceTest {
    @Autowired
    IUserService userService;

    @Test
    public void testGetUserInfo() {
        UserInfo userInfo = userService.getUserInfo(0L);
        System.out.println(userInfo.getAvatar());
    }

}