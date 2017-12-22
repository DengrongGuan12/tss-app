package cn.superid.tss.constant;

import cn.superid.tss.annotation.ApiType;
import cn.superid.tss.annotation.ApiTypeItem;

/**
 * 四位数字
 */
@ApiType("返回码类型")
  @ApiTypeItem("出错")
  public static final int Error = -1;
  public class ResponseCode {

    @ApiTypeItem("正常")
  public static final int OK = 0;

  // 捕获的异常 2
  @ApiTypeItem("普通异常")
  public static final int CATCH_EXCEPTION = 2500;//普通异常
  @ApiTypeItem("数据库异常")
  public static final int DATA_BASE_EXCEPTION = 2600;//数据库异常
  @ApiTypeItem("空指针")
  public static final int NP_EXCEPTION = 2501;//空指针引用异常
  @ApiTypeItem("类型强制转换异常")
  public static final int CLASS_CAST_EXCEPTION = 2502;// 类型强制转换异常
  @ApiTypeItem("传递非法参数异常")
  public static final int ILLEGAL_ARGUMENT_EXCEPTION = 2503;//传递非法参数异常
  @ApiTypeItem("数组存储对象不兼容异常")
  public static final int ARRAY_STORE_EXCEPTION = 2505;// 向数组中存放与声明类型不兼容对象异常
  @ApiTypeItem("下标越界异常")
  public static final int INDEX_OUT_OF_BOUNDS_EXCEPTION = 2506;// 下标越界异常
  @ApiTypeItem("安全异常")
  public static final int SECURITY_EXCEPTION = 2507;// 安全异常
  @ApiTypeItem("不支持的操作异常")
  public static final int UNSUPPORTED_OPERATION_EXCEPTION = 2508;// 不支持的操作异常

  // 用户 1
  // 课程 7
  // 小组 3
  // 文件 4
  // 发布（教学，作业，考试） 5
  // 角色 6


}
