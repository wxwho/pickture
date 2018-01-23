package pickture.sopt20th.com.pickutre.PhotographerInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Adapter.PgReviewAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Item.PgReviewItem;
import pickture.sopt20th.com.pickutre.Model.PhotographerReviewResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  PhotographerReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<PgReviewItem> itemdata;
    private PgReviewAdapter pgReviewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NetworkService service;
    private String pgId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_photographer_review, container, false);

        Bundle bundle = getArguments();
        pgId = bundle.getString("pgId");



        recyclerView = (RecyclerView)view.findViewById(R.id.fpr_recyclerview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);




        // 서버통신
        service = ApplicationController.getInstance().getNetworkService();
        String testId = "dandelion";
        Call<PhotographerReviewResult> photographerReviewResultCall= service.getPhotographerReviewResult(pgId);
        //Call<PhotographerReviewResult> photographerReviewResultCall= service.getPhotographerReviewResult(pgId);
        photographerReviewResultCall.enqueue(new Callback<PhotographerReviewResult>() {
            @Override
            public void onResponse(Call<PhotographerReviewResult> call, Response<PhotographerReviewResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        pgReviewAdapter= new PgReviewAdapter(response.body().reviews);
                        recyclerView.setAdapter(pgReviewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotographerReviewResult> call, Throwable t) {

            }
        });



        return view;
    }
}
