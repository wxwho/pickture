package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerDelResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerPickData;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerPickResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.PhotographerInfoFragment.PhotographerTabPagerAdapter;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotographerInfoActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button sendButton;
    private ImageButton pickButton;
    private int pickFlag =0; //pick 여부에 따라 버튼 이미지를 바꿔주기 위한 변수
    private TextView photographerId;
    private TextView photographerLocation;
    private CircularImageView imageView;
    private TextView photographerHashtag;


    private NetworkService service;

    private String pgId;
    private String pgProfileUrl;
    private String location;
    private boolean picked;
    private String hashtag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_info);

        // get intent
        Intent intent = getIntent();
        pgId = intent.getStringExtra("pgId");
        pgProfileUrl = intent.getStringExtra("pgProfileUrl");
        location = intent.getStringExtra("location");
        picked = intent.getBooleanExtra("picked", false);
        hashtag = intent.getStringExtra("hashtag");


        // 초기화
        pickButton = (ImageButton) findViewById(R.id.api_pick_btn);
        sendButton = (Button)findViewById(R.id.api_send_btn);
        photographerId = (TextView) findViewById(R.id.api_photographer_name);
        photographerLocation = (TextView) findViewById(R.id.api_photographer_area);
        photographerHashtag = (TextView) findViewById(R.id.api_photographer_hashtag);
        pickFlag = picked ? 1 : 0;
        if(picked) pickButton.setBackgroundResource(R.drawable.pick_yellow);

        // 네트워크초기화
        service = ApplicationController.getInstance().getNetworkService();

        // 이미지 설정
        imageView = (CircularImageView)findViewById(R.id.api_photographer_img);
        Glide.with(getApplicationContext())
                .load(pgProfileUrl)
                .into(imageView);

        photographerId.setText(pgId);
        photographerLocation.setText(location);
        photographerHashtag.setText(hashtag);

        // Adding Toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("PORTFOLIO"));
        tabLayout.addTab(tabLayout.newTab().setText("INFO"));
        tabLayout.addTab(tabLayout.newTab().setText("REVIEW"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        // Creating TabPagerAdapter adapter
        PhotographerTabPagerAdapter pagerAdapter = new PhotographerTabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), pgId);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pickFlag == 1){
                    //TODO 서버 통해서 mypick 목록에서 작가 지우기
                    Call<MyPickPhotographerDelResult> photographerDelResultCall = service.getMyPickPhotographerDelResult(pgId);
                    photographerDelResultCall.enqueue(new Callback<MyPickPhotographerDelResult>() {
                        @Override
                        public void onResponse(Call<MyPickPhotographerDelResult> call, Response<MyPickPhotographerDelResult> response) {
                            if(response.isSuccessful()) {
                                if(response.body().result) {
                                    pickButton.setImageResource(R.drawable.pick_gray);
                                    Toast.makeText(PhotographerInfoActivity.this, "MyPick 작가목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    pickFlag = 0;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyPickPhotographerDelResult> call, Throwable t) {

                        }
                    });
                }else if(pickFlag == 0){
                    //TODO 서버 통해서 mypick 목록에 작가 추가하기
                    MyPickPhotographerPickData data = new MyPickPhotographerPickData();
                    data.photographer_id = pgId;
                    Call<MyPickPhotographerPickResult> myPickPhotographerPickResultCall = service.getMyPickPhotographerPickResult(data);
                    myPickPhotographerPickResultCall.enqueue(new Callback<MyPickPhotographerPickResult>() {
                        @Override
                        public void onResponse(Call<MyPickPhotographerPickResult> call, Response<MyPickPhotographerPickResult> response) {
                            if(response.isSuccessful()) {
                                if(response.body().result) {
                                    pickButton.setImageResource(R.drawable.pick_yellow);
                                    Toast.makeText(PhotographerInfoActivity.this, "MyPick 작가목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                    pickFlag = 1;
                                }
                                else {
                                    //ApplicationController.showToast(getApplicationContext(), "통신오류");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyPickPhotographerPickResult> call, Throwable t) {
                            ApplicationController.showToast(getApplicationContext(), "통신실패");
                            //Log.i("mypick", t.toString());
                        }
                    });
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotographerInfoActivity.this, MakeEstimateActivity.class);
                intent.putExtra("pgId", pgId);
                startActivity(intent);
            }
        });


    }
}
