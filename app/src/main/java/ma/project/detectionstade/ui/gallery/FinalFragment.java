package ma.project.detectionstade.ui.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ma.project.detectionstade.R;
import ma.project.detectionstade.model.Maladie;
import ma.project.detectionstade.model.Patient;
import ma.project.detectionstade.model.Stade;
import ma.project.detectionstade.viewModel.DetectionViewModel;


public class FinalFragment extends Fragment {

    private DetectionViewModel detectionViewModel;
    private DetectionViewModel detectionViewModel1;
    private List<Stade> patientList = new ArrayList<>();
    private List<String> tab2 = new ArrayList<>();
    Spinner spinner;
    private Stade stade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detectionViewModel1 = DetectionViewModel.getInit();
        View root = inflater.inflate(R.layout.fragment_final, container, false);
        detectionViewModel = new ViewModelProvider(this).get(DetectionViewModel.class);
        spinner = root.findViewById(R.id.spinner3);
        searchStadesApi(detectionViewModel1.getMaladie());
        ObserveAnyChange();
        Toast.makeText(getActivity(), " "+detectionViewModel1.getMaladie(), Toast.LENGTH_SHORT).show();

        return root;
    }

    private void ObserveAnyChange(){
        detectionViewModel.getStades().observe(getActivity(), new Observer<List<Stade>>() {
            @Override
            public void onChanged(List<Stade> restaurants) {
                if(restaurants != null){
                    for(Stade restaurant : restaurants){
                        System.out.println(restaurant.getDescription());
                    }
                    List<String> produitName= new ArrayList<>();
                    List<Stade> produit= new ArrayList<>();
                    produit = restaurants;
                    patientList = restaurants;
                    for(Stade c:produit){
                        produitName.add(c.getDescription());
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
                            stade = patientList.get(i);
                            detectionViewModel1.setStade(stade);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

            }
        });
    }

    private void searchStadesApi(Maladie maladie){
        detectionViewModel.searchStadesApi(maladie);
    }
}