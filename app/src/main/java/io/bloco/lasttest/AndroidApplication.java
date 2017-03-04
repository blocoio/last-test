package io.bloco.lasttest;

import android.app.Application;
import io.bloco.lasttest.common.di.ApplicationComponent;
import io.bloco.lasttest.common.di.ApplicationModule;
import io.bloco.lasttest.common.di.DaggerApplicationComponent;
import timber.log.Timber;

public class AndroidApplication extends Application {

  private ApplicationComponent applicationComponent;
  private Mode mode;

  @Override public void onCreate() {
    super.onCreate();
    checkTestMode();
    this.initializeInjector();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  public Mode getMode() {
    return mode;
  }

  private void initializeInjector() {
    this.applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  // Test loading a random test class, to check if we're in test mode
  private void checkTestMode() {
    try {
      getClassLoader().loadClass("io.bloco.lasttest.testing.Wait");
      mode = Mode.TEST;
    } catch (final Exception e) {
      mode = Mode.NORMAL;
    }
  }

  public enum Mode {
    NORMAL, TEST
  }
}