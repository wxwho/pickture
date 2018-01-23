package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 3..
 */

public class CategoryResult {

    public boolean result;
    public ArrayList<Photographers> photographers;

    public class Photographers {
        public String id;
        public String profile_url;
        public String location;
        public ArrayList<String> recent_photos;
        public boolean picked;
        public String hashtag;
    }
}