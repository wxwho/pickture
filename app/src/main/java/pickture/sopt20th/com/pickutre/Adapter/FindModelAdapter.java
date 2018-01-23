package pickture.sopt20th.com.pickutre.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.FindModelResult;
import pickture.sopt20th.com.pickutre.R;
import pickture.sopt20th.com.pickutre.ViewHolder.MyViewHolders;

/**
 * Created by Kyuhee on 2017-07-01.
 */

public class FindModelAdapter extends RecyclerView.Adapter<MyViewHolders>{
    ArrayList<FindModelResult.recruitData> item;
    View.OnClickListener clickListener;
    View view;


    public FindModelAdapter(ArrayList<FindModelResult.recruitData> item, View.OnClickListener clickListener) {
        this.item = item;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_findmodel,parent,false);
        MyViewHolders viewHolder = new MyViewHolders(view);             //커스텀 뷰홀더 객체 생성
        view.setOnClickListener(clickListener);                     // 정의된 클릭이벤트를 달아준다
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolders holder, int position) {
        Glide.with(view.getContext())
                .load(item.get(position).photographer.profile_url)
                .into(holder.findmodel_profile);
        holder.findmodel_id.setText(item.get(position).photographer.id);
        holder.findmodel_text.setText(item.get(position).title);
        holder.findmodel_region.setText(item.get(position).photographer.location);
        holder.findmodel_dday.setText("D - " + item.get(position).d_day);

        ArrayList<String> images = new ArrayList<String>();
        images = item.get(position).images;

        ImageView[] findmodelPhoto = new ImageView[5];
        findmodelPhoto[0] = holder.findmodel_photo1;
        findmodelPhoto[1] = holder.findmodel_photo2;
        findmodelPhoto[2] = holder.findmodel_photo3;
        findmodelPhoto[3] = holder.findmodel_photo4;
        findmodelPhoto[4] = holder.findmodel_photo5;

        for(int i=0; i<item.get(position).images.size();i++){
            Glide.with(view.getContext())
                    .load(item.get(position).images.get(i))
                    .centerCrop()
                    .into(findmodelPhoto[i]);
        }




    }

    @Override
    public int getItemCount() {
        return item != null ? item.size() : 0;
    }
}
