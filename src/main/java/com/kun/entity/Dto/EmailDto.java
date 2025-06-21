package com.kun.entity.Dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    // 邮件主题
    @NotEmpty(message = "邮件主题不能为空")
    private String Subject;
    // 邮件内容
    private String Content;
    // 收件人
    @NotEmpty(message = "收件人不能为空")
    private String To;

}
