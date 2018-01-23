package pickture.sopt20th.com.pickutre.Network;


import android.support.annotation.Nullable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pickture.sopt20th.com.pickutre.Model.BestPhotographerResult;
import pickture.sopt20th.com.pickutre.Model.CategoryResult;
import pickture.sopt20th.com.pickutre.Model.EstimateDetailPayData;
import pickture.sopt20th.com.pickutre.Model.EstimateDetailPayResult;
import pickture.sopt20th.com.pickutre.Model.EstimateListResult;
import pickture.sopt20th.com.pickutre.Model.FindModelResult;
import pickture.sopt20th.com.pickutre.Model.IdValidationResult;
import pickture.sopt20th.com.pickutre.Model.LoginInfo;
import pickture.sopt20th.com.pickutre.Model.LoginInfoResult;
import pickture.sopt20th.com.pickutre.Model.MakeEstimateData;
import pickture.sopt20th.com.pickutre.Model.MakeEstimateItemResult;
import pickture.sopt20th.com.pickutre.Model.MakeEstimateResult;
import pickture.sopt20th.com.pickutre.Model.MakeReviewData;
import pickture.sopt20th.com.pickutre.Model.MakeReviewResult;
import pickture.sopt20th.com.pickutre.Model.ModelApplyBoxResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoDelResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoPickData;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoPickResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotoResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerDelResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerPickData;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerPickResult;
import pickture.sopt20th.com.pickutre.Model.MyPickPhotographerResult;
import pickture.sopt20th.com.pickutre.Model.PhotographerDetailResult;
import pickture.sopt20th.com.pickutre.Model.PhotographerPortfolioResult;
import pickture.sopt20th.com.pickutre.Model.PhotographerReviewResult;
import pickture.sopt20th.com.pickutre.Model.PurchaseListResult;
import pickture.sopt20th.com.pickutre.Model.SearchResult;
import pickture.sopt20th.com.pickutre.Model.SignUpData;
import pickture.sopt20th.com.pickutre.Model.SignUpDataResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @POST("/users/login")
    Call<LoginInfoResult> getLoginInfoResult(@Body LoginInfo loginInfo);

    @GET("/photographers/home2category/{category}")
    Call<CategoryResult> getCategoryResult(@Path("category") String category, @Query("location") String location) ;

    @GET("/users/validation/{id}")
    Call<IdValidationResult> getIdValidationResult(@Path("id") String id);

    @POST("users/register")
    Call<SignUpDataResult> getSignUpDataResult(@Body SignUpData signUpData);

    @GET("/photographers/portfolio/{id}")
    Call<PhotographerPortfolioResult> getPotfolioResult(@Path("id") String id);

    @GET("/users/mypick/photographers")
    Call<MyPickPhotographerResult> getMyPickPhotographerResult();

    @DELETE("/users/mypick/photographers/{photographer_id}")
    Call<MyPickPhotographerDelResult> getMyPickPhotographerDelResult(@Path("photographer_id") String photographer_id);

    @GET("/users/mypick/photos")
    Call<MyPickPhotoResult> getMyPickPhotoResult();

    @DELETE("users/mypick/photos/{photo_id}")
    Call<MyPickPhotoDelResult> getMyPickPhotoDelResult(@Path("photo_id") int id);


    @GET("/photographers/info/{id}")
    Call<PhotographerDetailResult> getPhotographerDetailResult(@Path("id") String id);

    @GET("/photographers/reviews/{id}")
    Call<PhotographerReviewResult> getPhotographerReviewResult(@Path("id") String id);

    @GET("/photographers/best4")
    Call<BestPhotographerResult> getBestPhotographerResult();

    @GET("/photographers/items/{id}")
    Call<MakeEstimateItemResult> getMakeEstimateItemResult(@Path("id") String id);

    @GET("/recruits")
    Call<FindModelResult> getFindModelResult(@Nullable @Query("category") String category, @Nullable@Query("location") String location);

    @POST("/users/mypick/photographers")
    Call<MyPickPhotographerPickResult> getMyPickPhotographerPickResult(@Body MyPickPhotographerPickData data);

    @POST("/users/mypick/photos")
    Call<MyPickPhotoPickResult> getMyPickPhotoPickResult(@Body MyPickPhotoPickData data);

    @GET("/requests")
    Call<EstimateListResult> getEstimateListResult();

    @Multipart
    @POST("/requests")
    Call<MakeEstimateResult> getMakeEstimateResult(@Body MakeEstimateData data, @Part MultipartBody.Part image);

    @GET("/photographers/search/{search}")
    Call<SearchResult> getSearchResult(@Path("search") String search);

    @GET("/payments/history")
    Call<PurchaseListResult> getPurchaseListResult();

    @POST("/payments/review")
    Call<MakeReviewResult> getMakeReviewResult(@Body MakeReviewData data);

    @GET("/recruits/apply")
    Call<ModelApplyBoxResult> getModelApplyBoxResult();

    @POST("/payments/payMoney")
    Call<EstimateDetailPayResult> getEstimateDetailPayResult(@Body EstimateDetailPayData data);

    @Multipart
    @POST("/requests")
    Call<MakeEstimateResult> postRequests(
            @Nullable @Part MultipartBody.Part image1,
            @Nullable @Part MultipartBody.Part image2,
            @Nullable @Part MultipartBody.Part image3,
            @Part("photographer_id") RequestBody photographer_id,
            @Part("category") RequestBody category,
            @Part("placePrefer") RequestBody placePrefer,
            @Part("date") RequestBody date,
            @Part("item") RequestBody item,
            @Part("cntPeople") RequestBody cntPeople,
            @Part("detailComment") RequestBody detailComment
    );

    //@GET("/photographers/search?category={category}")
    //Call<CategoryResult> getCategoryResult(@Path("category") String category);


    /*
        @GET("/lists/{id}")
    Call<DetailResult> getDetailResult(@Path("id") String id);

     */
}

