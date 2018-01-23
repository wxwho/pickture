package pickture.sopt20th.com.pickutre.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ksj on 2017. 7. 6..
 */


public class EstimateListResult{
    public boolean result;
    public ArrayList<requestData> requests;

    @SuppressWarnings("serial")
    public class requestData implements Serializable {
        public int id;
        public String model_id;
        public String category; //희망 카테고리
        public SelectedProduct item;    //희망 상품.. 서버에서는 객체로 줌
        public String date; //희망 날짜
        public int cntPeople;   //희망 인원
        public String placePrefer;  //희망 장소
        public String detailComment;    //세부 희망 사항
        public String status;   //답변상태
        public String photoResponse;    //답변내용
        public String timestamp;    //필요없음
        public String pay_at;   //입금한 시간
        public String type; //필요없음
        public ArrayList<String> photos;    //첨부사진
        public PhotographerDataOnResponse photographer;//작가 정보
    }

    @SuppressWarnings("serial")
    public class SelectedProduct implements Serializable{
        public String item_name;
    }

    @SuppressWarnings("serial")
    public class PhotographerDataOnResponse implements  Serializable{
        public String id;
        public String profile_url;
        public String location;
        public String brandName;
        public String ceo;
        public String address;
        public String phoneNum;
        public String email;
    }
}