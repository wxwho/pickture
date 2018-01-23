package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.LoginInfo;
import pickture.sopt20th.com.pickutre.Model.LoginInfoResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText al_id_edit,al_pw_edit;
    private ImageView al_public_signup,al_photographer_signup;
    private TextView al_loginbtn;
    private String id;
    private String password;

    NetworkService service;

    private CallbackManager callbackManager;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        initView();
        initNetwork();
        initClickEvent();

    }

    // 뷰 초기화
    private void initView() {
        al_id_edit=(EditText)findViewById(R.id.al_id_edit);
        al_pw_edit=(EditText)findViewById(R.id.al_pw_edit);
        al_loginbtn=(TextView)findViewById(R.id.al_loginbtn);
        al_public_signup=(ImageView)findViewById(R.id.al_public_signup);
        al_photographer_signup=(ImageView)findViewById(R.id.al_photographer_signup);
    }

    // 네트워크 초기화
    private void initNetwork() {
        service = ApplicationController.getInstance().getNetworkService();
    }

    // 클릭이벤트 초기화
    private void initClickEvent() {
        al_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 아이디 공백 체크
                if(al_id_edit.length() == 0){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 패스워드 공백 체크
                if(al_pw_edit.length() == 0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 서버통신부
                LoginInfo loginInfo =  new LoginInfo();
                loginInfo.id = al_id_edit.getText().toString();
                loginInfo.password = al_pw_edit.getText().toString();

                Call<LoginInfoResult> loginInfoResultCall = service.getLoginInfoResult(loginInfo);
                loginInfoResultCall.enqueue(new Callback<LoginInfoResult>() {
                    @Override
                    public void onResponse(Call<LoginInfoResult> call, Response<LoginInfoResult> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(), "통신 성공", Toast.LENGTH_SHORT).show();
                            if (response.body().result) {
                                //Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginInfoResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "통신실패", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });

        al_public_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup1 = new Intent(getApplicationContext(), UserSignUpActivity.class);
                startActivity(signup1);
            }
        });

        al_photographer_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup2 = new Intent(getApplicationContext(), PhotographerSignUpActivity1.class);
                startActivity(signup2);
            }
        });
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void facebookLoginOnClick(View v) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "뒤로가기를 한 번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}