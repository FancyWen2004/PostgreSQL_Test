package com.kun.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 邮件实体类
 */
public class Email {

    // 邮件主题
    private String Subject;
    // 邮件内容
    private String Content;
    // 收件人
    private String To;
    // 发件人
    @Value("spring.mail.username")
    private String From;
}
