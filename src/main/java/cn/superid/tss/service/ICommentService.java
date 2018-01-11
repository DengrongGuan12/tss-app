package cn.superid.tss.service;

import cn.superid.tss.vo.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getComments(long roleId, long announcementId);
}
