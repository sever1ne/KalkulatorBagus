package com.example.kalkulatorbagus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalkulatorbagus.Kalkulator;
import com.example.kalkulatorbagus.R;

import java.util.ArrayList;

public class KalkulatorAdapter extends RecyclerView.Adapter<KalkulatorAdapter.ViewHolder> {
    public ArrayList<Kalkulator> listHasil;

    public KalkulatorAdapter(ArrayList<Kalkulator> listHasil) {
        this.listHasil = listHasil;
    }

    @NonNull
    @Override
    public KalkulatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder =new ViewHolder(inflater.inflate(R.layout.item_activity, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kalkulator kalkulator = listHasil.get(position);
        holder.txtAngka1.setText(kalkulator.getAngka1());
        holder.txtAngka2.setText(kalkulator.getAngka2());
        holder.txtHasil.setText(kalkulator.getHasil());
        holder.txtOperasi.setText(kalkulator.getOperasi());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int newPosition = holder.getAdapterPosition();
                listHasil.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, listHasil.size());

                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return listHasil.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtAngka1, txtAngka2, txtHasil, txtOperasi;
        public ConstraintLayout itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAngka1 =itemView.findViewById(R.id.txtAngka1);
            txtAngka2 = itemView.findViewById(R.id.txtAngka2);
            txtHasil = itemView.findViewById(R.id.txtHasil);
            txtOperasi = itemView.findViewById(R.id.txtOperasi);
            this.itemView = (ConstraintLayout) itemView.findViewById(R.id.main_layout);
        }
    }
}
