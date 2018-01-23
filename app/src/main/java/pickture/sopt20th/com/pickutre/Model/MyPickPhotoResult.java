package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 4..
 */

public class MyPickPhotoResult {
    public boolean result;
    public ArrayList<PhotoData> photos;
    public String error;

    public class PhotoData {
        public int photo_id;
        public String url;
        public PhotographerData photographer;
    }
    public class PhotographerData {
        public String id;
        public String profile_url;
    }
}