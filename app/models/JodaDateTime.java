package models;

/**
 * Created by test on 04/02/16.
 */
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@play.data.Form.Display(name = "format.joda.datetime", attributes = { "format" })
public @interface JodaDateTime {
    String format() default "";
}