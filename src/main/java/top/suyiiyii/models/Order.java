package top.suyiiyii.models;

import top.suyiiyii.su.orm.annotation.ColumnSetting;
import top.suyiiyii.su.orm.annotation.TableRegister;

/**
 * 订单表
 * 存储订单信息
 *
 * @author suyiiyii
 */
@TableRegister("order")
public class Order {
    @ColumnSetting(isPrimaryKey = true, isAutoIncrement = true)
    public int orderId;
    public int uid;
    public int tableId;
    @ColumnSetting(stringLength = 100)
    public String status;
    public int totalPrice;
    public int createdAt;
}
