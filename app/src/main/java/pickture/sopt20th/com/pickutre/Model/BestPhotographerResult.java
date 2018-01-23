package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 4..
 */

public class BestPhotographerResult {
    public boolean result;
    public ArrayList<BestPhotographerData> photographers;
    public String error;

    public class BestPhotographerData {
        public String profile_url;
        public String id;
        public String rate;
    }
}