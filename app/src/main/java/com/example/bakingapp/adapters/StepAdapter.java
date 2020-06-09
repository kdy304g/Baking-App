package com.example.bakingapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.BitMapTask;
import com.example.bakingapp.R;
import com.example.bakingapp.databinding.StepItemBinding;
import com.example.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> steps;
    private Step step;
    private LayoutInflater mInflater;
    private StepClickListener mClickListener;
    private StepItemBinding binding;
    final int THUMBNAIL_SIZE = 64;
    private Context context;
    private Bitmap icon;

    public StepAdapter(Context context, List<Step> steps, StepClickListener stepClickListener){
        this.mInflater = LayoutInflater.from(context);
        this.steps = steps;
        this.mClickListener = stepClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = StepItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(binding.getRoot(), mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepAdapter.ViewHolder holder, int position) {
        holder.stepDescriptionTextView.setText(steps.get(position).getShortDescription());
        step = steps.get(position);
        if (step.getThumbnailURL() == "" && step.getVideoURL() == ""){
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pngwing_com);
        }else if(step.getVideoURL() == "") {
            new BitMapTask(step.getThumbnailURL()).setBitMapLoaded(new BitMapTask.OnBitmapLoaded() {
                @Override
                public void loadBitmap(Bitmap bitmap) {
                    if(bitmap != null){
                        icon = bitmap;
                        icon = ThumbnailUtils.extractThumbnail(icon, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
                        holder.stepThumbnail.setImageBitmap(icon);
                    }
                }
            }).execute();
        } else{
            new BitMapTask(step.getVideoURL()).setBitMapLoaded(new BitMapTask.OnBitmapLoaded() {
                @Override
                public void loadBitmap(Bitmap bitmap) {
                    if(bitmap != null){
                        icon = bitmap;
                        icon = ThumbnailUtils.extractThumbnail(icon, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
                        holder.stepThumbnail.setImageBitmap(icon);
                    }
                }
            }).execute();
        }
        icon = ThumbnailUtils.extractThumbnail(icon, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        holder.stepThumbnail.setImageBitmap(icon);
    }

    @Override
    public int getItemCount() {
        if (steps!= null){
            return steps.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView stepDescriptionTextView;
        public ImageView stepThumbnail;
        StepClickListener stepClickListener;

        public ViewHolder(View itemView, StepClickListener stepClickListener){
            super(itemView);
            stepDescriptionTextView = binding.stepDescription;
            stepThumbnail = binding.stepThumbnail;
            this.stepClickListener= stepClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onStepItemClick(steps.get(getAdapterPosition()));
        }
    }

    public interface StepClickListener {
        void onStepItemClick(Step step);
    }
}



