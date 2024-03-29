package top.suyiiyii.models;

import top.suyiiyii.su.orm.annotation.ColumnSetting;
import top.suyiiyii.su.orm.annotation.TableRegister;

/**
 * Table 模型
 *
 * @author suyiiyii
 */
@TableRegister("table")
public class Table {
    @ColumnSetting(isPrimaryKey = true, isAutoIncrement = true)
    public int id;
    @ColumnSetting(isUnique = true, stringLength = 100, isNotNull = true)
    public String name;
    @ColumnSetting(stringLength = 255)
    public String description;
    @ColumnSetting(stringLength = 20)
    public String status;
    public int registerTime;
    public int uid;
    @ColumnSetting(stringLength = 255)
    public String imgUrl;
}
