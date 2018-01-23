package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Adapter.EstimateAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.EstimateListResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstimateBoxActivity extends AppCompatActivity {

    private NetworkService service;
    private ArrayList<EstimateListResult.requestData> requestDatas;
    private ListView listview;
    private EstimateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_box);


        service = ApplicationController.getInstance().getNetworkService();

        getNetworkData();


        listview = (ListView) findViewById(R.id.aeb_listview);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // 넘길 데이터 목록
                // 카테고리 상품명 날짜 인원 희망촬영장소 세부희망사항 첨부사진
                // 답변완료시 작가이름 코멘트
                // 입금완료시 연락처
                // TODO 넘길 데이터가 너무 많다 id(키값)만 넘기는 방향으로 해보자
                Bundle bundle = new Bundle();
                bundle.putSerializable("request", requestDatas.get(position));
                Intent intent = new Intent(EstimateBoxActivity.this, EstimateDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getNetworkData() {
        Call<EstimateListResult> estimateListResultCall = service.getEstimateListResult();
        estimateListResultCall.enqueue(new Callback<EstimateListResult>() {
            @Override
            public void onResponse(Call<EstimateListResult> call, Response<EstimateListResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        requestDatas = response.body().requests;
                        adapter = new EstimateAdapter(requestDatas);
                        listview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<EstimateListResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getNetworkData();
    }

    //    @Override   //답변완료->입금완료 변환 --> 서버로 하기
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        TextView response_state = (TextView)findViewById(R.id.le_response_state);
//
//        if(resultCode==RESULT_OK){
//            if(requestCode==1){
//                String change = data.getStringExtra("changedResponse");
//                Toast.makeText(getApplicationContext(), change, Toast.LENGTH_SHORT);
//                response_state.setText(data.getStringExtra("changedResponse"));
//            }
//        }
//    }
}