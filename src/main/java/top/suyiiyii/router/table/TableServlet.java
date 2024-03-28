package top.suyiiyii.router.table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.suyiiyii.models.Table;
import top.suyiiyii.service.TableService;
import top.suyiiyii.su.WebUtils;
import top.suyiiyii.su.servlet.BaseHttpServlet;

import java.io.IOException;
import java.util.List;

/**
 * Table Servlet
 *
 * @author suyiiyii
 */
@WebServlet("/table")
public class TableServlet extends BaseHttpServlet {
    TableService tableService;

    /**
     * 获取所有桌子的信息
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Table> tables = tableService.getTables();
        WebUtils.respWrite(resp, tables);
    }

    /**
     * 创建一张桌子
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!role.equals("admin")) {
            req.setAttribute("statusCode", 403);
            throw new RuntimeException("Permission denied");
        }
        Table table = WebUtils.readRequestBody2Obj(req, Table.class);
        // 检查参数
        if (table.name == null || table.description == null) {
            throw new RuntimeException("name or description is null");
        }
        tableService.createTable(table.name, table.description);
        table = tableService.getTableByName(table.name);
        WebUtils.respWrite(resp, table);
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.service(req, resp);
        tableService = new TableService(db);
    }
}

