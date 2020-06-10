package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingapp.adapters.StepAdapter;
import com.example.bakingapp.databinding.ActivityStepBinding;
import com.example.bakingapp.fragments.IngredientFragment;
import com.example.bakingapp.fragments.StepFragment;
import com.example.bakingapp.fragments.ViewFragment;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Step;

import java.io.Serializable;
import java.util.List;

public class StepActivity extends AppCompatActivity implements StepAdapter.StepClickListener {

    private List<Step> steps;
    private List<Ingredient> ingredients;
    private ActivityStepBinding binding;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        binding = ActivityStepBinding.inflate(getLayoutInflater());
        Intent i = getIntent();
        steps = (List<Step>) i.getSerializableExtra("steps");
        ingredients = (List<Ingredient>) i.getSerializableExtra("ingredients");

        if(binding.ingredientStepLinearLayout != null){
            mTwoPane = true;
            if (savedInstanceState == null){
                Bundle bundleIngredient = new Bundle();
                bundleIngredient.putSerializable(getString(R.string.ingredients_key), (Serializable) ingredients);
                FragmentManager fragmentManager = getSupportFragmentManager();
                IngredientFragment ingredientFragment = new IngredientFragment();
                ingredientFragment.setArguments(bundleIngredient);
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_step_container, ingredientFragment)
                        .commit();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.steps_key), (Serializable) steps);
        bundle.putSerializable(getString(R.string.ingredients_key), (Serializable) ingredients);
        bundle.putSerializable(getString(R.string.mTwoPane), mTwoPane);
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.step_list_fragment, stepFragment).commit();
    }

    @Override
    public void onStepItemClick(Step step) {
        if(mTwoPane){
            Bundle bundleStep = new Bundle();
            bundleStep.putSerializable(getString(R.string.steps_key), (Serializable) steps);
            bundleStep.putSerializable(getString(R.string.step_key), (Serializable) step);
            bundleStep.putSerializable(getString(R.string.mTwoPane), mTwoPane);
            FragmentManager fragmentManager = getSupportFragmentManager();
            ViewFragment viewFragment = new ViewFragment();
            viewFragment.setArguments(bundleStep);
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_step_container, viewFragment)
                    .commit();
        } else{
            final Intent intent = new Intent (StepActivity.this, ViewActivity.class);
            intent.putExtra(getString(R.string.step_key), step);
            intent.putExtra(getString(R.string.steps_key), (Serializable) steps);
            intent.putExtra(getString(R.string.mTwoPane), (Serializable) mTwoPane);
            StepActivity.this.startActivity(intent);
        }
    }

    public void onIngredientsClick() {
        Bundle bundleIngredient = new Bundle();
        bundleIngredient.putSerializable(getString(R.string.ingredients_key), (Serializable) ingredients);
        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setArguments(bundleIngredient);
        fragmentManager.beginTransaction()
                .replace(R.id.ingredient_step_container, ingredientFragment)
                .commit();
    }
}
