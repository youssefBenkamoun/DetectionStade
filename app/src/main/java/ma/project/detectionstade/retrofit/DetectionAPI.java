package ma.project.detectionstade.retrofit;

import java.util.HashMap;
import java.util.List;
;
import ma.project.detectionstade.model.Detection;
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

    @GET("/maladie/all")
    Call<List<Maladie>> getAllMaladies(@Query("patient") int patient);


    @GET("/stade/byMaladie")
    Call<List<Stade>> getAllStades(@Query("id") int id);


    @Headers("Content-Type:application/json")
    @POST("/detection/add")
    Call<Detection> add(@Body HashMap map );

    @GET("/login")
    Call<String> login(@Query("email") String email, @Query("password") String password);

}
