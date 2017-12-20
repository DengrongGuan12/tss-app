package cn.superid.tss.controller;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.service.impl.RoleService;
import cn.superid.tss.vo.RoleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping(value = "getCourseRole",method = RequestMethod.GET)
    public SimpleResponse getCourseRoleList(@RequestParam(value = "courseId")long courseId){
        List<RoleGroup> groups = roleService.getRoleByCourseId(courseId);

        return SimpleResponse.ok(groups);
    }





}
