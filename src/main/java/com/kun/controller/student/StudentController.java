package com.kun.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kun.entity.Student;
import com.kun.entity.UserParam;
import com.kun.result.Result;
import com.kun.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "学生管理", description = "学生信息的CRUD接口")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    // @Autowired
    // private IStudentService studentService;

    // 使用构造器注入，并使用lombok简化代码
    private final IStudentService studentService;

    // 查询所有学生数据
    @Operation(summary = "获取所有学生信息", description = "返回数据库中所有学生的信息列表")
    @GetMapping("/getAllStudents")
    public Result getAllStudents() {
        return Result.success(studentService.list());
    }

    @Operation(summary = "获取所有学生信息（带排序）", description = "返回按ID降序排序的所有学生信息列表")
    @GetMapping("/findAll")
    public Result getStudent() {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.isNotNull("id");
        studentQueryWrapper.orderByDesc("id");
        List<Student> list = studentService.list(studentQueryWrapper);
        return Result.success(list);
    }

    // 查询单个学生数据
    @Operation(summary = "根据ID查询学生", description = "根据学生ID查询单个学生的详细信息")
    @GetMapping("/find/{id}")
    public Result getStudent(@PathVariable @Parameter(description = "学生ID") Long id) {
        Student student = studentService.getById(id);
        return student != null ? Result.success(student) : Result.error("未找到该学生");
    }

    // 添加学生数据
    @Operation(summary = "添加学生", description = "新增一条学生信息记录")
    @PostMapping("/add")
    public Result addStudent(@RequestBody @Parameter(description = "学生信息") Student student) {
        if (studentService.save(student)) {
            return Result.success(student.getId());
        } else {
            return Result.error("学生信息有误！");
        }
    }

    // 更新学生数据
    @Operation(summary = "更新学生信息", description = "根据学生ID更新对应的学生信息")
    @PutMapping("/update")
    public Result updateStudent(
            @RequestBody @Parameter(description = "更新的学生信息") Student student) {
        studentService.updateById(student);
        return Result.success();
    }

    // 删除学生数据
    @Operation(summary = "删除学生", description = "根据学生ID删除对应的学生信息")
    @DeleteMapping("/delete/{id}")
    public Result deleteStudent(@PathVariable @Parameter(description = "学生ID") Long id) {
        studentService.removeById(id);
        return Result.success();
    }

    // 根据年龄范围查询学生数据
    @Operation(summary = "按年龄范围查询学生", description = "查询指定年龄范围内的所有学生信息")
    @GetMapping("/byAgeRange")
    public Result findByAgeRange(
            @RequestParam @Parameter(description = "最小年龄") Integer minAge,
            @RequestParam @Parameter(description = "最大年龄") Integer maxAge) {
        return Result.success(studentService.findByAgeRange(minAge, maxAge));
    }

    // 根据年级查询学生数据
    @Operation(summary = "按年级查询学生", description = "查询指定年级的所有学生信息")
    @GetMapping("/byGrade/{grade}")
    public Result findByGrade(@PathVariable @Parameter(description = "年级") String grade) {
        return Result.success(studentService.findByGrade(grade));
    }

    // 根据邮箱模糊查询学生数据
    @Operation(summary = "按邮箱模糊查询学生", description = "根据邮箱地址模糊查询学生信息")
    @GetMapping("/byEmail")
    public Result findByEmailLike(@RequestParam @Parameter(description = "邮箱地址关键字") String email) {
        return Result.success(studentService.findByEmailLike(email));
    }

    // 根据年龄查询学生数据
    @Operation(summary = "按年龄查询学生", description = "查询指定年龄的学生信息")
    @GetMapping("/findByAge/{age}")
    public Result findByAge(@PathVariable @Parameter(description = "年龄") Integer age) {
        Student student = studentService.selectByage(age);
        return Result.success(student);
    }

    // 分页查询学生数据
    @Operation(summary = "分页查询学生", description = "查询指定页码和每页记录数的学生信息")
    @GetMapping("/page/{current}/{size}")
    public Result page(@PathVariable @Parameter(description = "页码") Integer current,
                       @PathVariable @Parameter(description = "每页记录数") Integer size) {
        IPage<Student> students = studentService.findByPage(current, size);
        return Result.success(students);
    }

    @Operation(summary = "测试自定义查询条件的分页查询")
    @GetMapping("/queryPage")
    public Result page(@RequestParam @Min(1) Integer current,
                       @RequestParam @Min(1) Integer size,
                       @RequestParam(required = false) @Min(0) Integer age) {
        return Result.success(studentService.findByPageQueryAge(current, size, age));
    }

    @Operation(summary = "测试Valid校验器")
    @PostMapping("/validator")
    public String ValidatorTest(@RequestBody @Valid UserParam user) {
        UserParam contractParam = new UserParam();
        BeanUtils.copyProperties(user, contractParam);
        System.out.println(contractParam);
        return "success";
    }
}
