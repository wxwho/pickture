package pickture.sopt20th.com.pickutre.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Adapter.CategoryAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.CategoryResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CategoryResult.Photographers> itemdata;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayAdapter<CharSequence> adapterLocation;

    private TextView categoryTextview;
    private Spinner spinnerLocation;

    private String category;
    private int prePosition;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // 객체 선언
        initView();
        // 객체 설정
        setView();
        // 네트워크 초기화
        initNetwork();
        // 이벤트 초가화
        initEvent();
        // 서버에서 데이텨 가져오기 및 셋팅
        getNetworkData("");

    }

    private void initEvent() {
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getNetworkData(spinnerLocation.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        category = intent.getExtras().getString("category");
        categoryTextview.setText(category.toUpperCase());

        spinnerLocation = (Spinner)findViewById(R.id.ca_region);

        adapterLocation = ArrayAdapter.createFromResource(this, R.array.city, R.layout.item_spinner_category_location);
        adapterLocation.setDropDownViewResource(R.layout.item_spinner_category_location_dropdown);
        spinnerLocation.setAdapter(adapterLocation);

    }

    private void initView() {
        categoryTextview = (TextView) findViewById(R.id.ca_category);
        recyclerView = (RecyclerView)findViewById(R.id.ca_recyclerview);

        linearLayoutManager = new LinearLayoutManager(this);
        //리니어레이아웃의 형태이면 방향은 수직
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    private void initNetwork() {
        service = ApplicationController.getInstance().getNetworkService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData(spinnerLocation.getSelectedItem().toString());
        //TODO 작가인포에서 작가리스트로 돌아왔을 때 이전에 클릭했던 위치로 되돌아오기 구현하기
//        linearLayoutManager.scrollToPosition(prePosition);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        ApplicationController.showToast(getApplicationContext(), prePosition + "");
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함
            //Toast.makeText(getApplicationContext(), itemdata.get(itemPosition).id+"번 리스트 클릭!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), PhotographerInfoActivity.class);
            intent.putExtra("pgId", itemdata.get(itemPosition).id);
            intent.putExtra("pgProfileUrl", itemdata.get(itemPosition).profile_url);
            intent.putExtra("location", itemdata.get(itemPosition).location);
            intent.putExtra("picked", itemdata.get(itemPosition).picked);
            intent.putExtra("hashtag", itemdata.get(itemPosition).hashtag);
            prePosition = itemPosition;
            startActivity(intent);
        }
    };

    public void getNetworkData(String location) {
        location = (location == null || location.equals("지역 선택")) ? "" : location;

        Call<CategoryResult> categoryResultCall = service.getCategoryResult(category, location);
        categoryResultCall.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                if(response.isSuccessful()) {
                    if (response.body().result) {
                        itemdata = response.body().photographers;
                        categoryAdapter = new CategoryAdapter(getApplicationContext(), response.body().photographers, clickEvent);
                        recyclerView.setAdapter(categoryAdapter);
                    }
                    else {
                        ApplicationController.showToast(getApplication(), "데이터 가져오기 실패");
                    }
                }
                else {
                    ApplicationController.showToast(getApplication(), "통신오류");
                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {
                ApplicationController.showToast(getApplication(), "통신실패");
            }
        });

    }
}
