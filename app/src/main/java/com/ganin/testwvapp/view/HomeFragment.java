package com.ganin.testwvapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ganin.testwvapp.R;
import com.ganin.testwvapp.model.Workout;
import com.ganin.testwvapp.model.WorkoutDAO;

import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWorkoutsList(view);
        initNewButton(view);
        initSettingsButton(view);
    }

    private void initWorkoutsList(View view) {
        Context context = requireContext();
        ListView listView = view.findViewById(R.id.list_view_workouts);
        TextView tvNoWorkouts = view.findViewById(R.id.text_view_no_workouts);

        List<Workout> workouts = new WorkoutDAO(context).getAll();
        if (workouts.isEmpty()) {
            listView.setVisibility(View.GONE);
            tvNoWorkouts.setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        tvNoWorkouts.setVisibility(View.GONE);
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(workouts, context);
        listView.setAdapter(workoutAdapter);
    }

    private void initNewButton(@NonNull View view) {
        view.findViewById(R.id.button_new).setOnClickListener(view1 -> {
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_HomeFragment_to_SelectFragment);
        });
    }

    private void initSettingsButton(View view) {
        view.findViewById(R.id.button_settings).setOnClickListener(view1 -> {
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_HomeFragment_to_SettingsFragment);
        });
    }
}