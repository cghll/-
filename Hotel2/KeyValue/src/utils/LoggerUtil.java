package utils;

import config.ServerConfig;

import java.io.File;
import java.util.logging.*;
import java.io.IOException;

/**
 * 日志管理工具
 * - 职责：统一记录系统运行日志
 * - 特性：自动创建日志文件，按天滚动归档
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger("KV-DB");

    static {
        try {
            // 从配置获取日志路径
            String logFile = ServerConfig.getLogFile();

            // 创建日志目录（如果不存在）
            new File(logFile).getParentFile().mkdirs();

            // 初始化文件处理器（追加模式，每天一个文件）
            FileHandler fh = new FileHandler(logFile, true);
            fh.setFormatter(new SimpleFormatter()); // 简单文本格式
            logger.addHandler(fh);

            // 禁用默认控制台输出
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException("日志初始化失败", e);
        }
    }

    /**
     * 记录一般信息
     * @param message 日志内容
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * 记录严重错误
     * @param message 错误信息
     */
    public static void logSevere(String message) {
        logger.severe(message);
    }
}