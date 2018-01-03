package cn.superid.tss.dao.impl;

import cn.superid.tss.dao.ISubmitDao;
import cn.superid.tss.model.SubmitEntity;
import cn.superid.tss.util.DStatement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubmitDao implements ISubmitDao{


    @Override
    public int saveSubmit(SubmitEntity entity) {
        //TODO genetate Id
        return 0;
    }

    @Override
    public List<SubmitEntity> getSubmits(long activityId) {
        return DStatement.build(SubmitEntity.class).eq("activity_id",activityId).selectList();
    }

    @Override
    public List<SubmitEntity> getSubmits(List<Long> activityIds) {

        return DStatement.build(SubmitEntity.class).in("activity_id",activityIds).selectList();
    }
}
