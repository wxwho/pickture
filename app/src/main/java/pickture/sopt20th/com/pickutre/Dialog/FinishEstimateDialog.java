package pickture.sopt20th.com.pickutre.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import pickture.sopt20th.com.pickutre.Activity.EstimateBoxActivity;
import pickture.sopt20th.com.pickutre.R;

public class FinishEstimateDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finish_estimate_dialog);

        Button goToEstimateBoxButton = (Button)findViewById(R.id.afed_go_to_estimate_box_btn);
        Button okButton = (Button)findViewById(R.id.afed_ok_btn);

        goToEstimateBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                startActivity(new Intent(getApplicationContext(), EstimateBoxActivity.class));
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
