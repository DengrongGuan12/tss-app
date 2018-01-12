package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.AnnouncementCommentVO;
import cn.superid.tss.service.ICommentService;
import cn.superid.tss.vo.Comment;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Comment> comments;

        List<AnnouncementCommentVO> commentVOS = businessClient.getCommentList(roleId, announcementId);
        if(CollectionUtils.isEmpty(commentVOS)){

        }
        return null;
    }

    @Override
    public Comment addComment(long acticityId, String content, long roleId, long toRoleId) {
        businessClient.addComment(roleId,acticityId,content,toRoleId);
        return null;
    }


}
