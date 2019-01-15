package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.rightide.R;

import java.util.ArrayList;
import java.util.List;

import Models.CountyAutoTextViewItem;

public class AutoCompleteCountyAdapter extends ArrayAdapter<CountyAutoTextViewItem> {
    List<CountyAutoTextViewItem> autoTextViewItems;

    public AutoCompleteCountyAdapter(@NonNull Context context, @NonNull List<CountyAutoTextViewItem> objects) {
        super(context, 0, objects);
        this.autoTextViewItems = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countyFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_explore_individual_view, parent, false);
        }

        TextView countyName = convertView.findViewById(R.id.autocomplete_view_county_text);
        ImageView countyImage = convertView.findViewById(R.id.autocomplete_view_county_image);

        CountyAutoTextViewItem item = getItem(position);

        if (item != null) {
            countyName.setText(item.getCountyName());
            countyImage.setImageResource(item.getImageId());
        }

        return convertView;
    }

    private Filter countyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<CountyAutoTextViewItem> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(autoTextViewItems);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountyAutoTextViewItem item : autoTextViewItems) {
                    if (item.getCountyName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            filterResults.values = suggestions;
            filterResults.count = suggestions.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CountyAutoTextViewItem) resultValue).getCountyName();
        }
    };
}
