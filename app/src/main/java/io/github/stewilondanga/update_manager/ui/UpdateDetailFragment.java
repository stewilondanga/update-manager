package io.github.stewilondanga.update_manager.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.stewilondanga.update_manager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDetailFragment extends Fragment {


    public UpdateDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_detail, container, false);
    }

}
