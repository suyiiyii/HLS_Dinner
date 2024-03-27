package top.suyiiyii.su.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.schemas.TokenData;
import top.suyiiyii.su.ConfigManger;
import top.suyiiyii.su.orm.core.ModelManger;
import top.suyiiyii.su.orm.core.Session;

import java.io.IOException;

/**
 * BaseHttpServlet
 * 用于servlet的基类，提供了数据库会话和配置管理器，实现简单的依赖注入
 *
 * @author suyiiyii
 */
public class BaseHttpServlet extends HttpServlet {
    protected Session db;
    protected ConfigManger configManger;
    protected int uid = -1;

    /**
     * 依赖注入
     * 注意： ServletConfig是全局上下文，用于保存全局信息；而HttpServletRequest是请求上下文，用于保存请求信息
     * init方法是在servlet初始化时调用的，service方法是在每次请求时调用的
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if an exception occurs that interferes with the servlet's normal operation
     * @throws IOException      if an input or output exception occurs
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        this.db = ((ModelManger) config.getServletContext().getAttribute("ModelManger")).getSession();
        this.configManger = (ConfigManger) config.getServletContext().getAttribute("ConfigManger");
        TokenData tokenData = (TokenData) req.getAttribute("tokenData");
        if (tokenData != null) {
            this.uid = tokenData.uid;
        }
        try {
            super.service(req, resp);
        } finally {
            db.close();
        }
    }
}
