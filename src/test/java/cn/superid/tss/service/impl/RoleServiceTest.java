package cn.superid.tss.service.impl;


import cn.superid.tss.BaseTest;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceTest extends BaseTest{

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceTest.class);
    @Autowired
    IRoleService roleService;
    @Test
    public void testGetRoleByCourseId() throws Exception {
        List<RoleGroup> roles = roleService.getRoleByCourseId(130008);
        logger.info("roleGroups {}",roles);

    }

    @Test
    public void testDeleteRole() throws Exception {
        roleService.deleteRole(1022203,1022203,130008);
    }

    @Test
    public void testAddToCourse() throws Exception {
        roleService.addToCourse(130008,1022505,31011,
                UserType.STUDENT.getChName(),UserType.STUDENT.getIndex());
    }

    @Test
    public void testJoinCourseByCode() throws Exception {
    }

    @Test
    public void testGetTeachersOfDepartment() throws Exception {
        roleService.getTeachersOfDepartment(115708);

    }

}