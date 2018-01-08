package cn.superid.tss.service.impl;

import cn.superid.tss.BaseTest;
import cn.superid.tss.service.IGroupService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class GroupServiceTest extends BaseTest{

    @Autowired
    IGroupService groupService;

    @Test
    public void getGroupsOfCourse() throws Exception {
       Map map = groupService.getGroupsOfCourse(130008, 20203);
       System.out.println(map);
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
        groupService.createGroup(30720,920203,130008,"被遗忘的学生们","落单的学生们");
//        groupService.createGroup(30720,920203,130008,"tss开发小队","一把梭");
//        long groupId1 = groupService.createGroup(31011,920408,130008,"tss开发小队","一把梭");
//        long groupId2 = groupService.createGroup(31011,920408,130008,"开锁大王","行业表彰大会");
//        long groupId3 = groupService.createGroup(20209,1022203,130008,"PG_ONE","。。。。");
//        long groupId3 = groupService.createGroup(20203,1131603,130008,"tss前端小组","一把梭");
//        long groupId3 = groupService.createGroup(20203,1131603,130008,"皮鞋大王","一把梭");
    }

}