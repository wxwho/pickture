package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by Kyuhee on 2017-06-28.
 */

public class SearchItem {
    public int item_profile;
    public String item_id;
    public String item_category;
    public String item_region;
    public int item_mypickcheck;

    public SearchItem(int item_profile, String item_id, String item_category, String item_region,int item_mypickcheck) {
        this.item_profile = item_profile;
        this.item_id=item_id;
        this.item_category=item_category;
        this.item_region=item_region;
        this.item_mypickcheck=item_mypickcheck;
    }

}
