package venvo.log.annotation;

import java.lang.annotation.*;

/**
 * @author venvo
 * @date 2020-06-02
 * @description
 * @modified By
 * @version: jdk1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogAnnotation {
    /**
     * 模块
     */
//    String operationModule() default "";


    /**
     * 操作名称
     */
    String operationName() default "";



    /**
     * 操作类型
     */
//    String operationType() default "";


    /**
     * 描述
     */
    String description() default "";



}
