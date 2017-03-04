package io.bloco.lasttest.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import io.bloco.lasttest.common.Preconditions;
import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.ArtistModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

@PerApplication
public class ArtistRepository {

  private final SQLiteDatabase db;

  @Inject public ArtistRepository(SQLiteDatabase db) {
    this.db = db;
  }

  public void save(List<Artist> artists) {
    db.beginTransaction();
    try {
      deleteAll();
      ArtistModel.InsertQuery insertQuery = new Artist.InsertQuery(db);
      for (Artist artist : artists) {
        insertQuery.bind(
            artist.mbid(),
            artist.name(),
            artist.url(),
            artist.imageUrl(),
            artist.playCount()
        );
        long id = insertQuery.program.executeInsert();
        artist.setId(id);
      }
      db.setTransactionSuccessful();
    } catch (Exception exception) {
      Timber.e(exception);
    } finally {
      db.endTransaction();
    }
  }

  public List<Artist> getAll() {
    List<Artist> artists = new ArrayList<>();
    try (Cursor cursor = db.rawQuery(Artist.SELECT_ALL, new String[0])) {
      while (cursor.moveToNext()) {
        artists.add(Artist.FACTORY.select_allMapper().map(cursor));
      }
    }
    return artists;
  }

  public Artist get(long id) {
    SqlDelightStatement query = Artist.FACTORY.select_by_id(id);
    try (Cursor cursor = db.rawQuery(query.statement, query.args)) {
      Preconditions.checkState(cursor.moveToNext(), "Artist not found with id " + id);
      return Artist.FACTORY.select_allMapper().map(cursor);
    }
  }

  public void deleteAll() {
    db.execSQL(Artist.DELETEALL);
  }
}
