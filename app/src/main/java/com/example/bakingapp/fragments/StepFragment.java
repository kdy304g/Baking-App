package com.example.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.IngredientActivity;
import com.example.bakingapp.R;
import com.example.bakingapp.StepActivity;
import com.example.bakingapp.adapters.StepAdapter;
import com.example.bakingapp.databinding.StepListFragmentBinding;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Step;

import java.io.Serializable;
import java.util.List;

public class StepFragment extends Fragment {

    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private StepAdapter mStepAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private StepListFragmentBinding binding;
    private boolean mTwoPane;

    public StepFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StepListFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        stepList = (List<Step>) getArguments().getSerializable(getString(R.string.steps_key));
        ingredientList = (List<Ingredient>) getArguments().getSerializable(getString(R.string.ingredients_key));
        mTwoPane = (boolean) getArguments().getSerializable(getString(R.string.mTwoPane));
        binding.recipeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane){
                    ((StepActivity)getActivity()).onIngredientsClick();
                } else {
                    Intent intent = new Intent (getActivity(), IngredientActivity.class);
                    intent.putExtra(getString(R.string.ingredients_key),(Serializable) ingredientList);
                    getActivity().startActivity(intent);
                }
            }
        });
        RecyclerView recyclerView = binding.rvSteps;
        mLinearLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mStepAdapter = new StepAdapter(getActivity(), stepList, (StepAdapter.StepClickListener) getActivity());
        recyclerView.setAdapter(mStepAdapter);
    }

}
