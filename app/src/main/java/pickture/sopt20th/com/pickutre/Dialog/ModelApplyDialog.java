package pickture.sopt20th.com.pickutre.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import pickture.sopt20th.com.pickutre.Activity.ModelApplyBoxActivity;
import pickture.sopt20th.com.pickutre.R;

public class ModelApplyDialog extends Activity {

    private Button goToModelApplyBoxButton;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_model_apply_dialog);

        goToModelApplyBoxButton = (Button)findViewById(R.id.amad_go_to_model_apply_box_btn);
        okButton = (Button)findViewById(R.id.amad_ok_btn);

        goToModelApplyBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                startActivity(new Intent(getApplicationContext(), ModelApplyBoxActivity.class));
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
