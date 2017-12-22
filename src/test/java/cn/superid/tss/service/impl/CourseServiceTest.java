package cn.superid.tss.service.impl;


import cn.superid.tss.BaseTest;
import cn.superid.tss.service.ICourseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceTest extends BaseTest{
    @Autowired
    ICourseService courseService;

    @Test
    public void testGetUserCourses() throws Exception {
        courseService.getUserCourses(20203L);
    }

}