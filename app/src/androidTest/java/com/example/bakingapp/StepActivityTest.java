package com.example.bakingapp;

import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bakingapp.model.Step;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bakingapp.utils.TestUtils.withRecyclerView;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    private Step step;
    private List<Step> stepList;

    @Rule
    public IntentsTestRule<StepActivity> intentsTestRule =
            new IntentsTestRule<>(StepActivity.class, false, false);

    @Before
    public void initialize(){
        step = new Step();
        stepList = new ArrayList<>();
        stepList.add(step.mockStep());
        Intent i = new Intent();
        i.putExtra("steps", (Serializable) stepList);
        intentsTestRule.launchActivity(i);
    }

    @Test
    public void testLaunchStepActivity(){
        onView(withRecyclerView(R.id.rvSteps)
                .atPositionOnView(0, R.id.step_description))
                .check(matches(withText("dummy short description")));
    }

    @Test
    public void clickStepTem_OpensViewActivity(){
        onView(withId(R.id.rvSteps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(ViewActivity.class.getName()));
        onView(withId(R.id.view_step_description)).check(matches(withText("dummy description")));
    }
}
