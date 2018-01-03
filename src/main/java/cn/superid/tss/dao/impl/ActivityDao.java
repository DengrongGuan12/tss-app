package cn.superid.tss.dao.impl;

import cn.superid.tss.model.ActivityInfoEntity;
import cn.superid.tss.dao.IActivityDao;
import cn.superid.tss.util.DStatement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityDao implements IActivityDao{

    @Override
    public ActivityInfoEntity getActivityInfoById(long id) {
        return DStatement.build(ActivityInfoEntity.class).eq("id",id).selectOne();
    }

    @Override
    public List<ActivityInfoEntity> getActivitiesByParent(long parentId) {

        return DStatement.build(ActivityInfoEntity.class).eq("parent_id",parentId).selectList();
    }
}
