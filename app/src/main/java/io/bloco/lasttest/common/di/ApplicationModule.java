package io.bloco.lasttest.common.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import io.bloco.lasttest.AndroidApplication;
import io.bloco.lasttest.data.database.DatabaseHelper;

@Module public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @PerApplication public Context provideApplicationContext() {
    return application;
  }

  @Provides @PerApplication public AndroidApplication.Mode appMode() {
    return application.getMode();
  }

  @Provides @PerApplication public Resources provideResources(Context context) {
    return context.getResources();
  }

  @Provides @PerApplication public SQLiteDatabase sqLiteDatabase(DatabaseHelper databaseHelper) {
    return databaseHelper.getWritableDatabase();
  }

  @Provides @PerApplication public Gson gson() {
    return new Gson();
  }

  @Provides @PerApplication
  public SharedPreferences sharedPreferences(Context context) {
    return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
  }

  @Provides @PerApplication
  public Picasso picasso(Context context) {
    return Picasso.with(context);
  }
}
