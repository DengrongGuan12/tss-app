package cn.superid.tss.service.impl;

import cn.superid.tss.BaseTest;
import cn.superid.tss.service.IGroupService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupServiceTest extends BaseTest{

    @Autowired
    IGroupService groupService;

    @Test
    public void getGroupsOfCourse() throws Exception {
//        groupService.getGroupsOfCourse();
    }

    @Test
    public void getMyGroups() throws Exception {
    }

    @Test
    public void getAllGroups() throws Exception {
    }

    @Test
    public void getRolesOfGroup() throws Exception {
    }

    @Test
    public void createGroup() throws Exception {
        groupService.createGroup(30720,920203,130008,"葬爱家族","最后的战役之青春祭奠");
//        groupService.createGroup(30720,920203,130003,"葬爱家族","最后的战役之青春祭奠");
    }

}