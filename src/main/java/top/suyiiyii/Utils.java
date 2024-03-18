/**
 * 用来处理请求和响应的工具类
 * <p>
 * 封装了几个经常要用到的方法，比如读取请求体和写响应体
 *
 * @auther Suyiiyii
 * @date 2023.3.17
 */
package top.suyiiyii;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
    /**
     * 读取请求体（json格式），返回字符串
     *
     * @param req
     * @return String
     * @throws IOException
     */
    public static String readRequestBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    /**
     * 写响应体（json格式）
     * <p>
     * 把json字符串写入响应体的body中，并设置响应头的Content-Type为application/json
     *
     * @param resp
     * @param json
     * @throws IOException
     */

    public static void respWrite(HttpServletResponse resp, String json) throws IOException {
        // 设置允许跨域的响应头
        resp.setHeader("Access-Control-Allow-Origin", "*"); // 允许任何域名发起请求，也可以指定具体的域名
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS"); // 允许的HTTP方法
        resp.setHeader("Access-Control-Max-Age", "3600"); // 预检请求缓存时间（单位：秒）
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // 允许自定义请求头

        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        pw.write(json);
        pw.flush();
    }
}
