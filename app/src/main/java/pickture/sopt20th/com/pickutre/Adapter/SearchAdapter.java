package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.SearchResult;
import pickture.sopt20th.com.pickutre.R;
import pickture.sopt20th.com.pickutre.ViewHolder.MyViewHolders;

/**
 * Created by Kyuhee on 2017-06-28.
 */

public class SearchAdapter extends RecyclerView.Adapter<MyViewHolders>{
    private Context context;
    ArrayList<SearchResult.SearchData> itemdatas;
    ArrayList<SearchResult.SearchData> filterdatas;
    View.OnClickListener clickListener;

    public SearchAdapter(Context context,ArrayList<SearchResult.SearchData> itemdatas, View.OnClickListener clickListener) {
        this.context=context;
        this.itemdatas = itemdatas;
        this.clickListener = clickListener;
        this.filterdatas = new ArrayList<SearchResult.SearchData>();
        this.filterdatas.addAll(itemdatas);
    }
    @Override
    public MyViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        MyViewHolders viewHolder = new MyViewHolders(view);
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolders holder, int position) {
        Glide.with(context)
                .load(itemdatas.get(position).profile_url)
                .into(holder.item_profile);
        holder.item_id.setText(itemdatas.get(position).id);
        holder.item_category.setText(itemdatas.get(position).hashtag);
        holder.item_region.setText(itemdatas.get(position).location);
        if(itemdatas.get(position).picked){
            holder.item_mypickcheck.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }

    public void filter(String text) {
        text=text.toLowerCase();
        itemdatas.clear();
        if (text.length() == 0) {
            //itemdatas.addAll(filterdatas);
        } else {
            for (SearchResult.SearchData item : filterdatas) {
                if (item.id.toLowerCase().contains(text)) {
                    itemdatas.add(item);
                }
                if(item.location.toLowerCase().contains(text)){
                    itemdatas.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}