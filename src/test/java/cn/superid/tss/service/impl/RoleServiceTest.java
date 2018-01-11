package cn.superid.tss.service.impl;


import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.BaseTest;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.exception.ErrorCodeException;
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
    @Autowired
    BusinessClient businessClient;
    @Test
    //TEST PASS
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
    // TEST PASS
    public void testAddToCourse() throws Exception {
        roleService.addMember(130008,1022505,1022505,
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

    @Test
    public void fillRole(){
        List<RoleInfoDTO> roleInfoDTOS = businessClient.fillRole(new Long[]{});
        if (roleInfoDTOS == null || roleInfoDTOS.size() == 0){
            throw new ErrorCodeException(ResponseCode.PARAM_ERROR,"找不到对应的角色!");
        }
        System.out.println("....");
    }

}