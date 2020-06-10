package com.example.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

public class RecipeAppWidgetProvider extends AppWidgetProvider {
    private List<Ingredient> ingredients;
    private String recipeName;

    @Override
    public void onReceive(Context context, Intent intent) {
        recipeName = intent.getStringExtra(context.getString(R.string.recipeName_key));
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
            views.setTextViewText(R.id.widget_textview,recipeName);
            Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_list_view, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
