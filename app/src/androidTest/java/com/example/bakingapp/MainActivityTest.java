package com.example.bakingapp;

import android.app.Activity;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.bakingapp.fragments.RecipeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bakingapp.utils.TestUtils.withRecyclerView;
import static org.hamcrest.core.IsNot.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
    }

    @Before
    public void registerIdlingResource() {
        RecipeFragment recipeFragment = (RecipeFragment) intentsTestRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.recipe_list_fragment);
        mIdlingResource = recipeFragment.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void testLaunchMainActivity(){
        onView(withRecyclerView(R.id.rvRecipes)
                .atPositionOnView(0, R.id.recipe_name))
                .check(matches(withText("Nutella Pie")));
    }

    @Test
    public void clickRecipeItem_OpensStepActivity() {
        onView(withId(R.id.rvRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(StepActivity.class.getName()));

        onView(withRecyclerView(R.id.rvSteps)
                .atPositionOnView(2, R.id.step_description))
                .check(matches(withText("Prep the cookie crust.")));
    }

    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}
