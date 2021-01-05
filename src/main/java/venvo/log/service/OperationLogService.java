package venvo.log.service;


import venvo.log.entity.OperationLog;
import org.aspectj.lang.JoinPoint;

/**
 * @author venvo
 * @date 2020-06-02 12:22
 * @description
 * @modified By
 * @version: jdk1.8
 */
public interface OperationLogService {

    /**
     * 添加系统用户日志
     *
     * @param sysUserLog 日志信息
     */
    void addUserLog(OperationLog sysUserLog);

    /**
     * 添加系统用户操作日志
     *
     * @param joinPoint
     * @param methodName
     * @param operationName
     * @param description
     * @param operationModel
     * @param operationMethod
     */
    void put(JoinPoint joinPoint, String methodName, String operationName, String operationType, String description, String operationModel, String operationMethod);
}
