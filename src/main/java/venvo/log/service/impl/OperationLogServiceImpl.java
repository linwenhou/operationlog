package venvo.log.service.impl;


import venvo.log.entity.OperationLog;
import venvo.log.mapper.OperationLogMapper;
import venvo.log.service.OperationLogService;
import venvo.log.util.IdUtil;
import venvo.log.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


/**
 * @author venvo
 * @date 2020-06-02 12:47
 * @description
 * @modified By
 * @version: jdk1.8
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Value("${login.username}")
    private String username;

    @Override
    public void addUserLog(OperationLog operationLog) {
        try {
            operationLogMapper.insert(operationLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加系统用户操作日志
     *
     * @param joinPoint
     * @param methodName
     * @param operationName
     * @param description
     * @param operstionModel
     * @param operationMethod
     */
    @Override
    public void put(JoinPoint joinPoint, String methodName, String operationName, String operationType, String description, String operstionModel, String operationMethod) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            OperationLog log = new OperationLog();

            log.setOperationName(operationName);
            log.setDescription(description);
            log.setIp(IpUtil.getIpAddr(request));
//            log.setUserName((String) request.getSession().getAttribute("username"));
            log.setUserName((String) request.getSession().getAttribute(username));
            log.setOperationId(IdUtil.genPrimaryKey());
            log.setOperationTime(LocalDateTime.now());
            log.setOperationType(operationType);
            log.setOperationModel(operstionModel);
            log.setOperationMethod(operationMethod);
            operationLogMapper.insert(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
