package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.IdValidationResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotographerSignUpActivity1 extends AppCompatActivity {

    private EditText as2_id_edit, as2_pwd_edit, as2_pwd_check_edit;
    private Button as2_id_check_btn, as2_next_btn;
    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_sign_up1);

        as2_id_edit = (EditText) findViewById(R.id.as2_id_edit);
        as2_pwd_edit = (EditText) findViewById(R.id.as2_pwd_edit);
        as2_pwd_check_edit = (EditText) findViewById(R.id.as2_pwd_check_edit);
        as2_id_check_btn = (Button) findViewById(R.id.as2_id_check_btn);
        as2_next_btn = (Button) findViewById(R.id.as2_next_btn);

        service = ApplicationController.getInstance().getNetworkService();

        as2_pwd_check_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = as2_pwd_edit.getText().toString();
                String confirm = as2_pwd_check_edit.getText().toString();

                if (hasFocus == false) {
                    Log.i("pw : ", password);
                    Log.i("pwconfirm : ", confirm);
                    if (password.equals(confirm)) {
                        Log.i("1 : ", " Same ");
                    } else {
                        Log.i("1 : ", " No ");
                        as2_pwd_check_edit.setText("");
                        Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        as2_id_check_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //중복 체크 //DB랑 체크
                Call<IdValidationResult> idValidationResultCall = service.getIdValidationResult(as2_id_edit.getText().toString());
                idValidationResultCall.enqueue(new Callback<IdValidationResult>() {
                    @Override
                    public void onResponse(Call<IdValidationResult> call, Response<IdValidationResult> response) {
                        if(response.isSuccessful()) {
                            if(response.body().result) {
                                ApplicationController.showToast(getApplication(), "아이디가 중복되었습니다");
                            }
                            else {
                                ApplicationController.showToast(getApplication(), "사용할 수 있는 아이디입니다");
                            }
                        }
                        else {
                            ApplicationController.showToast(getApplication(), "통신오류");
                        }
                    }

                    @Override
                    public void onFailure(Call<IdValidationResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "통신실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        as2_next_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (as2_id_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "ID을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    as2_id_edit.requestFocus();                 // requestFocus() id_edittext로 focus 이동
                    return;
                }
                if (as2_pwd_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    as2_pwd_edit.requestFocus();
                    return;
                }
                if (as2_pwd_check_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    as2_pwd_check_edit.requestFocus();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), PhotographerSignUpActivity2.class);
                intent.putExtra("id", String.valueOf(as2_id_edit.getText()));
                intent.putExtra("pwd", String.valueOf(as2_pwd_edit.getText()));
                startActivity(intent);
                finish();
            }
        });
    }


}
