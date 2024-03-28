package top.suyiiyii.router.table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.suyiiyii.models.Table;
import top.suyiiyii.service.TableService;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;

@WebServlet("/table/*")
public class TableIDServlet extends BaseHttpServlet {
    TableService tableService;
    Log logger = LogFactory.getLog(TableIDServlet.class);
    int id = -1;

    /**
     * 获取一张桌子的具体信息
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("获取一张桌子的具体信息" + id);
        Table table = tableService.getTableById(id);
        WebUtils.respWrite(resp, table);
    }


    /**
     * 更新一张桌子的具体信息
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!role.equals("admin")) {
            req.setAttribute("statusCode", 403);
            throw new RuntimeException("Permission denied");
        }
        Table table = WebUtils.readRequestBody2Obj(req, Table.class);
        table.id = id;
        tableService.updateTable(table);
        table = tableService.getTableById(id);
        WebUtils.respWrite(resp, table);
    }

    /**
     * 删除一张桌子
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!role.equals("admin")) {
            req.setAttribute("statusCode", 403);
            throw new RuntimeException("Permission denied");
        }
        tableService.deleteTable(id);
        WebUtils.respWrite(resp, "Delete success");
    }


    /**
     * 更新一张桌子
     * 直接操作数据库，只有管理员可以操作
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws IOException
     */
    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Table table = WebUtils.readRequestBody2Obj(req, Table.class);
        table.id = id;
        if (role.equals("admin")) {
            // 更新表
            tableService.updateTable(table);
            WebUtils.respWrite(resp, table);

        } else if (role.equals("user")) {

        } else {
            throw new RuntimeException("Permission denied");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        tableService = new TableService(db);
        String path = req.getPathInfo();
        String[] paths = path.split("/");
        if (paths.length > 1) {
            id = Integer.parseInt(paths[1]);
        }
        super.service(req, resp);
    }
}
