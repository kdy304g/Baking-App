package com.example.bakingapp;

import android.content.Intent;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bakingapp.model.Step;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ViewActivityTest {
    private Step step;
    private List<Step> stepList;

    @Rule
    public IntentsTestRule<ViewActivity> intentsTestRule =
            new IntentsTestRule<>(ViewActivity.class, false, false);

    @Before
    public void initialize(){
        step = new Step();
        stepList = new ArrayList<>();
        stepList.add(step.mockStep());
        Intent i = new Intent();
        i.putExtra("steps", (Serializable) stepList);
        i.putExtra("step", step.mockStep());
        i.putExtra("mTwoPane",false);
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void testLaunchViewActivity() throws InterruptedException {
        onView(withId(R.id.playerView)).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.view_step_description)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}
