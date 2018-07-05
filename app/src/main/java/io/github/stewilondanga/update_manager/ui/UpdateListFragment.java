package io.github.stewilondanga.update_manager.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.github.stewilondanga.update_manager.Constants;
import io.github.stewilondanga.update_manager.R;
import io.github.stewilondanga.update_manager.adapters.UpdateListAdapter;
import io.github.stewilondanga.update_manager.models.Update;
import io.github.stewilondanga.update_manager.services.UpdateService;
import io.github.stewilondanga.update_manager.util.OnUpdateSelectedListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpdateListFragment extends Fragment {
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private UpdateListAdapter mAdapter;
    public ArrayList<Update> mUpdates = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    public UpdateListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            mEditor = mSharedPreferences.edit();

            setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mOnUpdateSelectedListener = (OnUpdateSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public View onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getUpdates(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Call call, Response response){
        mUpdates = UpdateService.processResults(response);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new UpdateListAdapter(getActivity(), mUpdates, mOnUpdateSelectedListener);
                mRecyclerView.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setHasFixedSize(true);
            }
        });
    }

}