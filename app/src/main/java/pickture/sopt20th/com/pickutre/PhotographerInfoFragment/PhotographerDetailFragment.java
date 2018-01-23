package pickture.sopt20th.com.pickutre.PhotographerInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import pickture.sopt20th.com.pickutre.Adapter.PgDetailProductAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.PhotographerDetailResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotographerDetailFragment extends Fragment {

    private TextView infoTextview;
    private String pgId;
    private ListView listview;
    private PgDetailProductAdapter adapter;
    private NetworkService service;

    private TextView header_brand;
    private TextView header_ceo;
    private TextView header_address;

    //* 서버에서 가지고 와야 함.
//    String productname;     // product이름
//    String productprice;    // product가격

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view= inflater.inflate(R.layout.fragment_photographer_detail, container, false);

        Bundle bundle = getArguments();
        pgId = bundle.getString("pgId");


        final View header=inflater.inflate(R.layout.viewheader,null,false);
        final View footer=inflater.inflate(R.layout.viewfooter,null,false);
        infoTextview = (TextView) header.findViewById(R.id.v_introduction_brand);
        listview = (ListView) view.findViewById(R.id.fpd_product_listview);
        service = ApplicationController.getInstance().getNetworkService();

        header_brand = (TextView)header.findViewById(R.id.v_introduction_brand);
        header_ceo = (TextView)header.findViewById(R.id.v_introduction_ceo);
        header_address = (TextView)header.findViewById(R.id.v_introduction_address);




        String testId = "dandelion";
        // 서버통신
        Call<PhotographerDetailResult> photographerDetailResultCall = service.getPhotographerDetailResult(pgId);
        photographerDetailResultCall.enqueue(new Callback<PhotographerDetailResult>() {
            @Override
            public void onResponse(Call<PhotographerDetailResult> call, Response<PhotographerDetailResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        header_brand.setText("상호: " + response.body().brand);
                        header_ceo.setText("대표: " + response.body().ceo);
                        header_address.setText("주소: " + response.body().address);
                        listview.addHeaderView(header);
                        listview.addFooterView(footer);
                        adapter = new PgDetailProductAdapter(response.body().items);
                        listview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotographerDetailResult> call, Throwable t) {

            }
        });


//        setListViewHeightBasedOnChildren(listview);


        return view;
    }

//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        PgDetailProductAdapter listAdapter = (PgDetailProductAdapter) listView.getAdapter();
//
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }


}