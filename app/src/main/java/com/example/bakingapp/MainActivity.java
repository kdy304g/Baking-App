package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.adapters.RecipeAdapter;
import com.example.bakingapp.model.Recipe;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        final Intent intent = new Intent(MainActivity.this, StepActivity.class);
        intent.putExtra("steps", (Serializable) recipe.getSteps());
        intent.putExtra("ingredients", (Serializable) recipe.getIngredients());
        MainActivity.this.startActivity(intent);
    }
}
