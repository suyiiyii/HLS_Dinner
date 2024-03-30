package top.suyiiyii.service;

import top.suyiiyii.models.Dish;
import top.suyiiyii.models.Order;
import top.suyiiyii.models.OrderItem;
import top.suyiiyii.schemas.OrderResp;
import top.suyiiyii.su.orm.core.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有关订单的业务逻辑
 *
 * @author suyiiyii
 */
public class OrderService {
    private static final Lock lock = new ReentrantLock();
    private Session db;

    public OrderService(Session db) {
        this.db = db;
    }

    /**
     * 通过订单编号查询订单的基础信息
     *
     * @param id 订单编号
     * @return 订单基础信息
     */
    public Order getOrderById(int id) {
        try {
            return db.query(Order.class).eq("order_id", id).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过订单编号查询订单的详细信息
     *
     * @param id 订单编号
     * @return 订单详细信息
     */

    public OrderResp getOrderRespById(int id) {
        Order order = getOrderById(id);
        // 填入基本信息
        OrderResp orderResp = new OrderResp();
        orderResp.order_id = order.orderId;
        orderResp.created_at = order.createdAt;

        // 填入菜品信息
        List<OrderItem> orderItems;
        try {
            orderItems = db.query(OrderItem.class).eq("order_id", id).all();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (OrderItem orderItem : orderItems) {
            OrderResp.Dish dish = new OrderResp.Dish();
            dish.dish_id = orderItem.dishId;
//            dish.dish_name = orderItem.dish_id;
            dish.dish_num = orderItem.quantity;
            dish.dish_price = orderItem.price;
            orderResp.dish.add(dish);
        }
        // 菜品的名字还不知道，还要到数据库去查
        // 自己写的orm，没有Join功能，下次还是用成品吧
        // 懒得写map了，直接暴力查吧
        for (OrderResp.Dish dish : orderResp.dish) {
            try {
                Dish dish1 = db.query(Dish.class).eq("id", dish.dish_id).first();
                String name = dish1.name;
                dish.dish_name = name;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return orderResp;
    }

    /**
     * 创建订单
     *
     * @param uid    用户id
     * @param dishes 菜品编号和数量的映射
     * @return 订单编号
     */
    public int createOrder(int uid, Map<String, Integer> dishes) {
        lock.lock();
        // 麻了，暴力查吧
        try {
            Order order = new Order();
            order.uid = uid;
            order.status = "unpaid";
            order.totalPrice = 0;
            order.createdAt = (int) (System.currentTimeMillis() / 1000);
            db.insert(order);
            order = db.query(Order.class).eq("uid", uid).eq("created_at", order.createdAt).first();
            int orderId = order.orderId;
            for (Map.Entry<String, Integer> entry : dishes.entrySet()) {
                OrderItem orderItem = new OrderItem();
                orderItem.orderId = orderId;
                orderItem.dishId = Integer.parseInt(entry.getKey());
                orderItem.quantity = entry.getValue();
                Dish dish = db.query(Dish.class).eq("id", entry.getKey()).first();
                orderItem.price = dish.price;
                db.insert(orderItem);
            }
            return orderId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
