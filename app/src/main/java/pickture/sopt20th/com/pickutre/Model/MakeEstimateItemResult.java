package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 7..
 */

public class MakeEstimateItemResult {
    public boolean result;
    public ArrayList<itemData> items;

    public class itemData {
        public int id;
        public String item_name;
        public int price;
    }
}