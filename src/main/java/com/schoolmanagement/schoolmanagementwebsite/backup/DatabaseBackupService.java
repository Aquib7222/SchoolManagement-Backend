package com.schoolmanagement.schoolmanagementwebsite.backup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;

@Service
public class DatabaseBackupService {

    private final S3Client r2Client;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    public DatabaseBackupService(S3Client r2Client) {
        this.r2Client = r2Client;
    }

    public void createBackup() {

        Path sqlFile = null;
        Path gzipFile = null;

        try {

            System.out.println("======================================");
            System.out.println("ZYNTaks MySQL Backup Started");
            System.out.println("======================================");

            // ---------------------------------------
            // 1. Database information
            // ---------------------------------------

            String jdbcUrl = datasourceUrl
                    .replace("jdbc:mysql://", "");

            String hostAndDatabase = jdbcUrl.split("\\?")[0];

            String hostPart = hostAndDatabase.substring(
                    0,
                    hostAndDatabase.lastIndexOf("/")
            );

            String databaseName = hostAndDatabase.substring(
                    hostAndDatabase.lastIndexOf("/") + 1
            );

            String host;
            String port = "3306";

            if (hostPart.contains(":")) {

                host = hostPart.substring(
                        0,
                        hostPart.lastIndexOf(":")
                );

                port = hostPart.substring(
                        hostPart.lastIndexOf(":") + 1
                );

            } else {

                host = hostPart;
            }

            System.out.println("MySQL Host: " + host);
            System.out.println("MySQL Port: " + port);
            System.out.println("Database: " + databaseName);

            // ---------------------------------------
            // 2. Temporary files
            // ---------------------------------------

            String timestamp = LocalDateTime.now()
                    .format(
                            DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd_HH-mm-ss"
                            )
                    );

            sqlFile = Files.createTempFile(
                    "zyntaks-mysql-backup-",
                    ".sql"
            );

            gzipFile = Files.createTempFile(
                    "zyntaks-mysql-backup-",
                    ".sql.gz"
            );

            // ---------------------------------------
            // 3. Run mysqldump
            // ---------------------------------------

            System.out.println("📦 Creating MySQL dump...");

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "mysqldump",

                            "--host=" + host,

                            "--port=" + port,

                            "--user=" + databaseUsername,

                            "--password=" + databasePassword,

                            "--single-transaction",

                            "--routines",

                            "--triggers",

                            "--events",

                            databaseName
                    );

            processBuilder.redirectOutput(
                    sqlFile.toFile()
            );

            processBuilder.redirectErrorStream(false);

            Process process = processBuilder.start();

            String errorOutput;

            try (
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            process.getErrorStream()
                                    )
                            )
            ) {

                StringBuilder errorBuilder =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    errorBuilder
                            .append(line)
                            .append(System.lineSeparator());
                }

                errorOutput = errorBuilder.toString();
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {

                throw new RuntimeException(
                        "mysqldump failed: "
                                + errorOutput
                );
            }

            System.out.println(
                    "✅ MySQL dump created"
            );

            // ---------------------------------------
            // 4. Compress SQL file
            // ---------------------------------------

            System.out.println(
                    "🗜️ Compressing backup..."
            );

            try (
                    InputStream inputStream =
                            Files.newInputStream(sqlFile);

                    OutputStream outputStream =
                            Files.newOutputStream(gzipFile);

                    GZIPOutputStream gzipOutputStream =
                            new GZIPOutputStream(
                                    outputStream
                            )
            ) {

                byte[] buffer = new byte[8192];

                int length;

                while (
                        (length =
                                inputStream.read(buffer))
                                > 0
                ) {

                    gzipOutputStream.write(
                            buffer,
                            0,
                            length
                    );
                }
            }

            System.out.println(
                    "✅ Backup compressed"
            );

            // ---------------------------------------
            // 5. Upload to Cloudflare R2
            // ---------------------------------------

            String objectKey =
                    "mysql/"
                            + "mysql-backup-"
                            + timestamp
                            + ".sql.gz";

            System.out.println(
                    "☁️ Uploading backup to Cloudflare R2..."
            );

            PutObjectRequest request =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .contentType(
                                    "application/gzip"
                            )
                            .build();

            r2Client.putObject(
                    request,
                    RequestBody.fromFile(
                            gzipFile
                    )
            );

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "✅ BACKUP SUCCESSFUL"
            );

            System.out.println(
                    "R2 File: " + objectKey
            );

            System.out.println(
                    "======================================"
            );

        } catch (Exception e) {

            System.err.println(
                    "======================================"
            );

            System.err.println(
                    "❌ BACKUP FAILED"
            );

            System.err.println(
                    "======================================"
            );

            e.printStackTrace();

        } finally {

            // ---------------------------------------
            // 6. Delete temporary files
            // ---------------------------------------

            try {

                if (sqlFile != null) {

                    Files.deleteIfExists(
                            sqlFile
                    );
                }

                if (gzipFile != null) {

                    Files.deleteIfExists(
                            gzipFile
                    );
                }

            } catch (IOException e) {

                System.err.println(
                        "Could not delete temporary files: "
                                + e.getMessage()
                );
            }
        }
    }
}


