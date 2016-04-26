package ControllerTests;

import controllers.routes;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.xalan.xsltc.compiler.Constants;
import org.junit.Test;
import play.api.libs.Files;
import play.api.mvc.AnyContent;
import play.api.mvc.AnyContentAsMultipartFormData;
import play.api.mvc.MultipartFormData;
import play.mvc.Result;
import play.libs.Scala;
import play.mvc.Http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

/**
 * Created by Ejub on 28.3.2016.
 */
public class PhotoTest  extends Http.RequestBuilder{
/*
    @Test
    public void uploadPhotoToInvalidRestaurant() {
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/444/photos");
            Result result = route(rb);

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }

    @Test
    public void uploadPhotoToWithNoUploadForm() {
        running(fakeApplication(),()-> {
            Http.RequestBuilder rb = new Http.RequestBuilder().method(POST).uri("/v1/restaurants/5/photos");
            Result result = route(rb);

            assertEquals(Http.Status.BAD_REQUEST, result.status());
        });
    }
    */
/*
   @Test
   public void uploadPhotoWithValidImageTest() {
       running(fakeApplication(),()-> {
           File testFile = new File("C:\\Users\\Ejub\\Desktop\\rest.jpg");
           DefaultHttpClient httpclient = new DefaultHttpClient();
           HttpPost method = new HttpPost("http://localhost:9000/v1/restaurants/1/photos");
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
}