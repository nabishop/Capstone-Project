package com.example.android.rightide.UI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.rightide.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ContentProvider.CursorHelper;
import Models.Beach;
import Models.County;
import Utils.BeachLoader;

public class FavoitesFragment extends Fragment {
    private ArrayList<Integer> favoritedBeachIds;
    private static ArrayList<ArrayList<Beach>> favoritedBeaches;
    private FrameLayout frameLayout;


    public FavoitesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.favorites_fragment, container, false);

        frameLayout = root.findViewById(R.id.favorites_fragment_frame);

        // load favorites screen depending on whether the user has favorites
        if (getContext() != null) {
            favoritedBeachIds = CursorHelper.getFavoritedBeacheIds(getContext());

            View view;
            if (favoritedBeachIds == null || favoritedBeachIds.size() == 0) {
                // no favorites
                view = inflater.inflate(R.layout.no_favorites_holder, frameLayout, false);
            } else {
                // load favorited beaches
                view = inflater.inflate(R.layout.favorites_holder, frameLayout, false);

                loadFavoritedBeaches();
            }
            frameLayout.addView(view);
        }

        return root;
    }

    private void loadFavoritedBeaches() {
        favoritedBeaches = new ArrayList<>();

        for (Integer id : favoritedBeachIds) {
            new BeachASyncTask().execute(id);
        }
    }

    private static class BeachASyncTask extends AsyncTask<Integer, Void, ArrayList<ArrayList<Beach>>> {
        @Override
        protected ArrayList<ArrayList<Beach>> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return BeachLoader.getFavoritedBeachInfo(integers);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<Beach>> beaches) {
            super.onPostExecute(beaches);

            favoritedBeaches.addAll(beaches);
        }
    }
}
