package com.example.bakingapp;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.example.bakingapp.model.Ingredient;

import java.util.List;

public class RecipeWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
