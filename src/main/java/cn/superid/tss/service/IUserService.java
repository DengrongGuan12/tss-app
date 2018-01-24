package cn.superid.tss.service;

import cn.superid.tss.vo.UserInfo;

import javax.management.relation.RoleInfo;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午1:19
 **/
public interface IUserService {
    UserInfo getUserInfo(long userId);
    long getDepartmentIdOfUser(long userId);
    int[] calYearDegreeByGrade(String grade);
}
