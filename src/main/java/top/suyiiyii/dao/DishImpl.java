package top.suyiiyii.dao;

import top.suyiiyii.models.Dish;
import top.suyiiyii.su.orm.core.Session;

import java.sql.SQLException;
import java.util.List;

/**
 * 菜品数据访问DAO
 * 由于只有管理员可以操作菜品，所以不需要对菜品进行权限控制
 * 所以懒得写Service了
 * servlet直接调用DAO
 * //PROBLEM
 */
public class DishImpl extends BaseImpl implements DishDAO {
    public DishImpl(Session db) {
        super(db);
    }

    @Override
    public void addDish(Dish dish) {
        try {
            db.insert(dish);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDish(int id) {
        try {
            db.delete(Dish.class).eq("id", id).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDish(Dish dish) {
        try {
            db.update(dish);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish getDish(int id) {
        try {
            return db.query(Dish.class).eq("id", id).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish getDish(String name) {
        try {
            return db.query(Dish.class).eq("name", name).first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> getAllDishes() {
        try {
            return db.query(Dish.class).all();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
