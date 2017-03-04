package io.bloco.lasttest.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import io.bloco.lasttest.AndroidApplication;
import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.Track;
import javax.inject.Inject;
import timber.log.Timber;

@PerApplication public class DatabaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "example.db";
  private static final String DATABASE_NAME_TEST = "example_test.db";

  @Inject public DatabaseHelper(Context context, AndroidApplication.Mode mode) {
    super(context, getDatabaseName(mode), null, DATABASE_VERSION);
  }

  @NonNull private static String getDatabaseName(AndroidApplication.Mode mode) {
    if (mode == AndroidApplication.Mode.NORMAL) {
      return DATABASE_NAME;
    } else {
      return DATABASE_NAME_TEST;
    }
  }

  public void onCreate(SQLiteDatabase db) {
    db.execSQL(Artist.CREATE_TABLE);
    db.execSQL(Track.CREATE_TABLE);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    dropTables(db);
    onCreate(db);
  }

  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }

  private void dropTables(SQLiteDatabase db) {
    try {
      db.execSQL("DROP TABLE IF EXISTS " + Artist.TABLE_NAME);
      db.execSQL("DROP TABLE IF EXISTS " + Track.TABLE_NAME);
    } catch (SQLiteException exception) {
      Timber.w(exception);
    }
  }
}
