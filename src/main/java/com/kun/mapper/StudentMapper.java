package com.kun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kun.entity.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 根据年龄范围查询学生
     */
    List<Student> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    // @Param的作用是将参数名与SQL语句中的参数名对应起来
    // 避免了SQL语句中的参数名与Java代码中的参数名不一致的问题。
    /**
     * 根据年级查询学生
     */
    List<Student> findByGrade(@Param("grade") String grade);
    
    /**
     * 根据邮箱模糊查询学生
     */
    List<Student> findByEmailLike(@Param("email") String email);

    /**
     * 根据年龄查询学生信息
     */
    Student findByAge(@Param("age")Integer age);

    /**
     * 根据邮箱地址查询学生信息
     */
    Student findByEmail(@Param("email") String email);

    IPage<Student> selectUserPage(IPage<Student> page ,int age);
}