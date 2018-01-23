package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.EstimateListResult;
import pickture.sopt20th.com.pickutre.R;

/**
 * Created by EOJIN on 2017-06-28.
 */

public class EstimateAdapter extends BaseAdapter {

    private ArrayList<EstimateListResult.requestData> item;

    public EstimateAdapter(ArrayList<EstimateListResult.requestData> item){
        this.item=item;
    }

    @Override
    public int getCount() {
        return item.size() ;
    }

    @Override
    public Object getItem(int position) {
        return item.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(converView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.item_estimate, parent, false);
        }

        TextView indexView = (TextView) converView.findViewById(R.id.le_index);
        TextView photographerNameTextView = (TextView) converView.findViewById(R.id.le_photographer_name);
        TextView responseStateTextView = (TextView) converView.findViewById(R.id.le_response_state);

        indexView.setText((position < 10 ? "0" : "") + position);
        photographerNameTextView.setText(item.get(position).photographer.id);
        responseStateTextView.setText(item.get(position).status);

        if((item.get(position).status).equals("답변대기")){
            responseStateTextView.setTextColor(Color.parseColor("#939393"));
        }else if((item.get(position).status).equals("답변완료")){
            responseStateTextView.setTextColor(Color.parseColor("#E5A71C"));
        }else if((item.get(position).status).equals("예약완료")){
            responseStateTextView.setTextColor(Color.parseColor("#075804"));
        }


//        ListViewEstimateItem listViewEstimate = listViewEstimateList.get(position);
//
//        indexView.setText(listViewEstimate.getIndex());
//        photographerNameTextView.setText(listViewEstimate.getNameOfPhotographer());
//        responseStateTextView.setText(listViewEstimate.getResponseState());
//
//        if(listViewEstimate.getResponseState().equals("답변대기")){
//            responseStateTextView.setTextColor(Color.parseColor("#939393"));
//        }else if(listViewEstimate.getResponseState().equals("답변완료")){
//            responseStateTextView.setTextColor(Color.parseColor("#E5A71C"));
//        }else if(listViewEstimate.getResponseState().equals("예약완료")){
//            responseStateTextView.setTextColor(Color.parseColor("#075804"));
//        }

        return converView;
    }

}