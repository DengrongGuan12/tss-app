package cn.superid.tss.service;

import cn.superid.tss.vo.UserInfo;

/**
 * @author DengrongGuan
 * @create 2017-12-18 下午1:19
 **/
public interface IUserService {
    UserInfo getUserInfo(long userId);
}
