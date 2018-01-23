package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.PhotographerPortfolioResult;
import pickture.sopt20th.com.pickutre.R;
/**
 * Created by ksj on 2017. 6. 28..
 */


public class PhotographerPortfoilioGridAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<PhotographerPortfolioResult.PicturesData> item;
    LayoutInflater inf;

    public PhotographerPortfoilioGridAdapter(Context context, int layout, ArrayList<PhotographerPortfolioResult.PicturesData> item) {
        this.context = context;
        this.layout = layout;
        this.item = item;
        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.igi_imageView);
        Glide.with(context)
                .load(item.get(position).url)
                .into(iv);


        return convertView;
    }
}