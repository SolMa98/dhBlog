package com.dhkim.blog.util;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Data
public class ImageUtils {
    private String originalFileName;
    private String fileName;
    private String filePath;

    private static final String UPLOAD_DIR = "src/main/resources/static/upload/img/";

    public static ImageUtils saveImgFile(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Uploaded image is empty");
        }

        // 파일 이름 생성 (UUID 사용)
        String originalFileName = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 파일 저장 경로 설정
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // 디렉토리 생성
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new IOException("Failed to create directories for file: " + filePath, e);
        }

        // 파일 저장
        try {
            Files.copy(image.getInputStream(), filePath);
        } catch (IOException e) {
            throw new IOException("Failed to store file " + fileName, e);
        }

        // ImageUtils 객체 생성 및 설정
        ImageUtils imageUtils = new ImageUtils();
        imageUtils.setOriginalFileName(originalFileName);
        imageUtils.setFileName(fileName);
        imageUtils.setFilePath(filePath.toString());

        // ImageUtils 객체 반환
        return imageUtils;
    }
}
