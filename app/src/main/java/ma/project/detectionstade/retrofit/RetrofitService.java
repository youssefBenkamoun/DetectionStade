package ma.project.detectionstade.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import ma.project.detectionstade.viewModel.DetectionViewModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static DetectionViewModel detectionViewModel1;

    public static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            detectionViewModel1 = DetectionViewModel.getInit();
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " +detectionViewModel1.getToken() )
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl("http://192.168.1.108:8083")
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create()))
            .build();

    private static DetectionAPI detectionAPI = retrofit.create(DetectionAPI.class);

    public static DetectionAPI getDetectionAPI() {
        return detectionAPI;
    }
}
