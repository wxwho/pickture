package pickture.sopt20th.com.pickutre.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import pickture.sopt20th.com.pickutre.Activity.EstimateBoxActivity;
import pickture.sopt20th.com.pickutre.Activity.LoginActivity;
import pickture.sopt20th.com.pickutre.Activity.ModelApplyBoxActivity;
import pickture.sopt20th.com.pickutre.Activity.PurchaseListActivity;
import pickture.sopt20th.com.pickutre.Dialog.OnlyForPhotographerDialog;
import pickture.sopt20th.com.pickutre.R;

public class MyPageFragment extends Fragment {

    Button[] mypage_user = new Button[3];
    Button[] mypage_photographer = new Button[5];
    Button[] mypage_setting = new Button[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        mypage_user[0] = (Button) view.findViewById(R.id.fmp_estimate_box_btn);
        mypage_user[1] = (Button) view.findViewById(R.id.fmp_model_application_box_btn);
        mypage_user[2] = (Button) view.findViewById(R.id.fmp_purchase_list_btn);

        mypage_photographer[0] = (Button) view.findViewById(R.id.fmp_page_management_btn);
        mypage_photographer[1] = (Button) view.findViewById(R.id.fmp_estimate_management_btn);
        mypage_photographer[2] = (Button) view.findViewById(R.id.fmp_model_requiring_management_btn);
        mypage_photographer[3] = (Button) view.findViewById(R.id.fmp_model_requiring_box_btn);
        mypage_photographer[4] = (Button) view.findViewById(R.id.fmp_model_application_management_btn);

        mypage_setting[0] = (Button) view.findViewById(R.id.fmp_setting_btn);
        mypage_setting[1] = (Button) view.findViewById(R.id.fmp_setting_logout);

        mypage_user[0].setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), EstimateBoxActivity.class);
                startActivity(intent1);
            }
        });

        mypage_user[1].setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), ModelApplyBoxActivity.class);
                startActivity(intent2);
            }
        });

        mypage_user[2].setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), PurchaseListActivity.class);
                startActivity(intent3);
            }
        });

        for (int i = 0; i < 5; i++) {
            mypage_photographer[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), OnlyForPhotographerDialog.class));
                }
            });
        }
        mypage_setting[0].setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "서비스 준비중입니다", Toast.LENGTH_SHORT).show();
            }
        });

        mypage_setting[1].setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                Toast.makeText(getActivity(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}