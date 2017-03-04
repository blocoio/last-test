package io.bloco.lasttest.ui.login;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import io.bloco.lasttest.data.model.User;
import io.bloco.lasttest.testing.TestStateManager;
import io.bloco.lasttest.ui.main.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<MainActivity>(MainActivity.class, true, false) {
        @Override protected Intent getActivityIntent() {
          return super.getActivityIntent();
        }
      };

  private User user;

  @Before
  public void setUp() throws Exception {
    user = new TestStateManager().login();
    activityTestRule.launchActivity(null);
  }

  @After
  public void tearDown() throws Exception {
    new TestStateManager().logout();
  }

  @Test
  public void userName() throws Exception {
    onView(withText(user.getBestName())).check(matches(isDisplayed()));
  }
}
