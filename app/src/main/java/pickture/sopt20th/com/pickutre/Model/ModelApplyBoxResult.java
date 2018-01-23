package pickture.sopt20th.com.pickutre.Model;

import java.util.ArrayList;

/**
 * Created by EOJIN on 2017-07-07.
 */

public class ModelApplyBoxResult {
    public boolean result;
    public ArrayList<applyData> applies;

    public class applyData{
        public PhotographerId photographer_id;
        public String status;
    }

    public class PhotographerId{
        public String photographer_id;
    }
}