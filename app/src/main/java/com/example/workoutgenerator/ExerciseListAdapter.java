package com.example.workoutgenerator;

import android.content.Context;
import android.util.Log;
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

public class ExerciseListAdapter extends ArrayAdapter<ListOfExercises.Exercise> {
    ArrayList<ListOfExercises.Exercise> data_array = new ArrayList<>();
    private static final String TAG = "ExerciseListAdapter";
    private Context mContext;
    int mResource;



    public ExerciseListAdapter(@NonNull Context context, int resource, @NonNull List<ListOfExercises.Exercise> objects,Context mContext) {
        super(context, resource, objects);
        this.data_array = (ArrayList<ListOfExercises.Exercise>) objects;
        this.mContext = mContext;
        this.mResource = resource;
    }

    public ListOfExercises.Exercise getItem(int i) {
        return data_array.get(i);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        int imageLoc = getItem(position).getImageLoc();
        boolean isliked = getItem(position).isLiked();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView  = inflater.inflate(mResource, parent, false);

        ImageView ivExerciseImage = (ImageView) convertView.findViewById(R.id.ivExerciseImage);
        TextView tvNameOfExercise = (TextView) convertView.findViewById(R.id.tvNameOfExercise);
        ImageView ivLike = (ImageView) convertView.findViewById(R.id.ivLike);
        if(isliked)
            ivLike.setImageResource(R.drawable.ic_heart);
        else
            ivLike.setImageResource(R.drawable.ic_empty_heart);
        ivExerciseImage.setImageResource(imageLoc);
        tvNameOfExercise.setText(name);
        return convertView;
    }
}

