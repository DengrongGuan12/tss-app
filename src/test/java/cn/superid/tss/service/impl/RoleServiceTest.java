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
    //TODO 测试是否过滤了已经删掉的角色
    public void testGetRoleByCourseId() throws Exception {
        List<RoleGroup> roles = roleService.getRoleByCourseId(130008);
        logger.info("roleGroups {}",roles);

    }

    @Test
    //test PASS
    public void testDeleteRole() throws Exception {
        roleService.deleteRole(1022203,1022203,130008);
    }

    @Test
    // 400 bad request
    public void testAddToCourse() throws Exception {
        roleService.addMember(920208,1022505,1022505,
                UserType.STUDENT.getChName(),UserType.STUDENT.getIndex());
    }

    @Test
    //400 bad request
    public void testJoinCourseByCode() throws Exception {
        roleService.joinCourseByCode(31011,130008,"123gdf43");
    }

    @Test
    //test pass
    public void testGetTeachersOfDepartment() throws Exception {
        roleService.getTeachersOfDepartment(115708);

    }

    @Test
    //test pass
    public void testGetRoleInAffair(){
        Role r = roleService.getRoleInAffair(130008,30104);
    }

}