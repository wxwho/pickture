package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import pickture.sopt20th.com.pickutre.R;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoNextActivity();
            }
        }, 5000); // 5000
    }

    private void gotoNextActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

//        Boolean description = ApplicationController.getInstance().getDescription();
//
//        if (description) {
//            Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }
}
