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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WorkoutListAdapter extends ArrayAdapter<Workout> {
    ArrayList<Workout> data_array = new ArrayList<>();
    private static final String TAG = "WorkoutListAdapter";
    private Context mContext;
    int mResource;


    public WorkoutListAdapter(@NonNull Context context, int resource, @NonNull List<Workout> objects) {
        super(context, resource, objects);
        this.data_array = (ArrayList<Workout>) objects;
        this.mContext = context;
        this.mResource = resource;
    }

    public Workout getItem(int i) {
        return data_array.get(i);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        String prepare = getTimeFormatted(getItem(position).getPrepareTime());
        String work = getTimeFormatted(getItem(position).getWorkTime());
        String rest = getTimeFormatted(getItem(position).getRestTime());
        String numOfCycles = getItem(position).getNumOfCycles();
        String numOfSets = getItem(position).getNumOfSets();
        String restBetweenSets = getTimeFormatted(getItem(position).getRestBetweenSetsTime());
        String strings = getItem(position).getStrings();
        String strings1 = setExercisesText(strings);


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView  = inflater.inflate(mResource, parent, false);

        TextView tvPrepareTime = (TextView) convertView.findViewById(R.id.tv1);
        TextView tvWorkTime = (TextView) convertView.findViewById(R.id.tv2);
        TextView tvRestTime = (TextView) convertView.findViewById(R.id.tv3);
        TextView tvNumOfCycles = (TextView) convertView.findViewById(R.id.tv4);
        TextView tvNumOfSets = (TextView) convertView.findViewById(R.id.tv5);
        TextView tvRestBetweenSets = (TextView) convertView.findViewById(R.id.tv6);
        TextView tvExercises = (TextView) convertView.findViewById(R.id.tv7);

        tvPrepareTime.setText(prepare);
        tvWorkTime.setText(work);
        tvRestTime.setText(rest);
        tvNumOfCycles.setText(numOfCycles);
        tvNumOfSets.setText(numOfSets);
        tvRestBetweenSets.setText(restBetweenSets);
        tvExercises.setText("תרגילים: " + strings1);

        return convertView;
    }

    public String getTimeFormatted(String time){
        Long millisInput = Long.parseLong(time) * 1000;
        int hours = (int) (millisInput / 1000) / 3600;
        int minutes = (int) ((millisInput / 1000) % 3600) / 60;
        int seconds = (int) (millisInput / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        return timeLeftFormatted;
    }

    public String setExercisesText(String str){ // str = "[a,b,c]"
        String replace = str.replace("[",""); // replace = "a,b,c]"
        String replace1 = replace.replace("]",""); // replace1 = "a,b,c"
        return replace1;
    }


}
