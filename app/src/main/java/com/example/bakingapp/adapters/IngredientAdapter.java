package com.example.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.databinding.IngredientItemBinding;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    private LayoutInflater mInflater;
    private IngredientItemBinding binding;

    public IngredientAdapter(Context context, List<Ingredient> ingredients){
        this.mInflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = IngredientItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        holder.ingredientNameTextView.setText(ingredients.get(position).getIngredient());
        holder.measureTextview.setText( ingredients.get(position).getQuantity().toString() + " "+ ingredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ingredientNameTextView;
        public TextView measureTextview;

        public ViewHolder(View itemView){
            super(itemView);
            ingredientNameTextView = binding.ingredientName;
            measureTextview = binding.quantityMeasure;
        }
    }
}
