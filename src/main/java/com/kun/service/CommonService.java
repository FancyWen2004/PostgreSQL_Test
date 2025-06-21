package com.kun.service;

import com.kun.entity.Dto.EmailDto;
import com.kun.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    String getCheckCode2();

    String upload(MultipartFile file);

    void sendMail(EmailDto email);

    User mongoFindName(String name);
}
