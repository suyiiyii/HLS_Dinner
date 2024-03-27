package top.suyiiyii.su.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.schemas.TokenData;
import top.suyiiyii.su.ConfigManger;
import top.suyiiyii.su.UniversalUtils;

import java.io.IOException;

import static top.suyiiyii.su.JwtUtils.verifyToken;


/**
 * jwt 校验过滤器
 * 如果校验成功，注入tokenData到servletRequest上下文
 *
 * @author suyiiyii
 */
public class JwtFilter implements Filter {
    Log logger = LogFactory.getLog(JwtFilter.class);
    ConfigManger configManger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        configManger = (ConfigManger) filterConfig.getServletContext().getAttribute("ConfigManger");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        logger.info("JwtFilter: " + req.getRequestURI());
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("缺少Authorization头或者Authorization头不以Bearer开头");
        }
        String token = authHeader.substring(7);
        String tokenStr = verifyToken(token, configManger.get("secret"));
        // 验证token
        if (!tokenStr.isEmpty()) {
            // 注入tokenData
            TokenData tokenData = UniversalUtils.json2Obj(tokenStr, TokenData.class);
            req.setAttribute("tokenData", tokenData);
            logger.info("token verify success");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("token verify failed");
            throw new ServletException("token验证失败");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
