package com.kun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kun.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    
    /**
     * 根据职称查询教师
     */
    @Select("SELECT * FROM teacher WHERE title = #{title}")
    List<Teacher> findByTitle(String title);
    
    /**
     * 根据学科查询教师
     */
    @Select("SELECT * FROM teacher WHERE subject = #{subject}")
    List<Teacher> findBySubject(String subject);
    
    /**
     * 根据邮箱模糊查询教师
     */
    @Select("SELECT * FROM teacher WHERE email LIKE CONCAT('%', #{email}, '%')")
    List<Teacher> findByEmailLike(String email);
    
    /**
     * 根据工号查询教师
     */
    @Select("SELECT * FROM teacher WHERE teacher_no = #{teacherNo}")
    Teacher findByTeacherNo(String teacherNo);
}