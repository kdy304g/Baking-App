package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.adapters.RecipeAdapter;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.widget.RecipeAppWidgetProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        final Intent intent = new Intent(MainActivity.this, StepActivity.class);
        intent.putExtra(getString(R.string.steps_key), (Serializable) recipe.getSteps());
        intent.putExtra(getString(R.string.ingredients_key), (Serializable) recipe.getIngredients());
        MainActivity.this.startActivity(intent);

        updateWidget(this, recipe);
    }

    public static void updateWidget(Context context, Recipe recipe){
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.data), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String ingredients = gson.toJson(recipe.getIngredients(), new TypeToken<ArrayList<Ingredient>>() {}.getType());
        editor.putString(context.getString(R.string.ingredients_key),ingredients);
        editor.commit();

        Intent intent = new Intent(context, RecipeAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra(context.getString(R.string.recipeName_key),recipe.getName());
        context.sendBroadcast(intent);
    }
}
