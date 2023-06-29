package com.example.blooddonations.ui.requestdonator;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blooddonations.R;
import com.example.blooddonations.databinding.FragmentReqDonBinding;

public class RequestDonatorFragment extends Fragment {
    private FragmentReqDonBinding binding;

    // Ορισμός μεταβλητών για το όνομα, το τηλέφωνο και το Button
    private String name, phone;
    private Button ConfirmBM;
    private EditText edName, edPhone;

    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentReqDonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Ορισμός τιμών edName, edPhone, ConfirmBM
        edName = root.findViewById(R.id.NameText);
        edPhone = root.findViewById(R.id.PhoneText);
        ConfirmBM = root.findViewById(R.id.confirmButton);

        // Ορισμός φίλτρου για edName ώστε όλο το όνομα να γράφεται με κεφαλαία
        edName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        ConfirmBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ορισμός τιμών name, phone
                name = edName.getText().toString();
                phone = edPhone.getText().toString();

                // Έλεγχος αν συμπληρώθηκαν και τα δύο πεδία
                if(!name.equals("") && !phone.equals("")){
                    // Έλεγχος αν ο αριθμός τηλεφώνου που δόθηκε είναι 10ψήφιος
                    if (phone.length() == 10)
                        // Κλήση μεθόδου inserUser()
                        insertUser();
                    else Toast.makeText(getContext(),
                            "Ο αριθμός τηλεφώνου δεν είναι 10ψήφιος", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "Συμπλήρωσε όλα τα πεδία",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void insertUser(){
        // Ορισμός στοιχείων για την εισαγωγή τους στην βάση δεδομένων και κλήση της μεθόδου addBMRequest
        DBHelperBoneMarrow dB = new DBHelperBoneMarrow(getContext());
        dB.addBMRequest(edName.getText().toString().trim(), edPhone.getText().toString().trim());
    }
}
