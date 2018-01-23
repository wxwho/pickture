package pickture.sopt20th.com.pickutre.Item;

import android.graphics.Color;

/**
 * Created by EOJIN on 2017-06-28.
 */

public class EstimateItem {
    private String index;
    private String nameOfPhotographer;
    private String responseState;
    private Color fontColor;

    public void setIndex(String indexNum){
        index = indexNum;
    }

    public void setNameOfPhotographer(String name){
        nameOfPhotographer = name;
    }

    public void setResponseState(String state){
        responseState = state;
    }

    public String getIndex(){
        return this.index;
    }

    public String getNameOfPhotographer(){
        return this.nameOfPhotographer;
    }

    public String getResponseState(){
        return this.responseState;
    }
}