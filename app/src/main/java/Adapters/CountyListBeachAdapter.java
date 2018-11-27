package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.rightide.R;

import java.util.ArrayList;

import Models.Beach;

public class CountyListBeachAdapter extends RecyclerView.Adapter<CountyListBeachAdapter.BeachAdapterViewHolder> {
    private ArrayList<Beach> beaches;

    public void setBeaches(ArrayList<Beach> beaches) {
        this.beaches = beaches;
    }

    @NonNull
    @Override
    public BeachAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.county_list_individual_view, parent, false);
        return new BeachAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeachAdapterViewHolder holder, int position) {
        holder.beachName.setText(beaches.get(position).getBeachName());
        holder.score.setText(String.valueOf(beaches.get(position).getScore()));

    }

    @Override
    public int getItemCount() {
        if (beaches == null)
            return 0;
        return beaches.size();
    }

    public class BeachAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView beachName;
        TextView score;
        ImageView safe;

        public BeachAdapterViewHolder(View itemView) {
            super(itemView);
            beachName = itemView.findViewById(R.id.county_list_individual_beach);
            score = itemView.findViewById(R.id.county_list_individual_score);
            safe = itemView.findViewById(R.id.county_list_individual_safetosurf);
        }
    }
}
