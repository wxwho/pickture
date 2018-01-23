package pickture.sopt20th.com.pickutre.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 5..
 */

public class FindModelResult {
    public boolean result;
    public ArrayList<recruitData> recruits;

    public class recruitData implements Serializable {
        public int id;
        public String title;
        public String concept;
        public String modelRequired;
        public String dateHours;
        public String location;
        public String photoPlace;
        public String etc;
        public ArrayList<String> images;
        public int d_day;
        //public String comment;
        //public String timestamp;
        //public String due_date;
        public PhotographerData photographer;
    }

    public class PhotographerData implements Serializable {
        public String id;
        public String location;
        public String profile_url;
    }
}