package lk.earth.earthuniversity.util;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface RegexPattern{

    public String reg() default "";
    public String msg() default "";

}