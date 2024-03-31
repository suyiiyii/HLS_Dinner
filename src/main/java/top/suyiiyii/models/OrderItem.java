package top.suyiiyii.models;

import top.suyiiyii.su.orm.annotation.ColumnSetting;
import top.suyiiyii.su.orm.annotation.TableRegister;

/**
 * 订单商品关系表
 * 存储一个订单中的商品信息
 *
 * @author suyiiyii
 */
@TableRegister("order_item")
public class OrderItem {
    @ColumnSetting(isPrimaryKey = true, isAutoIncrement = true)
    public int id;
    public int orderId;
    public int dishId;
    public int quantity;
    public int price;
}
