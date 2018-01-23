package pickture.sopt20th.com.pickutre.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pickture.sopt20th.com.pickutre.R;

/**
 * Created by pc on 2017-06-02.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public static TextView header_introduction;    //static 변수로 선언 (메인 부분에서 접근하기 위해)
    public static TextView header_introduction_text;
    public static TextView header_product;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        header_introduction = (TextView) itemView.findViewById(R.id.v_introduction);
        header_introduction_text=(TextView)itemView.findViewById(R.id.v_introduction_brand);
        header_product = (TextView) itemView.findViewById(R.id.header_product);
    }
}