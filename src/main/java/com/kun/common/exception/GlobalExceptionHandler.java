package com.kun.common.exception;

import com.kun.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.IOException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // 注释：@ControllerAdvice表示该类是一个全局异常处理类，用于处理整个应用程序中的异常。
    // 注释：@ResponseBody表示该方法的返回结果直接写入HTTP response body中，而不是跳转到某个页面。
    // 注释：@ExceptionHandler用来定义函数针对的异常类型，最后将Exception类型的异常映射到一个统一的返回结果
    // 注释：@ResponseStatus用于将特定类型的异常映射到HTTP状态码

    // 全局异常处理方法，并添加Swagger中的@Operation注解，用于描述哪个接口发生了异常
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result runtimeExceptionHandler(Exception e , HandlerMethod method) {
        Operation operation = method.getMethod().getAnnotation(Operation.class);
        String summary = operation.summary();
        log.error("接口：{} 发生异常：{}", summary, e.getMessage());
        return Result.error(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    // 对应自定义BusinessException编写异常处理方法
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result businessExceptionHandler(BusinessException e) {
        // 自定义返回错误信息
        return Result.error(e.getErrorCode(),e.getErrorMessage());
    }

    // 对应RedisConnectionFailureException编写异常处理方法
    @ResponseBody
    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result redisConnectionFailureExceptionHandler(RedisConnectionFailureException e) {
        // 自定义返回错误信息，Redis连接失败
        return Result.error("Redis服务故障");
    }

    // 对应DataAccessException编写异常处理方法(Valid校验失败)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder error = new StringBuilder("校验失败：");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            error.append("参数："+fieldError.getField())
                    .append(" 原因：")
                    .append(fieldError.getDefaultMessage())
                    .append(", ");
        }
        return Result.error(error.toString());
    }

    // 对ConstraintViolationException校验失败异常进行处理(@Valid校验失败)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder error = new StringBuilder("校验失败：");
        e.getConstraintViolations().forEach(violation -> {
            error.append("参数：").append(violation.getPropertyPath())
                    .append(" 原因：").append(violation.getMessage())
                    .append(", ");
        });
        return Result.error(error.toString());
    }

    // 常用异常处理方法
    // 1.空指针异常
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result nullPointerExceptionHandler(NullPointerException e) {
        return Result.error(CommonError.NULL_POINTER_ERROR.getErrMessage());
    }

    // 2.类型转换异常
    @ResponseBody
    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result classCastExceptionHandler(ClassCastException e) {
        return Result.error(CommonError.CLASS_CAST_ERROR.getErrMessage());
    }

    // 3.数组下标越界异常
    @ResponseBody
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result arrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException e) {
        return Result.error(CommonError.ARRAY_INDEX_OUT_OF_BOUNDS_ERROR.getErrMessage());
    }

    // 4.数学运算异常
    @ResponseBody
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result arithmeticExceptionHandler(ArithmeticException e) {
        return Result.error(CommonError.ARITHMETIC_ERROR.getErrMessage());
    }

    // 5. 非法参数异常（参数校验失败）
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return Result.error(CommonError.ILLEGAL_ARGUMENT_ERROR.getErrMessage());
    }

    // 6. 非法状态异常（业务状态不满足操作条件）
    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result illegalStateExceptionHandler(IllegalStateException e) {
        return Result.error(CommonError.ILLEGAL_STATE_ERROR.getErrMessage());
    }

    // 8. 参数校验失败异常（@Valid校验失败）
//    @ResponseBody
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
//        // 从异常中获取错误信息,并拼接成字符串返回
//        String errorMsg = e.getBindingResult().getFieldErrors().stream()
//                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
//                .collect(Collectors.joining("; "));
//        return Result.error(errorMsg);
//    }

    // 9. 请求参数缺失异常
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return Result.error(ErrorCode.MISSING_PARAMETER.getMessage()+"："+e.getParameterName());
    }

    // 10. 数据库操作异常（通用数据访问异常）
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result dataAccessExceptionHandler(DataAccessException e) {
        return Result.error(CommonError.DATABASE_OPERATION_ERROR.getErrMessage());
    }

    // 11. 文件操作异常
    @ResponseBody
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result ioExceptionHandler(IOException e) {
        return Result.error(CommonError.FILE_OPERATION_ERROR.getErrMessage());
    }
}
