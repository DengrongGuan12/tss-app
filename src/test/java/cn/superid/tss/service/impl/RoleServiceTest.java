package cn.superid.tss.service.impl;


import cn.superid.tss.BaseTest;
import cn.superid.tss.service.IRoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceTest extends BaseTest{
    @Autowired
    IRoleService roleService;
    @Test
    public void testGetRoleByCourseId() throws Exception {
        roleService.getRoleByCourseId(115708);
    }

    @Test
    public void testDeleteRole() throws Exception {
        roleService.deleteRole(920408,130008);
    }

    @Test
    public void testAddToCourse() throws Exception {
    }

    @Test
    public void testJoinCourseByCode() throws Exception {
    }

    @Test
    public void testGetTeachersOfDepartment() throws Exception {
        roleService.getTeachersOfDepartment(115708);

    }

}