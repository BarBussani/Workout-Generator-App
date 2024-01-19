package com.example.workoutgenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSpinnerAdapter extends ArrayAdapter<ListOfExercises.Exercise> {
    ArrayList<ListOfExercises.Exercise> data_array = new ArrayList<>();
    private static final String TAG = "ExerciseSpinnerAdapter";
    private Context mContext;
    int mResource;

    public ExerciseSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ListOfExercises.Exercise> objects,Context mContext) {
        super(context, resource, objects);
        this.data_array = (ArrayList<ListOfExercises.Exercise>) objects;
        this.mContext = mContext;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        String name = getItem(position).getName();
        int imageLoc = getItem(position).getImageLoc();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView  = inflater.inflate(mResource, parent, false);

        ImageView ivExerciseImage = (ImageView) convertView.findViewById(R.id.ivExerciseImage);
        TextView tvNameOfExercise = (TextView) convertView.findViewById(R.id.tvNameOfExercise);
        ivExerciseImage.setImageResource(imageLoc);
        tvNameOfExercise.setText(name);
        return convertView;
    }

    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent){
        return getView(position, convertView,parent);
    }
}
