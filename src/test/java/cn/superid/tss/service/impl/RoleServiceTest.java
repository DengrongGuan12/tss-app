package cn.superid.tss.service.impl;


import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.BaseTest;
import cn.superid.tss.constant.AffairType;
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
//        roleService.deleteRole(1022203,1022203,130008, AffairType.COURSE, true);
        roleService.deleteRole(1252722,1252722,130008, AffairType.COURSE, true);
    }

    @Test
    // TEST PASS
    public void testAddToCourse() throws Exception {
//        roleService.addMember(341103,1131803,new Long[]{1250608L},
//                UserType.TEACHER.getChName(),UserType.TEACHER.getIndex(), AffairType.COURSE);

//        roleService.addMember(115708,906213,new Long[]{901304L},
//                UserType.STUDENT.getChName(),UserType.STUDENT.getIndex(), AffairType.DEPARTMENT);

        roleService.addMember(345803,906213,new Long[]{1131604L},
                UserType.MEMBER, AffairType.GROUP);
    }

    @Test
    public void addToDepartment(){
        roleService.addMember(115708, 906213, new Long[]{829703L}, UserType.STUDENT, AffairType.DEPARTMENT);
    }

    @Test
    //400 bad request
    public void testJoinCourseByCode() throws Exception {
        roleService.joinCourseByCode(20203,130008,"20180121");
    }

    @Test
    //test pass
    public void testGetTeachersOfDepartment() throws Exception {
        roleService.getTeachersOfDepartment(115708);

    }

    @Test
    public void getStudentsOfDepartment(){
        roleService.getStudentsOfDepartment(115708, 2014, 0);
    }

    @Test
    //test pass
    public void testGetRoleInAffair(){
        Role r = roleService.getRoleInAffair(130006,20203);
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