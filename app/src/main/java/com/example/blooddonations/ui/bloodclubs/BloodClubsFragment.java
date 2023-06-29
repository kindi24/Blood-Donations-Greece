package com.example.blooddonations.ui.bloodclubs;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonations.databinding.FragmentBloodClubsBinding;

import java.util.ArrayList;

public class BloodClubsFragment extends Fragment {
    private FragmentBloodClubsBinding binding;

    /* DBHelperBloodClubs dB = new DBHelperBloodClubs(getContext());

    // Ορισμός RecyclerView και της ArrayList με τα σημεία που θα εμφανίζονται
    RecyclerView recyclerView;
    ArrayList <String> id, city, prefecture;

    CustomAdapter customAdapter; */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBloodClubsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*
        // Ορισμός της ArrayList κάθε στοιχείου
        id = new ArrayList<>();
        city = new ArrayList<>();
        prefecture = new ArrayList<>();


        // Ορισμός customAdapter και recyclerView
        customAdapter = new CustomAdapter(getContext(), id, city, prefecture);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Κλήση μεθόδου
        displayData(); */

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*void displayData(){
        Cursor cursor = dB.ReadData();

        if (cursor.getCount() == 0) Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        else{
            // Δομή επανάληψης για όσο υπάρχουν στοιχεία
            while (cursor.moveToNext()){
                // Προσθήκη στοιχείων
                id.add(cursor.getString(0));
                city.add(cursor.getString(1));
                prefecture.add(cursor.getString(2));
            }
        }
    } */
}
