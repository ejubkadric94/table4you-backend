package plugins;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.inject.Inject;
import play.Application;
import play.Logger;
import play.Plugin;

/**
 * Class S3Plugin can be used for dealing with AWS related information such as AWS access and secret keys and other
 * sensitive data.
 * It is recommended to save sensitive data in form of environment variables and not directly inside the code for
 * security reasons.
 */
public class S3Plugin extends Plugin {
    public static final String AWS_S3_BUCKET = "aws.s3.bucket";
    public static final String AWS_ACCESS_KEY = "aws.access.key";
    public static final String AWS_SECRET_KEY = "aws.secret.key";
    private final Application application;

    public static AmazonS3 amazonS3;

    public static String s3Bucket;

    @Inject
    public S3Plugin(Application application) {
        this.application = application;
    }

    /**
     * Retrieves the keys from environment variables and sets them for further usage.
     */
    @Override
    public void onStart() {
        String accessKey = application.configuration().getString(AWS_ACCESS_KEY);
        String secretKey = application.configuration().getString(AWS_SECRET_KEY);
        s3Bucket = application.configuration().getString(AWS_S3_BUCKET);

        if ((accessKey != null) && (secretKey != null)) {
            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            amazonS3 = new AmazonS3Client(awsCredentials);
            amazonS3.createBucket(s3Bucket);
            Logger.info("Using S3 Bucket: " + s3Bucket);
        }
    }

    /**
     * Checks if everything is ready to proceed.
     *
     * @return true or false respectively
     */
    @Override
    public boolean enabled() {
        return (application.configuration().keys().contains(AWS_ACCESS_KEY) &&
                application.configuration().keys().contains(AWS_SECRET_KEY) &&
                application.configuration().keys().contains(AWS_S3_BUCKET));
    }

}