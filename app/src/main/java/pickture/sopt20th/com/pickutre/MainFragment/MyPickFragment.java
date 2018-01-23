package pickture.sopt20th.com.pickutre.MainFragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.FrameLayout;

import pickture.sopt20th.com.pickutre.MyPickFragment.MyPickPhotographerFragment;
import pickture.sopt20th.com.pickutre.MyPickFragment.MyPickPhotoFragment;
import pickture.sopt20th.com.pickutre.R;

public class MyPickFragment extends Fragment implements View.OnClickListener {

    private Button pg_btn,photo_btn;


    FrameLayout mypick_fragment;

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_my_pick, container, false);

        pg_btn=(Button)view.findViewById(R.id.fm_pg_btn);
        photo_btn=(Button)view.findViewById(R.id.fm_photo_btn);

        mypick_fragment = (FrameLayout) view.findViewById(R.id.mypick_fragment);

        callFragment(FRAGMENT1);


        pg_btn.setOnClickListener(this);
        photo_btn.setOnClickListener(this);


        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fm_pg_btn:

                callFragment(FRAGMENT1);
                break;
            case R.id.fm_photo_btn:
                photo_btn.setTextColor(Color.parseColor("#ffffff"));
                pg_btn.setTextColor(Color.parseColor("#A5A5A5"));
                photo_btn.setBackgroundColor(Color.parseColor("#000000"));
                pg_btn.setBackgroundColor(Color.parseColor("#e9e9e9"));
                callFragment(FRAGMENT2);
                break;
        }
    }

    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction=getFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
                pg_btn.setTextColor(Color.parseColor("#ffffff"));
                photo_btn.setTextColor(Color.parseColor("#A5A5A5"));
                pg_btn.setBackgroundColor(Color.parseColor("#000000"));
                photo_btn.setBackgroundColor(Color.parseColor("#e9e9e9"));
                MyPickPhotographerFragment mypick_pg_fragment = new MyPickPhotographerFragment();
                transaction.replace(R.id.mypick_fragment, mypick_pg_fragment);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                MyPickPhotoFragment mypick_photo_fragment = new MyPickPhotoFragment();
                transaction.replace(R.id.mypick_fragment, mypick_photo_fragment);
                transaction.commit();
                break;
        }

    }

}