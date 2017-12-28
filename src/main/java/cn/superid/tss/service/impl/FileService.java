package cn.superid.tss.service.impl;

import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.forms.SubmitForm;
import cn.superid.tss.service.IFileService;
import cn.superid.tss.vo.Attachment;
import cn.superid.tss.vo.Submit;
import cn.superid.tss.vo.SubmitCount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements IFileService{
    @Override
    public List<Attachment> getAttachments(long activity) {
        return null;
    }

    @Override
    public List<Long> uploadAttachments(List<AttachmentForm> attachmentFormList, long activityId, long courseId) {
        return null;
    }

    @Override
    public long submitHomework(SubmitForm form, long activityId, long courseId) {
        return 0;
    }

    @Override
    public List<Submit> getSubmits(long activityId) {
        return null;
    }

    @Override
    public SubmitCount getSubmitCount(long activityId) {
        return null;
    }
}
