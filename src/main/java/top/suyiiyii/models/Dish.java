package top.suyiiyii.models;

import top.suyiiyii.su.orm.annotation.ColumnSetting;
import top.suyiiyii.su.orm.annotation.TableRegister;

/**
 * 菜品实体类
 *
 * @author suyiiyii
 */
@TableRegister("dish")
public class Dish {
    @ColumnSetting(isPrimaryKey = true, isAutoIncrement = true)
    public int id;
    @ColumnSetting(stringLength = 100, isNotNull = true, isUnique = true)
    public String name;
    @ColumnSetting(stringLength = 100)
    public String description;
    @ColumnSetting(stringLength = 100)
    public String type;
    @ColumnSetting(stringLength = 100)
    public String status;
    @ColumnSetting(stringLength = 255)
    public String imgUrl;
    public int price;
    public int stock;
}
