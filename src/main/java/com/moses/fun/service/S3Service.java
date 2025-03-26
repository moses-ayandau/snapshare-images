package com.moses.fun.service;

import com.moses.fun.model.Image;
import com.moses.fun.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    private final ImageRepository imageRepository;

    private static final String BUCKET_NAME = "snapshare-s3-bk";

    public S3Service(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        s3Client = S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public List<Image> listImages(int page, int size) {
        try {
            List<Image> allImages = imageRepository.findAll();
            allImages.sort(Comparator.comparing(Image::getLastModified).reversed());

            int startIndex = (page - 1) * size;

            if (startIndex >= allImages.size()) {
                return Collections.emptyList();
            }

            int endIndex = Math.min(startIndex + size, allImages.size());

            return allImages.subList(startIndex, endIndex);
        } catch (Exception e) {
            log.error("Error listing images from database", e);
            throw new RuntimeException("Failed to list images", e);
        }
    }

    public int countImages() {
        try {
            return (int) imageRepository.count();
        } catch (Exception e) {
            log.error("Error counting images in database", e);
            throw new RuntimeException("Failed to count images", e);
        }
    }
    @Transactional
    public Image uploadImage(MultipartFile file, String title, String description) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String key = UUID.randomUUID().toString() + extension;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            String imageUrl = generatePresignedUrl(key);

            Image image = Image.builder()
                    .key(key)
                    .fileName(originalFilename)
                    .url(imageUrl)
                    .size(file.getSize())
                    .lastModified(Instant.now())
                    .title(title)
                    .description(description)
                    .build();

            imageRepository.save(image);

            return image;
        } catch (IOException e) {
            log.error("Error uploading image to S3", e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public void deleteImage(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

            imageRepository.deleteByKey(key);
        } catch (Exception e) {
            log.error("Error deleting image from S3 and database", e);
            throw new RuntimeException("Failed to delete image", e);
        }
    }

    private String generatePresignedUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", BUCKET_NAME, Region.EU_CENTRAL_1, key);
    }
}