package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.dao.ICourseDao;
import cn.superid.tss.dao.IUserDao;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hmef.attribute.MAPIAttribute;
import org.exemodel.orm.ExecutableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleInfo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService{

    @Autowired
    BusinessClient businessClient;

    @Autowired
    IUserService userService;

    @Autowired
    ICourseDao courseDao;

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Override
    public List<RoleGroup> getRoleByCourseId(long courseId) {
        logger.info("courseId {}", courseId);
        List<RoleGroup> groups = new LinkedList<>();

        //获得事务下的所有角色
        List<RoleInfoDTO> roles = businessClient.getAffairAllRoles(courseId);
        logger.info("roles{}", roles);

        //将role按角色整理
        Map<Integer,List<RoleInfoDTO>> group = roles.stream().collect(Collectors.groupingBy(RoleInfoDTO::getMold));
        for(Map.Entry<Integer,List<RoleInfoDTO>> entry: group.entrySet()){
            String roleType = UserType.getName(entry.getKey());
            List<Role> roleList = new ArrayList<>();
            entry.getValue().stream().forEach(item->{
                roleList.add(roleTransform(item));
            });
            groups.add(new RoleGroup(roleType, roleList));
        }

        return groups;
    }

    @Override
    public int deleteRole(long operateId, long roleId, long courseId) {

        boolean result;
        try{
            SimpleResponse response = businessClient.deleteRole(operateId,roleId);
            result = response.getCode() == 0 ? true : false;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ErrorCodeException(ResponseCode.DELETE_ROLE_FAILURE,"删除角色失败");
        }

        if(!result){
            throw new ErrorCodeException(ResponseCode.DELETE_ROLE_FAILURE,"删除角色失败");
        }
        return result == true ? 0 : 1 ;
    }

    @Override
    public long addMember(long courseId, long operatorRoleId, long beAllocatedRoleId, String roleTitle, int roleType){
        long roleId;
        try {

            SimpleResponse response = businessClient.allocateNewRole(courseId, operatorRoleId,
                    beAllocatedRoleId, roleType, roleTitle);
            roleId = Long.valueOf((Integer)response.getData());
        }catch (Exception e){
            logger.error("add member fail:",e);
            throw new ErrorCodeException(ResponseCode.INVITE_ROLE_FAILURE,"邀请角色失败");
        }
        return roleId;
    }


    @Override
    public long joinCourseByCode(long userId, long courseId, String code) {
        //验证是否和邀请码匹配
        String inviteCode = courseDao.getInviteCode(courseId);
        if(!code.equals(inviteCode)){
            throw new ErrorCodeException(ResponseCode.WRONG_INVITECODE,"错误的邀请码");
        }
        long roleId;
        try {
            long departmentId = userService.getDepartmentIdOfUser(userId);
            List<RoleInfoDTO> infos = businessClient.getAffairRoleByUserId(departmentId, userId);
            long beAllocatedRoleId = infos.get(0).getRoleId();
            roleId = (long)businessClient.allocateNewRole(courseId, beAllocatedRoleId, beAllocatedRoleId, UserType.STUDENT.getIndex(), UserType.STUDENT.getName()).getData();
        }catch (Exception e){
            throw new ErrorCodeException(ResponseCode.INVITE_ROLE_FAILURE,"加入课程失败");
        }
        return roleId;

    }

    @Override
    public List<Role> getTeachersOfDepartment(long departmentId) {
        List<Role> roles = new ArrayList<>();
        try {
            List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(departmentId, UserType.TEACHER.getIndex());

            if (!CollectionUtils.isEmpty(roleInfoDTOS)) {
                roleInfoDTOS.stream().forEach(item -> {
                    roles.add(roleTransform(item));
                });
            }
        } catch (Exception e) {
            throw new ErrorCodeException(ResponseCode.TEACHERLIST_FAILURE,"获取教师列表失败");
        }

        return roles;
    }

    @Override
    public Role getRoleInAffair(long affairId, long userId) {
        Role r;
        try {
            List<RoleInfoDTO> infos = businessClient.getAffairRoleByUserId(affairId, userId);
            if (CollectionUtils.isEmpty(infos)){
                return null;
            }
            r = roleTransform(infos.get(0));
        } catch (Exception e) {
            logger.error("get role in affair fail:",e);
            throw new ErrorCodeException(ResponseCode.ROLE_IN_COURSE_FAILURE,"无法在课程中获得此人员");
        }

        return r;
    }

    @Override
    public Role getRoleById(long roleId) {
        List<RoleInfoDTO> roleInfoDTOS = businessClient.fillRole(new Long[]{roleId});
        return roleTransform(roleInfoDTOS.get(0));
    }


    private Role roleTransform(RoleInfoDTO dto){
        if(null == dto){
            return  null;
        }
        long userId = dto.getUserId();
        String number = userService.getUserInfo(userId).getNumber();
        Role r = new Role(dto.getRoleId(),dto.getUserId(),
                dto.getRoleTitle(),dto.getUsername(),number,dto.getAvatar(),dto.getGender(),dto.getMold());
        return r;
    }

}
