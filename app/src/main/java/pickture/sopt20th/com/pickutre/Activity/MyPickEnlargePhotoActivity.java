package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import pickture.sopt20th.com.pickutre.Adapter.EnlargePhotoAdapter;
import pickture.sopt20th.com.pickutre.R;

public class MyPickEnlargePhotoActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private EnlargePhotoAdapter adapter;
    private ImageButton pickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pick_enlarge_photo);

        Intent intent = getIntent();
        int curPosition = intent.getIntExtra("position", 0);
        String[] imageUrl = intent.getStringArrayExtra("imageUrl");

        viewPager = (ViewPager) findViewById(R.id.ampep_viewpager);
        // TODO 어댑터 새로 만들어서 적용시켜야할듯
        //adapter = new EnlargePhotoAdapter(getLayoutInflater(), imageUrl);

        //viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(curPosition);

    }
}
