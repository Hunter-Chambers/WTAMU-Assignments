package com.example.cscourses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class    CourseFragment extends ListFragment {
    private boolean mDualPane;
    private int mCurrPosition = 0;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurrPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int index){
        mCurrPosition = index;
        if (mDualPane) {
            getListView().setItemChecked(index, true);
            CourseDetails details = (CourseDetails)
                    getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShowIndex() != index) {
                details = CourseDetails.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(),CourseActivity.class);
            intent.putExtra("index",index);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> connectAdapterToListView = new ArrayAdapter<>(getActivity(),
                R.layout.row_layout, R.id.label,Courses.COURSES);
        setListAdapter(connectAdapterToListView);
        View detailFrame = getActivity().findViewById(R.id.details);
        mDualPane = (detailFrame != null && detailFrame.getVisibility() == View.VISIBLE);
        if(savedInstanceState != null)
            mCurrPosition = savedInstanceState.getInt("curChoice",0);
        if(mDualPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurrPosition);
        } else {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(mCurrPosition,true);
        }
    }

}