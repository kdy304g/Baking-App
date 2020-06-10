package com.example.bakingapp.fragments;

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

import com.example.bakingapp.R;
import com.example.bakingapp.adapters.IngredientAdapter;
import com.example.bakingapp.databinding.IngredientListFragmentBinding;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientFragment extends Fragment {

    private IngredientListFragmentBinding binding;
    private IngredientAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Ingredient> ingredientList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = IngredientListFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientList = (List<Ingredient>) getArguments().getSerializable(getString(R.string.ingredients_key));
        RecyclerView recyclerView = binding.rvIngredients;
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IngredientAdapter(getActivity(), ingredientList);
        recyclerView.setAdapter(adapter);
    }
}
