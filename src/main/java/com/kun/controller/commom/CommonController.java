package com.kun.controller.commom;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.alibaba.fastjson2.JSON;
import com.kun.constant.Constant;
import com.kun.entity.Dto.EmailDto;
import com.kun.entity.Student;
import com.kun.result.Result;
import com.kun.service.CommonService;
import com.kun.service.FileService;
import com.kun.service.IStudentService;
import com.kun.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@Validated
@Tag(name = "模拟用户登陆接口")
@RequestMapping("/common")
public class CommonController {

    private final RedisUtil redisUtil;
    private final IStudentService studentService;
    private final FileService fileservice;
    private final CommonService commonService;
    private final RedisIdWorker redisIdWorker;
    private final MongoTemplate mongoTemplate;
    private final MongoUtil mongoUtil;

    @Operation(summary = "生成验证码(Redis)")
    @PostMapping("/getCode")
    // 使用hutool的验证码工具实现验证码
    public Result checkCode1(HttpServletResponse response) {
        // 生成验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(100, 30);
        String code = circleCaptcha.getCode();
        // 使用verify方法可以直接校验验证码是否正确
        // boolean verify = circleCaptcha.verify(code);
        log.info("验证码：{}", code);
        // 使用md5加密验证码作为key
        String md5Code = MD5Utils.string2MD5(code);
        // Redis存储路径拼接
        String checkCodeKey = Constant.CHECK_CODE_KEY + md5Code;
        // 日志记录产生的key
        log.info("验证码key：{}", checkCodeKey);
        // 将验证码存入redis
        redisUtil.set(checkCodeKey, code, 60);
        try {
            // 获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
            // 将图片写入输出流
            circleCaptcha.write(outputStream);
            // 关闭
            outputStream.close();
        } catch (IOException e) {
            // 抛出异常
            throw new RuntimeException(e);
        }
        // 返回checkCodeKey
        return Result.success(checkCodeKey);
    }

    // 使用redis配合验证码实现登陆模拟
    @Operation(summary = "验证码+邮箱登陆(Redis)")
    @GetMapping("/login")
    public Result login(@RequestParam String code,
                        @RequestParam String checkCodeKey,
                        @RequestParam String email) {
        if (EmptyChecker.isEmpty(checkCodeKey)) {
            return Result.error("验证码密钥不能为空！");
        }
        Student student = studentService.findByEmail(email);
        String StudentJson = JSON.toJSONString(student);
        log.info("登陆学生信息{}", StudentJson);
        redisUtil.set(Constant.STUDENT_INFO, StudentJson, 60);
        if (EmptyChecker.isEmpty(student)) {
            return Result.error("邮箱未注册！");
        }
        // 获取redis中"key："路径下的验证码
        Object checkCode = redisUtil.get(Constant.CHECK_CODE_KEY + checkCodeKey);
        // 对checkCode进行判空，如果为空，说明验证码已过期
        if (checkCode == null) {
            return Result.error("验证码已过期！");
        } else {
            // 如果不为空，说明验证码未过期，进行验证码校验
            if (checkCode.equals(code)) {
                return Result.success();
            } else {
                return Result.error("验证码错误");
            }
        }
    }

    @Operation(summary = "生成验证码(Redis)2")
    @GetMapping("/getCode2")
    public Result checkCode2() {
        String checkCode = commonService.getCheckCode2();
        if (EmptyChecker.isEmpty(checkCode)) {
            return Result.error("验证码生成失败");
        } else {
            return Result.success(checkCode);
        }
    }

    // 使用RedisIdWorker测试生成全局唯一ID
    @Operation(summary = "获取id")
    @GetMapping("/getId")
    public Result getId() {
        long orderID = redisIdWorker.nextId("order");
        log.info("生成的订单ID：{}", orderID);
        return Result.success(orderID);
    }

    /**
     * @Description: TODO: 实现文件上传
     * @Author Wen
     * @Date 2025/3/25 20:14
     * @Param file
     */
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result upload(@Parameter(description = "上传的文件", required = true)
                         @RequestPart MultipartFile file) {
        String url = commonService.upload(file);
        log.info("上传文件地址：{}", url);
        return Result.success(url);
    }

    /**
     * @Description: TODO: 测试MongoDB查询数据
     * @Author Wen
     * @Date 2025/4/1 14:58
     * @Param
     */
    @Operation(summary = "测试MongoDB查询数据")
    @GetMapping("/mongo")
    public Result mongo(@Parameter(description = "姓名") @RequestParam @NotBlank(message = "查询MongoDB的那么参数不能为空") String name) {
        return Result.success(commonService.mongoFindName(name));
    }

    // 使用MailUtil测试发送邮件
    @Operation(summary = "测试发送邮件")
    @PostMapping("/sendMail")
    public Result sendMail(@RequestBody @Valid EmailDto email) {
        commonService.sendMail(email);
        return Result.success(email.getTo());
    }

    // 使用MailUtil测试发送邮件
    @Operation(summary = "测试参数校验")
    @GetMapping("/validation")
    public Result validation(@RequestParam @NotBlank(message = "emailNum参数不能为空") String emailNum) {
        return Result.success(emailNum);
    }
}

