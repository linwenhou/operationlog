package venvo.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author venvo
 * @date 2020-06-02
 * @description 系统操作日志
 * @modified By
 * @version: jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("operation_id")
    private String operationId;

    @TableField("operation_name")
    private String operationName;

    @TableField("operation_model")
    private String operationModel;

    @TableField("description")
    private String description;

    @TableField("operation_method")
    private String operationMethod;

    @TableField("operation_time")
    private LocalDateTime operationTime;

    @TableField("operation_type")
    private String operationType;

    @TableField("user_name")
    private String userName;

    @TableField("ip")
    private String ip;


}
