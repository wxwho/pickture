package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 4..
 */

public class PhotographerReviewResult {
    public boolean result;
    public ArrayList<ReviewData> reviews;
    public String error;

    public class ReviewData {
        public int id;
        public int request_id;
        public String photographer_id;
        public String user_id;
        public int rate;
        public String timestamp;
        public String reviewContent;
    }
}
