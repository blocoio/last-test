package io.bloco.lasttest.data;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.model.User;
import javax.inject.Inject;

@PerApplication
public class UserRepository {

  private static final String KEY_USER = "user";

  private final SharedPreferences sharedPreferences;
  private final Gson gson;

  @Inject public UserRepository(SharedPreferences sharedPreferences, Gson gson) {
    this.sharedPreferences = sharedPreferences;
    this.gson = gson;
  }

  public User get() {
    String userJson = sharedPreferences.getString(KEY_USER, null);
    if (userJson == null) {
      return null;
    }
    return gson.fromJson(userJson, User.class);
  }

  public void set(User user) {
    String userJson = gson.toJson(user);
    sharedPreferences.edit().putString(KEY_USER, userJson).apply();
  }

  public void delete() {
    sharedPreferences.edit().remove(KEY_USER).apply();
  }
}
