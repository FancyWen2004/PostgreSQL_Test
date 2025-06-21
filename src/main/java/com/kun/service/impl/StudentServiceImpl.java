package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.mapper.StudentMapper;
import com.kun.entity.Student;
import com.kun.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    
    private final StudentMapper studentMapper;

    @Override
    public List<Student> findByAgeRange(Integer minAge, Integer maxAge) {
        return studentMapper.findByAgeRange(minAge, maxAge);
    }
    
    @Override
    public List<Student> findByGrade(String grade) {
        return studentMapper.findByGrade(grade);
    }
    
    @Override
    public List<Student> findByEmailLike(String email) {
        return studentMapper.findByEmailLike(email);
    }

    @Override
    public Student selectByage(Integer age) {
        return studentMapper.findByAge(age);
    }

    @Override
    public Student findByEmail(String email) {
        return studentMapper.findByEmail(email);
    }

    @Override
    public IPage<Student> findByPage(Integer current, Integer size) {
        Page<Student> page = new Page<>(current, size);
        LambdaQueryWrapper<Student> studentQuery = new LambdaQueryWrapper<>();
        studentQuery.gt(Student::getAge, 16).or().eq(Student::getAge, 16);
        return studentMapper.selectPage(page, studentQuery);
    }
    // 自定义查询条件进行分页查询（示例：查询age大于18的学生）
    @Override
    public IPage<Student> findByPageQueryAge(Integer current, Integer size, Integer age) {
        Page<Student> page = new Page<>(current, size);
        IPage<Student> students = studentMapper.selectUserPage(page,age);
        return students;
    }
}