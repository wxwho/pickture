package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 6..
 */

public class SearchResult {
    public boolean result;
    public ArrayList<SearchData> photographers;

    public class SearchData {
        public String id;
        public String location;
        public String profile_url;
        public String hashtag;
        public boolean picked;
    }
}
