package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 4..
 */
public class MyPickPhotographerResult {
    public boolean result;
    public ArrayList<PhotographerData> photographers;
    public String error;

    public class PhotographerData {
        public String id;
        public String profile_url;
        public String location;
        public String hashtag;
        public boolean picked;
    }
}