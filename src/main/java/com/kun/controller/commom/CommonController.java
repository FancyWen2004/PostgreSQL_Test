package com.kun.controller.commom;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.alibaba.fastjson2.JSON;
import com.kun.constant.Constant;
import com.kun.entity.Student;
import com.kun.entity.User;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
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

    @PostMapping("/getCode")
    @Operation(summary = "生成验证码(Redis)")
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
            e.printStackTrace();
        }
        // 返回checkCodeKey
        return Result.success(checkCodeKey);
    }

    // 使用redis配合验证码实现登陆模拟
    @PostMapping("/login")
    @Operation(summary = "验证码+邮箱登陆(Redis)")
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
        }
        // 如果不为空，说明验证码未过期，进行验证码校验
        else {
            // 校验验证码
            if (checkCode.equals(code)) {
                return Result.success();
            } else {
                return Result.error("邮箱或验证码错误");
            }
        }
    }

    @PostMapping("/getCode2")
    @Operation(summary = "生成验证码(Redis)2")
    public Result checkCode2() {
        String checkCode = commonService.getCheckCode2();
        if (EmptyChecker.isEmpty(checkCode)) {
            return Result.error("验证码生成失败");
        } else {
            return Result.success(checkCode);
        }
    }


    // 使用RedisIdWorker测试生成全局唯一ID
    @GetMapping("/getId")
    @Operation(summary = "获取id")
    public Result getId() {
        long orderID = redisIdWorker.nextId("order");
        log.info("生成的订单ID：{}", orderID);
        return Result.success(orderID);
    }

    /**
     * @Description: TODO
     * @Author Wen
     * @Date 2025/3/25 20:14
     * @Param file
    */
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result upload(@Parameter(description = "上传的文件", required = true)
                         @RequestPart MultipartFile file)
    {
        String url = commonService.upload(file);
        log.info("上传文件地址：{}", url);
        return Result.success(url);
    }

    // 使用MongoDB测试
    /**
     * @Description: TODO:测试MongoDB查询数据
     * @Author Wen
     * @Date 2025/4/1 14:58
     * @Param
    */
    @GetMapping("/mongo")
    @Operation(summary = "测试MongoDB查询数据")
    public Result mongo() {

        // 查询mongoDB中userTest下users集合中的所有数据
        // query条件是name为“wenwenwen”的数据
        Query query = new Query(Criteria.where("name").is("wenwenwen"));

        User mongoTemplateOne = mongoTemplate.findOne(query, User.class);
        User users = mongoUtil.findOne(query, User.class);

        Query ageQuery = new Query();
        ageQuery.addCriteria(Criteria.where("age").gt(18));
        List<User> userList = mongoTemplate.find(ageQuery, User.class);

        log.info("使用MongoDB工具类查询到的成员列表数据：{}", userList);

        log.info("使用MongoDB工具类查询到的数据：{}", users);
        log.info("MongoDB查询到的数据：{}", mongoTemplateOne);

        return Result.success(mongoTemplateOne);
    }
}

