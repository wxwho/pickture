package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.EstimateDetailPayData;
import pickture.sopt20th.com.pickutre.Model.EstimateDetailPayResult;
import pickture.sopt20th.com.pickutre.Model.EstimateListResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstimateDetailActivity extends AppCompatActivity {

    private Intent intent;
    private ImageView pgProfile;
    private TextView pgId;
    private TextView category, product, date, part;
    private TextView preferedSpot, preferedDetail;
    private ImageView attached1, attached2, attached3;

    private TextView answerWait;
    private ImageView pgProfile2;
    private TextView pgId2;
    private TextView comment;

    private NetworkService service;

    private EstimateListResult.requestData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esitmate_detail);

        //뷰초기화
        intent = getIntent();
        data =  (EstimateListResult.requestData)intent.getSerializableExtra("request");

        pgProfile = (ImageView)findViewById(R.id.aed_profile);
        pgId = (TextView)findViewById(R.id.aed_photographer_id_1);
        category = (TextView)findViewById(R.id.aed_category);
        product = (TextView)findViewById(R.id.aed_product);
        date = (TextView)findViewById(R.id.aed_date);
        part = (TextView)findViewById(R.id.aed_part);

        preferedSpot = (TextView)findViewById(R.id.aed_prefered_spot);
        preferedDetail = (TextView)findViewById(R.id.aed_prefered_detail);

        attached1 = (ImageView)findViewById(R.id.aed_attached_1);
        attached2 = (ImageView)findViewById(R.id.aed_attached_2);
        attached3 = (ImageView)findViewById(R.id.aed_attached_3);

        answerWait=(TextView)findViewById(R.id.aed_answerwait);

        pgProfile2 = (ImageView)findViewById(R.id.aed_profile_2);
        pgId2 = (TextView)findViewById(R.id.aed_photographer_id_2);
        comment = (TextView)findViewById(R.id.aed_comment_txt);

        LinearLayout commentTextView = (LinearLayout) findViewById(R.id.aed_comment);
        final LinearLayout depositButton = (LinearLayout) findViewById(R.id.aed_deposit_btn);
        final LinearLayout depositfinish=(LinearLayout)findViewById(R.id.aed_deposit_finish);
        final TextView phoneNumTextView = (TextView) findViewById(R.id.aed_phoneNum_txt);

        service = ApplicationController.getInstance().getNetworkService();

        //서버로부터 받아온 값 넣기
        Glide.with(getApplicationContext())
                .load(data.photographer.profile_url)
                .into(pgProfile);

        Glide.with(getApplicationContext())
                .load(data.photographer.profile_url)
                .into(pgProfile2);

        pgId.setText(data.photographer.id);
        pgId2.setText(data.photographer.id);
        category.setText(data.category);
        product.setText(data.item.item_name);
        date.setText(data.date);
        part.setText(data.cntPeople+"명");

        preferedSpot.setText(data.placePrefer);
        preferedDetail.setText(data.detailComment);

        ArrayList<String> images = new ArrayList<String>();
        images = data.photos;

        ImageView[] findmodelPhoto = new ImageView[3];
        findmodelPhoto[0] = attached1;
        findmodelPhoto[1] = attached2;
        findmodelPhoto[2] = attached3;

        for(int i=0; i<data.photos.size();i++){
            Glide.with(getApplicationContext())
                    .load(data.photos.get(i))
                    .centerCrop()
                    .into(findmodelPhoto[i]);
        }

        answerWait.setVisibility(View.VISIBLE);
        commentTextView.setVisibility(View.GONE);
        depositButton.setVisibility(View.GONE);
        depositfinish.setVisibility(View.GONE);
//        contactTextView.setVisibility(View.INVISIBLE);
        phoneNumTextView.setVisibility(View.GONE);


        if(data.status.equals("답변완료")){
            answerWait.setVisibility(View.GONE);
            commentTextView.setVisibility(View.VISIBLE);
            depositButton.setVisibility(View.VISIBLE);
        }else if(data.status.equals("예약완료")){
            answerWait.setVisibility(View.GONE);
            commentTextView.setVisibility(View.VISIBLE);
            depositButton.setVisibility(View.GONE);
            depositfinish.setVisibility(View.VISIBLE);
//            contactTextView.setVisibility(View.VISIBLE);
            phoneNumTextView.setVisibility(View.VISIBLE);
        }

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EstimateDetailPayData Iddata = new EstimateDetailPayData();
                Iddata.request_id = data.id;
                Call<EstimateDetailPayResult> estimateDetailPayResultCall = service.getEstimateDetailPayResult(Iddata);
                estimateDetailPayResultCall.enqueue(new Callback<EstimateDetailPayResult>() {
                    @Override
                    public void onResponse(Call<EstimateDetailPayResult> call, Response<EstimateDetailPayResult> response) {
                        if(response.isSuccessful()) {
                            if(response.body().result) {
                                depositButton.setVisibility(View.GONE);
                                depositfinish.setVisibility(View.VISIBLE);
                                phoneNumTextView.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EstimateDetailPayResult> call, Throwable t) {

                    }
                });





//                //해당 견적서 답변완료->예약완료로 변경..API없음->나중에 알려주기로
            }
        });

    }
}