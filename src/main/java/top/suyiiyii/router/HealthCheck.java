package top.suyiiyii.router;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.su.WebUtils;

import java.io.IOException;

/**
 * HealthCheck Servlet
 *
 * @author suyiiyii
 */
@WebServlet("/health_check")
public class HealthCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HealthCheckResponse healthCheckResponse = new HealthCheckResponse("heathy");
        WebUtils.respWrite(resp, healthCheckResponse);
    }

}

class HealthCheckResponse {
    public final String status;

    public HealthCheckResponse(String status) {
        this.status = status;
    }
}
