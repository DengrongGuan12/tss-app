package cn.superid.tss.dao;

import cn.superid.tss.model.SubmitEntity;

import java.util.List;

public interface ISubmitDao {

    int saveSubmit(SubmitEntity entity);

    List<SubmitEntity> getSubmits(long activityId);

    List<SubmitEntity> getSubmits(List<Long> activityIds);
}
