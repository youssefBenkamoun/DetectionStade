package ma.project.detectionstade.viewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import ma.project.detectionstade.model.Detection;
import ma.project.detectionstade.model.JwtResponse;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.model.Patient;
import ma.project.detectionstade.model.Stade;
import ma.project.detectionstade.repository.DetectionRepository;


public class DetectionViewModel extends ViewModel {

    private static DetectionViewModel init;

    public static DetectionViewModel getInit(){
        if(init == null){
            init = new DetectionViewModel();
        }
        return init;
    }

    private Maladie maladie;
    private Stade stade;
    private Patient patient;
    private Uri imagePath;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Stade getStade() {
        return stade;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Maladie getMaladie() {
        return maladie;
    }

    public void setMaladie(Maladie maladie) {
        this.maladie = maladie;
    }

    private DetectionRepository detectionRepository;

    public DetectionViewModel(){
        detectionRepository = DetectionRepository.getInstance();
    }

    public LiveData<List<Patient>> getDetections(){
        return  detectionRepository.getDetection();
    }
    public LiveData<List<Patient>> getPatients(){
        return  detectionRepository.getDetection();
    }
    public LiveData<List<Maladie>> getMaladies(){
        return  detectionRepository.getMaladie();
    }
    public LiveData<List<Stade>> getStades(){
        return  detectionRepository.getStade();
    }

    public void searchPatientsApi(){
        detectionRepository.searchDetectionsApi();
    }
    public void searchMaladiesApi(int patient){
        detectionRepository.searchMaladiesApi(patient);
    }
    public void searchStadesApi(Maladie maladie){
        detectionRepository.searchStadesApi(maladie);
    }
    public void addDetectionsApi(Detection detection, Maladie maladie){
        detectionRepository.addDetectionsApi(detection, maladie);
    }
    public LiveData<JwtResponse> loginApi(String email, String password){
        return detectionRepository.loginApi(email,password);
    }
}
