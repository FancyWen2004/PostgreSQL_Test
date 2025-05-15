package com.kun.service.impl;

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
        Student student = studentMapper.findByAge(age);
        return  student;
    }

    @Override
    public Student findByEmail(String email) {
        Student student = studentMapper.findByEmail(email);
        return student;
    }
}