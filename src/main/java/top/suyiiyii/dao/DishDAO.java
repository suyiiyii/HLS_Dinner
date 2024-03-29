package top.suyiiyii.dao;

import top.suyiiyii.models.Dish;

import java.util.List;

/**
 * 菜品数据访问接口
 *
 * @author suyiiyii
 */
public interface DishDAO extends BaseDAO{
    public void addDish(Dish dish);

    public void deleteDish(int id);

    public void updateDish(Dish dish);

    public Dish getDish(int id);

    public List<Dish> getAllDishes();
//    public List<Dish> getDishesByType(String type);
//    public List<Dish> getDishesByStatus(String status);
//    public List<Dish> getDishesByPrice(int min, int max);
//    public List<Dish> getDishesByStock(int min, int max);
//    public List<Dish> getDishesByKeyword(String keyword);
}
