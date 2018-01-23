package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by Kyuhee on 2017-07-01.
 */

public class FindModelItem {

    public int findmodel_profile;   //작가의 프로필사진
    public String findmodel_id;   //작가의 아이디
    public String findmodel_text;
    public String findmodel_region;// 작가의 지역
    public String findmodel_dday;
    public int findmodel_photo1;//작가의 사진1
    public int findmodel_photo2;
    public int findmodel_photo3;
    public int findmodel_photo4;
    public int findmodel_photo5;


    public FindModelItem(int findmodel_profile, String findmodel_id, String findmodel_text, String findmodel_region, String findmodel_dday,
                         int findmodel_photo1, int findmodel_photo2, int findmodel_photo3, int findmodel_photo4, int findmodel_photo5 ){
        this.findmodel_profile=findmodel_profile;
        this.findmodel_id=findmodel_id;
        this.findmodel_text=findmodel_text;
        this.findmodel_region=findmodel_region;
        this.findmodel_dday=findmodel_dday;
        this.findmodel_photo1=findmodel_photo1;
        this.findmodel_photo2=findmodel_photo2;
        this.findmodel_photo3=findmodel_photo3;
        this.findmodel_photo4=findmodel_photo4;
        this.findmodel_photo5=findmodel_photo5;
    }
}
