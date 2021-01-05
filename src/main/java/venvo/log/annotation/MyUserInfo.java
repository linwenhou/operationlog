package venvo.log.annotation;

import java.lang.annotation.*;

/**
 * @author venvo
 * @date 2020-06-03 11:28
 * @description 获取用户的名称
 * @modified By
 * @version: jdk1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyUserInfo {
}
