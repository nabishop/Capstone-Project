package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rightide.R;

import java.util.ArrayList;

import Models.Beach;

public class WarningsListBeachDetailAdapter extends RecyclerView.Adapter<WarningsListBeachDetailAdapter.WarningAdapterViewHolder> {
    private ArrayList<Beach> warnings;

    public void setWarnings(ArrayList<Beach> warnings) {
        this.warnings = warnings;
    }

    @NonNull
    @Override
    public WarningAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.warnings_list_individual_view, viewGroup, false);
        return new WarningAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WarningAdapterViewHolder holder, int position) {
        holder.time.setText(warnings.get(position).getHour());

        StringBuilder fullWarnings = new StringBuilder();
        for (String curWarning : warnings.get(position).getWarnings()) {
            if (fullWarnings.length() == 0)
                fullWarnings.append(curWarning);
            else
                fullWarnings.append(", ").append(curWarning);
        }
        holder.warning.setText(fullWarnings);
    }

    @Override
    public int getItemCount() {
        return warnings.size();
    }

    class WarningAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView warning;

        WarningAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.warnings_list_individual_view_time);
            warning = itemView.findViewById(R.id.warnings_list_individual_view_warning);
        }
    }
}
