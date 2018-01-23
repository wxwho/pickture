package pickture.sopt20th.com.pickutre.Item;

/**
 * Created by EOJIN on 2017-06-28.
 */

public class ModelApplyBoxItem {
    private String index;
    private String nameOfPhotographer;
    private String acceptState;

    public void setIndex(String indexNum){
        index = indexNum;
    }

    public void setNameOfPhotographer(String name){
        nameOfPhotographer = name;
    }

    public void setAcceptState(String state){
        acceptState = state;
    }

    public String getIndex(){
        return this.index;
    }

    public String getNameOfPhotographer(){
        return this.nameOfPhotographer;
    }

    public String getAcceptState(){
        return this.acceptState;
    }

}

