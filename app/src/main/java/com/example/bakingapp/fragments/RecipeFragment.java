package com.example.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.bakingapp.R;
import com.example.bakingapp.adapters.RecipeAdapter;
import com.example.bakingapp.databinding.RecipeListFragmentBinding;
import com.example.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private List<Recipe> myRecipeList;
    private RecipeAdapter mRecipeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    GridLayoutManager mGridLayoutManager;
    private RecipeListFragmentBinding binding;
    private final static String CANT_FETCH_DATA = "unable to fetch data: ";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public RecipeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecipeListFragmentBinding.inflate(inflater, container,false);
        View view = binding.getRoot();
        getIdlingResource();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myRecipeList = new ArrayList<>();
        RecyclerView recyclerView = binding.rvRecipes;
        mGridLayoutManager = new GridLayoutManager(getActivity(), calculateNoOfColumns(getActivity()));
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
/**
 *      Used below code block to test idling resource working properly.
 *      Previously, tests passed no matter what because JsonArrayRequest was too fast.
 */

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fetch();
//            }
//        }, 100);

        fetch();

        mRecipeAdapter = new RecipeAdapter(getActivity(), myRecipeList, (RecipeAdapter.RecipeClickListener) getActivity());
        recyclerView.setAdapter(mRecipeAdapter);
    }

    private void fetch () {

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        JsonArrayRequest request = new JsonArrayRequest(getString(R.string.url),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Recipe>>(){}.getType();
                        List<Recipe> recipeList = gson.fromJson(response.toString(), listType);
                        myRecipeList.addAll(recipeList);
                        mRecipeAdapter.notifyDataSetChanged();
                        if (mIdlingResource != null) {
                            mIdlingResource.setIdleState(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), CANT_FETCH_DATA + error.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mIdlingResource != null) {
                            mIdlingResource.setIdleState(true);
                        }
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 400;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

}
