package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by EOJIN on 2017-06-29.
 */

public class PurchaseItem {
    private String index;
    private String nameOfPhotographer;
    private String purchaseDate;
    private int reviewFlag;

    public void setIndex(String indexNum){
        index = indexNum;
    }

    public void setNameOfPhotographer(String name){
        nameOfPhotographer=name;
    }

    public void setPurchaseDate(String date){
        purchaseDate = date;
    }

    public void setMakingReview(int flag){
        reviewFlag=flag;
    }


    public String getIndex(){
        return this.index;
    }

    public String getNameOfPhotographer(){
        return this.nameOfPhotographer;
    }

    public String getPurchaseDate(){
        return this.purchaseDate;
    }

    public int getMakingReview(){
        return this.reviewFlag;
    }
}
