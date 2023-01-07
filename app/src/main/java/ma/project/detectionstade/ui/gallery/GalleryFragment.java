package ma.project.detectionstade.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ma.project.detectionstade.R;
import ma.project.detectionstade.databinding.FragmentGalleryBinding;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.model.Patient;
import ma.project.detectionstade.viewModel.DetectionViewModel;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private DetectionViewModel detectionViewModel;
    private DetectionViewModel detectionViewModel1;

    Spinner spinner,spinner2;
    Button next;
    private List<String> tab = new ArrayList<>();
    private List<String> tab1 = new ArrayList<>();
    private List<String> tab2 = new ArrayList<>();
    private List<Patient> patientList = new ArrayList<>();
    private List<Maladie> maladieList = new ArrayList<>();
    private Maladie maladie;
    private Patient patient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detectionViewModel1 = DetectionViewModel.getInit();
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        detectionViewModel = new ViewModelProvider(this).get(DetectionViewModel.class);
        spinner = root.findViewById(R.id.spinner);
        spinner2 = root.findViewById(R.id.spinner2);
        next = root.findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        searchPatientsApi();
        ObserveAnyChange();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void ObserveAnyChange(){
        detectionViewModel.getPatients().observe(getActivity(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> restaurants) {
                if(restaurants != null){
                    for(Patient restaurant : restaurants){
                        System.out.println(restaurant.getNom());
                    }
                    List<String> produitName= new ArrayList<>();
                    List<Patient> produit= new ArrayList<>();
                    produit = restaurants;
                    patientList = restaurants;
                    for(Patient c:produit){
                        produitName.add(c.getNom()+" "+c.getPrenom());
                        System.out.println("hgvhgv"+c.getNom());
                    }
                    tab2 = produitName;
                    if (this!=null){
                        ArrayAdapter<String> adap1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tab2);
                        adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adap1);
                    }
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            patient = patientList.get(i);
                            detectionViewModel1.setPatient(patient);
                            searchMaladiesApi(patient.getId());
                            ObserveAnyChange1();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

            }
        });
    }
    private void ObserveAnyChange1(){
        detectionViewModel.getMaladies().observe(this, new Observer<List<Maladie>>() {
            @Override
            public void onChanged(List<Maladie> restaurants) {
                if(restaurants != null){
                    for(Maladie restaurant : restaurants){
                        System.out.println(restaurant.getNom());
                    }
                    List<String> produitName= new ArrayList<>();
                    List<Maladie> produit= new ArrayList<>();
                    produit = restaurants;
                    maladieList = restaurants;
                    for(Maladie c:produit){
                        produitName.add(c.getNom());
                        System.out.println("maladie: "+c.getNom());
                    }
                    tab = produitName;
                    if (this!=null){
                        ArrayAdapter<String> adap2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tab);
                        adap2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adap2);
                    }
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            maladie = maladieList.get(i);
                            detectionViewModel1.setMaladie(maladie);
                            System.out.println(maladie);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

            }
        });
    }

    private void searchPatientsApi(){
        detectionViewModel.searchPatientsApi();
    }
    private void searchMaladiesApi(int patient){
        detectionViewModel.searchMaladiesApi(patient);
    }
}