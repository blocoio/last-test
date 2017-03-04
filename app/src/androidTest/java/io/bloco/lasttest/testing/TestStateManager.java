package io.bloco.lasttest.testing;

import android.support.test.InstrumentationRegistry;
import io.bloco.lasttest.AndroidApplication;
import io.bloco.lasttest.common.di.ApplicationComponent;
import io.bloco.lasttest.data.model.User;
import io.bloco.lasttest.factories.UserFactory;

public class TestStateManager {

  private final ApplicationComponent applicationComponent;

  public TestStateManager() {
    applicationComponent = ((AndroidApplication) InstrumentationRegistry.getTargetContext()
        .getApplicationContext()).getApplicationComponent();
  }

  public User login() {
    User user = new UserFactory().build();
    applicationComponent.userRepository().set(user);
    return user;
  }

  public void logout() {
    applicationComponent.logout().logout();
  }
}
