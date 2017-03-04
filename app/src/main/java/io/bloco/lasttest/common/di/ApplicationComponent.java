package io.bloco.lasttest.common.di;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import dagger.Component;
import io.bloco.lasttest.data.ArtistRepository;
import io.bloco.lasttest.data.UserRepository;
import io.bloco.lasttest.domain.Logout;

@PerApplication @Component(modules = {
    ApplicationModule.class, ApiModule.class
})
public interface ApplicationComponent {
  ActivityComponent plus(ActivityModule activityModule);

  UserRepository userRepository();

  ArtistRepository artistRepository();

  SharedPreferences sharePreferences();

  Logout logout();

  Gson gson();

  SQLiteDatabase db();
}
