package pickture.sopt20th.com.pickutre.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pickture.sopt20th.com.pickutre.R;

//TODO 미완성
public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photo_detail);

        int img;

        TextView textView = (TextView)findViewById(R.id.apd_text);
        textView.setText("작가이름");


    }
}
