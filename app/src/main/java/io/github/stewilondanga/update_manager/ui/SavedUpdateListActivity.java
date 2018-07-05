package io.github.stewilondanga.update_manager.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.stewilondanga.update_manager.R;
import io.github.stewilondanga.update_manager.util.OnStartDragListener;

public class SavedUpdateListActivity extends AppCompatActivity implements OnStartDragListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_update_list);
    }
}
