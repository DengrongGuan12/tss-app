package cn.superid.tss.service.impl;


import cn.superid.common.rest.client.BusinessClient;
import cn.superid.tss.BaseTest;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.service.IRoleService;
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
    @Autowired
    BusinessClient businessClient;

    @Test
    public void testGetRoleByCourseId() throws Exception {
        List<RoleGroup> roles = roleService.getRoleByCourseId(130008);
        logger.info("roleGroups {}",roles);

    }

    @Test
    public void testDeleteRole() throws Exception {
        roleService.deleteRole(1022603,1022603,130008);
    }

    @Test
    public void testAddToCourse() throws Exception {
        roleService.addMember(130008,1022505,31011,
                UserType.STUDENT.getChName(),UserType.STUDENT.getIndex());
    }

    @Test
    public void testJoinCourseByCode() throws Exception {
        roleService.joinCourseByCode(31011,130008,"123gdf43");
    }

    @Test
    public void testGetTeachersOfDepartment() throws Exception {
        roleService.getTeachersOfDepartment(115708);

    }

}