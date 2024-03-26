package top.suyiiyii.Su.orm;

import jakarta.servlet.ServletConfig;
import top.suyiiyii.Su.ConfigManger;
import top.suyiiyii.Su.orm.core.ModelManger;
import top.suyiiyii.Su.orm.core.Session;

/**
 * WebUtils
 * 配合servlet使用
 */
public class WebUtils {

    /**
     * 从上下文中获取Session
     * （从上下文读取ModelManger，然后获取session）
     *
     * @param config ServletConfig
     * @return Session
     */

    public static Session getSessionFromConfig(ServletConfig config) {
        return ((ModelManger) config.getServletContext().getAttribute("modelManger")).getSession();
    }

    /**
     * 从上下文中获取ConfigManger
     *
     * @param config ServletConfig
     * @return ConfigManger
     */

    public static ConfigManger getConfigMangerFromConfig(ServletConfig config) {
        return (ConfigManger) config.getServletContext().getAttribute("configManger");
    }
}
