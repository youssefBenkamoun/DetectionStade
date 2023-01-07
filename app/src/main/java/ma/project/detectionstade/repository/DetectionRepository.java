package ma.project.detectionstade.repository;

import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import ma.project.detectionstade.model.Detection;
import ma.project.detectionstade.model.JwtResponse;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.model.Patient;
import ma.project.detectionstade.model.Stade;
import ma.project.detectionstade.response.DetectionClientAPI;


public class DetectionRepository {


    private static DetectionRepository instance;

    private DetectionClientAPI detectionClientAPI;

    public static DetectionRepository getInstance(){

        if(instance == null){
            instance = new DetectionRepository();
        }
        return instance;

    }

    private DetectionRepository(){
        detectionClientAPI = DetectionClientAPI.getInstance();
    }

    public LiveData<List<Patient>> getDetection(){
         return detectionClientAPI.getDetection();
    }
    public LiveData<List<Maladie>> getMaladie(){
        return detectionClientAPI.getMaladie();
    }
    public LiveData<List<Stade>> getStade(){
        return detectionClientAPI.getStade();
    }

    public void searchDetectionsApi(){
        detectionClientAPI.searchDetectionsApi();
    }
    public void searchMaladiesApi(int patient){
        detectionClientAPI.searchMaladiesApi(patient);
    }
    public void searchStadesApi(Maladie maladie){
        detectionClientAPI.searchStadesApi(maladie);
    }
    public void addDetectionsApi(Detection detection, Maladie maladie1){
        detectionClientAPI.addDetectionsApi(detection, maladie1);
    }
    public LiveData<JwtResponse> loginApi(String email, String password){
        detectionClientAPI.loginApi(email,password);
        return detectionClientAPI.getLogin();
    }
}
