package cn.superid.tss.service.impl;

import cn.superid.tss.BaseTest;
import cn.superid.tss.forms.AttachmentForm;
import cn.superid.tss.forms.SubmitForm;
import cn.superid.tss.service.IFileService;
import cn.superid.tss.vo.Attachment;
import cn.superid.tss.vo.Submit;
import cn.superid.tss.vo.SubmitCount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileServiceTest extends BaseTest{

    private  static final String FILE_NAME = "测试文件1.docx";
    private static final String FILE_URL = "affair/115103/files/AMJrklvEI/测试文件1.docx";
    private static final double SIZE = 28030;


    private static final long courseId = 130008;
    private static final long teacherRoleID = 920208;
    private static final long teacherUserID = 30720;
    private static final long ACTIVITYID = 240504;

    private static final long STUDENT_ROLE_ID = 1131603;
    private static final long STUDENT_USER_ID = 20203;




    @Autowired
    IFileService fileService;



    @Test
    public void testUploadAttachment(){

        List<AttachmentForm> forms = new ArrayList<>();
        forms.add(new AttachmentForm(FILE_NAME,FILE_URL,SIZE));
        forms.add(new AttachmentForm(FILE_NAME,FILE_URL,SIZE));
        fileService.uploadAttachments(forms,ACTIVITYID,courseId,teacherRoleID,teacherUserID);
    }

    @Test
    public void testSubmitHomework(){
        SubmitForm form = new SubmitForm();
        form.setFileName(FILE_NAME);
        form.setUrl(FILE_URL);
        form.setSize(SIZE);
        fileService.submitHomework(form, ACTIVITYID,courseId,STUDENT_ROLE_ID,STUDENT_USER_ID);
    }


    @Test
    public void testGetAttachments(){
        List<Attachment> attachments = fileService.getAttachments(240503);

    }

    @Test
    public void testGetSubmits(){
        List<Submit> submits = fileService.getSubmits(240503);
    }

    @Test
    public void testSubmitCount(){
        SubmitCount count = fileService.getSubmitCount(240503,130008);
    }

}
