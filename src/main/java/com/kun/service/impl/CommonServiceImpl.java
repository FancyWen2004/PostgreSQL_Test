package com.kun.service.impl;

import com.kun.common.exception.BusinessException;
import com.kun.common.exception.ErrorCode;
import com.kun.common.minio.MinioProperties;
import com.kun.constant.Constant;
import com.kun.entity.Dto.EmailDto;
import com.kun.entity.Email;
import com.kun.entity.User;
import com.kun.service.CommonService;
import com.kun.service.FileService;
import com.kun.utils.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MinioProperties properties;

    @Autowired
    private MinioClient client;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FileService fileService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String getCheckCode2() {
        SpecCaptcha captchaCode = new SpecCaptcha(100, 48, 4);
        String code = captchaCode.text();
        // 将code转小写
        String checkCode = code.toLowerCase();
        log.info("生成的验证码：{}", checkCode);
        // 使用commons-codec的DigestUtils工具类进行md5加密
        String codeKey = DigestUtils.md5Hex(code);
        log.info("验证码密钥:{}", codeKey);
        // 将验证码转成小写，并保存到redis中
        redisUtil.set(Constant.CHECK_CODE_KEY + codeKey, checkCode, 60);

        return captchaCode.toBase64();
    }

    @Override
    public String upload(MultipartFile file) {
        return fileService.upload(file);
    }

    @Override
    public void sendMail(EmailDto emailDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto, email);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(email.getSubject());
        mail.setText(email.getContent());
        mail.setFrom(email.getFrom());
        mail.setTo(email.getTo());
        try {
            javaMailSender.send(mail);
        } catch (MailException e) {
            throw new BusinessException(ErrorCode.EMAIL_SEND_ERROR);
        }
        log.info("邮件发送成功，邮件内容：{}", emailDto.getContent());
    }

    @Override
    public User mongoFindName(String name) {
        // 查询mongoDB中userTest下users集合中的所有数据
        // query条件是name为“wenwenwen”的数据
        Query query = new Query(Criteria.where("name").is(name));

        User mongoTemplateOne = mongoTemplate.findOne(query, User.class);
//        //User users = mongoUtil.findOne(query, User.class);
//
//        Query ageQuery = new Query();
//        ageQuery.addCriteria(Criteria.where("age").gt(18));
//        List<User> userList = mongoTemplate.find(ageQuery, User.class);
//
//        log.info("使用MongoDB工具类查询到的成员列表数据：{}", userList);
//        //log.info("使用MongoDB工具类查询到的数据：{}", users);
        log.info("MongoDB查询到的数据：{}", mongoTemplateOne);
        return mongoTemplateOne;
    }

}
