package pickture.sopt20th.com.pickutre.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pickture.sopt20th.com.pickutre.R;

/**
 * Created by pc on 2017-06-02.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    TextView product_text;
    TextView howtouse;
    ImageView image;

    public FooterViewHolder(View itemView) {
        super(itemView);
      product_text=(TextView)itemView.findViewById(R.id.footer_text);
        howtouse=(TextView)itemView.findViewById(R.id.footer_howtouse);
        image=(ImageView)itemView.findViewById(R.id.footer_image);
    }
}