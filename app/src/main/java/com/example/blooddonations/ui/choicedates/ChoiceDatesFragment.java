package com.example.blooddonations.ui.choicedates;

import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.blooddonations.MainActivity;
import com.example.blooddonations.R;
import com.example.blooddonations.databinding.FragmentChoDatesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ChoiceDatesFragment extends Fragment {
    private FragmentChoDatesBinding binding;

    // Ορισμός μεταβλητών για τα στοιχεία που θα εισάγει ο χρήστης και το Button (Choice Date)
    private String name, bloodClub, date;
    private Button ConfirmCD;

    private EditText edName, edBloodClub, edDate;

    // Ορισμός του καθορισμένου date format και μεταβλητής dateCFormat τύπου LocalDate
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate dateCFormat;

    // Ορισμός boolean μεταβλητής για να ελέγξει αν χρήστης σύνδεθηκε (έκανε αυθεντικοποίηση)
    public static boolean isAuth = MainActivity.isUserAuthenticated();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChoDatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Ορισμός τιμών του τύπου Editext, Button
        edName = root.findViewById(R.id.NameTextChoice);
        edBloodClub = root.findViewById(R.id.ClubDonatorName);
        edDate = root.findViewById(R.id.DateChoice);
        ConfirmCD = root.findViewById(R.id.ConfirmButtonChoiceDate);

        // Ορισμός φίλτρου για edName ώστε όλο το όνομα να γράφεται με κεφαλαία
        edName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        // Όταν πατηθεί το κουμπί
        ConfirmCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Γίνεται ο έλεγχος αν έχει συνδεθεί ο χρήστης
                if (isAuth){
                    name = edName.getText().toString();
                    bloodClub = edBloodClub.getText().toString();
                    date = edDate.getText().toString();
                    // Έλεγχος αν συμπληρώθηκαν όλα τα πεδία
                    if(!name.equals("") && !bloodClub.equals("") && !date.equals("")){
                        // Try-catch για να ελεγχθεί αν ο χρήστης έδωσε έγκυρη ημερομηνία
                        try {
                            dateCFormat = LocalDate.parse(date, formatter);
                            // Κλήση μεθόδου insertRequest()
                            insertChoiceDate();
                        } catch (Exception e) {
                            // Εμφάνιση μηνύματος σφάλματος λόγω μη έγκυρης ημερομηνίας
                            Toast.makeText(getContext(), "Μη έγκυρη μορφή ημερομηνίας", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),"Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    // Αφαίρεση στοιχείων που δόθηκαν από τον μη-συνδεδεμένο χρήστη και εμφάνιση μηνύματος
                    edName.setText("");
                    edBloodClub.setText("");
                    edDate.setText("");
                    Toast.makeText(getContext(), "Πρέπει να συνδεθείς για να διάλεξεις ημερομηνία",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void insertChoiceDate(){
        // Ορισμός σημερινής ημερομηνίας
        LocalDate TodayDate = LocalDate.now();

        // Έλεγχος αν η ημερομηνία που εισάχθηκε από τον χρήστη είναι πιο πριν από την σημερινή
        if (dateCFormat.isBefore(TodayDate)) {
            Toast.makeText(getContext(), "Δόθηκε ημερομηνία πριν από την τωρινή",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Ορισμός στοιχείων για την εισαγωγή τους στην βάση δεδομένων και κλήση της μεθόδου choiceDate
        DBHelperChoiceDate dB = new DBHelperChoiceDate(getContext());
        dB.choiceDate(edName.getText().toString().trim(),
                edBloodClub.getText().toString().trim(), edDate.getText().toString().trim());
    }
}
