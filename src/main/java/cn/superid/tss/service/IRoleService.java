package cn.superid.tss.service;

import cn.superid.tss.vo.RoleGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IRoleService {

    public List<RoleGroup> getRoleByCourseId(long courseId);



}
