package venvo.log.aop;

import venvo.log.annotation.MyUserInfo;
import venvo.log.util.Object2Map;
import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author venvo
 * @date 2020-06-03 16:03
 * @description
 * @modified By
 * @version: jdk1.8
 */
@Aspect
@Component
@Order(1)
public class MyUserInfoAspect {

    @Value("${login.username}")
    private String username;

    /**
     * 日志切入点
     */

    @Pointcut("@annotation(venvo.log.annotation.MyUserInfo)")
    public void myUserInfoPointCuts() {
    }

    @AfterReturning(pointcut = "myUserInfoPointCuts()")
    public void doAfter(JoinPoint joinPoint) {
        /**
         * 解析Log注解
         */
        String methodName = joinPoint.getSignature().getName();
        Method method = currentMethod(joinPoint, methodName);
        MyUserInfo log = method.getAnnotation(MyUserInfo.class);
        Object[] args = joinPoint.getArgs();
        String usernamestr = "未知用户";
        if (args.length > 0) {
            Map map=new HashMap();
            try {
                map= Object2Map.getObjectToMap(args[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            usernamestr = MapUtils.getString(map, username, null);

        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(username, usernamestr);

    }

    /**
     * 获取当前执行的方法
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        /**
         * 获取目标类的所有方法，找到当前要执行的方法
         */
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

}
