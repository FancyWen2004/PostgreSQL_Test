package com.kun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.mapper.TeacherMapper;
import com.kun.entity.Teacher;
import com.kun.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    
    private final TeacherMapper teacherMapper;
    
    @Override
    public List<Teacher> findByTitle(String title) {
        return teacherMapper.findByTitle(title);
    }
    
    @Override
    public List<Teacher> findBySubject(String subject) {
        return teacherMapper.findBySubject(subject);
    }
    
    @Override
    public List<Teacher> findByEmailLike(String email) {
        return teacherMapper.findByEmailLike(email);
    }
    
    @Override
    public Teacher findByTeacherNo(String teacherNo) {
        return teacherMapper.findByTeacherNo(teacherNo);
    }
}