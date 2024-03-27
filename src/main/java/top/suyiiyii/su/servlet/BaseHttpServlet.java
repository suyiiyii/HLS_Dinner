package top.suyiiyii.su.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import top.suyiiyii.su.ConfigManger;
import top.suyiiyii.su.orm.core.Session;

import static top.suyiiyii.su.orm.WebUtils.getConfigMangerFromConfig;
import static top.suyiiyii.su.orm.WebUtils.getSessionFromConfig;

/**
 * BaseHttpServlet
 * 用于servlet的基类，提供了数据库会话和配置管理器，实现简单的依赖注入
 *
 * @author suyiiyii
 */
public class BaseHttpServlet extends HttpServlet {
    protected Session db;
    protected ConfigManger configManger;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.db = getSessionFromConfig(config);
        this.configManger = getConfigMangerFromConfig(config);
    }
}
