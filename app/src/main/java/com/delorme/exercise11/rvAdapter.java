
package com.delorme.exercise11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.ViewHolder> implements Filterable {

    private List<Contravention> localDataSet;
    private MainActivity mainActivity;


    public rvAdapter(Context context) {
        this.mainActivity = (MainActivity) context;
        this.localDataSet = AppService.getInstance().getContraventions();
    }

    public List<Contravention> getLocalDataSet() {
        return localDataSet;
    }

    public rvAdapter setLocalDataSet(List<Contravention> localDataSet) {
        this.localDataSet = localDataSet;
        notifyDataSetChanged();
        return this;
    }

    public void updateContravention(Contravention contravention, int pos) {
        localDataSet.set(pos, contravention);
        notifyDataSetChanged();
    }

    public void deleteContravention(Contravention contravention){
        int index = localDataSet.indexOf(contravention);
        localDataSet.remove(index);
        notifyItemRemoved(index);
    }

    public void deleteContravention(int pos) {
        localDataSet.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addContravention(Contravention contravention){
        localDataSet.add(0, contravention);
        notifyItemInserted(0);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textNom;
        public TextView textDate;
        public TextView textMontant;
        private MainActivity mainActivity;

        public ViewHolder(View view, MainActivity mainActivity) {
            super(view);
            // Define click listener for the ViewHolder's View
            itemView.setOnClickListener(this);
            textNom = view.findViewById(R.id.idRecyclerNom);
            textDate = view.findViewById(R.id.idRecyclerDate);
            textMontant = view.findViewById(R.id.idRecyclerMontant);
            this.mainActivity = mainActivity;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Contravention contravention = localDataSet.get(pos);
            mainActivity.loadContravention(pos, contravention);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_template, viewGroup, false);
        return new ViewHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(rvAdapter.ViewHolder holder, final int position) {
        Contravention contravention = localDataSet.get(position);
        holder.textNom.setText(contravention.getNom() + " " + contravention.getPrenom());
        holder.textDate.setText(contravention.getDateFormat());
        holder.textMontant.setText(contravention.getMontantDeAmende() +" " +"$");
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
