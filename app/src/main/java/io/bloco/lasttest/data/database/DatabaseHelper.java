package io.bloco.lasttest.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.Track;
import javax.inject.Inject;
import timber.log.Timber;

@PerApplication public class DatabaseHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "example.db";

  @Inject public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
