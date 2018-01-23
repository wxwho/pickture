package pickture.sopt20th.com.pickutre.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import pickture.sopt20th.com.pickutre.R;

public class ReviewDialog extends Activity {

    private TextView message;
    private Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_review_dialog);

        message = (TextView)findViewById(R.id.ard_msg_txt);
        okButton = (Button)findViewById(R.id.ard_ok_btn);

        Intent intent = getIntent();
        message.setText(intent.getStringExtra("zero"));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().equals("리뷰가 등록되었습니다.")){
                    setResult(RESULT_OK);
                    finish();
                }else{
                    finish();
                }
            }
        });
    }

}
