package ma.project.detectionstade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import ma.project.detectionstade.model.JwtResponse;
import ma.project.detectionstade.retrofit.RetrofitService;
import ma.project.detectionstade.viewModel.DetectionViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView registerLien;
    private DetectionViewModel detectionViewModel;
    private DetectionViewModel detectionViewModel1;

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        detectionViewModel = new ViewModelProvider(this).get(DetectionViewModel.class);
        detectionViewModel1 = DetectionViewModel.getInit();
        Window window;
        window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.teal_200));

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                ObserveAnyChange(emailValue,passwordValue);

            }
        });
    }

    private void ObserveAnyChange(String email, String password){
        detectionViewModel.loginApi(email,password).observe(this, new Observer<JwtResponse>() {
            @Override
            public void onChanged(JwtResponse check) {
                if(check != null){
                    Toast.makeText(LoginActivity.this, check.getAccessToken(), Toast.LENGTH_SHORT).show();

//                    if(check.equals("done")){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
//                    }else{
//                        Toast.makeText(LoginActivity.this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
//                    }

                }

            }
        });
    }


    private LiveData<JwtResponse> loginApi(String email, String password){
        return detectionViewModel.loginApi(email,password);
    }
}