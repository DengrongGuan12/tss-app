package cn.superid.tss.service;

import java.util.Map;

public interface ICourseService {
    Map<String,Map> getUserCourses(long userId);
}
