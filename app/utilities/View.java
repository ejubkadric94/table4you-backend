package utilities;

/**
 * Created by Ejub on 23/02/16.
 * Class View is used with JsonView annotation to specify which User properties will be shown in specific
 * serialization.
 */
public class View {
    /**
     * Basic details are:
     */
    public static class BasicDetails {
    }

    /**
     * Properties added on top of basic details:
     */
    public static class AllDetails extends BasicDetails {
    }

    public static class AdditionalDetails extends BasicDetails {

    }
}
/*
scp -i /Users/test/Documents/Ejub/praksa/aws_key.pem /Users/test/Documents/Ejub/praksa/target/scala-2.11/api ec2-user@ec2-54-191-24-106.us-west-2.compute.amazonaws.com:/api


ssh -i Users/test/Documents/Ejub/praksa/aws_key.pem ec2-user@ec2-54-191-24-106.us-west-2.compute.amazonaws.com

ssh -i /Users/test/Documents/Ejub/praksa/aws_key.pem ec2-user@ec2-54-191-24-106.us-west-2.compute.amazonaws.com
 */
