package com.back.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ImageUploadController {

    private static final String UPLOAD_DIR = "/upload/";

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@Valid @RequestParam("file") MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사에 실패한 경우
            return new ResponseEntity<>("Invalid file", HttpStatus.BAD_REQUEST);
        }

        if (!file.isEmpty()) {
            try {
                // 업로드할 디렉토리 경로 확인 및 생성
                String currentDir = System.getProperty("user.dir");
                File uploadDir = new File(currentDir + UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 이름 생성 (중복 방지)
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

                // 업로드된 파일을 서버에 저장
                String filePath = uploadDir.getAbsolutePath() + "/" + uniqueFileName;
                File dest = new File(filePath);
                file.transferTo(dest);

                return new ResponseEntity<>("File '" + fileName + "' uploaded successfully", HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }
    }
}