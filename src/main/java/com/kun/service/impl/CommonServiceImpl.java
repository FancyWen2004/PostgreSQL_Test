package com.kun.service.impl;

import com.kun.common.minio.MinioProperties;
import com.kun.constant.Constant;
import com.kun.result.Result;
import com.kun.service.CommonService;
import com.kun.utils.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MinioProperties properties;

    @Autowired
    private MinioClient client;

    @Override
    public String getCheckCode2() {
        SpecCaptcha captchaCode = new SpecCaptcha(100, 48, 4);
        String code = captchaCode.text();
        // 将code转小写
        String checkCode = code.toLowerCase();
        log.info("生成的验证码：{}", checkCode);
        // 生成md5加密的key
        String codeKey = DigestUtils.md5Hex(code);
        log.info("验证码密钥:{}", codeKey);
        // 将验证码转成小写，并保存到redis中
        redisUtil.set(Constant.CHECK_CODE_KEY + codeKey, checkCode, 60);

        return captchaCode.toBase64();
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucketName()).build());
            if (!bucketExists) {
                client.makeBucket(MakeBucketArgs
                        .builder()
                        .bucket(properties.getBucketName())
                        .build());
                client.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(properties.getBucketName())
                        .config(createBucketPolicyConfig(properties.getBucketName()))
                        .build());
            }

            String filename = new SimpleDateFormat("yyyyMMdd")
                    .format(new Date()) + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            client.putObject(PutObjectArgs.builder().bucket(properties.getBucketName())
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    // 设置文件类型，防止生成的链接是下载类型
                    .contentType(file.getContentType())
                    .build());

            return String.join("/", properties.getEndpoint(), properties.getBucketName(), filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 创建桶策略
    private String createBucketPolicyConfig(String bucketName) {
        return """
            {
              "Statement" : [ {
                "Action" : "s3:GetObject",
                "Effect" : "Allow",
                "Principal" : "*",
                "Resource" : "arn:aws:s3:::%s/*"
              } ],
              "Version" : "2012-10-17"
            }
            """.formatted(bucketName);
    }
}
