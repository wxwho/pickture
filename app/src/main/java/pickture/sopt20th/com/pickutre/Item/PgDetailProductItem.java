package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by Kyuhee on 2017-06-30.
 */

public class PgDetailProductItem {
     private String product_name;
     private String product_price;

    public void setName(String product_name){
        this.product_name=product_name;
    }
    public void setPrice(String product_price){
        this.product_price=product_price;
    }
    public String getProductName(){
        return product_name;
    }
    public String getProductPrice(){
        return product_price;
    }
}
