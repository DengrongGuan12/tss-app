package cn.superid.tss.service.impl;

import cn.superid.common.notification.dto.CommonMessage;
import cn.superid.common.notification.enums.MsgType;
import cn.superid.common.notification.enums.ResourceType;
import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.common.rest.dto.business.RoleInfoDTO;
import cn.superid.tss.constant.*;
import cn.superid.tss.dao.ICourseDao;
import cn.superid.tss.dao.IUserDao;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.msg.MsgComponent;
import cn.superid.tss.msg.TssSource;
import cn.superid.tss.service.IRoleService;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.util.JSONObjectBuilder;
import cn.superid.tss.vo.Role;
import cn.superid.tss.vo.RoleGroup;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService{

    @Autowired
    BusinessClient businessClient;

    @Autowired
    IUserService userService;

    @Autowired
    ICourseDao courseDao;

    @Autowired
    MsgComponent msgComponent;

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Override
    public List<RoleGroup> getRoleByCourseId(long courseId) {
        logger.info("courseId {}", courseId);
        List<RoleGroup> groups = new LinkedList<>();

        //获得事务下的所有角色
        List<RoleInfoDTO> roles = businessClient.getAffairAllRoles(courseId, StateType.NORMAL.getIndex());
        logger.info("roles{}", roles);

        //将role按角色整理
        Map<Integer,List<RoleInfoDTO>> group = roles.stream().collect(Collectors.groupingBy(RoleInfoDTO::getMold));
        for(Map.Entry<Integer,List<RoleInfoDTO>> entry: group.entrySet()){
            List<Role> roleList = new ArrayList<>();
            entry.getValue().stream().forEach(item->{
                roleList.add(roleTransform(item));
            });
            groups.add(new RoleGroup(entry.getKey(), roleList));
        }

        return groups;
    }

    @Override
    public int deleteRole(long operateId, long roleId, long affairId, AffairType affairType, boolean active) {

        boolean result;
        try{
            CommonMessage commonMessage = null;
            if (!active){
                //TODO 3 TEST
                JSONObject jsonObject = new JSONObjectBuilder().put("affairType", affairType.getChName()).getJsonObject();
                commonMessage = msgComponent.genCommonMsg(affairId, operateId, Arrays.asList(new Long[]{roleId}), getMsgTypeByAffairType(affairType), ResourceType.AFFAIR, affairId, MsgTemplateType.TSS_DELETE_ROLE,jsonObject);
            }
            SimpleResponse response = businessClient.deleteRole(operateId,roleId, commonMessage);
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
    public List<Long> addMember(long courseId, long operatorRoleId, Long[] beAllocatedRoleId, UserType userType, AffairType affairType){
        List<Long> roleIds;
        try {
            //TODO 3 TEST
            JSONObject jsonObject = new JSONObjectBuilder().put("affairType", affairType.getChName()).put("roleTitle", userType.getChName()).getJsonObject();
            CommonMessage commonMessage = msgComponent.genCommonMsg(courseId, operatorRoleId, Arrays.asList(beAllocatedRoleId), getMsgTypeByAffairType(affairType), ResourceType.AFFAIR, courseId, MsgTemplateType.TSS_ADD_MEMBER, jsonObject);
            SimpleResponse response = businessClient.allocateNewRole(courseId, operatorRoleId,
                    beAllocatedRoleId, userType.getIndex(), userType.getChName(), commonMessage);
            roleIds = (List<Long>) response.getData();
        }catch (Exception e){
            logger.error("add member fail:",e);
            throw new ErrorCodeException(ResponseCode.INVITE_ROLE_FAILURE,"邀请角色失败");
        }
        return roleIds;
    }

    private MsgType getMsgTypeByAffairType(AffairType affairType){
        MsgType msgType = MsgType.GROUP;
        if (affairType == AffairType.COURSE){
            msgType = MsgType.COURSE;
        }
        return msgType;
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
            List<RoleInfoDTO> infos = businessClient.getAffairRoleByUserId(departmentId, userId, StateType.NORMAL.getIndex());
            long beAllocatedRoleId = infos.get(0).getRoleId();
            SimpleResponse response = businessClient.allocateNewRole(courseId, beAllocatedRoleId,
                    new Long[]{beAllocatedRoleId}, UserType.STUDENT.getIndex(), UserType.STUDENT.getChName(),null);
            roleId = Long.valueOf((Integer)response.getData());
        }catch (Exception e){
            throw new ErrorCodeException(ResponseCode.INVITE_ROLE_FAILURE,"加入课程失败");
        }
        return roleId;

    }

    @Override
    public List<Role> getTeachersOfDepartment(long departmentId) {
        List<Role> roles = new ArrayList<>();
        try {
            List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(departmentId, UserType.TEACHER.getIndex(), StateType.NORMAL.getIndex());

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
            List<RoleInfoDTO> infos = businessClient.getAffairRoleByUserId(affairId, userId, StateType.NORMAL.getIndex());
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
    public List<Role> getRolesByIds(Long[] roleIds) {
        List<RoleInfoDTO> roleInfoDTOS = businessClient.fillRole(roleIds);
        if (roleInfoDTOS != null){
            return roleInfoDTOS.stream().map(roleInfoDTO -> roleTransform(roleInfoDTO)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Long> getRoleIdsInAffairWithType(long affairId, UserType type) {
        List<RoleInfoDTO> roles = businessClient.getRolesByType(affairId,type.getIndex(), StateType.NORMAL.getIndex());
        if (roles == null){
            throw new ErrorCodeException(ResponseCode.NP_EXCEPTION, "获取角色列表失败");
        }
        List<Long> receiverIds = roles.stream().map(RoleInfoDTO::getRoleId).collect(Collectors.toList());
        return receiverIds;
    }

    @Override
    public List<Long> getRoleIdsInAffair(long affairId) {
        List<RoleInfoDTO> roles = businessClient.getAffairAllRoles(affairId, StateType.NORMAL.getIndex());
        if (roles == null){
            throw new ErrorCodeException(ResponseCode.NP_EXCEPTION, "获取角色列表失败");
        }
        List<Long> receiverIds = roles.stream().map(RoleInfoDTO::getRoleId).collect(Collectors.toList());
        return receiverIds;
    }

    @Override
    public void rejectJoin(long affairId, long roleId, AffairType affairType, long rejectedId, String reason) {
        MsgType msgType = MsgType.COURSE;
        if (affairType == AffairType.GROUP){
            msgType = MsgType.GROUP;
        }
        JSONObject jsonObject = new JSONObjectBuilder().put("affairType",affairType.getChName()).put("reason", reason).getJsonObject();
        //TODO 3
        CommonMessage commonMessage = msgComponent.genCommonMsg(affairId, roleId, Arrays.asList(new Long[]{rejectedId}), msgType, ResourceType.AFFAIR, affairId, MsgTemplateType.TSS_REJECT_JOIN, jsonObject);
        msgComponent.sendMsg(commonMessage);
    }

    @Override
    public void applyJoin(long affairId, long roleId, AffairType affairType, String reason) {
        MsgType msgType = MsgType.COURSE;
        List<Long> receiverIds;
        if (affairType == AffairType.GROUP){
            msgType = MsgType.GROUP;
            List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(affairId, UserType.LEADER.getIndex(), StateType.NORMAL.getIndex());
            receiverIds = roleInfoDTOS.stream().map(RoleInfoDTO::getRoleId).collect(Collectors.toList());
        }else{
            List<RoleInfoDTO> roleInfoDTOS = businessClient.getRolesByType(affairId, UserType.LEADER.getIndex(), StateType.NORMAL.getIndex());
            receiverIds = roleInfoDTOS.stream().map(RoleInfoDTO::getRoleId).collect(Collectors.toList());
        }

        JSONObject jsonObject = new JSONObjectBuilder().put("affairType",affairType.getChName()).put("reason", reason).getJsonObject();
        //TODO 3
        CommonMessage commonMessage = msgComponent.genCommonMsg(affairId, roleId, receiverIds, msgType, ResourceType.AFFAIR, affairId, MsgTemplateType.TSS_APPLY_JOIN, jsonObject);
        msgComponent.sendMsg(commonMessage);
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
