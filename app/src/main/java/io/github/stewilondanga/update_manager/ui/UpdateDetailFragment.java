package io.github.stewilondanga.update_manager.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.github.stewilondanga.update_manager.Constants;
import io.github.stewilondanga.update_manager.R;
import io.github.stewilondanga.update_manager.models.Update;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @BindView(R.id.updateImageView) ImageView mImageLabel;
    @BindView(R.id.updateTextView) TextView mNameLabel;
    @BindView(R.id.articleTextView) TextView mArticleLabel;
    @BindView(R.id.saveArticleButton) Button mSaveButton;

    private Update mUpdate;
    private ArrayList<Update> mUpdates;
    private int mPosition;
    private String mSource;

    public static UpdateDetailFragment newInstance(ArrayList<Update> updates, Integer position, String source){
        UpdateDetailFragment updateDetailFragment = new UpdateDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_UPDATES, Parcels.wrap(updates));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);

        updateDetailFragment.setArguments(args);
        return updateDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUpdate = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_UPDATES));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mUpdate = mUpdates.get(mPosition);
        mSource = getArguments().toString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_update_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mUpdate.getResponse().getCurrentPage())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mUpdate.getResponse().getCurrentPage());
        mArticleLabel.setText(mArticleLabel.getText());

        if (mSource.equals(Constants.SOURCE_SAVED)){
            mSaveButton.setVisibility(View.GONE);
        } else {
            mSaveButton.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
      if (v == mSaveButton) {
          FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
          String uid = user.getUid();

          DatabaseReference restaurantRef = FirebaseDatabase
                 .getInstance()
                 .getReference(Constants.FIREBASE_CHILD_UPDATES);
                 .child(uid);

          DatabaseReference PushRef = updateRef.push();
          String pushId = pushRef.getKey();
          mUpdate.setPushId(pushId);
          pushRef.setValue(mUpdate);

          Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
      }
    }

}