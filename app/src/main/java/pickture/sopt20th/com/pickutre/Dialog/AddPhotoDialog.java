package pickture.sopt20th.com.pickutre.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import pickture.sopt20th.com.pickutre.Activity.SelectMyPickPhotoActivity;
import pickture.sopt20th.com.pickutre.R;

public class AddPhotoDialog extends Activity {

    private static int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private static int PICK_IMAGE_FROM_MY_PiICK_PHOTO_REQUEST = 2;
    //private ImageView imgView;
    private int photoNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_photo_dialog);
    }

    public void loadImagefromGallery(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Intent 생성
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
        intent.setType("image/*"); //이미지만 보이게
        //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FROM_GALLERY_REQUEST);
    }

    public void loadImagefromMyPick(View view) {
        Intent intent = new Intent(getApplicationContext(), SelectMyPickPhotoActivity.class);
        startActivityForResult(intent, PICK_IMAGE_FROM_MY_PiICK_PHOTO_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();
                Intent resultUri = new Intent();
                resultUri.putExtra("selected", uri.toString());
                resultUri.putExtra("type", "GALLERY");
                setResult(RESULT_OK, resultUri);
                finish();
            } else if (requestCode == PICK_IMAGE_FROM_MY_PiICK_PHOTO_REQUEST && resultCode == RESULT_OK && data != null) {
                String photoUrl = data.getStringExtra("selected");

                Intent resultUri = new Intent();
                resultUri.putExtra("selected", photoUrl);
                resultUri.putExtra("type", "MY_PICK_PHOTO");
                setResult(RESULT_OK, resultUri);
                finish();
            } else {
                Toast.makeText(this, "취소 되었습니다.11", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.11", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



}
