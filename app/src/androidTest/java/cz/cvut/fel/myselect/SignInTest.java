package cz.cvut.fel.myselect;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class SignInTest {

    @Test
    public void signInSucceeded() {
        ActivityScenario.launch(AuthActivity.class);
        // ARRANGE
        Espresso.onView(ViewMatchers.withId(R.id.txtEmailSignIn)).perform(ViewActions.typeText("family@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.typeText("123456"));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.closeSoftKeyboard());

        // ACT
        Intents.init();
        Espresso.onView(ViewMatchers.withId(R.id.btnForLogin_LoginPage)).perform(ViewActions.click());

        // ASSERT
        Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void signInFailed() {
        // ARRANGE
        ActivityScenario.launch(AuthActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.txtEmailSignIn)).perform(ViewActions.typeText("family@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.typeText("invalid_password113337"));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.closeSoftKeyboard());

        // ACT
        Espresso.onView(ViewMatchers.withId(R.id.btnForLogin_LoginPage)).perform(ViewActions.click());

        // ASSERT
        Espresso.onView(ViewMatchers.withId(R.id.inputLogin_LoginPage)).check(ViewAssertions.matches(isDisplayed()));
    }


    @Test
    public void signInInvalidEmail() {
        // ARRANGE
        ActivityScenario.launch(AuthActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.txtEmailSignIn)).perform(ViewActions.typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.typeText("123456"));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.closeSoftKeyboard());

        // ACT
        Espresso.onView(ViewMatchers.withId(R.id.btnForLogin_LoginPage)).perform(ViewActions.click());

        // ASSERT
        Espresso.onView(ViewMatchers.withId(R.id.inputLogin_LoginPage)).check(ViewAssertions.matches(TextInputLayoutMatchers.hasError()));
    }

    @Test
    public void signInInvalidPassword() {
        // ARRANGE
        ActivityScenario.launch(AuthActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.txtEmailSignIn)).perform(ViewActions.typeText("family@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.txtLayoutPasswordSignIn)).perform(ViewActions.closeSoftKeyboard());

        // ACT
        Espresso.onView(ViewMatchers.withId(R.id.btnForLogin_LoginPage)).perform(ViewActions.click());

        // ASSERT
        Espresso.onView(ViewMatchers.withId(R.id.inputPass_LoginPage)).check(ViewAssertions.matches(TextInputLayoutMatchers.hasError()));
    }
}
