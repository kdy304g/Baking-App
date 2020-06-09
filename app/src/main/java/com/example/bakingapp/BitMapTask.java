package com.example.bakingapp;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;

import java.util.HashMap;

public class BitMapTask extends AsyncTask<Void, Void, Bitmap> {

    public interface OnBitmapLoaded{
        void loadBitmap(Bitmap bitmap);
    }

    private OnBitmapLoaded bitmapLoaded;
    private String url;

    public BitMapTask(String url){
        this.url = url;
    }

    public BitMapTask setBitMapLoaded(OnBitmapLoaded bitMapLoaded){
        this.bitmapLoaded = bitMapLoaded;
        return this;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = null;
        try {
            bitmap = retrieveVideoFrameFromVideo(url);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        if(bitmapLoaded != null) bitmapLoaded.loadBitmap(result);
    }

    public static Bitmap retrieveVideoFrameFromVideo(String videoPath)throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mmr = null;
        try
        {
            mmr = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mmr.setDataSource(videoPath, new HashMap<String, String>());
            else
                mmr.setDataSource(videoPath);
            bitmap = mmr.getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)"+ e.getMessage());
        }
        finally
        {
            if (mmr != null)
            {
                mmr.release();
            }
        }
        return bitmap;
    }
}