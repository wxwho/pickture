package pickture.sopt20th.com.pickutre.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import pickture.sopt20th.com.pickutre.R;

/**
 * Created by Kyuhee on 2017-06-29.
 */

public class MyViewHolders extends RecyclerView.ViewHolder  {

    //category_viewHolder
    public ImageView pg_profile;
    public TextView pg_id;
    public TextView pg_region;
    public ImageView pg_photo1;
    public ImageView pg_photo2;
    public ImageView pg_photo3;

    //search_ViewHolder
    public ImageView item_profile;
    public TextView item_id;
    public TextView item_category;
    public TextView item_region;
    public ImageView item_mypickcheck;

    //mypick_viewHolder
    //public ImageView pick_profile;
    //public TextView pick_id;
    //public TextView pick_category;

    //pgreview_viewHolder
    public TextView review_id;
    public TextView review_date;
    public RatingBar review_star;
    public TextView review;

    //findmodel_viewHolder
    public ImageView findmodel_profile;
    public TextView findmodel_id;
    public TextView findmodel_text;
    public TextView findmodel_region;
    public TextView findmodel_dday;
    public ImageView findmodel_photo1;
    public ImageView findmodel_photo2;
    public ImageView findmodel_photo3;
    public ImageView findmodel_photo4;
    public ImageView findmodel_photo5;

    public MyViewHolders(View itemView) {
        super(itemView);

        //category_viewHolder
        pg_profile = (ImageView)itemView.findViewById(R.id.ci_profile);
        pg_id = (TextView)itemView.findViewById(R.id.ci_id);
        pg_region = (TextView)itemView.findViewById(R.id.ci_region);
        pg_photo1 = (ImageView) itemView.findViewById(R.id.ci_photo1);
        pg_photo2 = (ImageView) itemView.findViewById(R.id.ci_photo2);
        pg_photo3 = (ImageView) itemView.findViewById(R.id.ci_photo3);

        //search_viewHolder
        item_profile=(ImageView)itemView.findViewById(R.id.si_profile);
        item_id = (TextView)itemView.findViewById(R.id.si_id);
        item_category = (TextView)itemView.findViewById(R.id.si_category);
        item_region = (TextView)itemView.findViewById(R.id.si_region);
        item_mypickcheck=(ImageView) itemView.findViewById(R.id.si_mypickcheck);

        //mypick_viewHolder
        //pick_profile=(ImageView)itemView.findViewById(R.id.impg_profile);
        //pick_id = (TextView)itemView.findViewById(R.id.impg_id);
        //pick_category = (TextView)itemView.findViewById(R.id.impg_category);

        //pgreview_viewHolder
        review_id=(TextView)itemView.findViewById(R.id.ip_id);
        review_date=(TextView)itemView.findViewById(R.id.ip_date);
        review_star=(RatingBar)itemView.findViewById(R.id.ip_ratingbar);
        review=(TextView)itemView.findViewById(R.id.ip_review);

        // findmodel_viewHolder
        findmodel_profile=(ImageView)itemView.findViewById(R.id.if_profile);
        findmodel_id=(TextView)itemView.findViewById(R.id.if_id);
        findmodel_text=(TextView)itemView.findViewById(R.id.if_text);
        findmodel_region=(TextView)itemView.findViewById(R.id.if_region);
        findmodel_dday=(TextView)itemView.findViewById(R.id.if_dday);
        findmodel_photo1=(ImageView) itemView.findViewById(R.id.if_photo1);
        findmodel_photo2=(ImageView) itemView.findViewById(R.id.if_photo2);
        findmodel_photo3=(ImageView) itemView.findViewById(R.id.if_photo3);
        findmodel_photo4=(ImageView) itemView.findViewById(R.id.if_photo4);
        findmodel_photo5=(ImageView) itemView.findViewById(R.id.if_photo5);
    }
}
