package io.github.stewilondanga.update_manager.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.stewilondanga.update_manager.Constants;
import io.github.stewilondanga.update_manager.R;
import io.github.stewilondanga.update_manager.adapters.UpdatePagerAdapter;
import io.github.stewilondanga.update_manager.models.Update;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;

    private UpdatePagerAdapter adapterViewPager;
    ArrayList<Update> mUpdates = new ArrayList<>();
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        ButterKnife.bind(this);

        mUpdates = Parcels.unwrap(getIntent().getParcelableExtra("briefs"));
        mSource = getIntent().getStringExtra(Constants.KEY_SOURCE);
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new UpdatePagerAdapter(getSupportFragmentManager(), mUpdates, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
