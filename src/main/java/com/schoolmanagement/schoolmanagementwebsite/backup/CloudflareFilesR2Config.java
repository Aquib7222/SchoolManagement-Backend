package com.schoolmanagement.schoolmanagementwebsite.backup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class CloudflareFilesR2Config {

    @Value("${cloudflare.files-r2.account-id}")
    private String accountId;

    @Value("${cloudflare.files-r2.access-key-id}")
    private String accessKeyId;

    @Value("${cloudflare.files-r2.secret-access-key}")
    private String secretAccessKey;

    @Bean(name = "filesR2Client")
    public S3Client filesR2Client() {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        accessKeyId,
                        secretAccessKey
                );

        return S3Client.builder()
                .endpointOverride(
                        URI.create(
                                "https://"
                                        + accountId
                                        + ".r2.cloudflarestorage.com"
                        )
                )
                .region(Region.of("auto"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();
    }
}