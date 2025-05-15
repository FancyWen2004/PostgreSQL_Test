package com.kun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String teacherNo;
    
    private String name;

    private Integer age;

    private String title;

    private String subject;
    
    private String email;
    
    private String phone;

    private Integer status;
}