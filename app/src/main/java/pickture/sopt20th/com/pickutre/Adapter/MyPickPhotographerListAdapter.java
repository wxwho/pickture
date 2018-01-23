package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerResult;
import pickture.sopt20th.com.pickutre.R;

/**
 * Created by ksj on 2017. 7. 2..
 */

public class MyPickPhotographerListAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<MyPickPhotographerResult.PhotographerData> item;

    // ListViewAdapter의 생성자
    public MyPickPhotographerListAdapter() {

    }

    public MyPickPhotographerListAdapter(ArrayList<MyPickPhotographerResult.PhotographerData> item) {
        this.item = item;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return item.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_mypick_photographer_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        CircularImageView profileImageView = (CircularImageView) convertView.findViewById(R.id.impg_profile) ;
        TextView idTextView = (TextView) convertView.findViewById(R.id.impg_id) ;
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.impg_category) ;

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context)
                .load(item.get(position).profile_url)
                .into(profileImageView);
        idTextView.setText(item.get(position).id);
        categoryTextView.setText(item.get(position).hashtag);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return item.get(position) ;
    }

}