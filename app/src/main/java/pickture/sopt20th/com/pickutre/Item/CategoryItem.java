package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by Kyuhee on 2017-06-28.
 */

public class CategoryItem {

    public String pg_profile;   //작가의 프로필사진
    public String pg_id;   //작가의 아이디
    public String pg_region;// 작가의 지역
    public String pg_photo1;//작가의 사진1
    public String pg_photo2;
    public String pg_photo3;

    public CategoryItem(String pg_profile, String pg_id, String pg_region, String pg_photo1, String pg_photo2, String pg_photo3) {
        this.pg_id = pg_id;
        this.pg_profile = pg_profile;
        this.pg_region = pg_region;
        this.pg_photo1 = pg_photo1;
        this.pg_photo2 = pg_photo2;
        this.pg_photo3 = pg_photo3;
    }
}
