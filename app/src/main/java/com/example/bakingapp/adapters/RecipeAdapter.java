package com.example.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private LayoutInflater mInflater;
    private RecipeClickListener mClickListener;

    public RecipeAdapter(Context context, List<Recipe> recipes, RecipeClickListener recipeClickListener){
        this.mInflater = LayoutInflater.from(context);
        this.recipes = recipes;
        this.mClickListener = recipeClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        holder.recipeNameTextView.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView recipeNameTextView;
        RecipeClickListener recipeClickListener;

        public ViewHolder(View itemView, RecipeClickListener recipeClickListener){
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipe_name);
            this.recipeClickListener= recipeClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onRecipeItemClick(recipes.get(getAdapterPosition()));
        }
    }

    public interface RecipeClickListener {
        void onRecipeItemClick(Recipe recipe);
    }
}
