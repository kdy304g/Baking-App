/**
 * Created by dannyroa on 5/10/15.
 * source: https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
 */
package com.example.bakingapp.utils;

public class TestUtils {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    }

}
