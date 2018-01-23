package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by Kyuhee on 2017-06-30.
 */

public class PgReviewItem {

    public String review_id;
    public String review_date;
    public float review_star;
    public String review;

    public PgReviewItem(String review_id, String review_date, float review_star, String review){
        this.review_id=review_id;
        this.review_date=review_date;
        this.review_star=review_star;
        this.review=review;
    }
}
