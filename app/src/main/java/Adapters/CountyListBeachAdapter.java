package Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.rightide.R;
import com.example.android.rightide.UI.BeachDetailFragment;
import com.example.android.rightide.UI.CountyFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Models.Beach;
import Models.County;

public class CountyListBeachAdapter extends RecyclerView.Adapter<CountyListBeachAdapter.BeachAdapterViewHolder> {
    private List<County> beaches;
    private FragmentManager fragmentManager;


    public void setBeachesAndContext(List<County> beaches, FragmentManager fragmentManager) {
        this.beaches = beaches;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public BeachAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_beach_list, parent, false);
        return new BeachAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeachAdapterViewHolder holder, final int position) {
        holder.beachName.setText(beaches.get(position).getBeachesInCounty().get(0).getBeachName());

        double average = 0;
        ArrayList<Beach> avgBeaches = beaches.get(position).getBeachesInCounty();
        Log.d("BIND", String.valueOf(position));
        for (Beach beach : avgBeaches) {
            average += beach.getWaveSizeFt();
        }

        average = average / avgBeaches.size();

        holder.waveHeight.setText(String.format("%s ft",
                String.format(Locale.US, "%.2f", average)));

        holder.score.setText(String.format("%s/10", String.format(Locale.US, "%.2f",
                beaches.get(position).getAverageScore())));

        if (beaches.get(position).getTemperatureFahrenheit() == 0) {
            holder.temperature.setText("Not Available");
        } else {
            holder.temperature.setText(String.format("%s f",
                    String.format(Locale.US, "%.2f",
                            beaches.get(position).getTemperatureFahrenheit())));
        }

        // gets current hour of day, if there is a warning for that hour, show thumbs down, else up
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (beaches.get(position).getBeachesInCounty().get(currentHour).getWarnings().isEmpty()) {
            holder.warnings.setText("No Warnings this Hour");
        } else {
            holder.warnings.setText(String.format(Locale.US, "%d Warnings this Hour",
                    beaches.get(position).getBeachesInCounty().get(currentHour).getWarnings().size()));
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(CountyFragment.SAVED_BEACHES_STATE,
                        beaches.get(position));
                BeachDetailFragment beachDetailFragment = new BeachDetailFragment();
                beachDetailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.master_activity_fragment, beachDetailFragment);
                fragmentTransaction.addToBackStack("Beach_Detail_fragment");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (beaches == null)
            return 0;
        return beaches.size();
    }

    public class BeachAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView beachName;
        TextView waveHeight;
        TextView score;
        TextView warnings;
        TextView temperature;
        ConstraintLayout parentLayout;

        public BeachAdapterViewHolder(View itemView) {
            super(itemView);
            beachName = itemView.findViewById(R.id.recycler_view_beach_list_beach_name);
            waveHeight = itemView.findViewById(R.id.recycler_view_beach_list_beach_wave_size_number);
            score = itemView.findViewById(R.id.recycler_view_beach_list_score);
            warnings = itemView.findViewById(R.id.recycler_view_beach_list_beach_warnings);
            temperature = itemView.findViewById(R.id.recycler_view_beach_list_beach_temp);
            parentLayout = itemView.findViewById(R.id.recycler_view_beach_list_parent_layout);
        }
    }
}
