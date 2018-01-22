package cn.superid.tss.service.impl;

import cn.superid.tss.BaseTest;
import cn.superid.tss.service.ICommentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CommentServiceTest extends BaseTest{
    @Autowired
    ICommentService commentService;
    @Test
    public void getComments() throws Exception {
        commentService.getComments(0, 241242);
    }

    @Test
    public void addComment() throws Exception {
    }

    @Test
    public void deleteComment() throws Exception {
    }

}