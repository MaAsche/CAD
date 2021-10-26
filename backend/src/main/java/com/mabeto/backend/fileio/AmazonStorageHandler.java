package com.mabeto.backend.fileio;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AmazonStorageHandler {
    private static final String bucketName = "mabetoexercise";
    private final AmazonS3 s3client;

    public AmazonStorageHandler(){
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
    }

    public String saveFile(MultipartFile multipartFile) throws AmazonServiceException, IOException {
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(multipartFile.getContentType());
        data.setContentLength(multipartFile.getSize());
        String filename = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + multipartFile.getOriginalFilename();
        PutObjectResult objectResult = s3client.putObject(bucketName, filename, multipartFile.getInputStream(), data);
        System.out.println(objectResult.getContentMd5()); //you can verify MD5
        return filename;
    }

    public void deleteFIle(String objectKEy) throws  AmazonServiceException {
         s3client.deleteObject(bucketName, objectKEy);
    }
}
