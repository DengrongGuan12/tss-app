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
//        groupService.createGroup(30720,920203,130008,"葬爱家族","最后的战役之青春祭奠");
//        groupService.createGroup(30720,920203,130008,"tss开发小队","一把梭");
//        long groupId1 = groupService.createGroup(31011,920408,130008,"tss开发小队","一把梭");
//        long groupId2 = groupService.createGroup(31011,920408,130008,"开锁大王","行业表彰大会");
//        long groupId3 = groupService.createGroup(20209,1022203,130008,"PG_ONE","。。。。");
//        long groupId3 = groupService.createGroup(20203,1131603,130008,"tss前端小组","一把梭");
        long groupId3 = groupService.createGroup(20203,1131603,130008,"皮鞋大王","一把梭");
    }

}