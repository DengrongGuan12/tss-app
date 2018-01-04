package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.UserClient;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.dao.IActivityDao;
import cn.superid.tss.dao.IAttachmentDao;
import cn.superid.tss.dao.ISubmitDao;
import cn.superid.tss.dao.impl.ActivityDao;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.forms.SubmitForm;
import cn.superid.tss.model.ActivityInfoEntity;
import cn.superid.tss.model.AttachmentEntity;
import cn.superid.tss.model.SubmitEntity;
import cn.superid.tss.service.IFileService;
import cn.superid.tss.util.DStatement;
import cn.superid.tss.util.ObjectUtil;
import cn.superid.tss.vo.Attachment;
import cn.superid.tss.vo.Submit;
import cn.superid.tss.vo.SubmitCount;
import org.apache.commons.collections.CollectionUtils;
import org.exemodel.session.AbstractSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService implements IFileService{

    @Autowired
    IAttachmentDao attachmentDao;

    @Autowired
    ISubmitDao submitDao;

    @Autowired
    UserClient userClient;

    @Autowired
    IActivityDao activityDao;





    @Override
    public List<Attachment> getAttachments(long activityId) {
        List<AttachmentEntity> entities = attachmentDao.getAttachmentsByActivityId(activityId);
        List<Attachment> attachments = Collections.emptyList();
        entities.stream().forEach(item->{
            attachments.add((Attachment) ObjectUtil.deepCopy(item,Attachment.class));
        });
        return attachments;
    }

    @Override
    public int uploadAttachments(List<AttachmentForm> attachmentFormList, long activityId, long courseId,long roleId,long userId) {
        List<AttachmentEntity> entities = new ArrayList<>();
        attachmentFormList.stream().forEach(item->{
            entities.add(buildEntity(item,activityId,roleId,userId));
        });
        //TODO 上传到文件库里
        attachmentDao.batchSave(entities);
        return 0;
    }

    @Override
    public long submitHomework(SubmitForm form, long activityId, long courseId,long roleId,long userId) {
        try {
            SubmitEntity entity = buildEntity(form,activityId,roleId,userId);
            submitDao.saveSubmit(entity);
        } catch (Exception e) {
           throw new ErrorCodeException(ResponseCode.SUBMIT_HOMEWORK_FAILURE,"提交作业失败");
        }
        return 0;
    }

    @Override
    public List<Submit> getSubmits(long activityId) {
        //获得本身的submit
        List<SubmitEntity> entities = submitDao.getSubmits(activityId);
        //获得由这个activity复制的
        List<ActivityInfoEntity> infoEntities = activityDao.getActivitiesByParent(activityId);


        if(!CollectionUtils.isEmpty(infoEntities)){
            List<Long> ids = infoEntities.stream().map(ActivityInfoEntity::getId).collect(Collectors.toList());
            List<SubmitEntity> childEnts = submitDao.getSubmits(ids);
            entities.addAll(childEnts);
        }


        List<Submit> submits = Collections.emptyList();
        entities.stream().forEach(item->{
            submits.add(entity2submit(item));
        });
        return null;
    }

    @Override
    public SubmitCount getSubmitCount(long activityId) {
        return null;
    }

    private AttachmentEntity buildEntity(AttachmentForm form,long activityId,long roleId,long userId){
        String userName = userClient.findById(userId).getUsername();
        //TODO 获得roleTitle
        String roleTile = null;
        return new AttachmentEntity(0l,form.getUrl(),form.getFileName(),
                activityId,roleId,roleTile,userId,userName,new Timestamp(System.currentTimeMillis()));

    }

    private SubmitEntity buildEntity(SubmitForm form,long activityId,long roleId,long userId){
        String userName = userClient.findById(userId).getUsername();
        //TODO 获得roleTitle
        String roleTile = null;
        Timestamp deadline = activityDao.getActivityInfoById(activityId).getDeadline();
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        return new SubmitEntity(0l,form.getUrl(),form.getFileName(),
                activityId,roleId,roleTile,userId,userName,curr,curr.after(deadline)? 0:1);
    }

    private Submit entity2submit(SubmitEntity entity){
        return new Submit(entity.getId(),entity.getAttachmentUrl(),entity.getFileName(),entity.getActivityId(),entity.getRoleId(),
                entity.getRoleTitle(),entity.getUserId(),entity.getUserName(),entity.getSubmitTime(),entity.getIsDelayed());
    }
}
