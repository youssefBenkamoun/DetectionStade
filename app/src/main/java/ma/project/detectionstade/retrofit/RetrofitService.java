package ma.project.detectionstade.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {


    private static Retrofit retrofit = new Retrofit.Builder()
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
