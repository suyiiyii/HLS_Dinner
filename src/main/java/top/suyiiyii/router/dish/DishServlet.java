package top.suyiiyii.router.dish;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.dao.DishDAO;
import top.suyiiyii.dao.DishImpl;
import top.suyiiyii.models.Dish;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

@WebServlet("/dish")
public class DishServlet extends BaseHttpServlet {
    DishDAO dishDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.respWrite(resp, dishDAO.getAllDishes());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dish dish = WebUtils.readRequestBody2Obj(req, top.suyiiyii.models.Dish.class);
        dishDAO.addDish(dish);
        dish = dishDAO.getDish(dish.name);
        WebUtils.respWrite(resp, dish);
        /*
        dish 的 name属性是唯一的，所以可以通过name属性来获取dish
        可以只传入name属性来新增数据
         */
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.service(req, resp, () -> {
            dishDAO = new DishImpl(db);
        });
    }
}