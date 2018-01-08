package cn.superid.tss.dao;

import cn.superid.tss.model.ActivityInfoEntity;
import cn.superid.tss.vo.Activity;

import java.util.List;

public interface IActivityDao {

    ActivityInfoEntity getActivityInfoById(long id);

    List<ActivityInfoEntity> getActivitiesByParent(long parentId);

    int saveActivity(ActivityInfoEntity entity);


}
