package com.example.blooddonations.ui.lastdonation;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.blooddonations.R;
import com.example.blooddonations.databinding.FragmentLastDonBinding;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LastDonationFragment extends Fragment {
    private FragmentLastDonBinding binding;

    // Ορισμός μεταβλητών για την ημερομηνία, το radio button που επιλέχθηκε και το Button (Check Button)
    private String lastDate;
    private Button checkB;
    private EditText edLastDate;

    // Ορισμός μεταβλητών τύπου LocalDate
    LocalDate Ldate, TodayDate;

    // Ορισμός του καθορισμένου date format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLastDonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Ορισμός των μεταβλητών
        edLastDate = root.findViewById(R.id.InputLastDate);
        checkB = root.findViewById(R.id.CheckButton);

        checkB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                lastDate = edLastDate.getText().toString();

                if(!lastDate.equals("")) checkLastDonation(lastDate);

                else Toast.makeText(getContext(), "Συμπλήρωσε ημερομηνία", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkLastDonation(String LastDate){
        // Try-catch για να ελεγχθεί αν ο χρήστης έδωσε έγκυρη ημερομηνία
        try {
            Ldate = LocalDate.parse(LastDate, formatter);

            // Κλήση μεθόδου
            dateDifference();
        } catch (Exception e) {
            // Εμφάνιση μηνύματος σφάλματος λόγω μη έγκυρης ημερομηνίας
            Toast.makeText(getContext(), "Μη έγκυρη μορφή ημερομηνίας", Toast.LENGTH_SHORT).show();
        }


    }

    // Υπολογισμός διάρκειας από την τελευταία αιμοδοσία μέχρι σήμερα
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void dateDifference(){
        // Ορισμός σημερινής ημερομηνίας
        TodayDate = LocalDate.now();

        // Έλεγχος αν η ημερομηνία που εισάχθηκε από τον χρήστη είναι πιο μετά από την σημερινή
        if (Ldate.isAfter(TodayDate)) {
            Toast.makeText(getContext(), "Δόθηκε ημερομηνία μετά από την τωρινή",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Υπολογισμός διαφοράς
        Period period = Period.between(Ldate, TodayDate);

        // Διαχωρισμός σε χρόνια, μήνες και μέρες
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // Εμφάνιση αποτελέσματος
        // Γίνεται ταυτόχρονα έλεγχος του ποσού των χρόνων των ημερών και των ημερών
        // Για καλύτερη εμφάνιση του μηνύματος
        if (years != 1 && months != 1 && days != 1) Toast.makeText(getContext(),
                years + " χρόνια, " + months + " μήνες, " + days + " μέρες",
                Toast.LENGTH_SHORT).show();

        else if (years != 1 && months != 1 && days == 1) Toast.makeText(getContext(),
                years + " χρόνια, " + months + " μήνες, " + days + " μέρα",
                Toast.LENGTH_SHORT).show();

        else if (years != 1 && months == 1 && days != 1) Toast.makeText(getContext(),
                years + " χρόνια, " + months + " μήνας, " + days + " μέρες",
                Toast.LENGTH_SHORT).show();

        else if (years != 1 && months == 1 && days == 1) Toast.makeText(getContext(),
                years + " χρόνια, " + months + " μήνας, " + days + " μέρα",
                Toast.LENGTH_SHORT).show();

        else if (years == 1 && months != 1 && days != 1) Toast.makeText(getContext(),
                years + " χρόνος, " + months + " μήνες, " + days + " μέρες",
                Toast.LENGTH_SHORT).show();

        else if (years == 1 && months != 1 && days == 1) Toast.makeText(getContext(),
                years + " χρόνος, " + months + " μήνες, " + days + " μέρα",
                Toast.LENGTH_SHORT).show();

        else if (years == 1 && months == 1 && days != 1) Toast.makeText(getContext(),
                years + " χρόνος, " + months + " μήνας, " + days + " μέρες",
                Toast.LENGTH_SHORT).show();

        else if (years == 1 && months == 1 && days == 1) Toast.makeText(getContext(),
                years + " χρόνος, " + months + " μήνας, " + days + " μέρα",
                Toast.LENGTH_SHORT).show();
    }
}
