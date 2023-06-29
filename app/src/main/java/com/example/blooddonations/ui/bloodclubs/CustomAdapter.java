package com.example.blooddonations.ui.bloodclubs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonations.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList BC_id, BC_city, BC_pref;

    CustomAdapter(Context context, ArrayList BC_id, ArrayList BC_city, ArrayList BC_pref){
        this.context = context;
        this.BC_id = BC_id;
        this.BC_city = BC_city;
        this.BC_pref = BC_pref;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_blood_clubs, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(BC_id.get(position)));
        holder.city.setText(String.valueOf(BC_city.get(position)));
        holder.pref.setText(String.valueOf(BC_pref.get(position)));
    }

    @Override
    public int getItemCount() {
        return BC_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, city, pref;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_BC);
            city = itemView.findViewById(R.id.city_BC);
            pref = itemView.findViewById(R.id.pref_BC);
        }
    }
}
