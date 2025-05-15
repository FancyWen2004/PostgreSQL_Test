package com.kun.controller.teacher;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kun.entity.Teacher;
import com.kun.result.Result;
import com.kun.service.ITeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "教师管理接口")
@RestController
@RequestMapping("api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final ITeacherService teacherService;

    @Operation(operationId = "查询所有教师")
    @GetMapping("/list")
    public Result<List<Teacher>> list() {
        return Result.success(teacherService.list());
    }

    @Operation(operationId = "查询所有教师(带排序)")
    @GetMapping("/list/sort")
    public Result<List<Teacher>> listWithSort() {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("id").orderByDesc("id");
        return Result.success(teacherService.list(queryWrapper));
    }

    @Operation(operationId = "根据ID查询教师")
    @GetMapping("/{id}")
    public Result<Teacher> getById(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher == null) {
            return Result.error("未找到该教师");
        }
        return Result.success(teacher);
    }

    @Operation(operationId = "新增教师")
    @PostMapping
    public Result<Teacher> save(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.success(teacher);
    }

    @Operation(operationId = "更新教师")
    @PutMapping
    public Result<Teacher> update(@RequestBody Teacher teacher) {
        teacher.setId(teacher.getId());
        teacherService.updateById(teacher);
        return Result.success(teacher);
    }

    @Operation(operationId = "删除教师")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        teacherService.removeById(id);
        return Result.success();
    }

    // 查询status为1的教师
    @Operation(operationId = "查询对应status的教师信息")
    @GetMapping("/status/{status}")
    public Result<List<Teacher>> findByStatus(@PathVariable Integer status) {
        // 使用mybatisPlus的lambdaQueryWrapper进行查询
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getStatus, status);
        List<Teacher> list = teacherService.list(queryWrapper);
        return Result.success(list);
    }

    @Operation(operationId = "根据职称查询教师")
    @GetMapping("/title/{title}")
    public Result<List<Teacher>> findByTitle(@PathVariable String title) {
        return Result.success(teacherService.findByTitle(title));
    }

    @Operation(operationId = "根据学科查询教师")
    @GetMapping("/subject/{subject}")
    public Result<List<Teacher>> findBySubject(@PathVariable String subject) {
        return Result.success(teacherService.findBySubject(subject));
    }

    @Operation(operationId = "根据邮箱模糊查询教师")
    @GetMapping("/email/{email}")
    public Result<List<Teacher>> findByEmailLike(@PathVariable String email) {
        return Result.success(teacherService.findByEmailLike(email));
    }

    @Operation(operationId = "根据工号查询教师")
    @GetMapping("/no/{teacherNo}")
    public Result<Teacher> findByTeacherNo(@PathVariable String teacherNo) {
        return Result.success(teacherService.findByTeacherNo(teacherNo));
    }
}