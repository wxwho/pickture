package pickture.sopt20th.com.pickutre.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 7..
 */

public class PurchaseListResult {
    public boolean result;
    public ArrayList<purchaseData> pay_history;

    public class purchaseData implements Serializable {
        public int id;
        public String model_id;
        public String category;
        public int item;
        public String date;
        public int cntPeople;
        public String placePrefer;
        public String detailComment;
        public String status;
        public String photoResponse;
        public String timestamp;
        public String pay_at;
        public String type;
        public photographerData photographer;
        public boolean review;

        public class photographerData {
            public String id;
            public String profile_url;
        }
    }
}