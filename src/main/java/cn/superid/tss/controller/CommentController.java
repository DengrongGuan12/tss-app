package cn.superid.tss.controller;


import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.service.ICommentService;
import cn.superid.tss.vo.Comment;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liushao
 */
@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {

    @Autowired
    ICommentService commentService;

    @ApiOperation(value = "获取活动下的评论", response = Comment.class)
    @RequestMapping(value = "/getComments", method = RequestMethod.GET)
    public SimpleResponse getComments(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "activityId") long activityId){
        List<Comment> comments = commentService.getComments(roleId,activityId);
        return SimpleResponse.ok(comments);
    }


    @ApiOperation(value = "添加评论", response = SimpleResponse.class)
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public SimpleResponse addComment(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                      @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                      @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                      @RequestParam(value = "activityId") long activityId,
                                      @RequestParam(value = "toRoleId") long toRoleId,
                                     @RequestParam(value = "content") String content){
        long id = commentService.addComment(activityId,content,roleId,toRoleId);
        return SimpleResponse.ok("success");
    }


    @ApiOperation(value = "删除评论", response = SimpleResponse.class)
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public SimpleResponse deleteComment(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                     @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                     @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                     @RequestParam(value = "activityId") long activityId,
                                     @RequestParam(value = "commentId") long commentId
                                     ){
        long id = commentService.deleteComment(commentId,roleId,activityId);
        return SimpleResponse.ok("success");
    }






}
