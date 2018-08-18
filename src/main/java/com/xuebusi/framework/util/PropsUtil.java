package com.xuebusi.framework.util;

import com.xuebusi.framework.core.Constant;
import com.xuebusi.framework.ioc.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties文件操作工具类
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    private static Properties props;

    static {
        props = PropsUtil.loadProps(Constant.CONFIG_PROPS);
    }

    /**
     * 根据配置文件路径加载配置
     *
     * @param propsPath
     * @return
     */
    public static Properties loadProps(String propsPath) {
        Properties props = new Properties();
        try {
            ClassLoader classLoader = ClassHelper.getClassLoader();
            props.load(classLoader.getResourceAsStream(propsPath));
        } catch (IOException e) {
            logger.error("读取配置失败!");
        }
        return props;
    }

    /**
     * 获取默认配置
     *
     * @param key
     * @return
     */
    public static String getDefaultConfig(String key) {
        return (String) props.get(key);
    }

    /**
     * 获取包路径
     *
     * @return
     */
    public static String getPackageName() {
        return PropsUtil.getDefaultConfig(Constant.BASE_PACKAGE_KEY);
    }
}
