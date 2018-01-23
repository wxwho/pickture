package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.IdValidationResult;
import pickture.sopt20th.com.pickutre.Model.SignUpData;
import pickture.sopt20th.com.pickutre.Model.SignUpDataResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserSignUpActivity extends AppCompatActivity {

    private EditText as_id_edit, as_pwd_edit, as_pwd_check_edit;
    private Button as_id_check_btn, as_generator_btn;
    private RadioButton male, female;
    private int flag = 0;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        initView();
        initNetwork();
        initClickEvent();

    }


    // 뷰 초기화
    private void initView() {
        as_id_edit = (EditText) findViewById(R.id.as1_id_edit);
        as_pwd_edit = (EditText) findViewById(R.id.as1_pwd_edit);
        as_pwd_check_edit = (EditText) findViewById(R.id.as1_pwd_check_edit);
        as_id_check_btn = (Button) findViewById(R.id.as1_id_check_btn);
        as_generator_btn = (Button) findViewById(R.id.as1_generator_btn);
        male = (RadioButton)findViewById(R.id.as1_male);
        female = (RadioButton)findViewById(R.id.as1_female);
    }

    // 네트워크 초기화
    private void initNetwork() {
        service = ApplicationController.getInstance().getNetworkService();
    }

    // 클릭이벤트 초기화
    private void initClickEvent() {

        as_id_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = as_id_edit.getText().toString();
                Call<IdValidationResult> IdValidationResultCall = service.getIdValidationResult(id);
                // onResponse에서 200~300은 정상 400~500은 비정상, 이를 isSuccessful로 판단
                // 서버에서 응답이 없거나 아예 접근이 안될 때(잘못되었을 때) onFailure로 감
                IdValidationResultCall.enqueue(new Callback<IdValidationResult>() {
                    @Override
                    public void onResponse(Call<IdValidationResult> call, Response<IdValidationResult> response) {
                        if (response.isSuccessful()) {
                            if(response.body().result) {
                                ApplicationController.showToast(getApplication(), "아이디가 중복되었습니다");
                            }
                            else {
                                ApplicationController.showToast(getApplication(), "사용할 수 있는 아이디입니다");
                                flag = 1;
                                as_id_edit.setFocusable(false);
                                as_id_edit.setClickable(false);
                            }
                        }
                        else {
                            ApplicationController.showToast(getApplication(), "서버통신오류");
                        }
                    }

                    @Override
                    public void onFailure(Call<IdValidationResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "통신실패", Toast.LENGTH_SHORT).show();
                        // 통신실패이유가 t에 저장됨
                        //Log.i("debug", t.toString());
                    }
                });
            }
        });

        //TODO 아이디 중복체크 완료시 완료버튼 활성화 되도록
        //TODO 아이디 중복체크 완료시 아이디 변경 불가능 하도록 바꾸기

        as_generator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0) {
                    Toast.makeText(getApplicationContext(), "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                }
                if(male.isChecked() == false && female.isChecked() == false){
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (as_id_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "ID을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    as_id_edit.requestFocus();                 // requestFocus() id_edittext로 focus 이동
                    return;
                }
                if (as_pwd_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    as_pwd_edit.requestFocus();
                    return;
                }
                else if( as_pwd_edit.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "비밀번호을 4자이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    as_pwd_edit.requestFocus();
                    return;
                }
                if (as_pwd_check_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    as_pwd_check_edit.requestFocus();
                    return;
                }

                if(!as_pwd_edit.getText().toString().equals(as_pwd_check_edit.getText().toString())){
                    Toast.makeText(getApplicationContext(), "비밀번호 다릅니다.", Toast.LENGTH_SHORT).show();
                    as_pwd_edit.setText("");
                    as_pwd_check_edit.setText("");
                    as_pwd_edit.requestFocus();
                    return;
                }

                //TODO 회원가입정보 서버에 넣기
                SignUpData signUpData = new SignUpData();
                signUpData.id = as_id_edit.getText().toString();
                signUpData.password = as_pwd_edit.getText().toString();
                Call<SignUpDataResult> signUpDataResultCall = service.getSignUpDataResult(signUpData);
                signUpDataResultCall.enqueue(new Callback<SignUpDataResult>() {
                    @Override
                    public void onResponse(Call<SignUpDataResult> call, Response<SignUpDataResult> response) {
                        if(response.isSuccessful()) {
                            if(response.body().result) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                ApplicationController.showToast(getApplication(), "회원가입이 완료되었습니다");
                                startActivity(intent);
                                finish();
                            }
                            else {
                                ApplicationController.showToast(getApplication(), "회원가입 실패");
                            }
                        }
                        else {
                            ApplicationController.showToast(getApplication(), "오류 " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpDataResult> call, Throwable t) {
                        ApplicationController.showToast(getApplication(), "통신실패");
                    }
                });
            }
        });
    }
}