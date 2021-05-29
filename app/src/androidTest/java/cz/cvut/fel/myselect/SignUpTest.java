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

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Test
    public void signUpInvalidNickname() {
        ActivityScenario.launch(AuthActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.txtSignUp)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.inputLoginText_SignUpPage)).perform(ViewActions.typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.inputLoginText_SignUpPage)).perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnForLogin_SignUpPage)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.inputLogin_SignUpPage)).check(ViewAssertions.matches(TextInputLayoutMatchers.hasError()));
    }

    @Test
    public void signUpPasswordMismatch() {
        ActivityScenario.launch(AuthActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.txtSignUp)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.inputLoginText_SignUpPage)).perform(ViewActions.typeText("asdasdasdasdasd"));
        Espresso.onView(ViewMatchers.withId(R.id.inputEmailText_SignUpPage)).perform(ViewActions.typeText("asdasdasdasdasd@o.com"));
        Espresso.onView(ViewMatchers.withId(R.id.inputPasswordText_SignUpPage)).perform(ViewActions.typeText("123456"));
        Espresso.onView(ViewMatchers.withId(R.id.inputPasswordText_SignUpPage)).perform(ViewActions.typeText("123457__"));
        Espresso.onView(ViewMatchers.withId(R.id.inputPasswordRepeatText_SignUpPage)).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.inputPasswordRepeatText_SignUpPage)).perform(ViewActions.typeText("123457__"));
        Espresso.onView(ViewMatchers.withId(R.id.inputPasswordRepeatText_SignUpPage)).perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnForLogin_SignUpPage)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.inputRepeatPass_SignUpPage)).check(ViewAssertions.matches(TextInputLayoutMatchers.hasError()));
    }
}
