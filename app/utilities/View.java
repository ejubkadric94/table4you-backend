package utilities;

/**
 * Created by Ejub on 23/02/16.
 * Class View is used with JsonView annotation to specify which User properties will be shown in specific
 * serialization.
 */
public class View {
    /**
     * Basic details
     */
    public static class BasicDetails {
    }

    /**
     * Properties added on top of basic details
     */
    public static class AllDetails extends BasicDetails {
    }

    public static class AdditionalDetails extends BasicDetails {
    }
}

