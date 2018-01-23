package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Dialog.ReviewDialog;
import pickture.sopt20th.com.pickutre.Model.MakeReviewData;
import pickture.sopt20th.com.pickutre.Model.MakeReviewResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeReviewActivity extends AppCompatActivity {

    private static int FINISH_REVIEW_REQUEST=111;
    MakeReviewData data;
    private NetworkService service;
    private CircularImageView imageViewPhotographerProfile;
    private TextView textViewPhotographerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_review);

        final RatingBar ratingBar = (RatingBar)findViewById(R.id.amr_ratingbar);
        final EditText editReview = (EditText) findViewById(R.id.amr_review_content_edit);
        imageViewPhotographerProfile = (CircularImageView) findViewById(R.id.amr_photographer_profile);
        textViewPhotographerId = (TextView) findViewById(R.id.amr_photographer_id);
        data = new MakeReviewData();

        Intent intent = getIntent();
        data.request_id = intent.getIntExtra("request_id", 0);
        data.photographer_id = intent.getStringExtra("photographer_id");
        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("profile_url"))
                .into(imageViewPhotographerProfile);
        textViewPhotographerId.setText(data.photographer_id);


        service = ApplicationController.getInstance().getNetworkService();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ApplicationController.showToast(getApplicationContext(), ratingBar.getRating() + "");
            }
        });
        Button btnReview = (Button)findViewById(R.id.amr_reivew_btn);

        btnReview.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(ratingBar.getRating()==0.0){
                    startActivity(new Intent(MakeReviewActivity.this, ReviewDialog.class).putExtra("zero","평점을 작성해주세요."));
                }else{

                    data.rate = (int)ratingBar.getRating();
                    data.content = editReview.getText().toString();
                    Call<MakeReviewResult> makeReviewResultCall = service.getMakeReviewResult(data);
                    makeReviewResultCall.enqueue(new Callback<MakeReviewResult>() {
                        @Override
                        public void onResponse(Call<MakeReviewResult> call, Response<MakeReviewResult> response) {
                            if(response.isSuccessful()) {
                                if(response.body().result) {
                                    startActivityForResult(new Intent(MakeReviewActivity.this, ReviewDialog.class).putExtra("zero","리뷰가 등록되었습니다."),FINISH_REVIEW_REQUEST);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MakeReviewResult> call, Throwable t) {

                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==FINISH_REVIEW_REQUEST){
            finish();
        }
    }
}
