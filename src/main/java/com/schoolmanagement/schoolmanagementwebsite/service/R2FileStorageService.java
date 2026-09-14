package com.schoolmanagement.schoolmanagementwebsite.service;

import java.io.IOException;
import java.net.URLConnection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
public class R2FileStorageService {

    private final S3Client r2Client;

    @Value("${cloudflare.files-r2.bucket-name}")
    private String bucketName;

    public R2FileStorageService(
            @Qualifier("filesR2Client") S3Client r2Client
    ) {
        this.r2Client = r2Client;
    }

    /**
     * Upload student photo to Cloudflare R2
     */
    public String uploadStudentPhoto(MultipartFile file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Photo file is empty");
        }

        String originalFilename =
                file.getOriginalFilename();

        String extension = "";

        if (originalFilename != null
                && originalFilename.contains(".")) {

            extension =
                    originalFilename.substring(
                            originalFilename.lastIndexOf(".")
                    );
        }

        String fileName =
                UUID.randomUUID()
                        + extension;

        String objectKey =
                "students/photos/" + fileName;

        String contentType =
                file.getContentType();

        if (contentType == null
                || contentType.isBlank()) {

            contentType =
                    URLConnection.guessContentTypeFromName(
                            originalFilename
                    );
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .contentType(contentType)
                        .build();

        r2Client.putObject(
                request,
                RequestBody.fromInputStream(
                        file.getInputStream(),
                        file.getSize()
                )
        );

        System.out.println(
                "✅ Student photo uploaded to R2: "
                        + objectKey
        );

        // Database me sirf filename save karenge
        return fileName;
    }

    /**
     * Get student photo from R2
     */
    public ResponseBytes<GetObjectResponse> getStudentPhoto(
            String fileName
    ) {

        String objectKey =
                "students/photos/" + fileName;

        GetObjectRequest request =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build();

        return r2Client.getObjectAsBytes(request);
    }
}


