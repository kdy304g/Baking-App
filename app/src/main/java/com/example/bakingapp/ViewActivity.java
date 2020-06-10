package com.example.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.fragments.ViewFragment;
import com.example.bakingapp.model.Step;

import java.io.Serializable;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private Step step;
    private List<Step> steps;
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent i = getIntent();
        step = (Step) i.getSerializableExtra(getString(R.string.step_key));
        steps = (List<Step>) i.getSerializableExtra(getString(R.string.steps_key));
        mTwoPane = (boolean) i.getSerializableExtra(getString(R.string.mTwoPane));

        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.step_key), (Serializable) step);
        bundle.putSerializable(getString(R.string.steps_key), (Serializable) steps);
        bundle.putBoolean(getString(R.string.mTwoPane), mTwoPane);
        ViewFragment viewFragment = new ViewFragment();
        viewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment, viewFragment).commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            ViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    public void onPreviousClick(View view){
        step = steps.get(step.getId()-1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.step_key), (Serializable) step);
        bundle.putSerializable(getString(R.string.steps_key), (Serializable) steps);
        ViewFragment viewFragment = new ViewFragment();
        viewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment, viewFragment).commit();
    }

    public void onNextClick(View view){
        step = steps.get(step.getId() + 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.step_key), (Serializable) step);
        bundle.putSerializable(getString(R.string.steps_key), (Serializable) steps);
        ViewFragment viewFragment = new ViewFragment();
        viewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment, viewFragment).commit();
    }

}
