package ControllerTests;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;

/**
 * Created by Ejub on 2.4.2016.
 */
public class uploadMenuTest {
/*
    @Test
    public void uploadMenuTest() {
        running(fakeApplication(),()-> {
            File testFile = new File("C:\\Users\\Ejub\\Desktop\\rest.jpg");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost method = new HttpPost("http://localhost:9000/v1/restaurants/2/menu");
            MultipartEntity entity = new MultipartEntity();
            FileBody fileBody = new FileBody(testFile);
            entity.addPart("file", fileBody);
            method.setEntity(entity);

            HttpResponse response = null;
            try {
                response = httpclient.execute(method);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assertEquals(Http.Status.OK,  response.getStatusLine().getStatusCode());
        });
    }
*/
    /*
    @Test
    public void uploadMenuToInvalidRestaurant() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("upload", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/444/menu").bodyForm(map);
            Result result = route(rb);

            assertEquals(Http.Status.NOT_FOUND, result.status());
        });
    }

    @Test
    public void uploadMenuToWithNoUploadForm() {
        running(fakeApplication(),()-> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("something", "D:\\Programiranje\\table4you\\restaurant.jpg");

            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/6/menu").bodyForm(map);
            Result result = route(rb);

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }
    */
}