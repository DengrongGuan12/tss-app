package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.AnnouncementDetailDTO;
import cn.superid.common.rest.dto.business.CreateAnnouncementForm;
import cn.superid.common.rest.dto.business.RichAnnouncementDTO;
import cn.superid.id_client.IdClient;
import cn.superid.tss.constant.ActivityType;
import cn.superid.tss.constant.HomeworkType;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.forms.AddHomeworkform;
import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.model.ActivityInfoEntity;
import cn.superid.tss.dao.IActivityDao;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.service.IFileService;
import cn.superid.tss.vo.Activity;
import cn.superid.tss.vo.GroupSimple;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ActivityService implements IActivityService{

    @Autowired
    BusinessClient client;
    @Autowired
    IFileService fileService;

    @Autowired
    IActivityDao activityDao;

    @Autowired
    IdClient idClient;

    @Autowired
    GroupService groupService;

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Override
    public List<Activity> getAllActivites(long courseId) {
        List<Activity> activities = new LinkedList<>();

        List<RichAnnouncementDTO> announcements;
        try {
            announcements = client.getAnnouncements(courseId);
            announcements.stream().forEach(item->{
                long id = item.getId();
                ActivityInfoEntity entity = activityDao.getActivityInfoById(id);
                if(null != entity){
                    activities.add(buildActivity(item,entity));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activities;
    }

    @Override
    public long createActivity(AddActivityForm form, int type, long courseId, long roleId, long userId) {

        long id = 0;
        CreateAnnouncementForm caf = new CreateAnnouncementForm();
        caf.setAffairId(courseId);caf.setTitle(form.getTitle());
        caf.setContent(form.getContent());caf.setRoleId(roleId);
        caf.setUseId(userId);
        try {
            SimpleResponse response = client.createAnnouncement(caf);
            id = Long.valueOf((Integer)response.getData());
            List<AttachmentForm> attachments = form.getAttachments();
            if(!CollectionUtils.isEmpty(attachments)){
                fileService.uploadAttachments(attachments,id,courseId,roleId,userId);
            }

            ActivityInfoEntity entity = new ActivityInfoEntity(id,type,-100,null,-100);
            activityDao.saveActivity(entity);
        } catch (Exception e) {
            throw new ErrorCodeException(ResponseCode.CREATE_ACTIVITY_FAILURE,"创建课程活动失败");
        }

        return id;
    }

    @Override
    public long createHomework(AddHomeworkform form, long courseId, long roleId, long userId) {
        //判断是什么类型的作业，如果是发布给小组的作业需要做特殊处理
        //调用superID创建发布接口
        //塞进自己的数据库里
        int homeworkType = form.getHomeworkType();
        if (homeworkType == HomeworkType.NORMAL.getIndex()) {//普通作业
            long homeworkID = insertHomework(form, courseId, roleId, userId, HomeworkType.NORMAL.getIndex(), -100);
        }

        if (homeworkType == HomeworkType.GROUP.getIndex()) {//小组作业
            long parentId = insertHomework(form, courseId, roleId, userId, HomeworkType.GROUP.getIndex(), -100);

            //分发给小组
            List<? extends GroupSimple> groups = groupService.getAllGroups(courseId, false);
            for (GroupSimple item : groups) {
                long groupId = item.getId();
                insertHomework(form, groupId, roleId, userId, HomeworkType.GROUP.getIndex(), parentId);
            }
        }

        return 0;
    }

    private long insertHomework(AddHomeworkform form, long affairId, long roleId, long userId,int homeworkType, long parentId){
        long homeworkId;
        CreateAnnouncementForm caf = new CreateAnnouncementForm();
        caf.setAffairId(affairId);
        caf.setTitle(form.getTitle());
        caf.setContent(form.getContent());
        caf.setRoleId(roleId);
        caf.setUseId(userId);
        try {
            SimpleResponse response = client.createAnnouncement(caf);
            homeworkId = Long.valueOf((Integer)response.getData());
            List<AttachmentForm> attachments = form.getAttachments();
            if(!CollectionUtils.isEmpty(attachments)){
                fileService.uploadAttachments(attachments, homeworkId, affairId, roleId, userId);
            }

            ActivityInfoEntity entity = new ActivityInfoEntity(homeworkId, ActivityType.Homework.getIndex(), homeworkType, form.getDeadline(), parentId);
            activityDao.saveActivity(entity);

        } catch (Exception e) {
            throw new ErrorCodeException(ResponseCode.CREATE_ACTIVITY_FAILURE, "创建小组作业失败");
        }

        return homeworkId;
    }

    @Override
    public Activity getActivity(long activityId) {

        AnnouncementDetailDTO dto;
        ActivityInfoEntity entity;
        try {
            dto = client.getAnnouncementDetails(activityId);
            entity = activityDao.getActivityInfoById(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorCodeException(ResponseCode.GET_ACTIVITY_FAILURE,"无法获得活动信息");
        }

        return buildActivity(dto,entity);
    }

//    @Override
//    public Homework getHomework(long homeworkId) {
//        AnnouncementDetailDTO dto;
//        ActivityInfoEntity entity;
//        try {
//            dto = client.getAnnouncementDetails(homeworkId);
//            entity = activityDao.getActivityInfoById(homeworkId);
//        } catch (Exception e) {
//            throw new ErrorCodeException(ResponseCode.GET_ACTIVITY_FAILURE,"无法获得活动信息");
//        }
//
//        return buildHomework(dto,entity);
//
//
//    }


    private Activity buildActivity(RichAnnouncementDTO dto,ActivityInfoEntity entity){
        return new Activity(dto.getId(),dto.getTitle(),dto.getThumbContent(),
                dto.getCreatorId(),dto.getCreatorUserId(),dto.getRoleName(),dto.getUsername(),dto.getModifyTime(),
                dto.getAvatar(),entity.getType());

    }

    private Activity buildActivity(AnnouncementDetailDTO dto,ActivityInfoEntity entity){
        return new Activity(dto.getAnnouncementId(),dto.getTitle(),dto.getContent(),
                dto.getCreatorId(),dto.getCreatorUserId(),dto.getCreatorRoleName(),dto.getCreatorUsername(),dto.getModifyTime(),
                dto.getCreatorAvatar(),entity.getDeadline(),entity.getHomeworkType(),entity.getType());

    }






}
