package com.kun.common.exception;

public enum CommonError {

    UNKOWN_ERROR("系统内部错误"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询结果为空或图书已借出"),
    REQUEST_NULL("请求参数为空"),
    USER_NULL("用户为空"),
    NULL_POINTER_ERROR("空指针异常"),
    CLASS_CAST_ERROR("类型转换异常"),
    INDEX_OUT_OF_BOUNDS_ERROR("数组下标越界异常"),
    MATH_ERROR("数学运算异常"),
    ARRAY_INDEX_OUT_OF_BOUNDS_ERROR("数组下标越界异常"),
    ARITHMETIC_ERROR("数学运算异常"),
    ILLEGAL_ARGUMENT_ERROR("参数校验失败"),
    INVALID_JSON_ERROR("请求体格式错误"),
    ILLEGAL_STATE_ERROR("业务状态不满足操作条件"),
    DATABASE_OPERATION_ERROR("数据库操作异常"),
    FILE_OPERATION_ERROR("文件操作异常"),
    FILE_UPLOAD_ERROR("文件上传失败");

    private final String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    private CommonError( String errMessage) {
        this.errMessage = errMessage;
    }
}
