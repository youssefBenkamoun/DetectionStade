package ma.project.detectionstade.response;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import ma.project.detectionstade.AppExecutors;
import ma.project.detectionstade.model.Detection;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.model.Patient;
import ma.project.detectionstade.model.Stade;
import ma.project.detectionstade.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetectionClientAPI {

    private MutableLiveData<List<Patient>> patients;
    private MutableLiveData<List<Maladie>> maladies;
    private MutableLiveData<List<Stade>> stades;
    private MutableLiveData<String> check;

    private static DetectionClientAPI instance;

    private RetreiveDetectionsRunnable retreiveDetectionsRunnable;

    public static DetectionClientAPI getInstance(){
        if(instance == null){
            instance = new DetectionClientAPI();
        }
        return instance;
    }

    private DetectionClientAPI(){
        patients = new MutableLiveData<>();
        maladies = new MutableLiveData<>();
        stades = new MutableLiveData<>();
        check = new MutableLiveData<>();
    }

    public LiveData<List<Patient>> getDetection(){
        return patients;
    }
    public LiveData<List<Maladie>> getMaladie(){
        return maladies;
    }
    public LiveData<List<Stade>> getStade(){
        return stades;
    }
    public LiveData<String> getLogin(){
        return check;
    }

    public void addDetectionsApi(Detection detection, Maladie maladie){
        addDetections(detection, maladie).enqueue(new Callback<Detection>() {
            @Override
            public void onResponse(Call<Detection> call, Response<Detection> response) {
                Detection users = response.body();
                System.out.println(users);

            }

            @Override
            public void onFailure(Call<Detection> call, Throwable t) {
                System.out.println("walo"+ t.getMessage());
            }
        });
    }
    public void loginApi(String email, String password){
        login(email,password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String users = response.body();
                System.out.println(users);
                check.postValue(users);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("walo"+ t.getMessage());
            }
        });
    }
    public void searchMaladiesApi(int patient){
        getMaladies(patient).enqueue(new Callback<List<Maladie>>() {
            @Override
            public void onResponse(Call<List<Maladie>> call, Response<List<Maladie>> response) {
                List<Maladie> users = response.body();
                System.out.println(users);
                maladies.postValue(users);

            }

            @Override
            public void onFailure(Call<List<Maladie>> call, Throwable t) {
                System.out.println("walo"+ t.getMessage());
                maladies.postValue(null);
            }
        });
    }

    public void searchStadesApi(Maladie maladie){
        getStades(maladie).enqueue(new Callback<List<Stade>>() {
            @Override
            public void onResponse(Call<List<Stade>> call, Response<List<Stade>> response) {
                List<Stade> users = response.body();
                System.out.println(users);
                stades.postValue(users);

            }

            @Override
            public void onFailure(Call<List<Stade>> call, Throwable t) {
                System.out.println("walo"+ t.getMessage());
                stades.postValue(null);
            }
        });
    }



    public void searchDetectionsApi(){
        System.out.println("0");
        if(retreiveDetectionsRunnable !=  null){
            retreiveDetectionsRunnable = null;
            System.out.println("0.5");
        }
        System.out.println("1");

        retreiveDetectionsRunnable = new RetreiveDetectionsRunnable();
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retreiveDetectionsRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
                System.out.println("cancel");
            }
        },15000, TimeUnit.MICROSECONDS);
    }




    private class RetreiveDetectionsRunnable implements Runnable{

        boolean cancelrequest;
        @Override
        public void run() {

            getDetections().enqueue(new Callback<List<Patient>>() {
                @Override
                public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                    List<Patient>users = response.body();
                    System.out.println(users);
                    patients.postValue(users);

                }

                @Override
                public void onFailure(Call<List<Patient>> call, Throwable t) {
                    System.out.println("walo"+ t.getMessage());
                    patients.postValue(null);
                }
            });
            System.out.println("3");
            if (cancelrequest) {
                return;
            }


        }
        private Call<List<Patient>> getDetections(){
            return RetrofitService.getDetectionAPI().getAll();
        }



        private void cancelrequest(){
            Log.v("Tag","Cancelling request");
            cancelrequest = true;
        }



    }

    private Call<Detection> addDetections(Detection detection, Maladie maladie){

        HashMap map = new HashMap<>();
        map.put("detection", detection);
        map.put("maladie", maladie);
        return RetrofitService.getDetectionAPI().add(map);
    }

    private Call<String> login(String email, String password){

        return RetrofitService.getDetectionAPI().login(email,password);
    }

    private Call<List<Maladie>> getMaladies(int patient){
        return RetrofitService.getDetectionAPI().getAllMaladies(patient);
    }

    private Call<List<Stade>> getStades(Maladie maladie){
        System.out.println("youssef hak "+maladie);
        return RetrofitService.getDetectionAPI().getAllStades(maladie.getId());
    }


}
