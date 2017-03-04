package io.bloco.lasttest.ui.login;

import android.support.annotation.StringRes;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import io.bloco.lasttest.R;
import io.bloco.lasttest.ui.testing.Wait;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

  @Rule
  public ActivityTestRule<LoginActivity> activityTestRule =
      new ActivityTestRule<>(LoginActivity.class);

  @Test
  public void nameMissing() throws Exception {
    onNameField().perform(typeText(""));
    loginSubmit();

    new Wait(new Wait.Condition() {
      @Override public boolean check() {
        try {
          checkErrorMessage(R.string.login_user_missing);
          return true;
        } catch (Exception exception) {
          return false;
        }
      }
    }).waitForIt();
  }

  @Test
  public void nameTooShort() throws Exception {
    onNameField().perform(typeText("abc"), closeSoftKeyboard());
    loginSubmit();
    checkErrorMessage(R.string.login_user_too_short);
  }

  @Test
  public void loginSuccessful() throws Exception {
    onNameField().perform(typeText("neon_prannock"), closeSoftKeyboard());
    loginSubmit();
    onView(withText(R.string.login_submitting)).check(matches(isDisplayed()));
    Thread.sleep(1000);
    onView(withText(R.string.app_name)).check(matches(isDisplayed()));
  }

  // Helpers

  private ViewInteraction onNameField() {
    return onView(withId(R.id.login_user));
  }

  private void loginSubmit() {
    onView(withText(R.string.login_submit)).perform(click());
  }

  private void checkErrorMessage(@StringRes int stringRes) {
    onView(withText(stringRes)).check(matches(isDisplayed()));
  }
}
