package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Activity.MakeReviewActivity;
import pickture.sopt20th.com.pickutre.Model.PurchaseListResult;
import pickture.sopt20th.com.pickutre.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by EOJIN on 2017-06-29.
 */

public class PurchaseAdapter extends BaseAdapter {
    private ArrayList<PurchaseListResult.purchaseData> items;
    private PurchaseListResult.purchaseData item;

    private TextView indexTextView;
    private TextView photographerNameTextView;
    private TextView dateTextView;
    private Button reviewBtn;

    public PurchaseAdapter(ArrayList<PurchaseListResult.purchaseData> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_purchase, parent, false);
        }

        TextView indexTextView = (TextView) convertView.findViewById(R.id.ip_index);
        TextView photographerNameTextView = (TextView) convertView.findViewById(R.id.ip_phtographer_name);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.ip_date);
        Button reviewBtn = (Button) convertView.findViewById(R.id.ip_review_img);

        item = items.get(position);

        indexTextView.setText("0" + (position + 1));
        photographerNameTextView.setText(item.photographer.id);
        dateTextView.setText(item.pay_at.substring(0, 10));

        // item.review가 true면 이미 리뷰가 작성됨
        if(item.review) {
            reviewBtn.setBackgroundResource(R.drawable.review_gray);
            reviewBtn.setTag("gray");
        }else{
            reviewBtn.setBackgroundResource(R.drawable.review_yellow);
            reviewBtn.setTag("yellow");
        }

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString() == "yellow") {
                    Intent intent = new Intent(getApplicationContext(), MakeReviewActivity.class);
                    intent.putExtra("request_id", items.get(position).id);
                    intent.putExtra("photographer_id", items.get(position).photographer.id);
                    intent.putExtra("profile_url", items.get(position).photographer.profile_url);
                    context.startActivity(intent);
                }
            }
        });



        return  convertView;
    }

}