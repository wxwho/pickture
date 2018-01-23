package pickture.sopt20th.com.pickutre.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Adapter.PurchaseAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.PurchaseListResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseListActivity extends AppCompatActivity {

    private ArrayList<PurchaseListResult.purchaseData> datas;

    private ListView listview;
    private PurchaseAdapter adapter;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        listview= (ListView)findViewById(R.id.apl_listview);
        datas = new ArrayList<PurchaseListResult.purchaseData>();

        service = ApplicationController.getInstance().getNetworkService();

        getNetworkData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData();
    }

    private void getNetworkData() {
        Call<PurchaseListResult> purchaseListResultCall = service.getPurchaseListResult();
        purchaseListResultCall.enqueue(new Callback<PurchaseListResult>() {
            @Override
            public void onResponse(Call<PurchaseListResult> call, Response<PurchaseListResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
//                        Log.i("debug", "통신성공");
                        datas = response.body().pay_history;
                        adapter =  new PurchaseAdapter(datas);
                        listview.setAdapter(adapter);
                    }
                    else {
//                        Log.i("debug", "통신오류");
                    }
                }
            }

            @Override
            public void onFailure(Call<PurchaseListResult> call, Throwable t) {
//                Log.i("debug", "통신에러");
            }
        });
    }
}