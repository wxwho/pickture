package pickture.sopt20th.com.pickutre.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Adapter.ModelApplyAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.ModelApplyBoxResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelApplyBoxActivity extends AppCompatActivity {

    private NetworkService service;
    private ArrayList<ModelApplyBoxResult.applyData> applyDatas;
    private ListView listview;
    private ModelApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_apply_box);

        listview = (ListView) findViewById(R.id.amab_listview);
        loadData();

    }
    private void loadData() {
        service = ApplicationController.getInstance().getNetworkService();

        Call<ModelApplyBoxResult> modelApplyBoxResultCall = service.getModelApplyBoxResult();
        modelApplyBoxResultCall.enqueue(new Callback<ModelApplyBoxResult>() {
            @Override
            public void onResponse(Call<ModelApplyBoxResult> call, Response<ModelApplyBoxResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        applyDatas = response.body().applies;
                        adapter = new ModelApplyAdapter(applyDatas);
                        listview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelApplyBoxResult> call, Throwable t) {

            }
        });
    }
}