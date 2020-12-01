package com.example.cscourses;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CourseDetails extends Fragment {

    public static CourseDetails newInstance(int index){
        CourseDetails detailsFragment = new CourseDetails();
        Bundle args = new Bundle();
        args.putInt("index", index);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    public int getShowIndex(){
        return getArguments().getInt("index",0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null) return null;

        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setBackgroundColor(Color.rgb(255, 215, 0));

        TextView textView = new TextView(getActivity());
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,
                getActivity().getResources().getDisplayMetrics());
        textView.setPadding(padding,padding,padding,padding);
        scrollView.addView(textView);
        textView.setText(Courses.COURSES_INFO[getShowIndex()]);
        return scrollView;
    }
}