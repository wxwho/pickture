package pickture.sopt20th.com.pickutre.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pickture.sopt20th.com.pickutre.Model.ModelApplyBoxResult;
import pickture.sopt20th.com.pickutre.R;

/**
 * Created by EOJIN on 2017-06-28.
 */
public class ModelApplyAdapter extends BaseAdapter {
    private ArrayList<ModelApplyBoxResult.applyData> item;

    public ModelApplyAdapter(ArrayList<ModelApplyBoxResult.applyData> item){
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_model_apply, parent, false);
        }

        TextView indexView = (TextView) convertView.findViewById(R.id.lma_index);
        TextView photographerNameTextView = (TextView) convertView.findViewById(R.id.lma_photographer_name);
        TextView acceptStateTextView = (TextView) convertView.findViewById(R.id.lma_accept_state);


        indexView.setText((position < 10 ? "0" : "") + position);
        photographerNameTextView.setText(item.get(position).photographer_id.photographer_id);
        acceptStateTextView.setText(item.get(position).status);

        if((item.get(position).status).equals("답변대기")){
            acceptStateTextView.setTextColor(Color.parseColor("#939393"));
        }else if((item.get(position).status).equals("수락")){
            acceptStateTextView.setTextColor(Color.parseColor("#075804"));
        }else if((item.get(position).status).equals("거절")){
            acceptStateTextView.setTextColor(Color.parseColor("#C20A0A"));
        }

        return convertView;
    }

}