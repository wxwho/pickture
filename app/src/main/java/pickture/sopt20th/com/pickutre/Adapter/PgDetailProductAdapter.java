package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Item.PgDetailProductItem;
import pickture.sopt20th.com.pickutre.Model.PhotographerDetailResult;
import pickture.sopt20th.com.pickutre.R;

/**
 * Created by Kyuhee on 2017-06-30.
 */

public class PgDetailProductAdapter extends BaseAdapter {
    private ArrayList<PgDetailProductItem> itemdata=new ArrayList<PgDetailProductItem>();
    private ArrayList<PhotographerDetailResult.ProductItems> item;
//    private static final int TYPE_HEADER = 0;
//    private static final int TYPE_ITEM = 1;
//    private static final int TYPE_FOOTER = 2;

    public PgDetailProductAdapter() {        //생성자 정의
    }

    public PgDetailProductAdapter(ArrayList<PhotographerDetailResult.ProductItems> item) {        //생성자 정의
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos=position;
        final Context context=parent.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);         //xml에 정의된 내용이 객체와 되는 것!!!
            view = inflater.inflate(R.layout.item_pgdetail_product, parent, false);                                      //어떠한 xml을 객체화 할지를 정해줍니다!!
        }

        TextView product_name = (TextView)view.findViewById(R.id.ipp_productname);
        TextView product_price = (TextView)view.findViewById(R.id.ipp_productprice);


        product_name.setText(item.get(position).item_name);
        product_price.setText(item.get(position).price + "won");

        return view;
    }

    public void addItem(String productname,String productprice) {
        PgDetailProductItem item = new PgDetailProductItem();

        item.setName(productname);
        item.setPrice(productprice);

        itemdata.add(item);
    }
}
