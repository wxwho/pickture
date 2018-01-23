package pickture.sopt20th.com.pickutre.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Dialog.AddPhotoDialog;
import pickture.sopt20th.com.pickutre.Dialog.FinishEstimateDialog;
import pickture.sopt20th.com.pickutre.Model.MakeEstimateItemResult;
import pickture.sopt20th.com.pickutre.Model.MakeEstimateResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeEstimateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    // 스피너
    private Spinner spinnerKoreanCategory = null;
    private Spinner spinnerProduct = null;
    private Spinner spinnerParts = null;

    // View
    private ImageView addPhoto1;
    private ImageView addPhoto2;
    private ImageView addPhoto3;
    private TextView dateTextView;
    private TextView toolbarTextview;
    private EditText wishSpot;
    private EditText wishDetail;
    private Button sendButton;

    //
    private String pgId;

    // List View
    private ArrayAdapter<String> adapterProduct;
    private ArrayList<String> arrProduct;
    private ArrayList<Integer> arrProductId;

    // Request Code
    private static int PICK_IMAGE_REQUEST_1 = 11;
    private static int PICK_IMAGE_REQUEST_2 = 22;
    private static int PICK_IMAGE_REQUEST_3 = 33;
    private static int FINISH_ESTIMATE_REQUEST = 44;

    // Network
    private NetworkService service;

    // Bitmap List
