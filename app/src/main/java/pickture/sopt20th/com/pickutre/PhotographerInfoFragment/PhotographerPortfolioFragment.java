package pickture.sopt20th.com.pickutre.PhotographerInfoFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Activity.EnlargePhotoActivity;
import pickture.sopt20th.com.pickutre.Adapter.PhotographerPortfoilioGridAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.PhotographerPortfolioResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotographerPortfolioFragment extends Fragment {

    private NetworkService service;
    private ArrayList<PhotographerPortfolioResult.PicturesData> item;
    private PhotographerPortfoilioGridAdapter adapter;
    private View v;
    private GridView gridView;
    private String pgId;
    private int[] photo_id;
    private String[] imageUrl;
    private boolean[] picked;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Bundle getbundle = getArguments();
        pgId = getbundle.getString("pgId");
        service = ApplicationController.getInstance().getNetworkService();
        bundle = new Bundle();

        String testId = "dandelion";
        getNetworkData();


        v = inflater.inflate(R.layout.fragment_photographer_portfolio, container, false);
        gridView = (GridView) v.findViewById(R.id.fpp_gridView);





        //TODO 이미지 클릭시 이벤트 처리
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EnlargePhotoActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNetworkData();
    }

    private void getNetworkData() {
        Call<PhotographerPortfolioResult> photographerPortfolioResultCall = service.getPotfolioResult(pgId);
        photographerPortfolioResultCall.enqueue(new Callback<PhotographerPortfolioResult>() {
            @Override
            public void onResponse(Call<PhotographerPortfolioResult> call, Response<PhotographerPortfolioResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        item = response.body().pictures;
                        adapter = new PhotographerPortfoilioGridAdapter (
                                getActivity(),
                                R.layout.item_image_grid,
                                response.body().pictures);
                        gridView.setAdapter(adapter);

                        bundle.putSerializable("pictureDatas", response.body().pictures);

                    } else {
                        //ApplicationController.showToast(getActivity(), "통신오류");
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotographerPortfolioResult> call, Throwable t) {
                //ApplicationController.showToast(getActivity(), "통신실패");
            }
        });
    }
}
