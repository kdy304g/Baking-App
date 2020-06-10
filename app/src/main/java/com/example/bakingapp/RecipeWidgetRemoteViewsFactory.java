package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> ingredientList;
    private int appWidgetId;

    public RecipeWidgetRemoteViewsFactory(Context applicationContext, Intent intent){
        mContext = applicationContext;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        init();
    }

    public void init(){
        SharedPreferences prefs = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        String ingredients = prefs.getString("ingredients","");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>(){}.getType();
        ingredientList = gson.fromJson(ingredients, listType);
    }

    @Override
    public void onDataSetChanged() {
        init();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        if(ingredientList!=null){
            rv.setTextViewText(R.id.widget_item_textview, ingredientList.get(position).getIngredient());
        }
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
