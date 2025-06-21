package com.kun.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserParam {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度在6-20位之间")
    private String password;
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @NotBlank(message = "身份证不能为空")
    private String idCard;
    @NotBlank(message = "身份证姓名不能为空")
    private String idCardName;
    @NotBlank(message = "车牌号不能为空")
    private String carNumber;
    @NotBlank(message = "银行卡号不能为空")
    private String bankCardNumber;
}