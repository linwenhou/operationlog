package venvo.log.aop;

import venvo.log.annotation.OperationLogAnnotation;
import venvo.log.entity.OperationLog;
import venvo.log.service.OperationLogService;
import venvo.log.util.IdUtil;
import venvo.log.util.IpUtil;
import venvo.log.util.Object2Map;
import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author venvo
 * @date 2020-06-02
 * @description
 * @modified By
 * @version: jdk1.8
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class OperationLogAspect {



    @Autowired
    private OperationLogService operationLogService;

    @Value("${login.username}")
    private String username;

    @Value("${login.methodName}")
    private String methodName;

    /**
     * 日志切入点
     */

    @Pointcut("@annotation(venvo.log.annotation.OperationLogAnnotation)")
    public void logPointCuts() {
    }

    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     * <p>
     * 执行目标方法之前
     * result = pjp.proceed();
     * 执行目标方法之后
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(venvo.log.annotation.OperationLogAnnotation)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        OperationLogAnnotation logAnno = method.getAnnotation(OperationLogAnnotation.class);

        final String operationName = logAnno.operationName();
        final String description = logAnno.description();

        OperationLog logtable = new OperationLog();
        logtable.setOperationMethod(method.getName());
        logtable.setOperationName(operationName);
        logtable.setOperationModel(method.toString());
        logtable.setDescription(description);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logtable.setIp(IpUtil.getIpAddr(request));
        if (method.getName().equals(methodName)) {
            final Object[] args = pjp.getArgs();
            Map map=new HashMap();
            try {
                map= Object2Map.getObjectToMap(args[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            final String usernamestr = MapUtils.getString(map, username, null);
            logtable.setUserName(usernamestr);
        } else {
            logtable.setUserName((String) request.getSession().getAttribute(username));
        }
        logtable.setOperationId(IdUtil.genPrimaryKey());
        Object result = null;
        try {
            //让代理方法执行
            result = pjp.proceed();
            // 2.相当于后置通知(方法成功执行之后走这里)
//            if (((Result) result).getCode() == 3000) {
//                logtable.setOperationType("登录失败");// 设置操作结果
//            } else {
//                logtable.setOperationType("操作成功");// 设置操作结果
//            }
            logtable.setOperationType("操作成功");// 设置操作结果
        } catch (Exception e) {
            // 3.相当于异常通知部分
            logtable.setOperationType("操作失败");// 设置操作结果
        } finally {
            // 4.相当于最终通知
            logtable.setOperationTime(LocalDateTime.now());// 设置操作日期
            operationLogService.addUserLog(logtable);// 添加日志记录
        }
        return result;
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
