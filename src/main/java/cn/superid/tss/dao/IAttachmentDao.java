package cn.superid.tss.dao;

import cn.superid.tss.model.AttachmentEntity;

import java.util.List;

public interface IAttachmentDao {
    List<AttachmentEntity> getAttachmentsByActivityId(long activityId);

    int batchSave(List<AttachmentEntity> entities);

}
