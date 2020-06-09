package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.bakingapp.model.Ingredient;

import java.io.Serializable;
import java.util.List;

public class RecipeAppWidgetProvider extends AppWidgetProvider {
    private List<Ingredient> ingredients;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("onReceive", "onReceive()" + intent.getAction());
        if(intent.getSerializableExtra("ingredients") != null){
            ingredients = (List<Ingredient>) intent.getSerializableExtra("ingredients");
            Log.d("ingredient",ingredients.get(0).getIngredient());
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeAppWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn),R.id.widget_list_view);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("onUpdate","called with");
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
            intent.putExtra("ingredients", (Serializable) ingredients);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
            views.setRemoteAdapter(R.id.widget_list_view, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
