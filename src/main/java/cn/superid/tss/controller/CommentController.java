package cn.superid.tss.controller;


import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.vo.Comment;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liushao
 */
@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {
    @ApiOperation(value = "获取活动下的评论", response = Comment.class)
    @RequestMapping(value = "/getComments", method = RequestMethod.GET)
    public SimpleResponse getComments(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId){
        List<Comment> comments = new ArrayList<>();
        for (int i = 0;i<10;i++){
            comments.add(Comment.mock(i));
        }
        return SimpleResponse.ok(comments);
    }
}
