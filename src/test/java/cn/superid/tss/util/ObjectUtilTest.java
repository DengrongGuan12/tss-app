package cn.superid.tss.util;

import cn.superid.tss.BaseTest;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import org.junit.Test;


public class ObjectUtilTest extends BaseTest{

    @Test
    public void testDeepCopy() throws Exception {
        CourseForm courseForm = CourseForm.mock(3);
        CourseEntity courseEntity = (CourseEntity) ObjectUtil.deepCopy(courseForm,CourseEntity.class);
        courseEntity.save();
    }

}