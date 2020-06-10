package com.example.bakingapp.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.example.bakingapp.databinding.ViewStepFragmentBinding;
import com.example.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class ViewFragment extends Fragment {

    private Step step;
    private List<Step> steps;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private ViewStepFragmentBinding binding;
    private boolean mTwoPane;


    public ViewFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ViewStepFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        step = (Step) getArguments().getSerializable(getString(R.string.step_key));
        steps = (List<Step>) getArguments().getSerializable(getString(R.string.steps_key));
        mTwoPane = getArguments().getBoolean(getString(R.string.mTwoPane));
        Button previous = binding.previous;
        Button next = binding.next;
        if(mTwoPane){
            previous.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        } else {
            if (step.getId() == 0){
                previous.setVisibility(View.GONE);
            } else if (step.getId() == steps.size()-1){
                next.setVisibility(View.GONE);
            }
        }
        TextView textView = binding.viewStepDescription;
        textView.setText(step.getDescription());
        mPlayerView = binding.playerView;
        if (step.getVideoURL() == "" ){
            mPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=600;
            mPlayerView.setLayoutParams(params);
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }

    private void initializePlayer(){
        if (mExoPlayer == null && step.getVideoURL() != "") {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getActivity());
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), getString(R.string.app_name)));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(step.getVideoURL()));
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        initializePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
