package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 4..
 */

public class PhotographerDetailResult {
    public boolean result;
    public String brand;
    public String ceo;
    public String address;

    public ArrayList<ProductItems> items;

    public class ProductItems {
        public String item_name;
        public int price;
    }
}