package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;

import java.util.List;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe recipe;
    private List<Ingredient> ingredients;

    public RecipeWidgetRemoteViewsFactory(Context applicationContext, Intent intent){
        mContext = applicationContext;
        ingredients = (List<Ingredient>) intent.getSerializableExtra("ingredients");
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.widget_item, ingredients.get(position).getIngredient());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
