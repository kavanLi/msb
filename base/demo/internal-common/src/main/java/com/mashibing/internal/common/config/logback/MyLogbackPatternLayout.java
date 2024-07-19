package com.mashibing.internal.common.config.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * @author Joyce Huang
 */
public class MyLogbackPatternLayout extends PatternLayout {

    private static final Logger LOGGER = StatusLogger.getLogger();
    /** 正则替换规则 */
    private final MyLogbackReplaces replaces;
    /** 是否开启脱敏，默认false */
    private final Boolean sensitive;

    public MyLogbackPatternLayout(MyLogbackReplaces replaces, Boolean sensitive) {
        super();
        this.replaces = replaces;
        this.sensitive = sensitive;
        if (this.sensitive != null && this.sensitive) {
            LOGGER.error("======>>日志脱敏开启!");
            if (this.replaces == null || this.replaces.getReplace() == null || this.replaces.getReplace().isEmpty()) {
                LOGGER.error("======>>日志脱敏开启，但未配置脱敏规则!");
            }
        } else {
            LOGGER.error("======>>日志脱敏未开启!");
        }
    }

    /**
     * 格式化日志信息
     */
    @Override
    public String doLayout(ILoggingEvent event) {
        // 占位符填充
        String msg = super.doLayout(event);
        // 脱敏处理
        return this.buildSensitiveMsg(msg);
    }

    /**
     * 根据配置对日志进行脱敏
     */
    public String buildSensitiveMsg(String msg) {
        if (sensitive == null || !sensitive) {
            return msg;
        }
        if (this.replaces == null || this.replaces.getReplace() == null || this.replaces.getReplace().isEmpty()) {
            return msg;
        }
        String sensitiveMsg = msg;
        for (RegexReplacement replace : this.replaces.getReplace()) {
            // 遍历脱敏正则,替换敏感数据
            sensitiveMsg = replace.format(sensitiveMsg);
        }
        return sensitiveMsg;
    }
}
