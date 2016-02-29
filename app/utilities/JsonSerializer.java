package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import play.libs.Json;
import play.mvc.Http.Request;

/**
 * Created by Ejub on 15/02/16.
 * Class JsonSerializer can be used for serialization and deserialization of input and output.
 * More specifically, it provides methods for:
 *      serialization of Model classes to JSON format
 *      deserialization of JSON into the class specified
 */
public class JsonSerializer {
    /**
     * Deserializes the JSON object from request body.
     *
     * @param request the http request
     * @param classname the name of class to which the JSON Object should be deserialized
     * @return the deserialized object
     */
    public static Object deserialize(Request request, Class classname){
        return Json.fromJson(request.body().asJson(), classname);
    }

    public static String serializeObject(Object object){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String serializeBasicRestaurantDetails(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            result = mapper.writerWithView(RestaurantViews.BasicDetails.class).withDefaultPrettyPrinter().
                    writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String serializeAllRestaurantDetails(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            result = mapper.writerWithView(RestaurantViews.AllDetails.class).withDefaultPrettyPrinter().
                    writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
