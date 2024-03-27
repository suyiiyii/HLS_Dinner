package top.suyiiyii.su.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.su.WebUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * 异常处理过滤器
 * 捕获所有异常并返回异常信息
 *
 * @author suyiiyii
 */
public class ExceptionHandlerFilter implements Filter {

    private static final Log logger = LogFactory.getLog(ExceptionHandlerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        logger.info("开始处理请求： " + req.getRequestURI());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            // 将异常堆栈跟踪信息转换为换行符分隔的字符串
            String stackTrace = Arrays.stream(e.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n"));
            logger.error("请求处理失败： " + stackTrace);
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            WebUtils.respWrite(resp, e.getMessage());
            resp.setStatus(500);
        }
        logger.info("请求处理完成： " + req.getRequestURI());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}