package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.AnnouncementCommentVO;
import cn.superid.tss.constant.CommonConstant;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.service.ICommentService;
import cn.superid.tss.vo.Comment;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2018-01-11 上午9:32
 **/
@Service
public class CommentService implements ICommentService {

    @Autowired
    BusinessClient businessClient;

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
    public long addComment(long acticityId, String content, long roleId, long toRoleId) {
        try {
            SimpleResponse response = businessClient.addComment(roleId,acticityId,content,toRoleId);
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
