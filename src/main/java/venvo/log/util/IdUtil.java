package venvo.log.util;

import java.util.UUID;

/**
 * @author venvo
 * @date 2020-06-02 13:58
 * @description
 * @modified By
 * @version: jdk1.8
 */
public class IdUtil {


    public static String genPrimaryKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
