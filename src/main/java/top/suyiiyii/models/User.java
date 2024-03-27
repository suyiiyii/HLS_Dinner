package top.suyiiyii.models;

import top.suyiiyii.su.orm.annotation.ColumnSetting;
import top.suyiiyii.su.orm.annotation.TableRegister;

/**
 * user表模型
 * 用于存储用户信息
 *
 * @author suyiiyii
 */
@TableRegister("user")
public class User {
    @ColumnSetting(isPrimaryKey = true, isAutoIncrement = true)
    public int id;
    @ColumnSetting(stringLength = 100)
    public String username;
    @ColumnSetting(stringLength = 255)
    public String password;
    @ColumnSetting(stringLength = 20)
    public String role;
}
