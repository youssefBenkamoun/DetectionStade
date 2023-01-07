package ma.project.detectionstade.ui.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cloudinary.android.MediaManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ma.project.detectionstade.BuildConfig;
import ma.project.detectionstade.R;
import ma.project.detectionstade.viewModel.DetectionViewModel;


public class AddFragment extends Fragment {

    private DetectionViewModel detectionViewModel1;
    private static final String TAG = "Upload ###";
    private DetectionViewModel detectionViewModel;
    private static int IMAGE_REQ=1;
    private Uri imagePath;
    Map config = new HashMap();
    String imageURL;
    ActivityResultLauncher<Intent> activityResultLuncher;
    private ImageView imageView;
    private Button button, btn, prendre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detectionViewModel1 = DetectionViewModel.getInit();
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        detectionViewModel = new ViewModelProvider(this).get(DetectionViewModel.class);
        imageView=root.findViewById(R.id.imageView);
        button=root.findViewById(R.id.button);
        prendre=root.findViewById(R.id.prendre);

        prendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent,0);
                activityResultLuncher.launch(intent);
            }
        });

        activityResultLuncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Bundle extras = result.getData().getExtras();
                Uri imageUri;
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                WeakReference<Bitmap> result1 = new WeakReference<>(Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getHeight(), imageBitmap.getWidth(), false).copy(Bitmap.Config.RGB_565, true));
                Bitmap bm = result1.get();
                imageUri = saveImage(bm,getContext());
                imagePath = imageUri;
                detectionViewModel1.setImagePath(imagePath);
                System.out.println(imageUri+"");
                Picasso.get().load(imagePath).into(imageView);

            }
        });
        imageView.setOnClickListener(v -> {
            /*
             * 1.1. ask the user to give the media permission
             * 1.2. moving to the gallery
             *
             * */

            //1.1
            requestPermission();
            Log.d(TAG, ": "+"request permission");
        });




        return root;
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imagePath=data.getData();
                        detectionViewModel1.setImagePath(imagePath);
                        Picasso.get().load(imagePath).into(imageView);
                        System.out.println(imagePath);

                    }
                }
            });

    private void initCongif() {

        config.put("cloud_name", "dvwugomay");
        config.put("api_key","799653187745121");
        config.put("api_secret","ELCqgrjDSa9aQjMUlzg-y5RmpDg");
//        config.put("secure", true);
        MediaManager.init(getContext(), config);
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        {
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },IMAGE_REQ);
        }

    }
    private Uri saveImage(Bitmap image, Context context){
        File imageFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try{
            imageFolder.mkdir();
            File file = new File(imageFolder, "captured_image.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                    BuildConfig.APPLICATION_ID + ".provider", file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
    /*
     * sele the image from the gallery
     * */
    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");// if you want to you can use pdf/gif/video
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);

    }
}