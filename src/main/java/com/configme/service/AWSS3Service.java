package com.configme.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for managing AWS S3 functionnalities
 */
@Service
public class AWSS3Service {

    private AmazonS3 s3client;
    private TransferManager tansfermanager;

    private String bucketName = "monmegabucketconfigme";

    private final Logger log = LoggerFactory.getLogger(AWSS3Service.class);

    public void setS3client(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * Initialize s3client with credential retrived from environment variables:
     * AWS_SECRET_ACCESS_KEY and AWS_ACCESS_KEY
     */
    public AWSS3Service() {
        this.s3client =
            AmazonS3ClientBuilder
                .standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

        this.tansfermanager = TransferManagerBuilder.standard().withS3Client(this.s3client).build();
    }

    public List<Bucket> listBuckets() {
        return s3client.listBuckets();
    }

    public void printAllBuckets() {
        this.listBuckets().forEach(n -> log.info(n.getName()));
    }

    public Boolean doesBucketExist() {
        return s3client.doesBucketExistV2(this.bucketName);
    }

    public PutObjectResult putObject(String key, File file) {
        return s3client.putObject(this.bucketName, key, file);
    }

    public ObjectListing listObjects() {
        return s3client.listObjects(this.bucketName);
    }

    public S3Object getObject(String objectKey) {
        return s3client.getObject(this.bucketName, objectKey);
    }

    public void deleteObject(String objectKey) {
        s3client.deleteObject(this.bucketName, objectKey);
    }

    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq) {
        return s3client.deleteObjects(delObjReq);
    }

    public URL getUrl(String objectKey) {
        return s3client.getUrl(this.bucketName, objectKey);
    }

    public void upload(String file_name, File file) {
        Upload upload = this.tansfermanager.upload(this.bucketName, file_name, file);

        upload.addProgressListener(
            new ProgressListener() {
                public void progressChanged(ProgressEvent progressEvent) {
                    System.out.println(upload.getProgress().getPercentTransferred() + "%");

                    if (progressEvent.getEventType() == ProgressEventType.TRANSFER_COMPLETED_EVENT) {
                        System.out.println("Upload complete!!!");
                    }
                }
            }
        );
    }
}
