package pickture.sopt20th.com.pickutre.MyPickFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Activity.PhotographerInfoActivity;
import pickture.sopt20th.com.pickutre.Adapter.MyPickPhotographerListAdapter;
import pickture.sopt20th.com.pickutre.Adapter.MyPickPhotographerListDelAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerDelResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyPickPhotographerFragment extends Fragment {

    final static public int STATE_MODIFY = 0;
    final static public int STATE_DELETE = 1;
    static public int btnState = 0;

    private Button button;
    private ListView listview ;
    private MyPickPhotographerListAdapter adapter;
    private MyPickPhotographerListDelAdapter delAdapter;
    private ProgressDialog mProgressDialog;
    Handler handler;
    private NetworkService service;

    private ArrayList<MyPickPhotographerResult.PhotographerData> tmpData;
    private ArrayList<String> deleteQueue;

    public MyPickPhotographerFragment(){
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_mypick_pg, container, false);

        button = (Button) view.findViewById(R.id.fmpg_btn);
        listview = (ListView) view.findViewById(R.id.fmpg_listview);
        deleteQueue =  new ArrayList<String>();
        mProgressDialog = new ProgressDialog(getActivity());
        handler = new Handler();

        service = ApplicationController.getInstance().getNetworkService();

        getNetworkData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnState == STATE_MODIFY) {
                    btnState = STATE_DELETE;
                    button.setText("삭제");
                    listview.setAdapter(delAdapter);

                }
                else if(btnState == STATE_DELETE) {
                    btnState = STATE_MODIFY;
                    button.setText("수정");
                    // TODO 삭제할 목록 서버로 전송
                    delNetworkData(deleteQueue);
                    mProgressDialog.setMessage("삭제중입니다..");
                    mProgressDialog.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                            getNetworkData();
                        }
                    }, 1500);
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(btnState == STATE_DELETE) {
                    CheckBox cb = (CheckBox) view.findViewById(R.id.impgd_checkbox);
                    if (cb.isChecked()) {
                        // 삭제대기큐에 삽입
                        deleteQueue.add(tmpData.get(position).id);
                        //ApplicationController.showToast(getActivity(), position + "번 리스트 체크됨");
                    } else {
                        // 삭제대기큐에서 해제
                        deleteQueue.remove(tmpData.get(position).id);
                        //ApplicationController.showToast(getActivity(), position + "번 리스트 체크해제");
                    }
                }
                else if(btnState == STATE_MODIFY) {
                    // 작가정보페이지로 이동

                    Intent intent = new Intent(getApplicationContext(), PhotographerInfoActivity.class);
                    intent.putExtra("pgId", tmpData.get(position).id);
                    intent.putExtra("pgProfileUrl", tmpData.get(position).profile_url);
                    intent.putExtra("location", tmpData.get(position).location);
                    intent.putExtra("picked", tmpData.get(position).picked);
                    intent.putExtra("hashtag", tmpData.get(position).hashtag);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void delNetworkData(ArrayList<String> deleteQueue) {
        if(deleteQueue.isEmpty()) {
            return;
        }
        for(int i = 0; i < deleteQueue.size(); i++) {
            Call<MyPickPhotographerDelResult> myPickPhotographerResultCall = service.getMyPickPhotographerDelResult(deleteQueue.get(i));
            myPickPhotographerResultCall.enqueue(new Callback<MyPickPhotographerDelResult>() {
                @Override
                public void onResponse(Call<MyPickPhotographerDelResult> call, Response<MyPickPhotographerDelResult> response) {
                    if(response.isSuccessful()) {
                        if(response.body().result) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<MyPickPhotographerDelResult> call, Throwable t) {

                }
            });
        }
        ApplicationController.showToast(getActivity(), "사진이 마이픽에서 삭제되었습니다.");
    }

    private void getNetworkData() {
        Call<MyPickPhotographerResult> myPickPhotographerResultCall = service.getMyPickPhotographerResult();
        myPickPhotographerResultCall.enqueue(new Callback<MyPickPhotographerResult>() {
            @Override
            public void onResponse(Call<MyPickPhotographerResult> call, Response<MyPickPhotographerResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        tmpData = response.body().photographers;
                        adapter = new MyPickPhotographerListAdapter(response.body().photographers) ;
                        delAdapter = new MyPickPhotographerListDelAdapter(response.body().photographers);
                        listview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPickPhotographerResult> call, Throwable t) {

            }
        });
    }


}