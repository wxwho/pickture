package pickture.sopt20th.com.pickutre.MyPickFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Activity.MyPickEnlargePhotoActivity;
import pickture.sopt20th.com.pickutre.Adapter.MyPickPhotoListAdapter;
import pickture.sopt20th.com.pickutre.Adapter.MyPickPhotoListDelAdapter;
import pickture.sopt20th.com.pickutre.Application.ApplicationController;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoDelResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoResult;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import pickture.sopt20th.com.pickutre.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPickPhotoFragment extends Fragment{

    final static public int STATE_MODIFY = 0;
    final static public int STATE_DELETE = 1;
    public static int btnState = 0;

    private Button button;
    private GridView gridView;
    private MyPickPhotoListAdapter adapter;
    private MyPickPhotoListDelAdapter delAdapter;
    private ProgressDialog mProgressDialog;
    Handler handler;

    private NetworkService service;

    private ArrayList<MyPickPhotoResult.PhotoData> tmpData;
    private ArrayList<String> deleteQueue;

    public MyPickPhotoFragment(){
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_mypick_photo, container, false);

        button = (Button) view.findViewById(R.id.fmp_btn);
        deleteQueue = new ArrayList<String>();
        mProgressDialog = new ProgressDialog(getActivity());
        handler = new Handler();
        service = ApplicationController.getInstance().getNetworkService();

        getNetworkData();


        gridView = (GridView) view.findViewById(R.id.fmp_gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(btnState == STATE_MODIFY) {
                    Intent intent = new Intent(getActivity(), MyPickEnlargePhotoActivity.class);
                    intent.putExtra("position", position);
                }
                else if(btnState == STATE_DELETE) {
                    CheckBox cb = (CheckBox) view.findViewById(R.id.impld_checkbox);
                    if (cb.isChecked()) {
                        // 삭제대기큐에 삽입
                        deleteQueue.add(tmpData.get(position).photo_id + "");
                        Log.i("debug", tmpData.get(position).photo_id + "");
                        //ApplicationController.showToast(getActivity(), position + "번 리스트 체크됨");
                    } else {
                        // 삭제대기큐에서 해제
                        Log.i("debug", tmpData.get(position).photo_id + "");
                        deleteQueue.remove(tmpData.get(position).photo_id + "");
                        //ApplicationController.showToast(getActivity(), position + "번 리스트 체크해제");
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnState == STATE_MODIFY) {
                    btnState = STATE_DELETE;
                    button.setText("삭제");
                    gridView.setAdapter(delAdapter);

                }
                else if(btnState == STATE_DELETE) {
                    btnState = STATE_MODIFY;
                    button.setText("수정");
                    // 삭제할 목록 서버로 전송
                    delNetworkData(deleteQueue);
                    mProgressDialog.setMessage("삭제중입니다..");
                    mProgressDialog.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                        }
                    }, 3500);
                    getNetworkData();
                    adapter.notifyDataSetChanged();
                }
            }
        });


        return view;
    }

    private void delNetworkData(final ArrayList<String> deleteQueue) {
        if(deleteQueue.isEmpty()) {
            return;
        }
        for(int i = 0; i < deleteQueue.size(); i++) {
            Log.i("debug", deleteQueue.get(i) + "");
            Call<MyPickPhotoDelResult> myPickPhotoDelResultCall = service.getMyPickPhotoDelResult((Integer.parseInt(deleteQueue.get(i))));
            myPickPhotoDelResultCall.enqueue(new Callback<MyPickPhotoDelResult>() {
                @Override
                public void onResponse(Call<MyPickPhotoDelResult> call, Response<MyPickPhotoDelResult> response) {
                    if(response.isSuccessful()) {
                        if(response.body().result) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<MyPickPhotoDelResult> call, Throwable t) {

                }
            });
        }
        ApplicationController.showToast(getActivity(), "사진이 마이픽에서 삭제되었습니다.");
    }

    private void getNetworkData() {
        Call<MyPickPhotoResult> myPickPhotoResultCall = service.getMyPickPhotoResult();
        myPickPhotoResultCall.enqueue(new Callback<MyPickPhotoResult>() {
            @Override
            public void onResponse(Call<MyPickPhotoResult> call, Response<MyPickPhotoResult> response) {
                if(response.isSuccessful()) {
                    if(response.body().result) {
                        tmpData = response.body().photos;
                        adapter = new MyPickPhotoListAdapter(response.body().photos);
                        delAdapter = new MyPickPhotoListDelAdapter(response.body().photos);
                        gridView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPickPhotoResult> call, Throwable t) {

            }
        });
    }
}