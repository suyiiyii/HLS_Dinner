package top.suyiiyii.schemas;

import java.util.ArrayList;
import java.util.List;

public class OrderResp {
    public int order_id;
    public int table_id;
    public int uid;
    public String status;
    public List<Dish> dish = new ArrayList<>();
    public int total_price;
    public long created_at;

    public static class Dish {
        public int dish_id;
        public String dish_name;
        public int dish_price;
        public int dish_num;
        public int dish_total_price;
    }
}