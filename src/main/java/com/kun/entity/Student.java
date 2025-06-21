package com.kun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student")
public class Student {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private Integer age;
    
    private String grade;
    
    private String email;

}