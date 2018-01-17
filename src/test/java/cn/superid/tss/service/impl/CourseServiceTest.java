package cn.superid.tss.service.impl;


import cn.superid.common.rest.client.FileClient;
import cn.superid.id_client.IdClient;
import cn.superid.tss.BaseTest;
import cn.superid.tss.forms.CourseForm;
import cn.superid.tss.model.CourseEntity;
import cn.superid.tss.service.ICourseService;
import cn.superid.tss.util.DStatement;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;


public class CourseServiceTest extends BaseTest {
    @Autowired
    ICourseService courseService;

    @Autowired
    FileClient fileClient;

    @Autowired
    IdClient idClient;



    @Test
    public void testGetUserCourses() throws Exception {
       Map map = courseService.getUserCourses(30720L);
       System.out.println(map);
    }

    @Test
    public void testCreateCourse() {
//        CourseEntity courseEntity = CourseForm.mock(3);
//        courseEntity.save();
//        Field[] fields = courseEntity.getClass().getDeclaredFields();
//        for (Field field : fields) {
//
//        }
        CourseForm courseForm = CourseForm.mock();
        courseForm.setName("计算机网络");
        courseService.createCourse(courseForm, 906213, 115708, 30720);
    }

    @Test
    public void testAddFolder(){
        long folderId = idClient.nextId("file","file");
        fileClient.addFolder(0,"课程资料", 920208, 130008,folderId);
        CourseEntity courseEntity = DStatement.build(CourseEntity.class).id(130008).selectOne();
        courseEntity.setDefaultFolder(folderId);
        courseEntity.update();
    }

    @Test
    public void testGetCourseDetail() throws Exception {
        courseService.getCourseDetail(130008);
    }

    @Test
    public void testGetCoursesOfDepartment() throws Exception{
        courseService.getCoursesOfDepartment(115708);
    }

    @Test
    public void modifyCourse() throws Exception{
        CourseForm courseForm = CourseForm.mock(130008);
        courseForm.setName("计算机组成原理");
        courseForm.setDescription("计算机组成原理-任同韦");
        courseForm.setInviteCode("20180105");
        courseService.modifyCourse(courseForm,906213);
    }

}