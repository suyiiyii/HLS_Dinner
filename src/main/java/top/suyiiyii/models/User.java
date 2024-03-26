package top.suyiiyii.models;

import top.suyiiyii.Su.orm.annotation.ColumnSetting;
import top.suyiiyii.Su.orm.annotation.TableRegister;

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
