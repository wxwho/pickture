package pickture.sopt20th.com.pickutre.Application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.OkHttpClient;
import pickture.sopt20th.com.pickutre.Network.NetworkService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {

    // 먼저 어플리케이션 인스턴스 객체를 하나 선언
    private static ApplicationController instance;

    //
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // 베이스 url 초기화
    private static String baseUrl = "baseUrl";

    // 네트워크 서비스 객체 선언
    private NetworkService networkService;

    // 인스턴스 객체 반환  왜? static 안드에서 static 으로 선언된 변수는 매번 객체를 새로 생성하지 않아도 다른 액티비티에서
    // 자유롭게 사용가능합니다.
    public static ApplicationController getInstance() {
        return instance;
    }

    // 네트워크서비스 객체 반환
    public NetworkService getNetworkService() {
        return networkService;
    }

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    //인스턴스 객체 초기화
    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationController.instance = this;
        buildService();

    }

    public void buildService() {
        // Maven에서 포함한 PersistentCookieJar를 통해 쿠키 세팅
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
}
