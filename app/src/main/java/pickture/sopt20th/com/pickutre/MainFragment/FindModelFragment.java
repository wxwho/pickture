package pickture.sopt20th.com.pickutre.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Activity.ModelRecruitDetailActivity;
import pickture.sopt20th.com.pickutre.Adapter.FindModelAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.FindModelResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindModelFragment extends Fragment {

    private RecyclerView recyclerView;
    private FindModelAdapter findModelAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FindModelResult.recruitData> recruitDatas;

    private  Spinner spinnerCategory;
    private  Spinner spinnerLocation;
    private NetworkService service;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_find_model, container, false);

        spinnerCategory = (Spinner)view.findViewById(R.id.ffm_category);
        ArrayAdapter<CharSequence> adapterCategory= ArrayAdapter.createFromResource(getActivity(), R.array.category, R.layout.item_spinner_model_category);
        adapterCategory.setDropDownViewResource(R.layout.item_spinner_model_category_dropdown);
        spinnerCategory.setAdapter(adapterCategory);

        spinnerLocation = (Spinner)view.findViewById(R.id.ffm_region);
        ArrayAdapter<CharSequence> adapterLocation= ArrayAdapter.createFromResource(getActivity(), R.array.city, R.layout.item_spinner_model_area);
        adapterLocation.setDropDownViewResource(R.layout.item_spinner_model_area_dropdown);
        spinnerLocation.setAdapter(adapterLocation);

        //리사클러뷰
        recyclerView = (RecyclerView)view.findViewById(R.id.ffm_recyclerview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        service = ApplicationController.getInstance().getNetworkService();
        getData(spinnerCategory.getSelectedItem().toString().toLowerCase(), spinnerLocation.getSelectedItem().toString());

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData(spinnerCategory.getSelectedItem().toString().toLowerCase(), spinnerLocation.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData(spinnerCategory.getSelectedItem().toString(), spinnerLocation.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getData(String category, String location){
        location = (location == null || location.equals("지역 선택")) ? "" : location;
        category = (category == null || category.equals("category")) ? "" : category;


        Call<FindModelResult> findModelResultCall = service.getFindModelResult(category, location);
        findModelResultCall.enqueue(new Callback<FindModelResult>() {
            @Override
            public void onResponse(Call<FindModelResult> call, Response<FindModelResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        //TODO 어댑터 설정
                        recruitDatas =  response.body().recruits;
                        findModelAdapter= new FindModelAdapter(response.body().recruits, clickEvent);
                        recyclerView.setAdapter(findModelAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<FindModelResult> call, Throwable t) {

            }
        });


    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
//            Intent intent=new Intent(new Intent(getActivity(), ModelRecruitDetailActivity.class));
            final int itemPosition = recyclerView.getChildPosition(v);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recruits", recruitDatas.get(itemPosition));
            Intent intent = new Intent(getActivity(), ModelRecruitDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };
}