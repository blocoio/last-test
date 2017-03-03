package io.bloco.lasttest.common.di;

import dagger.Component;

@PerApplication @Component(modules = {
    ApplicationModule.class, ApiModule.class
})
public interface ApplicationComponent {
  ActivityComponent plus(ActivityModule activityModule);
}
