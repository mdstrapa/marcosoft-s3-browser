package com.marcosoft.storage;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;


import java.util.List;

public class AmazonS3Config {

    private final String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public AmazonS3 getClientS3() {
        return clientS3;
    }

    private final AmazonS3 clientS3;

    public AmazonS3Config(String accessKey,
                          String secretKey,
                          String endPoint,
                          String bucketName) {
        this.bucketName = getSimpleBucketName(bucketName);
        this.clientS3 = s3Client(accessKey, secretKey, endPoint);
        config(List.of(new CustomRuleDeleteFiles()));
    }

    private String getSimpleBucketName(String bucketName) {
        return bucketName.replace("/","");
    }

    private AmazonS3 s3Client(String accessKey, String secretKey, String endPoint) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, null))
                .enablePathStyleAccess()
                .build();
    }

    private void config(List<BucketLifecycleConfiguration.Rule> rules) {
        this.clientS3.setBucketLifecycleConfiguration(
                this.bucketName,
                new BucketLifecycleConfiguration().withRules(rules)
        );
    }

    public static class CustomRuleDeleteFiles extends BucketLifecycleConfiguration.Rule {

        public static final int EXPIRES_IN_DAYS = 30;
        private static final String CUSTOM_RULE_DELETE_FILES_WHEN_EXPIRES = "delete-files-when-expires";

        public CustomRuleDeleteFiles() {
            this.withId(CUSTOM_RULE_DELETE_FILES_WHEN_EXPIRES)
                    .withFilter(new LifecycleFilter())
                    .withStatus(BucketLifecycleConfiguration.ENABLED)
                    .withExpirationInDays(EXPIRES_IN_DAYS);
        }

    }
}
