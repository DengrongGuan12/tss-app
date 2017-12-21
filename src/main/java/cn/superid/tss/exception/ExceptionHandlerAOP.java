package cn.superid.tss.exception;

import cn.superid.common.rest.dto.SimpleResponse;
import cn.superid.tss.constant.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.exemodel.exceptions.JdbcRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by njuDYP on 17/7/28.
 */

@Aspect
@Order(3)
@Component
public class ExceptionHandlerAOP {

  private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAOP.class);

  private static List<Class> exceptionClass = new ArrayList<>();
  private static List<Integer> exceptionCode = new ArrayList<>();

  static {
    exceptionClass.add(JdbcRuntimeException.class);
    exceptionClass.add(NullPointerException.class);
    exceptionClass.add(ClassCastException.class);
    exceptionClass.add(IllegalArgumentException.class);
    exceptionClass.add(ArrayStoreException.class);
    exceptionClass.add(IndexOutOfBoundsException.class);
    exceptionClass.add(SecurityException.class);
    exceptionClass.add(UnsupportedOperationException.class);

    exceptionCode.add(ResponseCode.DATA_BASE_EXCEPTION);
    exceptionCode.add(ResponseCode.NP_EXCEPTION);
    exceptionCode.add(ResponseCode.CLASS_CAST_EXCEPTION);
    exceptionCode.add(ResponseCode.ILLEGAL_ARGUMENT_EXCEPTION);
    exceptionCode.add(ResponseCode.ARRAY_STORE_EXCEPTION);
    exceptionCode.add(ResponseCode.INDEX_OUT_OF_BOUNDS_EXCEPTION);
    exceptionCode.add(ResponseCode.SECURITY_EXCEPTION);
    exceptionCode.add(ResponseCode.UNSUPPORTED_OPERATION_EXCEPTION);
  }

  @Around(value = "execution(public * cn.superid.tss.controller.*.*(..))")
  public Object handleException(ProceedingJoinPoint pjp) {
    Object result;
    try {
      result = pjp.proceed(pjp.getArgs());
    } catch (Throwable ex) {
      logger.error("Controller execution error", ex);
      //自定义异常
      if (ex instanceof ErrorCodeException) {
        return new SimpleResponse(((ErrorCodeException) ex).getCode(), null, getMsg(ex));
      }
      //常规运行时异常
      for (int i = 0; i < exceptionClass.size(); i++) {
        if (ex.getClass().equals(exceptionClass.get(i))) {
          return new SimpleResponse(exceptionCode.get(i), null, getMsg(ex));
        }
      }

      return new SimpleResponse(ResponseCode.CATCH_EXCEPTION, null, getMsg(ex));
    }

    return result;

  }

  private String getMsg(Throwable ex) {
    StringBuilder sb = new StringBuilder(ex.getMessage()==null?"":ex.getMessage());
    for (int i = 0; i < 5 && i < ex.getStackTrace().length; i++) {
      sb.append("\n");
      sb.append(ex.getStackTrace()[i]);
    }
    return sb.toString();
  }

}
