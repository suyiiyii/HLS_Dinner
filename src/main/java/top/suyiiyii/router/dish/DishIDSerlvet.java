package top.suyiiyii.router.dish;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.dao.DishDAO;
import top.suyiiyii.dao.DishImpl;
import top.suyiiyii.models.Dish;
import top.suyiiyii.su.UniversalUtils;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

/**
 * 对单个菜品的操作
 * 仅管理员可操作
 *
 * @author suyiiyii
 */
@WebServlet("/dish/*")
public class DishIDSerlvet extends BaseHttpServlet {
    DishDAO dishDAO;
    int id = -1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.respWrite(resp, dishDAO.getDish(id));
    }

    /**
     * 部分更新菜品信息
     */
    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 先获取原来的菜品信息
        Dish oldDish = dishDAO.getDish(id);
        Dish newDish = WebUtils.readRequestBody2Obj(req, Dish.class);
        newDish.id = id;
        // 用新菜品信息来更新原来的菜品信息
        UniversalUtils.updateObj(oldDish, newDish);
        dishDAO.updateDish(oldDish);
        WebUtils.respWrite(resp, oldDish);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dishDAO.deleteDish(id);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.service(req, resp, () -> {
            dishDAO = new DishImpl(db);
            String path = req.getPathInfo();
            String[] paths = path.split("/");
            if (paths.length > 1) {
                id = Integer.parseInt(paths[1]);
            }
        });
    }
}
