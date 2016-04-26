package utilities;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import plugins.S3Plugin;
import java.io.BufferedInputStream;
import java.io.File;

/**
 * Created by Ejub on 25/03/16.
 * Class AWSManager can be used to perform saving and deleting objects from AWS S3.
 */
public class AWSManager {
    /**
     * Checks if the application is connected to AWS.
     *
     * @return true or false respectively
     */
    public static boolean isAWSConnected() {
        return S3Plugin.amazonS3 == null ? false : true;
    }

    /**
     * Retrieves the AWS S3 bucket.
     *
     * @return the S3 bucket
     */
    public static String getBucket() {
        return S3Plugin.s3Bucket;
    }

    /**
     * Saves a photo into AWS S3.
     *
     * @param name name of the file
     * @param file the photo
     */
    public static void savePhoto( String name, File file){
        PutObjectRequest putObjectRequest = new PutObjectRequest(getBucket(), name, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
        S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
    }

    /**
     * Deletes a photo from AWS S3.
     *
     * @param name the name of the photo
     */
    public static void deletePhoto(String name){
        S3Plugin.amazonS3.deleteObject(getBucket(), name);
    }

    /**
     * Saves a menu into AWS S3.
     *
     * @param name name of the file
     * @param file the menu
     */
	public static void saveMenu(String name, File file){
        PutObjectRequest putObjectRequest = new PutObjectRequest(getBucket(), name, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
        S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
    }

    /**
     * Deletes a menu from AWS S3.
     *
     * @param name the name of the menu
     */
    public static void deleteMenu(String name){
        S3Plugin.amazonS3.deleteObject(getBucket(), name);
    }
}