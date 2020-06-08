package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.fragments.IngredientFragment;
import com.example.bakingapp.model.Ingredient;

import java.io.Serializable;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private List<Ingredient> ingredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        Intent i = getIntent();
        ingredients = (List<Ingredient>) i.getSerializableExtra("ingredients");
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredients", (Serializable) ingredients);
        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.ingredient_list_fragment, ingredientFragment).commit();
    }
}
