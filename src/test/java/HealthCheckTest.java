import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import top.suyiiyii.router.HealthCheck;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HealthCheckTest {
    @Test
    public void testDoGet() throws Exception {
        // 创建模拟的请求和响应对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // 创建一个StringWriter对象来获取响应的输出
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // 当getResponseWriter被调用时，返回我们的writer
        when(response.getWriter()).thenReturn(writer);

        // 创建一个新的HealthCheck对象并调用doGet方法
        new HealthCheck().doGet(request, response);

        // 验证doGet方法的行为
        assertEquals("{\"status\":\"heath\"}", stringWriter.toString());
    }
}