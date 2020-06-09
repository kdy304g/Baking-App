package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe recipe;
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private int appWidgetId;

    public RecipeWidgetRemoteViewsFactory(Context applicationContext, Intent intent){
        mContext = applicationContext;
        ingredients = (List<Ingredient>) intent.getSerializableExtra("ingredients");
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.d("AppWidgetId",String.valueOf(appWidgetId));

        Log.d("remoteViewsFactory","constructor "+ingredients);
        if(ingredients!=null){
            Log.d("RemoteViewsFactory","constructor called"+ingredients.get(0).getIngredient());
        }
    }

    @Override
    public void onCreate() {
        Log.d("remoteViewsFactory","onCreate");
        if(ingredients != null){
            Log.d("onCreate of factory",ingredients.get(0).getIngredient());
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients==null){
            return 0;
        }
        return ingredients.size();
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
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
