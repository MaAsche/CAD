package com.mabeto.backend.io;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AmazonStorageHandler {
    private static final String bucketName = "mabetoexercise";
    private final AmazonS3 client;

    public AmazonStorageHandler(){
        client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new PropertiesFileCredentialsProvider("config/credentials"))
                .build();
    }

    public void saveFile(MultipartFile multipartFile, String id) throws AmazonServiceException, IOException {
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(multipartFile.getContentType());
        data.setContentLength(multipartFile.getSize());
        PutObjectRequest request = new PutObjectRequest(bucketName, id, multipartFile.getInputStream(), data)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        client.putObject(request);
    }

    public void deleteFIle(String id) throws  AmazonServiceException {
         client.deleteObject(bucketName, id);
    }
}
