package Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import Models.Beach;
import Models.County;

public class CountyListBeachAdapter extends RecyclerView.Adapter<CountyListBeachAdapter.BeachAdapterViewHolder> {
    private List<County> beaches;
    private Context context;
    private FragmentManager fragmentManager;


    public void setBeachesAndContext(List<County> beaches, Context context, FragmentManager fragmentManager) {
        this.beaches = beaches;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public BeachAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.county_list_individual_view, parent, false);
        return new BeachAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeachAdapterViewHolder holder, final int position) {
        holder.beachName.setText(beaches.get(position).getBeachesInCounty().get(0).getBeachName());
        holder.score.setText(String.valueOf(beaches.get(position).getAverageScore()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(CountyFragment.SAVED_BEACHES_STATE,
                        beaches.get(position));
                BeachDetailFragment beachDetailFragment = new BeachDetailFragment();
                beachDetailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.master_activity_fragment, beachDetailFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack("Beach_Detail_fragment");
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
        TextView score;
        ImageView safe;
        LinearLayout parentLayout;

        public BeachAdapterViewHolder(View itemView) {
            super(itemView);
            beachName = itemView.findViewById(R.id.county_list_individual_beach);
            score = itemView.findViewById(R.id.county_list_individual_score);
            safe = itemView.findViewById(R.id.county_list_individual_safetosurf);
            parentLayout = itemView.findViewById(R.id.county_list_parent_view);
        }
    }
}
