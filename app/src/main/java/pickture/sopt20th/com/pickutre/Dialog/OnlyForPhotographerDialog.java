package pickture.sopt20th.com.pickutre.Dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import pickture.sopt20th.com.pickutre.R;

public class OnlyForPhotographerDialog extends Activity {

    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_only_for_photographer_dialog);

        okButton = (Button)findViewById(R.id.aofpd_ok_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
