package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import pickture.sopt20th.com.pickutre.Adapter.SelectMyPickPhotoGridAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMyPickPhotoActivity extends AppCompatActivity {

    private GridView gridView;
    private SelectMyPickPhotoGridAdapter adapter;
    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_pick_photo);

        service = ApplicationController.getInstance().getNetworkService();

        initView();
        loadMyPickPhotos();
        setItemClickEvent();
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.asmpp_gridview);
    }

    private void loadMyPickPhotos() {
        Call<MyPickPhotoResult> myPickPhotoResultCall = service.getMyPickPhotoResult();
        myPickPhotoResultCall.enqueue(new Callback<MyPickPhotoResult>() {
            @Override
            public void onResponse(Call<MyPickPhotoResult> call, Response<MyPickPhotoResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().result) {
                        // Log
                        Log.i("debug", response.body().toString());

                        adapter = new SelectMyPickPhotoGridAdapter(
                                getApplicationContext(),
                                R.layout.item_image_grid,
                                response.body().photos);
                        gridView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPickPhotoResult> call, Throwable t) {

            }
        });
    }

    private void setItemClickEvent() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 사진 클릭시, 선택된 사진 정보를 이전 액티비티(MakeEstimateActivity)로 넘기기
                String photoUrl = adapter.getItem(position).toString();
                Intent intent = new Intent();
                intent.putExtra("selected", photoUrl);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
