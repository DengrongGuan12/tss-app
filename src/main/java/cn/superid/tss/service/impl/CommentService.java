package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.tss.service.ICommentService;
import cn.superid.tss.vo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DengrongGuan
 * @create 2018-01-11 上午9:32
 **/
@Service
public class CommentService implements ICommentService {

    @Autowired
    BusinessClient businessClient;

    @Override
    public List<Comment> getComments(long roleId, long announcementId) {
        businessClient.getCommentList(roleId, announcementId);
        return null;
    }
}
