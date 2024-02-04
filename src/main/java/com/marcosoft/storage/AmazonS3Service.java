package com.marcosoft.storage;

import com.amazonaws.services.s3.model.*;
import java.util.ArrayList;
import java.util.List;

public class AmazonS3Service {

    private final AmazonS3Config s3Config;

    public AmazonS3Service(AmazonS3Config s3Config) {
        this.s3Config = s3Config;
    }

    public AmazonS3Config getS3Config() {
        return s3Config;
    }

    public List<String> listS3Objects(){

        ListObjectsV2Request listObjectsV2Request =  new ListObjectsV2Request();
        listObjectsV2Request.setBucketName(s3Config.getBucketName());
        ListObjectsV2Result result = s3Config.getClientS3().listObjectsV2(listObjectsV2Request);
        List<S3ObjectSummary> objectList = result.getObjectSummaries();
        List<String> fileNameList = new ArrayList<>();
        objectList.forEach(o -> fileNameList.add(o.getKey()));
        return fileNameList;

    }

}
