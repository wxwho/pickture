package pickture.sopt20th.com.pickutre.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Activity.PhotographerInfoActivity;
import pickture.sopt20th.com.pickutre.Adapter.SearchAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.SearchResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by ksj on 2017. 6. 27..
 */

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<SearchResult.SearchData> itemdata;
    private SearchAdapter adapterSearch;
    private LinearLayoutManager linearLayoutManager;
    private EditText search_edit;
    private NetworkService service;

    private String searchText;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_search, container, false);

        search_edit=(EditText)view.findViewById(R.id.fs_edit);
        recyclerView = (RecyclerView)view.findViewById(R.id.fs_recyclerview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);             //리니어레이아웃의 형태이면 방향은 수직
        recyclerView.setLayoutManager(linearLayoutManager);

        service = ApplicationController.getInstance().getNetworkService();

        search_edit.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = search_edit.getText().toString();
                //Log.i("debug", searchText);
                getNetworkData(searchText);
                //입력이 끝난 후 처리를 담당
            }
        });

        return view;
    }

    private void getNetworkData(final String searchText) {
        Call<SearchResult> SearchResultCall = service.getSearchResult(searchText);
        // onResponse에서 200~300은 정상 400~500은 비정상, 이를 isSuccessful로 판단
        // 서버에서 응답이 없거나 아예 접근이 안될 때(잘못되었을 때) onFailure로 감
        SearchResultCall.enqueue(new Callback<SearchResult>()
        {
            @Override
            public void onResponse(Call < SearchResult > call, Response< SearchResult > response){
                if (response.isSuccessful()) {
                    if (response.body().result) {
                        itemdata = response.body().photographers;
                        adapterSearch = new SearchAdapter(getContext(),response.body().photographers,clickEvent);
                        recyclerView.setAdapter(adapterSearch);
                        adapterSearch.filter(searchText);
                        //ApplicationController.showToast(getActivity(), "검색 성공!");
                    } else {
                        //ApplicationController.showToast(getActivity(), "검색 실패..! ");
                    }
                } else {
                    //ApplicationController.showToast(getActivity(), "서버통신오류");
                }
            }

            @Override
            public void onFailure (Call < SearchResult > call, Throwable t){
                //Toast.makeText(getContext(), "통신실패", Toast.LENGTH_SHORT).show();
                // 통신실패이유가 t에 저장됨
                //Log.i("debug", t.toString());
            }
        });

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerView.getChildPosition(v);           //position 을 지원하지 않는다 따라서 직접 얻어와야함
            Intent intent = new Intent(getApplicationContext(), PhotographerInfoActivity.class);
            intent.putExtra("pgId", itemdata.get(itemPosition).id);
            intent.putExtra("pgProfileUrl", itemdata.get(itemPosition).profile_url);
            intent.putExtra("location", itemdata.get(itemPosition).location);
            intent.putExtra("picked", itemdata.get(itemPosition).picked);
            intent.putExtra("hashtag", itemdata.get(itemPosition).hashtag);
            startActivity(intent);
        }
    };
}