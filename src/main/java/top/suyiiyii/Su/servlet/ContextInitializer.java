package top.suyiiyii.Su.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import top.suyiiyii.Su.ConfigManger;
import top.suyiiyii.Su.orm.core.ModelManger;
import top.suyiiyii.Su.orm.utils.ConnectionBuilder;

/**
 * 依赖注入
 * 注入部分依赖到 ServletContext
 */
@WebListener
public class ContextInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context Initialized");

        ConfigManger configManger = new ConfigManger("application.properties");
        String url = configManger.get("url");
        String user = configManger.get("user");
        String password = configManger.get("password");

        System.out.println("url: " + url);
        System.out.println("user: " + user);
        System.out.println("password: " + password);

        ConnectionBuilder builder = new ConnectionBuilder(url, user, password);

//        ModelManger modelManger = new ModelManger("top.suyiiyii.router.models", builder::getConnection);

        ServletContext servletContext = sce.getServletContext();
//        servletContext.setAttribute("modelManger", modelManger);
        servletContext.setAttribute("config", configManger);
    }
}
