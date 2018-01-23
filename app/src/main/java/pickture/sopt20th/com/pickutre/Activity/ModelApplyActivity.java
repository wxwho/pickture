package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import pickture.sopt20th.com.pickutre.Dialog.ModelApplyDialog;
import pickture.sopt20th.com.pickutre.R;

public class ModelApplyActivity extends AppCompatActivity {

    private Intent intent;
    private String pg_id;
    private String pg_profile;
    private String pg_title;

    private static int PICK_IMAGE_REQUEST_1 = 1;
    private static int PICK_IMAGE_REQUEST_2 = 2;
    private static int PICK_IMAGE_REQUEST_3 = 3;

    private static int FINISH_MODEL_APPLY_REQUEST=4;

    private CircularImageView profile;
    private TextView id;
    private TextView title;

    private ImageView pickImage1;
    private ImageView pickImage2;
    private ImageView pickImage3;
    private Button finishButton;
    private EditText introduceEdit;
    private EditText contactEdit;

    private Bitmap[] bitmapList = new Bitmap[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_apply);

        //초기화
        intent=getIntent();
        pg_id = intent.getExtras().getString("pgId");
        pg_profile = intent.getExtras().getString("pgProfile");
        pg_title = intent.getExtras().getString("pgTitle");

        profile = (CircularImageView)findViewById(R.id.ama_profile);
        id = (TextView)findViewById(R.id.ama_pg_id);
        title = (TextView)findViewById(R.id.ama_title);

        pickImage1 = (ImageView)findViewById(R.id.ama_add_photo_img1);
        pickImage2 = (ImageView)findViewById(R.id.ama_add_photo_img2);
        pickImage3 = (ImageView)findViewById(R.id.ama_add_photo_img3);
        finishButton = (Button)findViewById(R.id.ama_finish_btn);
        introduceEdit = (EditText)findViewById(R.id.ama_introduce_edit);
        contactEdit = (EditText)findViewById(R.id.ama_contact_edit);

        //데이터 넣기
        Glide.with(getApplicationContext())
                .load(pg_profile)
                .into(profile);

        id.setText(pg_id);
        title.setText(pg_title);

        pickImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent 생성
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
                intent.setType("image/*"); //이미지만 보이게
                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_1);
            }
        });

        pickImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent 생성
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
                intent.setType("image/*"); //이미지만 보이게
                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_2);
            }
        });

        pickImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent 생성
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
                intent.setType("image/*"); //이미지만 보이게
                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_3);
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EditText)findViewById(R.id.ama_introduce_edit)).length()==0){
                    Toast.makeText(getApplicationContext(), "자기소개를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(((EditText)findViewById(R.id.ama_contact_edit)).length()==0){
                    Toast.makeText(getApplicationContext(), "연락처를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(new Intent(ModelApplyActivity.this, ModelApplyDialog.class).putExtra("pgId",pg_id),FINISH_MODEL_APPLY_REQUEST);
                }
            }
        });
    }

    //이미지 선택작업을 후의 결과 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode==FINISH_MODEL_APPLY_REQUEST && resultCode == RESULT_OK){
                finish();
            }
            //이미지를 하나 골랐을때
            else if (resultCode == RESULT_OK && null != data) {

                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (128.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 128, nh, true);

                if(requestCode == PICK_IMAGE_REQUEST_1){
                    pickImage1.setImageBitmap(scaled);
                }else if(requestCode==PICK_IMAGE_REQUEST_2){
                    pickImage2.setImageBitmap(scaled);
                }else if(requestCode==PICK_IMAGE_REQUEST_3){
                    pickImage3.setImageBitmap(scaled);
                }
            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}