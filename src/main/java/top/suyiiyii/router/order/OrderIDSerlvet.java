package top.suyiiyii.router.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.service.OrderService;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

/**
 * 查询单个订单
 * 订单不支持更改，只有查询单个订单的接口
 *
 * @author suyiiyii
 */
@WebServlet("/order/*")
public class OrderIDSerlvet extends BaseHttpServlet {
    OrderService orderService;
    int id = -1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.respWrite(resp, orderService.getOrderRespById(id));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.service(req, resp, () -> {
            orderService = new OrderService(db);
            String path = req.getPathInfo();
            String[] paths = path.split("/");
            if (paths.length > 1) {
                id = Integer.parseInt(paths[1]);
            }
        });
    }
}
