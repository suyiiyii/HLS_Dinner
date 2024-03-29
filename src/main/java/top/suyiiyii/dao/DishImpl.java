package top.suyiiyii.dao;

import top.suyiiyii.models.Dish;
import top.suyiiyii.su.orm.core.Session;

import java.sql.SQLException;
import java.util.List;

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
    public List<Dish> getAllDishes() {
        try {
            return db.query(Dish.class).all();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
