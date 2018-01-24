package cn.superid.tss.dao.impl;

import cn.superid.tss.dao.IUserDao;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.util.DStatement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<UserEntity> selectUsersByIds(Long[] ids, String... fields) {
        return DStatement.build(UserEntity.class).in("id", ids).selectList(fields);
    }

    public List<UserEntity> getUsersWithConditions(Map<String, Object> conditions, String... fields){
        DStatement statement = DStatement.build(UserEntity.class);
        conditions.entrySet().stream().forEach(stringObjectEntry -> statement.eq(stringObjectEntry.getKey(),stringObjectEntry.getValue()));
        return statement.selectList(fields);
    }
}
