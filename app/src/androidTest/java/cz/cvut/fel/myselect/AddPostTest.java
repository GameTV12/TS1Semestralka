package cz.cvut.fel.myselect;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddPostTest {
    @Test
    public void addPostInvalidText() {
        // ARRANGE
        ActivityScenario<AddPostActivity> scenario = ActivityScenario.launch(AddPostActivity.class);
        Activity activity = TestUtils.getActivity(scenario);

        // ACT
        Espresso.onView(ViewMatchers.withId(R.id.btnAddPost)).perform(ViewActions.click());

        // ASSERT
        Espresso.onView(ViewMatchers.withText(R.string.required_text)).inRoot(RootMatchers.withDecorView(Matchers.not(activity.getWindow().getDecorView())))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void addPostInvalidTitle() {
        // ARRANGE
        ActivityScenario<AddPostActivity> scenario = ActivityScenario.launch(AddPostActivity.class);
        Activity activity = TestUtils.getActivity(scenario);

        // ACT
        Espresso.onView(ViewMatchers.withId(R.id.txtTextTitleAdd)).perform(ViewActions.typeText(new String(new char[65]).replace("\0", "a")));
        Espresso.onView(ViewMatchers.withId(R.id.btnAddPost)).perform(ViewActions.click());

        // ASSERT
        Espresso.onView(ViewMatchers.withText(R.string.max_size_for_title)).inRoot(RootMatchers.withDecorView(Matchers.not(activity.getWindow().getDecorView())))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
