package io.bloco.lasttest.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import io.bloco.lasttest.AndroidApplication;
import io.bloco.lasttest.common.di.ApplicationComponent;
import io.bloco.lasttest.data.model.User;
import io.bloco.lasttest.testing.TestStateManager;
import io.bloco.lasttest.factories.UserFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {

  private UserRepository userRepository;

  @Before
  public void setUp() throws Exception {
    ApplicationComponent applicationComponent =
        ((AndroidApplication) InstrumentationRegistry.getTargetContext()
            .getApplicationContext()).getApplicationComponent();

    userRepository =
        new UserRepository(applicationComponent.sharePreferences(), applicationComponent.gson());
  }

  @After
  public void tearDown() throws Exception {
    new TestStateManager().logout();
  }

  @Test
  public void setAndGet() throws Exception {
    User user = new UserFactory().build();

    userRepository.set(user);
    User retrievedUser = userRepository.get();

    assertThat(retrievedUser.getName(), is(equalTo(user.getName())));
    assertThat(retrievedUser.getRealName(), is(equalTo(user.getRealName())));
  }
}