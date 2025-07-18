package com.kun.service.impl;

import com.kun.common.exception.BusinessException;
import com.kun.common.exception.ErrorCode;
import com.kun.common.minio.MinioProperties;
import com.kun.service.FileService;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioProperties properties;

    @Autowired
    private MinioClient client;

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
            throw new BusinessException(ErrorCode.FiLE_UPLOAD_ERROR);
        }
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
