package cn.superid.tss.dao.impl;

import cn.superid.tss.dao.IUserDao;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.util.DStatement;
import org.springframework.stereotype.Component;

/**
 * @author DengrongGuan
 * @create 2017-12-20 上午10:34
 **/

@Component
public class UserDao implements IUserDao{
    @Override
    public UserEntity getUserEntity(long userId) {
        return DStatement.build(UserEntity.class).id(userId).selectOne();
    }

    @Override
    public String getNumber(long userId) {
        UserEntity entity =  DStatement.build(UserEntity.class).id(userId).selectOne("number");
        return entity.getNumber();
    }
}
