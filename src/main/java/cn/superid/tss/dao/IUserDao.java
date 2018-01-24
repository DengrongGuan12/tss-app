package cn.superid.tss.dao;

import cn.superid.common.rest.dto.UserInfoDTO;
import cn.superid.tss.model.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author DengrongGuan
 * @create 2017-12-20 上午10:33
 **/
public interface IUserDao {

    UserEntity getUserEntity(long userId);

    String  getNumber(long userId);

    List<UserEntity> selectUsersByIds(Long[] ids, String ...fields);

    List<UserEntity> getUsersWithConditions(Map<String, Object> conditions, String... fields);

}
