package top.suyiiyii.schemas;

import java.util.ArrayList;
import java.util.List;

public class OrderResp {
    public int order_id;
    public List<Dish> dish = new ArrayList<>();
    public long created_at;

    public static class Dish {
        public int dish_id;
        public String dish_name;
        public int dish_price;
        public int dish_num;
    }
}