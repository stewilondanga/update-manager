package io.github.stewilondanga.update_manager.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import io.github.stewilondanga.update_manager.Constants;
import io.github.stewilondanga.update_manager.R;
import io.github.stewilondanga.update_manager.models.Update;
import io.github.stewilondanga.update_manager.ui.UpdateDetailActivity;
import io.github.stewilondanga.update_manager.ui.UpdateDetailFragment;
import io.github.stewilondanga.update_manager.util.ItemTouchHelperAdapter;
import io.github.stewilondanga.update_manager.util.OnStartDragListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by stewart on 7/2/18.
 */

public class FirebaseUpdateListAdapter (Class<Update> modelClass, int modelLayout,
        Class<FirebaseUpdateViewHolder> viewHolderClass,
        Query ref, OnStartDragListener onStartDragListener, Context context){
        super(modelClass,modelLayout,viewHolderClass,ref);
        mRef=ref.getRef();
        mOnStartDragListener=onStartDragListener;
        mContext=context;

        }
        @Override
        protected void populateViewHolder(final FirebaseUpdateViewHolder viewHolder,Update model,int position){
                viewHolder.bindUpdate(model);

        mOrientation=viewHolder.itemView.getResources().getConfiguration().orientation;
        if(mOrientation==Configuration.ORIENTATION_LANDSCAPE){
            createDetailFragment(0);

        viewHolder.mUpdateImageView.setOnTouchListener(new View.OnTouchListener()){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
            return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, UpdateDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_UPDATES, Parcels.wrap(mUpdates));
                    mContext.startActivity(intent);
                }
            }
        });
     }

     private void createDetailFragment(int position) {
        UpdateDetailFragment detailFragment = UpdateDetailFragment.newInstance(mUpdate, position);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.updateDetailContainer, detailFragment);
        ft.commit();
     }

     @Override
     public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
        }

     @Override
     public void onItemDismiss(int position){

        }
  }