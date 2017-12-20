package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.UserClient;
import cn.superid.common.rest.dto.UserInfoDTO;
import cn.superid.common.rest.forms.UserListForm;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.vo.StudentInfo;
import cn.superid.tss.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午1:24
 **/
@Component
public class UserService implements IUserService{
    @Autowired
    UserClient userClient;

    @Override
    public UserInfo getUserInfo(long userId) {
        // 获取用户通用信息
        UserInfoDTO userInfoDTO = userClient.findById(userId,UserInfoDTO.REALNAME,UserInfoDTO.GENDER,UserInfoDTO.AVATAR);

        return null;
    }

}
