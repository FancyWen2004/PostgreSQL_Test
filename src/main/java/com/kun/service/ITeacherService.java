package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.entity.Teacher;
import java.util.List;

public interface ITeacherService extends IService<Teacher> {
    /**
     * 根据职称查询教师
     */
    List<Teacher> findByTitle(String title);
    
    /**
     * 根据学科查询教师
     */
    List<Teacher> findBySubject(String subject);
    
    /**
     * 根据邮箱模糊查询教师
     */
    List<Teacher> findByEmailLike(String email);
    
    /**
     * 根据工号查询教师
     */
    Teacher findByTeacherNo(String teacherNo);
}