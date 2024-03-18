/**
 * 用来处理请求和响应的工具类
 * <p>
 * 封装了几个经常要用到的方法，比如读取请求体和写响应体
 *
 * @auther Suyiiyii
 * @date 2023.3.18
 * @version 1.2
 */
package top.suyiiyii;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {

    public static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 把对象转换成json字符串
     *
     * @param object Object
     * @return String
     * @throws IOException IOException
     */
    public static String obj2Json(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    /**
     * 把json字符串转换成对象
     *
     * @param json      String
     * @param valueType Class<T>
     * @param <T>       T
     * @return T
     * @throws IOException IOException
     */

    public static <T> T json2Obj(String json, Class<T> valueType) throws IOException {
        return mapper.readValue(json, valueType);
    }


    /**
     * 读取请求体（json格式），返回字符串
     *
     * @param req HttpServletRequest
     * @return String
     * @throws IOException IOException
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
     * 读取请求体（json格式），返回对象
     *
     * @param req       HttpServletRequest
     * @param valueType Class<T>
     * @param <T>       T
     * @return T
     * @throws IOException IOException
     */
    public static <T> T readRequestBody2Obj(HttpServletRequest req, Class<T> valueType) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String str = stringBuilder.toString();
        return mapper.readValue(str, valueType);
    }

    /**
     * 写响应体（json格式）
     * <p>
     * 把传入的对象转换成json字符串并写入到响应体，设置响应头的Content-Type为application/json
     *
     * @param resp   HttpServletResponse
     * @param object Object
     * @throws IOException IOException
     */

    public static void respWrite(HttpServletResponse resp, Object object) throws IOException {
        // 设置允许跨域的响应头
        resp.setHeader("Access-Control-Allow-Origin", "*"); // 允许任何域名发起请求，也可以指定具体的域名
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS"); // 允许的HTTP方法
        resp.setHeader("Access-Control-Max-Age", "3600"); // 预检请求缓存时间（单位：秒）
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // 允许自定义请求头

        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();

        String jsonStr = mapper.writeValueAsString(object);
        pw.write(jsonStr);
        pw.flush();
    }
}
