package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Adapter.EnlargePhotoAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoDelResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoPickData;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoPickResult;
import pickture.sopt20th.com.pickutre.Model.PhotographerPortfolioResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnlargePhotoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private EnlargePhotoAdapter adapter;

    private ImageButton pickButton;

    private ArrayList<PhotographerPortfolioResult.PicturesData> datas;
    private int curPosition;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_photo);

        initView();
        setView();
        initNetwork();
        initEvent();


    }

    private void initView() {
        Intent intent = getIntent();
        datas = (ArrayList<PhotographerPortfolioResult.PicturesData> )intent.getSerializableExtra("pictureDatas");
        curPosition = intent.getIntExtra("position", 0);

        pickButton = (ImageButton)findViewById(R.id.aep_btn_pick);
        viewPager = (ViewPager) findViewById(R.id.aep_viewpager);
        adapter = new EnlargePhotoAdapter(getLayoutInflater(), datas);
    }

    private void setView() {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(curPosition);
        if (datas.get(curPosition).picked) {
            pickButton.setBackgroundResource(R.drawable.pick_yellow);
        }
    }

    private void initNetwork() {
        service = ApplicationController.getInstance().getNetworkService();
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Log.i("debug", "position = " + position);
                curPosition = position;
                if(datas.get(position).picked) {
                    pickButton.setBackgroundResource(R.drawable.pick_yellow);
                }
                else {
                    pickButton.setBackgroundResource(R.drawable.pick_gray);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //TODO: 버튼 눌렀을 때 pick_yellow로 아이콘 바꿔주기

        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datas.get(curPosition).picked){
                    // 서버 통해서 mypick목록에서 사진 지우기
                    int photo_id = datas.get(curPosition).id;
                    Call<MyPickPhotoDelResult> myPickPhotoDelResultCall = service.getMyPickPhotoDelResult(photo_id);
                    myPickPhotoDelResultCall.enqueue(new Callback<MyPickPhotoDelResult>() {
                        @Override
                        public void onResponse(Call<MyPickPhotoDelResult> call, Response<MyPickPhotoDelResult> response) {
                            if(response.isSuccessful()) {
                                if(response.body().result) {
                                    pickButton.setBackgroundResource(R.drawable.pick_gray);
                                    Toast.makeText(EnlargePhotoActivity.this, "MyPick목록에서 사진이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    datas.get(curPosition).picked = false;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyPickPhotoDelResult> call, Throwable t) {

                        }
                    });

                } else {
                    // 서버 통해서 mypick목록에서 사진 추가하기

                    MyPickPhotoPickData data = new MyPickPhotoPickData();
                    data.photo_id = datas.get(curPosition).id;
                    Call<MyPickPhotoPickResult> myPickPhotoPickResultCall = service.getMyPickPhotoPickResult(data);
                    myPickPhotoPickResultCall.enqueue(new Callback<MyPickPhotoPickResult>() {
                        @Override
                        public void onResponse(Call<MyPickPhotoPickResult> call, Response<MyPickPhotoPickResult> response) {
                            if(response.isSuccessful()) {
                                if(response.body().result) {
                                    pickButton.setBackgroundResource(R.drawable.pick_yellow);
                                    Toast.makeText(EnlargePhotoActivity.this, "MyPick목록에 사진이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                    datas.get(curPosition).picked = true;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyPickPhotoPickResult> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
}
