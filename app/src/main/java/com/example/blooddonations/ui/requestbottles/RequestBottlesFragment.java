package com.example.blooddonations.ui.requestbottles;

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
import com.example.blooddonations.databinding.FragmentReqBottlesBinding;

public class RequestBottlesFragment extends Fragment {
    private FragmentReqBottlesBinding binding;

    // Ορισμός μεταβλητών για τα στοιχεία που θα εισάγει ο χρήστης και το Button (Request Bottles)
    private String name, namePatient, phone, bloodType, bottles;
    private Button ConfirmRB;

    private EditText edName, edNamePat, edPhone, edBloodType, edBottles;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentReqBottlesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Ορισμός τιμών του τύπου Editext, Button
        edName = root.findViewById(R.id.InputName);
        edNamePat = root.findViewById(R.id.InputNamePerson);
        edPhone = root.findViewById(R.id.InputPhone);
        edBloodType = root.findViewById(R.id.InputBloodType);
        edBottles = root.findViewById(R.id.InputBottles);
        ConfirmRB = root.findViewById(R.id.ConfirmButton);

        // Ορισμός φίλτρου για edName, edNamePat και edBloodType ώστε όλο το όνομα να γράφεται με κεφαλαία
        edName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edNamePat.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edBloodType.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        // Όταν πατηθεί το κουμπί
        ConfirmRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Οριζούμε τα στοιχεία που έχει εισάγει ο χρήστης
                name = edName.getText().toString();
                namePatient = edNamePat.getText().toString();
                phone = edPhone.getText().toString();
                bloodType = edBloodType.getText().toString();
                bottles = edBottles.getText().toString();

                // Έλεγχος αν συμπληρώθηκαν όλα τα πεδία, σε περίπτωση λάθους εμφανίζει κατάλληλο μήνυμα
                if(!name.equals("") && !namePatient.equals("") && !phone.equals("")
                && !bloodType.equals("") && !bottles.equals("")){

                    // Έλεγχος αν ο αριθμός τηλεφώνου που δόθηκε είναι 10ψήφιος, σε περίπτωση λάθους εμφανίζει κατάλληλο μήνυμα
                    if (phone.length() == 10) {

                        // Έλεγχος αν δόθηκε έγκυρος τύπος αίματος, σε περίπτωση λάθους εμφανίζει κατάλληλο μήνυμα
                        if (bloodType.equals("A+") || bloodType.equals("A-") || bloodType.equals("B+")
                                || bloodType.equals("B-") || bloodType.equals("AB+")
                                || bloodType.equals("AB-") || bloodType.equals("O+")
                                || bloodType.equals("0-")){
                            // Κλήση μεθόδου insertRequest()
                            insertRequest();
                            // Έλεγχος αν δόθηκε ο τύπος αίματος με ελληνικούς χαρακτήρες
                        } else if (bloodType.equals("Α+") || bloodType.equals("Α-") || bloodType.equals("Β+")
                                || bloodType.equals("Β-") || bloodType.equals("ΑΒ+")
                                || bloodType.equals("ΑΒ-") || bloodType.equals("Ο+")
                                || bloodType.equals("Ο-")) {
                            // Σε περίπτωση που αληθεύει τότε το EditText γράφεται με λατινικούς με το switch
                            // case για να βρέθει ο τύπος αίματος και να αλλαχθεί
                            switch (bloodType){
                                case "Α+": edBloodType.setText("A+");
                                break;

                                case "Α-": edBloodType.setText("A-");
                                break;

                                case "Β+": edBloodType.setText("B+");
                                break;

                                case "Β-": edBloodType.setText("B-");
                                break;

                                case "ΑΒ+": edBloodType.setText("AB+");
                                break;

                                case "ΑΒ-": edBloodType.setText("AB-");
                                break;

                                case "Ο+": edBloodType.setText("O+");
                                break;

                                case "Ο-": edBloodType.setText("O-");
                                break;
                            }
                            // Κλήση μεθόδου insertRequest()
                            insertRequest();
                        }
                        else Toast.makeText(getContext(),
                                "Δόθηκε μη έγκυρος τύπος αίματος", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getContext(),
                            "Ο αριθμός τηλεφώνου δεν είναι 10ψήφιος", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getContext(), "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void insertRequest(){
        // Ορισμός στοιχείων για την εισαγωγή τους στην βάση δεδομένων και κλήση της μεθόδου addBottleRequest
        DBHelperBottles dB = new DBHelperBottles(getContext());
        dB.addBottleRequest(edName.getText().toString().trim(),
                edNamePat.getText().toString().trim(), edPhone.getText().toString().trim(),
                edBloodType.getText().toString().trim(), edBottles.getText().toString().trim());
    }
}
