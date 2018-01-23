package pickture.sopt20th.com.pickutre.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 3..
 */

public class PhotographerPortfolioResult {
    public boolean result;
    public ArrayList<PicturesData> pictures;

    @SuppressWarnings("serial")
    public class PicturesData implements Serializable {
        public int id;
        public String url;
        public boolean picked;
    }
}
