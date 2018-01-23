package pickture.sopt20th.com.pickutre.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pickture.sopt20th.com.pickutre.R;

/**
 * Created by Kyuhee on 2017-07-02.
 */

public class EventBannerAdapter extends PagerAdapter {

    int[] imageId = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3};
    private Context context;
    private View viewItem;
    private LayoutInflater inflater;

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public EventBannerAdapter(Context context){
        this.context=context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = ((Activity) context).getLayoutInflater();
        viewItem = inflater.inflate(R.layout.event_banner, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.event_image);
        imageView.setImageResource(imageId[position]);
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}
