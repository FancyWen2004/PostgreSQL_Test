package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.entity.Student;
import java.util.List;

public interface IStudentService extends IService<Student> {
    /**
     * 根据年龄范围查询学生
     */
    List<Student> findByAgeRange(Integer minAge, Integer maxAge);
    
    /**
     * 根据年级查询学生
     */
    List<Student> findByGrade(String grade);
    
    /**
     * 根据邮箱模糊查询学生
     */
    List<Student> findByEmailLike(String email);

    Student selectByage(Integer age);

    Student findByEmail(String email);
}