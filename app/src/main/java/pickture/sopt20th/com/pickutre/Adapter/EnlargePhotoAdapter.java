package pickture.sopt20th.com.pickutre.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.PhotographerPortfolioResult;
import pickture.sopt20th.com.pickutre.R;

/**
 * Created by ksj on 2017. 7. 3..
 */

public class EnlargePhotoAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private ArrayList<PhotographerPortfolioResult.PicturesData> items;

    public EnlargePhotoAdapter(LayoutInflater inflater, ArrayList<PhotographerPortfolioResult.PicturesData> items) {
        this.inflater = inflater;
        this.items = items;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null;

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
        view = inflater.inflate(R.layout.item_enlarge_photo, null);


        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
        ImageView img= (ImageView)view.findViewById(R.id.iep_imageview);

        //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
        //현재 position에 해당하는 이미지를 setting
        Glide.with(view.getContext())
                .load(items.get(position).url)
                .into(img);

        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size() ;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



}
