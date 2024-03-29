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
     * 如果id=-1，说明是在处理/table/my的请求
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("获取一张桌子的具体信息" + id);

        // 如果id=-1，说明是在处理/table/my的请求
        if (id == -1) {
            this.myTable(req, resp);
            return;
        }

        Table table = tableService.getTableById(id);
        WebUtils.respWrite(resp, table);
    }


    /**
     * 更新一张桌子的具体信息
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the PUT request
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
     * @throws IOException if an input or output error is detected when the servlet handles the DELETE request
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
     * 管理员请求将会直接操作数据库
     * 用户请求将会根据表的状态进行操作
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the PATCH request
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
            // 更新表;
            String status = table.status;
            if (status.equals("空闲")) {
                tableService.releaseTable(id, uid);
            } else if (status.equals("已占用")) {
                tableService.registerTable(id, uid);
            } else {
                throw new RuntimeException("Table status error");
            }
            table = tableService.getTableById(id);
            WebUtils.respWrite(resp, table);
        } else {
            throw new RuntimeException("Permission denied");
        }
    }

    protected void myTable(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Table table = tableService.getTableByUid(uid);
        WebUtils.respWrite(resp, table);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        super.service(req, resp, () -> {
            tableService = new TableService(db);
            String path = req.getPathInfo();
            String[] paths = path.split("/");
            if (paths.length > 1) {
                // 这样在这里添加处理/table/my的逻辑是不行的，因为这里的函数只是在调用具体的方法前执行的一小段代码，最终还是会调用doGet/doPut/doDelete/doPatch
//                if (paths[1].equals("my")) {
//                    try {
//                        this.myTable(req, resp);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return;
//                }
                id = Integer.parseInt(paths[1]);
            }
        });
    }
}
