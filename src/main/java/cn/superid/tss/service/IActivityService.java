package cn.superid.tss.service;

import cn.superid.tss.vo.Activity;

import java.util.List;

public interface IActivityService {

    List<Activity> getAllActivites(long courseId);

}
