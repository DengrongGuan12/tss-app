package cn.superid.tss.service;

import cn.superid.tss.vo.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getComments(long roleId, long announcementId);

    long addComment(long acticityId,String content,long roleId,long toRoleId);

    int deleteComment(long commentId, long roleId, long activityId);


}