//    private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    private Uri[] uriList = new Uri[3];
    private boolean existEmptyForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_estimate);

        Intent intent = getIntent();
        pgId = intent.getStringExtra("pgId");

        initView();
        verifyStoragePermissions(this);
        setBtnEvent();
        loadData();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == FINISH_ESTIMATE_REQUEST) {
                finish();
            } else if (resultCode == RESULT_OK && data != null) {
                String type = data.getStringExtra("type");
                // 갤러리에서 사진을 불러오는 경우
                // bitmap -> uri
                if ("GALLERY".equals(type)) {
                    Uri uri = Uri.parse(data.getStringExtra("selected"));

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                    int nh = (int) (bitmap.getHeight() * (128.0 / bitmap.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 128, nh, true);

                    if (requestCode == PICK_IMAGE_REQUEST_1) {
                        addPhoto1.setImageBitmap(scaled);
                        uriList[0] = uri;
                    } else if (requestCode == PICK_IMAGE_REQUEST_2) {
                        addPhoto2.setImageBitmap(scaled);
                        uriList[1] = uri;
                    } else if (requestCode == PICK_IMAGE_REQUEST_3) {
                        addPhoto3.setImageBitmap(scaled);
                        uriList[2] = uri;
                    }

                    //Log.i("debug", Arrays.deepToString(uriList));
                }
                // 마이픽에서 사진을 선택하는 경우
                // url -> bitmap -> uri
                else {
                    final String photoUrl = data.getStringExtra("selected");
                    final Uri uri = Uri.parse(photoUrl);

                    if (requestCode == PICK_IMAGE_REQUEST_1) {
                        Glide.with(getApplicationContext())
                                .load(photoUrl)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        uriList[0] = getImageUri(getApplicationContext(), resource);
                                        addPhoto1.setImageBitmap(resource);
                                    }
                                });
                    } else if (requestCode == PICK_IMAGE_REQUEST_2) {
                        Glide.with(getApplicationContext())
                                .load(photoUrl)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        uriList[1] = getImageUri(getApplicationContext(), resource);
                                        addPhoto2.setImageBitmap(resource);
                                    }
                                });
                    } else if (requestCode == PICK_IMAGE_REQUEST_3) {
                        Glide.with(getApplicationContext())
                                .load(photoUrl)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        uriList[2] = getImageUri(getApplicationContext(), resource);
                                        addPhoto3.setImageBitmap(resource);
                                    }
                                });
                    }

                    //Log.i("debug", Arrays.deepToString(uriList));
                }
            } else {
                Toast.makeText(this, "사진선택이 취소 되었습니다", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.22", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void initView() {
        spinnerKoreanCategory = (Spinner) findViewById(R.id.ame_category_sp);
        spinnerProduct = (Spinner) findViewById(R.id.ame_product_sp);
        spinnerParts = (Spinner) findViewById(R.id.ame_parts_sp);

        addPhoto1 = (ImageView) findViewById(R.id.ame_add_photo_img1);
        addPhoto2 = (ImageView) findViewById(R.id.ame_add_photo_img2);
        addPhoto3 = (ImageView) findViewById(R.id.ame_add_photo_img3);

        dateTextView = (TextView) findViewById(R.id.ame_date_txt);
        toolbarTextview = (TextView) findViewById(R.id.ame_toolbar_pgId);
        sendButton = (Button) findViewById(R.id.ame_send_estimate_btn);

        //희망장소
        wishSpot = (EditText) findViewById(R.id.ame_wish_spot_edit);
        //세부희망사항
        wishDetail = (EditText) findViewById(R.id.ame_detail_wish_edit);
    }

    private void setBtnEvent() {
        // 이미지 뷰 1
        addPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(MakeEstimateActivity.this, AddPhotoDialog.class).putExtra("imgID",1), PICK_IMAGE_REQUEST);
                startActivityForResult(new Intent(MakeEstimateActivity.this, AddPhotoDialog.class), PICK_IMAGE_REQUEST_1);
            }
        });

        // 이미지 뷰 2
        addPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(MakeEstimateActivity.this, AddPhotoDialog.class).putExtra("imgID",2), PICK_IMAGE_REQUEST);
                startActivityForResult(new Intent(MakeEstimateActivity.this, AddPhotoDialog.class), PICK_IMAGE_REQUEST_2);
            }
        });

        // 이미지 뷰 3
        addPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(MakeEstimateActivity.this, AddPhotoDialog.class).putExtra("imgID",3), PICK_IMAGE_REQUEST);
                startActivityForResult(new Intent(MakeEstimateActivity.this, AddPhotoDialog.class), PICK_IMAGE_REQUEST_3);
            }
        });

        // 완료 버튼
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpCategoryText = spinnerKoreanCategory.getSelectedItem().toString();
                String tmpProductType = arrProductId.get(spinnerProduct.getSelectedItemPosition() - 1).toString();
                String tmpDateText = dateTextView.getText().toString();
                String tmpCntPeople = spinnerParts.getSelectedItem().toString();
                String tmpPlacePrefer = wishSpot.getText().toString();
                String tmpDetailComment = wishDetail.getText().toString();

                if (tmpCategoryText.equals("카테고리")) {
                    ApplicationController.showToast(getApplicationContext(), "카테고리를 선택하세요");
                } else if (tmpProductType.equals("상품선택")) {
                    ApplicationController.showToast(getApplicationContext(), "상품을 선택하세요");
                } else if (tmpDateText.equals("촬영날짜")) {
                    ApplicationController.showToast(getApplicationContext(), "촬영날짜를 입력하세요");
                } else if (tmpCntPeople.equals("촬영인원")) {
                    ApplicationController.showToast(getApplicationContext(), "촬영인원을 선택하세요");
                } else if (tmpPlacePrefer.length() == 0) {
                    ApplicationController.showToast(getApplicationContext(), "희망 촬영 장소를 입력하세요");
                } else if (addPhoto1.getDrawable() == null && addPhoto2.getDrawable() == null && addPhoto3.getDrawable() == null) {
                    ApplicationController.showToast(getApplicationContext(), "사진을 첨부해주세요");
                } else {
                    //ApplicationController.showToast(getApplicationContext(), "진입 !!");
                    MultipartBody.Part image1 = null;
                    if(uriList[0] != null) {
                        File file1 = new File(getRealPathFromURI(uriList[0]));
                        final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                        image1 = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);
                    }

                    MultipartBody.Part image2 = null;
                    if(uriList[1] != null) {
                        File file2 = new File(getRealPathFromURI(uriList[1]));
                        final RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                        image2 = MultipartBody.Part.createFormData("image", file2.getName(), requestBody2);

                    }
                    MultipartBody.Part image3 = null;
                    if(uriList[2] != null) {
                        File file3 = new File(getRealPathFromURI(uriList[2]));
                        final RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/*"), file3);
                        image3 = MultipartBody.Part.createFormData("image", file3.getName(), requestBody3);
                    }

                    // TODO 테스트 데이터 제거하고 위의 tmpData 넣기
                    RequestBody photographer_id = RequestBody.create(MediaType.parse("text/plain"), pgId);
                    RequestBody category = RequestBody.create(MediaType.parse("text/plain"), tmpCategoryText);
                    RequestBody placePrefer = RequestBody.create(MediaType.parse("text/plain"), tmpPlacePrefer);
                    RequestBody date = RequestBody.create(MediaType.parse("text/plain"), tmpDateText + " 00:00:00");
                    RequestBody item = RequestBody.create(MediaType.parse("text/plain"), tmpProductType);
                    RequestBody cntPeople = RequestBody.create(MediaType.parse("text/plain"), tmpCntPeople);
                    RequestBody detailComment = RequestBody.create(MediaType.parse("text/plain"), tmpDetailComment);
//                    RequestBody photographer_id = RequestBody.create(MediaType.parse("text/plain"), "basil");
//                    RequestBody category = RequestBody.create(MediaType.parse("text/plain"), "date");
//                    RequestBody placePrefer = RequestBody.create(MediaType.parse("text/plain"), "우리집");
//                    RequestBody date = RequestBody.create(MediaType.parse("text/plain"), "2017-07-07 00:00:00");
//                    RequestBody item = RequestBody.create(MediaType.parse("text/plain"), "261");
//                    RequestBody cntPeople = RequestBody.create(MediaType.parse("text/plain"), "1");
//                    RequestBody detailComment = RequestBody.create(MediaType.parse("text/plain"), "1");

                    Call<MakeEstimateResult> postRequests = service.postRequests(
                            image1,
                            image2,
                            image3,
                            photographer_id,
                            category,
                            placePrefer,
                            date,
                            item,
                            cntPeople,
                            detailComment
                    );

                    postRequests.enqueue(new Callback<MakeEstimateResult>() {
                        @Override
                        public void onResponse(Call<MakeEstimateResult> call, Response<MakeEstimateResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().result) {
                                    // TODO 테스트 Toast 제거
                                    //ApplicationController.showToast(getApplicationContext(), "성공 !!");
                                    startActivityForResult(new Intent(MakeEstimateActivity.this, FinishEstimateDialog.class), FINISH_ESTIMATE_REQUEST);
                                }
                                else {
                                    //ApplicationController.showToast(getApplicationContext(), "실패3 !!");
                                }
                            } else {
                                // TODO 테스트 Toast 제거
                                //ApplicationController.showToast(getApplicationContext(), "실패1 !!");
                            }
                        }

                        @Override
                        public void onFailure(Call<MakeEstimateResult> call, Throwable t) {
                            // TODO 테스트 Toast 제거
                            //ApplicationController.showToast(getApplicationContext(), "실패2 !!");
                        }
                    });
                }

            }
        });
    }

    private void loadData() {
        toolbarTextview.setText(pgId);

        ArrayAdapter<CharSequence> adapterKoreanCategory = ArrayAdapter.createFromResource(this, R.array.categoryKorean, R.layout.item_spinner_make_estimate);
        adapterKoreanCategory.setDropDownViewResource(R.layout.item_spinner_make_estimate_dropdown);
        spinnerKoreanCategory.setAdapter(adapterKoreanCategory);

        String testId = "dandelion";
        service = ApplicationController.getInstance().getNetworkService();
        Call<MakeEstimateItemResult> makeEstimateItemResultCall = service.getMakeEstimateItemResult(pgId);
        makeEstimateItemResultCall.enqueue(new Callback<MakeEstimateItemResult>() {
            @Override
            public void onResponse(Call<MakeEstimateItemResult> call, Response<MakeEstimateItemResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().result) {
                        arrProduct = new ArrayList<String>();
                        arrProductId = new ArrayList<Integer>();
                        arrProduct.add("상품선택");
                        for (int i = 0; i < response.body().items.size(); i++) {
                            arrProduct.add(response.body().items.get(i).item_name);
                            arrProductId.add(response.body().items.get(i).id);
                        }
                        adapterProduct = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner_make_estimate, arrProduct);
                        adapterProduct.setDropDownViewResource(R.layout.item_spinner_make_estimate_dropdown);
                        spinnerProduct.setAdapter(adapterProduct);
                    }
                }
            }

            @Override
            public void onFailure(Call<MakeEstimateItemResult> call, Throwable t) {

            }
        });

        ArrayAdapter<CharSequence> adapterParts = ArrayAdapter.createFromResource(this, R.array.parts, R.layout.item_spinner_make_estimate);
        adapterParts.setDropDownViewResource(R.layout.item_spinner_make_estimate_dropdown);
        spinnerParts.setAdapter(adapterParts);

        // 날짜선택 버튼 클릭 시 달력띄우기
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MakeEstimateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String month = (monthOfYear < 10 ? "0" : "") + monthOfYear;
        String day = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
        String date = year + "-" + month + "-" + day;
        dateTextView.setText(date);

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}