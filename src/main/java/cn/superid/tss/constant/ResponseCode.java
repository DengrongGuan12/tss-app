package cn.superid.tss.constant;

import cn.superid.tss.annotation.ApiType;
import cn.superid.tss.annotation.ApiTypeItem;

/**
 * 四位数字
 */
@ApiType("返回码类型")
public class ResponseCode {

  @ApiTypeItem("出错")
  public static final int Error = -1;
  @ApiTypeItem("正常")
  public static final int OK = 0;

  // 用户 1
  // 课程 2
  // 小组 3
  // 文件 4
  // 发布（教学，作业，考试） 5
  // 角色 6


}
