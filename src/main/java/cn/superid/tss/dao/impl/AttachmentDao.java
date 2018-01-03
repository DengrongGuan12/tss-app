package cn.superid.tss.dao.impl;

import cn.superid.tss.dao.IAttachmentDao;
import cn.superid.tss.model.AttachmentEntity;
import cn.superid.tss.util.DStatement;
import org.exemodel.session.AbstractSession;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AttachmentDao implements IAttachmentDao{


    @Override
    public List<AttachmentEntity> getAttachmentsByActivityId(long activityId) {
        return DStatement.build(AttachmentEntity.class).eq("activity_id",activityId).selectList();
    }

    @Override
    public int batchSave(List<AttachmentEntity> entities) {
        //TODO generateId
        AbstractSession.currentSession().saveBatch(entities);
        return 0;
    }
}
