package pickture.sopt20th.com.pickutre.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.PhotographerReviewResult;
import pickture.sopt20th.com.pickutre.ViewHolder.MyViewHolders;
import pickture.sopt20th.com.pickutre.R;

/**
 * Created by Kyuhee on 2017-06-30.
 */

public class PgReviewAdapter extends RecyclerView.Adapter<MyViewHolders> {

    ArrayList<PhotographerReviewResult.ReviewData> item;

    public PgReviewAdapter(){

    }

    public PgReviewAdapter(ArrayList<PhotographerReviewResult.ReviewData> item){
        this.item = item;
    }

    @Override
    public MyViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pgreview,parent,false);
        MyViewHolders viewHolder = new MyViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolders holder, int position) {
        holder.review_id.setText(item.get(position).user_id);
        holder.review_date.setText(item.get(position).timestamp.substring(0,10));
        holder.review_star.setRating(item.get(position).rate);
        holder.review.setText(item.get(position).reviewContent);
    }

    @Override
    public int getItemCount() {
        return item != null ? item.size() : 0;
    }
}
