package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
        updateWidget(this, recipe);
    }

    public static void updateWidget(Context context, Recipe recipe){
        Intent intent = new Intent(context, RecipeAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra("ingredients", (Serializable) recipe.getIngredients());
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }
}
