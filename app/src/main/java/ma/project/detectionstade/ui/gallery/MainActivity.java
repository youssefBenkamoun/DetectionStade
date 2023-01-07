package ma.project.detectionstade.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.HashMap;
import java.util.Map;

import ma.project.detectionstade.R;
import ma.project.detectionstade.model.Detection;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.viewModel.DetectionViewModel;

public class MainActivity extends AppCompatActivity {
    Button next;
    Map config = new HashMap();
    private static final String TAG = "Upload ###";
    private DetectionViewModel detectionViewModel;
    private DetectionViewModel detectionViewModel1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Detection");

        setContentView(R.layout.activity_main2);
        detectionViewModel = new ViewModelProvider(this).get(DetectionViewModel.class);
        detectionViewModel1 = DetectionViewModel.getInit();
        next = findViewById(R.id.nextButton);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((next.getText().toString()).equals("Next")) {
                    Fragment newFragment = new FinalFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    next.setText("Save");
                }else if((next.getText().toString()).equals("Try Again")) {
                    Intent intent = new Intent(MainActivity.this, ma.project.detectionstade.MainActivity.class);
                    startActivity(intent);
                }else{
                        Log.d(TAG, ": "+" button clicked");
                        initCongif();
                        MediaManager.get().upload(detectionViewModel1.getImagePath()).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Log.d(TAG, "onStart: "+"started");
                                Toast.makeText(MainActivity.this, "In progress...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {
                                Log.d(TAG, "onStart: "+"uploading");

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                Log.d(TAG, "onStart: "+"usuccess");
                                System.out.println(resultData.get("secure_url"));
                                String imageURL = resultData.get("secure_url").toString();
                                addDetectionApi(new Detection(imageURL, detectionViewModel1.getPatient(), detectionViewModel1.getStade()),detectionViewModel1.getMaladie());
//                            Toast.makeText(MainActivity.this, "Detection created successfully", Toast.LENGTH_LONG).show();
                                Fragment newFragment = new SuccessFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentContainerView, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                next.setText("Try Again");

                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d(TAG, "onStart: "+error);
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                                Log.d(TAG, "onStart: "+error);
                            }
                        }).dispatch();


                    }
                }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        next.setText("Next");
    }

    private void initCongif() {

        config.put("cloud_name", "dvwugomay");
        config.put("api_key","799653187745121");
        config.put("api_secret","ELCqgrjDSa9aQjMUlzg-y5RmpDg");
//        config.put("secure", true);
        MediaManager.init(this, config);
    }
    private void addDetectionApi(Detection detection, Maladie maladie){
        detectionViewModel.addDetectionsApi(detection, maladie);
    }
}