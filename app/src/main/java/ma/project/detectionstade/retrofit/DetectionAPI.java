package ma.project.detectionstade.retrofit;

import java.util.HashMap;
import java.util.List;
;
import ma.project.detectionstade.model.Detection;
import ma.project.detectionstade.model.JwtResponse;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.model.Patient;
import ma.project.detectionstade.model.Stade;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DetectionAPI {

    @GET("/patient/all")
    Call<List<Patient>> getAll();

    @GET("/maladie/alla")
    Call<List<Maladie>> getAllMaladies(@Query("patient") int patient);


    @GET("/stade/byMaladiee")
    Call<List<Stade>> getAllStades(@Query("id") int id);


    @Headers("Content-Type:application/json")
    @POST("/detection/adda")
    Call<Detection> add(@Body HashMap map );

    @GET("/logina")
    Call<String> login(@Query("email") String email, @Query("password") String password);

    @POST("/api/auth/signin")
    Call<JwtResponse> signin(@Body HashMap<String,String> map);

}
