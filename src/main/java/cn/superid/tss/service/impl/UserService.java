package cn.superid.tss.service.impl;

import cn.superid.common.rest.client.UserClient;
import cn.superid.common.rest.dto.UserInfoDTO;
import cn.superid.common.utils.BitMapUtil;
import cn.superid.tss.constant.DegreeType;
import cn.superid.tss.constant.GradeType;
import cn.superid.tss.constant.ResponseCode;
import cn.superid.tss.constant.UserType;
import cn.superid.tss.dao.IUserDao;
import cn.superid.tss.exception.ErrorCodeException;
import cn.superid.tss.model.UserEntity;
import cn.superid.tss.service.IUserService;
import cn.superid.tss.util.DStatement;
import cn.superid.tss.vo.PersonInfoPublic;
import cn.superid.tss.vo.UserInfo;
import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午1:24
 **/
@Service
public class UserService implements IUserService{
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserClient userClient;
    @Autowired
    IUserDao userDao;

    @Override
    public UserInfo getUserInfo(long userId) {
        // 获取用户通用信息
        UserInfoDTO userInfoDTO = userClient.findById(userId,UserInfoDTO.REALNAME,UserInfoDTO.GENDER,UserInfoDTO.AVATAR,UserInfoDTO.MOBILE,UserInfoDTO.PERSON_INFO_PUBLIC);
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(userInfoDTO.getAvatar());
        userInfo.setGender(userInfoDTO.getGender());
        userInfo.setMobile(userInfoDTO.getMobile());
        userInfo.setRealName(userInfoDTO.getRealname());
        userInfo.setPersonInfoPublic(BitMapUtil.fillDTO(userInfoDTO.getPersonInfoPublic(), PersonInfoPublic.class));

        UserEntity userEntity = userDao.getUserEntity(userId);
        if (userEntity != null){
            userInfo.setType(userEntity.getType());
            userInfo.setDegree(DegreeType.getName(userEntity.getDegree()));
            userInfo.setNumber(userEntity.getNumber());
            userInfo.setGrade(userEntity.getYear()+"级");
            userInfo.setDepartment(userEntity.getDepartment());
        }
        return userInfo;
    }

    @Override
    public long getDepartmentIdOfUser(long userId) {
        UserEntity userEntity = userDao.getUserEntity(userId);
        if (userEntity == null){
            throw new ErrorCodeException(ResponseCode.USER_NOT_EXIST,"用户不存在");
        }
        return userEntity.getDepartmentId();
    }

    @Override
    public int[] calYearDegreeByGrade(String grade) {
        int[] result = new int[2];
        GradeType gradeType = GradeType.getGradeTypeByName(grade);
        // 减去6个月 - gradeType.index
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MONTH,-6);//日期加3个月
        result[0] = rightNow.get(Calendar.YEAR) - (gradeType.getIndex() >= GradeType.MASTER1.getIndex() ? gradeType.getIndex() - GradeType.MASTER1.getIndex():gradeType.getIndex());
        if (gradeType.getIndex() >= GradeType.MASTER1.getIndex()){
            result[1] = DegreeType.MASTER.getIndex();
        }else {
            result[1] = DegreeType.BACHELOR.getIndex();
        }
        return result;
    }

}
