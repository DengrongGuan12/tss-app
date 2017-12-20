package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.UserClient;
import cn.superid.common.rest.dto.UserInfoDTO;
import cn.superid.common.utils.BitMapUtil;
import cn.superid.tss.constant.DegreeType;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.util.DStatement;
import cn.superid.tss.vo.PersonInfoPublic;
import cn.superid.tss.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午1:24
 **/
@Service
public class UserService implements IUserService{
    @Autowired
    UserClient userClient;

    @Override
    public UserInfo getUserInfo(long userId) {
        // 获取用户通用信息
        UserInfoDTO userInfoDTO = userClient.findById(userId,UserInfoDTO.REALNAME,UserInfoDTO.GENDER,UserInfoDTO.AVATAR,UserInfoDTO.MOBILE,UserInfoDTO.PERSON_INFO_PUBLIC);
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(userInfoDTO.getAvatar());
        userInfo.setGender(userInfoDTO.getGender());
        userInfo.setMobile(userInfoDTO.getMobile());
        userInfo.setRealName(userInfoDTO.getRealname());
        userInfo.setPersonInfoPublic(BitMapUtil.fillDTO(userInfoDTO.getPersonInfoPublic(), PersonInfoPublic.class));

        UserEntity userEntity = DStatement.build(UserEntity.class).id(userId).selectOne();
        userInfo.setType(UserType.getName(userEntity.getType()));
        userInfo.setDegree(DegreeType.getName(userEntity.getDegree()));
        userInfo.setNumber(userEntity.getNumber());
        return userInfo;
    }

}
