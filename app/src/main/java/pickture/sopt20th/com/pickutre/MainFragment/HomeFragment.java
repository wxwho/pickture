package pickture.sopt20th.com.pickutre.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import pickture.sopt20th.com.pickutre.Activity.CategoryActivity;
import pickture.sopt20th.com.pickutre.Adapter.EventBannerAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.BestPhotographerResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static int BEST_PHOTOGRAHPER_NUMBER = 4;
    private static ViewPager viewPager;
    CircleIndicator indicator;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private NetworkService service;
    private ImageView[] bestPgImageView;
    Timer swipeTimer;

    private Button btnDate;
    private Button btnFriend;
    private Button btnWedding;
    private Button btnBaby;
    private Button btnProfile;
    private Button btnEtc;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        swipeTimer.cancel();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);

        service = ApplicationController.getInstance().getNetworkService();
        Call<BestPhotographerResult> bestPhotographerResultCall = service.getBestPhotographerResult();
        bestPhotographerResultCall.enqueue(new Callback<BestPhotographerResult>() {
            @Override
            public void onResponse(Call<BestPhotographerResult> call, Response<BestPhotographerResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        for(int i = 0; i < BEST_PHOTOGRAHPER_NUMBER; i++) {
                            Glide.with(getActivity())
                                    .load(response.body().photographers.get(i).profile_url)
                                    .into(bestPgImageView[i]);
                        }
                    }
                    else {
                        ApplicationController.showToast(getActivity(), "통신실패");
                    }
                }
            }

            @Override
            public void onFailure(Call<BestPhotographerResult> call, Throwable t) {
                ApplicationController.showToast(getActivity(), "통신실패");
            }
        });



        viewPager = (ViewPager) view.findViewById(R.id.fh_pager);
        indicator = (CircleIndicator) view.findViewById(R.id.fh_indicator);
        bestPgImageView = new ImageView[BEST_PHOTOGRAHPER_NUMBER];
        bestPgImageView[0] = (ImageView) view.findViewById(R.id.fh_imageview_best1);
        bestPgImageView[1] = (ImageView) view.findViewById(R.id.fh_imageview_best2);
        bestPgImageView[2] = (ImageView) view.findViewById(R.id.fh_imageview_best3);
        bestPgImageView[3] = (ImageView) view.findViewById(R.id.fh_imageview_best4);



        PagerAdapter adapter = new EventBannerAdapter(getActivity());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);



        NUM_PAGES =adapter.getCount();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                currentPage = viewPager.getCurrentItem();
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = -1;
                }
                viewPager.setCurrentItem(++currentPage, true);
            }
        };

       swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);

        btnDate = (Button) view.findViewById(R.id.fh_btn_date);
        btnFriend = (Button) view.findViewById(R.id.fh_btn_friend);
        btnWedding = (Button) view.findViewById(R.id.fh_btn_wedding);
        btnBaby = (Button) view.findViewById(R.id.fh_btn_baby);
        btnProfile = (Button) view.findViewById(R.id.fh_btn_profile);
        btnEtc = (Button) view.findViewById(R.id.fh_btn_etc);

        btnDate.setOnClickListener(this);
        btnFriend.setOnClickListener(this);
        btnWedding.setOnClickListener(this);
        btnBaby.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnEtc.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String category;
        Intent intent;
        switch (v.getId()) {
            case R.id.fh_btn_date:
                category = btnDate.getText().toString().toLowerCase();
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
            case R.id.fh_btn_friend:
                category = btnFriend.getText().toString().toLowerCase();
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
            case R.id.fh_btn_wedding:
                category = btnWedding.getText().toString().toLowerCase();
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
            case R.id.fh_btn_baby:
                category = btnBaby.getText().toString().toLowerCase();
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
            case R.id.fh_btn_profile:
                category = btnProfile.getText().toString().toLowerCase();
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
            case R.id.fh_btn_etc:
                category = btnEtc.getText().toString().toLowerCase();
                intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
            default:
                category = "none";
                break;
        }

    }
}
