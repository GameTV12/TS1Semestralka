package cz.cvut.fel.myselect;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class TextInputLayoutMatchers {
    static TypeSafeMatcher<View> hasError() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            protected boolean matchesSafely(View item) {
                if (!(item instanceof TextInputLayout))
                    return false;

                TextInputLayout textInputLayout = (TextInputLayout) item;

                CharSequence error = textInputLayout.getError();
                return error != null && error.length() > 0;
            }
        };
    }
}
