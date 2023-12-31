package ru.skypro.homework.infrastructure.repository.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.core.repository.FileRepository;
import ru.skypro.homework.infrastructure.exception.FileNotLoadException;
import ru.skypro.homework.infrastructure.exception.FileNotUploadException;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FileRepositoryImpl implements FileRepository {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        try {
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                        .bucket(bucketName).build();
                minioClient.makeBucket(makeBucketArgs);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @Override
    public void addFile(String name, InputStream inputStream) {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(name)
                .stream(inputStream, -1, 10485760)
                .build();
        try (inputStream) {
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new FileNotUploadException(e);
        }
    }

    @Override
    public InputStream getFile(String name) {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(name)
                .build();
        try {
            return minioClient
                    .getObject(getObjectArgs);
        } catch (Exception e) {
            throw new FileNotLoadException(e);
        }
    }
}