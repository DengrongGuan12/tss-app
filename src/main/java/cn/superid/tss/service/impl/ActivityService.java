package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.AnnouncementDetailDTO;
import cn.superid.common.rest.dto.business.RichAnnouncementDTO;
import cn.superid.tss.constant.ActivityType;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.forms.AddActivityForm;
import cn.superid.tss.forms.AddHomeworkform;
import cn.superid.tss.model.ActivityInfoEntity;
import cn.superid.tss.dao.IActivityDao;
import cn.superid.tss.service.IActivityService;
import cn.superid.tss.vo.Activity;
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
    IActivityDao activityDao;

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
                activities.add(buildActivity(item,entity));
            });
        } catch (Exception e) {
            throw new ErrorCodeException(ResponseCode.GET_ACTIVITIES_FAILURE,"获得课程活动列表失败");
        }

        return activities;
    }

    @Override
    public long createActivity(AddActivityForm form, int type, long courseId, long roleId, long userId) {
        //调用superID创建发布接口
        //塞进自己的数据库里
        return 0;
    }

    @Override
    public long createHomework(AddHomeworkform form, long courseId, long roleId, long userId) {
        //判断是什么类型的作业，如果是发布给小组的作业需要做特殊处理
        //调用superID创建发布接口
        //塞进自己的数据库里

        return 0;
    }

    @Override
    public Activity getActivity(long activityId) {

        AnnouncementDetailDTO dto;
        ActivityInfoEntity entity;
        try {
            dto = client.getAnnouncementDetails(activityId);
            entity = activityDao.getActivityInfoById(activityId);
        } catch (Exception e) {
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
        return new Activity(dto.getId(),dto.getTitle(),dto.getContent(),
                dto.getCreatorId(),dto.getCreatorUserId(),dto.getRoleName(),dto.getUsername(),dto.getModifyTime(),
                dto.getAvatar(),entity.getType());

    }

    private Activity buildActivity(AnnouncementDetailDTO dto,ActivityInfoEntity entity){
        return new Activity(dto.getAnnouncementId(),dto.getTitle(),dto.getContent(),
                dto.getCreatorId(),dto.getCreatorUserId(),dto.getCreatorRoleName(),dto.getCreatorUsername(),dto.getModifyTime(),
                dto.getCreatorAvatar(),entity.getDeadline(),entity.getHomeworkType(),entity.getType());

    }




}
