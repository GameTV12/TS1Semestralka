package cz.cvut.fel.myselect;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import java.util.concurrent.atomic.AtomicReference;

public class TestUtils {
    public static <T extends Activity> T getActivity(ActivityScenario<T> activityScenario) {
        AtomicReference<T> activityRef = new AtomicReference<>();
        activityScenario.onActivity(activityRef::set);
        return activityRef.get();
    }
}
