package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import pickture.sopt20th.com.pickutre.Model.FindModelResult;
import pickture.sopt20th.com.pickutre.R;

public class ModelRecruitDetailActivity extends AppCompatActivity {



    private CircularImageView pg_profile;
    private TextView pg_id, title;
    private Button applyBtn;

    private ImageView[] imageViews = new ImageView[5];

    private TextView concept, required_model, date_hours, place, etc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_recruit_detail);

        Intent intent=getIntent();
        final FindModelResult.recruitData data =  (FindModelResult.recruitData)intent.getSerializableExtra("recruits");

        Log.i("success", "intent받아옴");
        //뷰 선언
        pg_profile = (CircularImageView)findViewById(R.id.amrd_profile);
        pg_id = (TextView)findViewById(R.id.amrd_pg_id);
        title = (TextView)findViewById(R.id.amrd_title);
        applyBtn = (Button)findViewById(R.id.amrd_apply_btn);

        imageViews[0] = (ImageView)findViewById(R.id.amrd_imageView);
        imageViews[1] = (ImageView)findViewById(R.id.amrd_imageView2);
        imageViews[2] = (ImageView)findViewById(R.id.amrd_imageView3);
        imageViews[3] = (ImageView)findViewById(R.id.amrd_imageView4);
        imageViews[4] = (ImageView)findViewById(R.id.amrd_imageView5);


        concept = (TextView)findViewById(R.id.amrd_concept);
        required_model = (TextView)findViewById(R.id.amrd_required_model);
        date_hours = (TextView)findViewById(R.id.amrd_date_hours);
        place =(TextView) findViewById(R.id.amrd_place);
        etc = (TextView)findViewById(R.id.amrd_etc);

        //받은 값 넣기
        Glide.with(getApplicationContext())
                .load(data.photographer.profile_url)
                .into(pg_profile);

        pg_id.setText(data.photographer.id);
        title.setText(data.title);

        // Log.i("numberImage",""+data.images.size());

        for(int i=0; i<data.images.size(); i++){
            Glide.with(getApplicationContext())
                    .load(data.images.get(i))
                    .into(imageViews[i]);
        }

        for(int i=data.images.size(); i<5; i++){
            imageViews[i].setVisibility(View.GONE);
        }

        concept.setText(data.concept);
        required_model.setText(data.modelRequired);
        date_hours.setText(data.dateHours);
        place.setText(data.photoPlace);
        etc.setText(data.etc);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelRecruitDetailActivity.this, ModelApplyActivity.class);
                intent.putExtra("pgId", data.photographer.id);
                intent.putExtra("pgProfile", data.photographer.profile_url);
                intent.putExtra("pgTitle",data.title);
                startActivity(intent);
                finish();
            }
        });
    }

}