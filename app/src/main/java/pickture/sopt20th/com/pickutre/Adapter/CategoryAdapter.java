package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.CategoryResult;
import pickture.sopt20th.com.pickutre.R;
import pickture.sopt20th.com.pickutre.ViewHolder.MyViewHolders;

/**
 * Created by Kyuhee on 2017-06-28.
 */

public class CategoryAdapter extends RecyclerView.Adapter<MyViewHolders>{
    private Context context;
    private ArrayList<CategoryResult.Photographers> itemdatas;
    private View.OnClickListener clickListener;
    private View view;

    public CategoryAdapter(Context context, ArrayList<CategoryResult.Photographers> itemdatas, View.OnClickListener clickListener) {
        this.context = context;
        this.itemdatas = itemdatas;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        MyViewHolders viewHolder = new MyViewHolders(view);             //커스텀 뷰홀더 객체 생성
        view.setOnClickListener(clickListener);                     // 정의된 클릭이벤트를 달아준다
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolders holder, int position) {
        Glide.with(context)
                .load(itemdatas.get(position).profile_url)
                .into(holder.pg_profile);
        holder.pg_id.setText(itemdatas.get(position).id);                                 //제목
        holder.pg_region.setText(itemdatas.get(position).location);                        //내용


        ArrayList<String> images = new ArrayList<String>();
        images = itemdatas.get(position).recent_photos;

        ImageView[] home2CategoryPhoto = new ImageView[3];
        home2CategoryPhoto[0] = holder.pg_photo1;
        home2CategoryPhoto[1] = holder.pg_photo2;
        home2CategoryPhoto[2] = holder.pg_photo3;

        for(int i=0; i<itemdatas.get(position).recent_photos.size();i++){
            Glide.with(view.getContext())
                    .load(itemdatas.get(position).recent_photos.get(i))
                    .into(home2CategoryPhoto[i]);
        }
//        Glide.with(context)
//                .load(itemdatas.get(position).recent_photos.get(0))
//                .into(holder.pg_photo1);
//        Glide.with(context)
//                .load(itemdatas.get(position).recent_photos.get(1))
//                .into(holder.pg_photo2);
//        Glide.with(context)
//                .load(itemdatas.get(position).recent_photos.get(2))
//                .into(holder.pg_photo3);
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}
