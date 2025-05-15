package com.kun.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    String getCheckCode2();

    String upload(MultipartFile file);
}
