package cn.superid.tss.service;

import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.forms.SubmitForm;
import cn.superid.tss.vo.Attachment;

import java.util.List;

public interface IFileService {

    List<Attachment> getAttachments(long activity);

    List<Long> uploadAttachments(List<AttachmentForm> attachmentFormList,long activityId,long courseId);

    long submitHomework(SubmitForm form,long activityId,long courseId);




}
