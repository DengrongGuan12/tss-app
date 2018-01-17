package cn.superid.tss.service.impl;

import cn.superid.common.notification.dto.CommonMessage;
import cn.superid.common.notification.enums.MsgType;
import cn.superid.common.notification.enums.ResourceType;
import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.AffairDetailDTO;
import cn.superid.common.rest.dto.business.AnnouncementCommentVO;
import cn.superid.common.rest.dto.business.AnnouncementDetailDTO;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.*;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.msg.MsgComponent;
import cn.superid.tss.service.ICommentService;
import cn.superid.tss.util.JSONObjectBuilder;
import cn.superid.tss.vo.Comment;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DengrongGuan
 * @create 2018-01-11 上午9:32
 **/
@Service
public class CommentService implements ICommentService {

    @Autowired
    BusinessClient businessClient;

    @Autowired
    MsgComponent msgComponent;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Override
    public List<Comment> getComments(long roleId, long announcementId) {
        List<Comment> comments;

        try {
            List<AnnouncementCommentVO> commentVOS = businessClient.getCommentList(announcementId);
            if(!CollectionUtils.isEmpty(commentVOS)){
                comments = new ArrayList<>();
                commentVOS.stream().forEach(item->{
                    comments.add(Comment.transForm(item));
                });
                return comments;
            }
            return Collections.emptyList();
        } catch (Exception e) {
            //hrow new ErrorCodeException(ResponseCode.GET_COMMENTS_FAILURE,"获得评论失败");
            throw e;
        }
    }

    @Override
    public long addComment(long affairId, long activityId, String content, long roleId, long toRoleId) {
        try {
            CommonMessage commonMessage = null;
            JSONObject jsonObject = new JSONObjectBuilder().put("content",content).getJsonObject();
            if (toRoleId == 0){
                // 根据事务类型确定发送的人
                List<Long> receiverIds = new ArrayList<>();
                AffairDetailDTO affairDetailDTO = businessClient.getAffairDetail(affairId);
                if (affairDetailDTO.getMold() == AffairType.GROUP.getIndex()){
                    List<RoleInfoDTO> roleInfoDTOS = businessClient.getAffairAllRoles(affairId, StateType.NORMAL.getIndex());
                    receiverIds = roleInfoDTOS.stream().filter(roleInfoDTO -> roleInfoDTO.getMold() == UserType.MEMBER.getIndex() && roleInfoDTO.getRoleId() != roleId).map(RoleInfoDTO::getRoleId).collect(Collectors.toList());
                }else{
                    AnnouncementDetailDTO announcementDetailDTO = businessClient.getAnnouncementDetails(activityId);
                    receiverIds.add(announcementDetailDTO.getCreatorId());
                }
                commonMessage = msgComponent.genCommonMsg(affairId, roleId, receiverIds, MsgType.COMMENT, ResourceType.ANNOUNCEMENT, activityId, MsgTemplateType.TSS_COMMENT_ANNOUNCEMENT, jsonObject);
            }else {
                commonMessage = msgComponent.genCommonMsg(affairId, roleId, Arrays.asList(new Long[]{toRoleId}), MsgType.COMMENT, ResourceType.ANNOUNCEMENT, activityId, MsgTemplateType.TSS_REPLY_COMMENT, jsonObject);
            }
            SimpleResponse response = businessClient.addComment(roleId,activityId,content,toRoleId,commonMessage);
            if(!(response.getCode() == CommonConstant.SUCCESS_CODE)){
                throw new ErrorCodeException(ResponseCode.ADD_COMMENTS_FAILURE,"发表评论失败");
            }
        } catch (Exception e) {
            logger.error("add comment failure {}",e);
            //throw new ErrorCodeException(ResponseCode.ADD_COMMENTS_FAILURE,"发表评论失败");
            throw e;
        }
        return 0;
    }

    @Override
    public int deleteComment(long commentId, long roleId, long activityId) {

        try {
            SimpleResponse response  =businessClient.deleteComment(commentId,roleId,activityId);
            if(!(response.getCode() == CommonConstant.SUCCESS_CODE)){
                throw new ErrorCodeException(ResponseCode.DELETE_COMMENT_FAILURE,"删除评论失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new ErrorCodeException(ResponseCode.DELETE_COMMENT_FAILURE,"删除评论失败");
            throw e;
        }

        return 0;
    }


}
