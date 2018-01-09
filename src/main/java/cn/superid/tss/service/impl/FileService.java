package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.client.FileClient;
import cn.superid.common.rest.client.UserClient;
import cn.superid.id_client.IdClient;
import cn.superid.tss.constant.CommonConstant;
import cn.superid.tss.constant.HomeworkType;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.dao.IActivityDao;
import cn.superid.tss.dao.IAttachmentDao;
import cn.superid.tss.dao.ICourseDao;
import cn.superid.tss.dao.ISubmitDao;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.forms.SubmitForm;
import cn.superid.tss.model.ActivityInfoEntity;
import cn.superid.tss.model.AttachmentEntity;
import cn.superid.tss.model.SubmitEntity;
import cn.superid.tss.service.IFileService;
import cn.superid.tss.util.ObjectUtil;
import cn.superid.tss.vo.Attachment;
import cn.superid.tss.vo.Submit;
import cn.superid.tss.vo.SubmitCount;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    IdClient idClient;

    @Autowired
    IActivityDao activityDao;

    @Autowired
    GroupService gs;

    @Autowired
    FileClient fileClient;

    @Autowired
    ICourseDao courseDao;

    @Autowired
    BusinessClient businessClient;




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
        if(!CollectionUtils.isEmpty(attachmentFormList)){
            attachmentFormList.stream().forEach(item->{
                entities.add(buildEntity(item,activityId,roleId,userId));
            });

            attachmentDao.batchSave(entities);

            long folderId = courseDao.selectCourseById(activityId).getDefaultFolder();
            entities.stream().forEach(item->{
                long fileId = idClient.nextId("file","file");
                fileClient.addFile(fileId,folderId,item.getFileName(),item.getAttachmentUrl(),item.getSize(),courseId,roleId);
            });
        }


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
        return submits;
    }

    @Override
    public SubmitCount getSubmitCount(long activityId) {
        SubmitCount count = new SubmitCount();
        ActivityInfoEntity entity  = activityDao.getActivityInfoById(activityId);
        //获得作业的类型
        int homeworkType = entity.getHomeworkType();
        int shouldSubmit = 0;
        if(homeworkType == HomeworkType.GROUP.getIndex()){//小组作业
            shouldSubmit= gs.getAllGroups(activityId,false).size();
            count.setTotal(shouldSubmit);
        }
        if(homeworkType == HomeworkType.NORMAL.getIndex()){//普通作业
            shouldSubmit = (int) businessClient.getAffairAllRoles(activityId).stream().filter(item->item.getMold()==UserType.STUDENT.getIndex()).count();
            count.setTotal(shouldSubmit);
        }

        int submitted = getSubmits(activityId).size();
        count.setSubmitted(submitted);
        count.setToSubmit(shouldSubmit - submitted);
        return count;
    }

    private AttachmentEntity buildEntity(AttachmentForm form,long activityId,long roleId,long userId){
        String userName = userClient.findById(userId).getUsername();
//
//        Long ids[] = new Long[1];
//        ids[0] = roleId;
//        String roleTile;
//        try {
//            roleTile= businessClient.fillRole(ids).get(0).getRoleTitle();
//        } catch (Exception e) {
//            roleTile = null;
//        }
        long attachmentId = idClient.nextId(CommonConstant.SERVICE_NAME,"attachment");
        return new AttachmentEntity(attachmentId,form.getUrl(),form.getFileName(),form.getSize(),
                activityId,roleId,null,userId,userName,new Timestamp(System.currentTimeMillis()));

    }

    private SubmitEntity buildEntity(SubmitForm form,long activityId,long roleId,long userId){
        String userName = userClient.findById(userId).getUsername();
//        Long ids[] = new Long[1];
//        ids[0] = roleId;
        String roleTile = null;//businessClient.fillRole(ids).get(0).getRoleTitle();
        Timestamp deadline = activityDao.getActivityInfoById(activityId).getDeadline();
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        long submitId = idClient.nextId(CommonConstant.SERVICE_NAME,"");
        return new SubmitEntity(submitId,form.getUrl(),form.getFileName(),
                activityId,roleId,roleTile,userId,userName,curr,curr.after(deadline)? 0:1);
    }

    private Submit entity2submit(SubmitEntity entity){
        return new Submit(entity.getId(),entity.getAttachmentUrl(),entity.getFileName(),entity.getActivityId(),entity.getRoleId(),
                entity.getRoleTitle(),entity.getUserId(),entity.getUserName(),entity.getSubmitTime(),entity.getIsDelayed());
    }
}
