package utilities;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import plugins.S3Plugin;
import java.io.BufferedInputStream;
import java.io.File;

/**
 * Created by Ejub on 26.3.2016.
 */
public class AWSManager {
    public static boolean isAWSConnected() {
        return S3Plugin.amazonS3 == null ? false : true;
    }

    public static String getBucket() {
        return S3Plugin.s3Bucket;
    }

    public static void savePhoto(String bucket, String name, File file){
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, name, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
        S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
    }

    public static void deletePhoto(String bucket, String name){
        S3Plugin.amazonS3.deleteObject(bucket, name);
    }
}
