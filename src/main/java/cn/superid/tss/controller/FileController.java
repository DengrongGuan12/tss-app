package cn.superid.tss.controller;


import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.RequestHeaders;
import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.forms.SubmitForm;
import cn.superid.tss.service.IFileService;
import cn.superid.tss.vo.Attachment;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liushao
 * 在活动详情中查看附件，上传附件
 */

@RestController
@RequestMapping(value = "/api/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    IFileService fileService;

    @ApiOperation(value = "获得活动中的附件",response = Attachment.class)
    @RequestMapping(value = "/getAttachments",method = RequestMethod.GET)
    public SimpleResponse getAttachments(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                         @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                         @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                         @RequestParam(value = "activityId")long activityId){
        List<Attachment> attachments = fileService.getAttachments(activityId);
        return SimpleResponse.ok(attachments);
    }

    @ApiOperation(value = "课程活动中上传附件",response = Long.class)
    @RequestMapping(value = "/uploadAttachment",method = RequestMethod.POST)
    public SimpleResponse uploadActivityAttachment(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                                   @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                                   @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                                   @RequestParam(value = "activityId")long activityId,
                                                   @RequestBody List<AttachmentForm> forms){

        List<Long> ids = fileService.uploadAttachments(forms,activityId,courseId);
        return SimpleResponse.ok(ids);

    }

    @ApiOperation(value = "课程活动中上传附件",response = Long.class)
    @RequestMapping(value = "/submitHomework",method = RequestMethod.POST)
    public SimpleResponse submitHomework(@RequestHeader(RequestHeaders.USER_ID_HEADER) long userId,
                                         @RequestHeader(RequestHeaders.ROLE_ID_HEADER) long roleId,
                                         @RequestHeader(RequestHeaders.AFFAIR_ID_HEADER) long courseId,
                                         @RequestParam(value = "activityId")long activityId,
                                         @RequestBody SubmitForm form){
        long id = fileService.submitHomework(form,activityId,courseId);
        return SimpleResponse.ok(id);
    }

}
