package cn.superid.tss.service.impl;


import cn.superid.tss.BaseTest;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.service.ICourseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;


public class CourseServiceTest extends BaseTest{
    @Autowired
    ICourseService courseService;

    @Test
    public void testGetUserCourses() throws Exception {
        courseService.getUserCourses(20203L);
    }

    @Test
    public void testCreateCourse(){
        CourseEntity courseEntity = CourseForm.mock(3);
//        courseEntity.save();
        Field[] fields = courseEntity.getClass().getDeclaredFields();
        for (Field field:fields){

        }
    }

}