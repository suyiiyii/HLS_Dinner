package top.suyiiyii.router.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.schemas.OrderResp;
import top.suyiiyii.service.OrderService;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends BaseHttpServlet {
    OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.respWrite(resp, orderService.getAllOrder());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        Map<String, Integer> dishes = WebUtils.readRequestBody2Obj(req, Map.class);
        int orderId = orderService.createOrder(uid, dishes);
        OrderResp orderResp = orderService.getOrderRespById(orderId);
        WebUtils.respWrite(resp, orderResp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.service(req, resp, () -> {
            orderService = new OrderService(db);
        });
    }
}