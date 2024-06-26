package top.suyiiyii.su.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.service.RedissionLock;
import top.suyiiyii.su.ConfigManger;
import top.suyiiyii.su.orm.core.ModelManger;
import top.suyiiyii.su.orm.utils.ConnectionBuilder;

/**
 * 依赖注入
 * 注入部分依赖到 ServletContext
 *
 * @author suyiiyii
 */

@WebListener
public class ContextInitializer implements ServletContextListener {
    Log logger = LogFactory.getLog(ContextInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("初始化依赖注入");

        ConfigManger configManger = new ConfigManger("application.properties");
        String url = configManger.get("JDBC_URL");
        String user = configManger.get("JDBC_USER");
        String password = configManger.get("JDBC_PASSWORD");
        logger.info("JDBC_URL: " + url);
        logger.info("JDBC_USER: " + user);

        ConnectionBuilder builder = new ConnectionBuilder(url, user, password);

        ModelManger modelManger = new ModelManger("top.suyiiyii.models", builder::getConnection);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("ModelManger", modelManger);
        servletContext.setAttribute("ConfigManger", configManger);

        RedissionLock.init();
        logger.info("依赖注入完成");
    }
}
